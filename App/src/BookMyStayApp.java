import java.util.*;

// Represents a completed, confirmed booking
class ConfirmedBooking {
    String guestName;
    String roomType;
    String roomId;
    double totalCost;

    public ConfirmedBooking(String guestName, String roomType, String roomId, double totalCost) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return String.format("Guest: %-10s | Room: %-10s | ID: %-6s | Total: $%.2f",
                guestName, roomType, roomId, totalCost);
    }
}

public class BookMyStayApp {
    // Inventory and Allocation (from UC6)
    private Map<String, Integer> inventory = new HashMap<>();
    private Map<String, Set<String>> allocatedRooms = new HashMap<>();

    // UC8: Historical Tracking (Persistence-oriented mindset)
    private List<ConfirmedBooking> bookingHistory = new ArrayList<>();

    public BookMyStayApp() {
        inventory.put("Deluxe", 3);
        inventory.put("Suite", 2);
        allocatedRooms.put("Deluxe", new HashSet<>());
        allocatedRooms.put("Suite", new HashSet<>());
    }

    /**
     * UC8: Confirmation & History Storage
     * This simulates the moment a booking moves from 'Pending' to 'History'
     */
    public void confirmAndRecord(String guest, String type, double cost) {
        if (inventory.containsKey(type) && inventory.get(type) > 0) {
            // Allocation logic (UC6)
            String roomId = type.substring(0, 2).toUpperCase() + "-" + (101 + allocatedRooms.get(type).size());
            allocatedRooms.get(type).add(roomId);
            inventory.put(type, inventory.get(type) - 1);

            // History Recording (UC8)
            ConfirmedBooking record = new ConfirmedBooking(guest, type, roomId, cost);
            bookingHistory.add(record);

            System.out.println("[RECORDED] Booking confirmed for " + guest);
        } else {
            System.out.println("[FAILED] Could not record booking for " + guest + " (No Inventory)");
        }
    }

    /**
     * UC8: Reporting Service
     * Generates a summary without modifying the underlying data.
     */
    public void generateAdminReport() {
        System.out.println("\n============================================");
        System.out.println("       OFFICIAL BOOKING HISTORY REPORT      ");
        System.out.println("============================================");

        if (bookingHistory.isEmpty()) {
            System.out.println("No records found.");
        } else {
            double totalRevenue = 0;
            for (ConfirmedBooking b : bookingHistory) {
                System.out.println(b);
                totalRevenue += b.totalCost;
            }
            System.out.println("--------------------------------------------");
            System.out.println("Total Bookings: " + bookingHistory.size());
            System.out.format("Total Revenue:  $%.2f\n", totalRevenue);
        }
        System.out.println("============================================\n");
    }

    public static void main(String[] args) {
        BookMyStayApp app = new BookMyStayApp();

        // Simulating confirmed transactions
        app.confirmAndRecord("Alice", "Deluxe", 225.0);
        app.confirmAndRecord("Bob", "Suite", 450.0);
        app.confirmAndRecord("Charlie", "Deluxe", 210.0);

        // Admin requests the report
        app.generateAdminReport();
    }
}