import java.io.*;
import java.util.*;

/**
 * ConfirmedBooking must implement Serializable to be saved to a file.
 */
class ConfirmedBooking implements Serializable {
    private static final long serialVersionUID = 1L;
    String guestName;
    String roomId;

    public ConfirmedBooking(String guestName, String roomId) {
        this.guestName = guestName;
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "Guest: " + guestName + " | Room: " + roomId;
    }
}

public class BookMyStayApp {
    private static final String DATA_FILE = "system_state.ser";

    // System State to be persisted
    private Map<String, Integer> inventory = new HashMap<>();
    private List<ConfirmedBooking> bookingHistory = new ArrayList<>();

    public BookMyStayApp() {
        // Default values if no file exists
        inventory.put("Deluxe", 5);
    }

    /**
     * UC12: Serialization - Saving state to a file
     */
    public void saveState() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(inventory);
            oos.writeObject(bookingHistory);
            System.out.println(">>> System state saved successfully to " + DATA_FILE);
        } catch (IOException e) {
            System.err.println("Error saving state: " + e.getMessage());
        }
    }

    /**
     * UC12: Deserialization - Restoring state from a file
     */
    @SuppressWarnings("unchecked")
    public void loadState() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            System.out.println("No previous state found. Starting fresh.");
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            inventory = (Map<String, Integer>) ois.readObject();
            bookingHistory = (List<ConfirmedBooking>) ois.readObject();
            System.out.println(">>> System state restored successfully from " + DATA_FILE);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Recovery failed. Initializing default state. Error: " + e.getMessage());
        }
    }

    public void addBooking(String guest, String roomId) {
        bookingHistory.add(new ConfirmedBooking(guest, roomId));
        inventory.put("Deluxe", inventory.get("Deluxe") - 1);
    }

    public void displayStatus() {
        System.out.println("Current Inventory: " + inventory);
        System.out.println("History: " + bookingHistory);
    }

    public static void main(String[] args) {
        BookMyStayApp app = new BookMyStayApp();

        // 1. Attempt to recover from a previous crash/restart
        app.loadState();
        app.displayStatus();

        // 2. Perform a new transaction if the app was empty
        if (app.bookingHistory.isEmpty()) {
            System.out.println("\nSimulating new booking...");
            app.addBooking("Alice", "DX-101");
            app.displayStatus();

            // 3. Persist the state before shutting down
            app.saveState();
            System.out.println("Application shutting down. Run again to see recovered data!");
        } else {
            System.out.println("\nData was recovered! The system successfully 'remembered' Alice.");
            // Optional: clear file to reset demo
            // new File(DATA_FILE).delete();
        }
    }
}