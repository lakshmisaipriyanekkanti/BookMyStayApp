import java.util.*;

// Helper class for Reservation intent
class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return guestName + " (" + roomType + ")";
    }
}

// Class name now matches the filename 'BookMyStayApp.java'
public class BookMyStayApp {

    private Map<String, Integer> inventory = new HashMap<>();
    private Queue<Reservation> requestQueue = new LinkedList<>();

    // Set structure to prevent double-booking (Use Case 6)
    private Map<String, Set<String>> allocatedRooms = new HashMap<>();

    public BookMyStayApp() {
        // 1. Initialize Inventory
        inventory.put("Deluxe", 2);
        inventory.put("Suite", 1);

        // 2. Initialize Sets for Room ID tracking
        allocatedRooms.put("Deluxe", new HashSet<>());
        allocatedRooms.put("Suite", new HashSet<>());
    }

    // UC5: Adding requests to the FIFO queue
    public void addBookingRequest(String name, String type) {
        requestQueue.add(new Reservation(name, type));
        System.out.println("Queued: " + name + " for " + type);
    }

    // UC6: Processing the queue and allocating unique rooms
    public void processAllocations() {
        System.out.println("\n--- Processing Room Allocations ---");

        while (!requestQueue.isEmpty()) {
            Reservation request = requestQueue.poll(); // FIFO Dequeue
            String type = request.roomType;

            // Check inventory consistency
            if (inventory.containsKey(type) && inventory.get(type) > 0) {

                // Generate a Unique Room ID
                String roomId = type.substring(0, 2).toUpperCase() + "-" + (101 + allocatedRooms.get(type).size());

                // Uniqueness Enforcement using Set
                if (allocatedRooms.get(type).add(roomId)) {
                    // Immediate Inventory Sync
                    inventory.put(type, inventory.get(type) - 1);
                    System.out.println("[SUCCESS] " + request.guestName + " assigned to " + roomId);
                }
            } else {
                System.out.println("[DENIED] No " + type + " rooms left for " + request.guestName);
            }
        }
        System.out.println("--- Allocation Complete ---\n");
    }

    public void displayFinalStatus() {
        System.out.println("Remaining Inventory: " + inventory);
        System.out.println("Allocated Room IDs: " + allocatedRooms);
    }

    public static void main(String[] args) {
        BookMyStayApp app = new BookMyStayApp();

        // Simulating incoming requests
        app.addBookingRequest("Alice", "Deluxe");
        app.addBookingRequest("Bob", "Suite");
        app.addBookingRequest("Charlie", "Deluxe");
        app.addBookingRequest("Diana", "Deluxe"); // Only 2 Deluxe available, Diana should be denied

        // Process based on First-Come-First-Served
        app.processAllocations();

        // Final state check
        app.displayFinalStatus();
    }
}
