import java.util.*;

public class BookMyStayApp {
    // Current Inventory
    private Map<String, Integer> inventory = new HashMap<>();

    // Tracking active allocations (ReservationID -> RoomID)
    private Map<String, String> activeBookings = new HashMap<>();

    // UC10: Stack for Rollback (LIFO) - Tracks recently released room IDs
    private Stack<String> releasedRoomStack = new Stack<>();

    public BookMyStayApp() {
        inventory.put("Deluxe", 5);
        inventory.put("Suite", 2);
    }

    /**
     * Helper to simulate a booking (UC6/UC8 logic)
     */
    public void confirmBooking(String resId, String type, String roomId) {
        activeBookings.put(resId, roomId);
        inventory.put(type, inventory.get(type) - 1);
        System.out.println("[CONFIRMED] " + resId + " assigned Room: " + roomId);
    }

    /**
     * UC10: Cancellation & Inventory Rollback
     * This method ensures the system state is reverted safely.
     */
    public void cancelBooking(String resId, String roomType) {
        System.out.println("\nInitiating cancellation for: " + resId + "...");

        // 1. Validation: Ensure reservation exists
        if (!activeBookings.containsKey(resId)) {
            System.err.println("[ERROR] Cancellation Failed: Reservation ID " + resId + " not found.");
            return;
        }

        // 2. Controlled Mutation: Get the Room ID and remove from active list
        String roomId = activeBookings.remove(resId);

        // 3. Stack Rollback: Push released room ID to the stack
        releasedRoomStack.push(roomId);

        // 4. Inventory Restoration: Increment count immediately
        inventory.put(roomType, inventory.get(roomType) + 1);

        System.out.println("[SUCCESS] Cancellation Complete.");
        System.out.println("Room " + roomId + " has been added to the Rollback Stack.");
        System.out.println("Inventory for " + roomType + " restored to: " + inventory.get(roomType));
    }

    public void displaySystemState() {
        System.out.println("\n--- Final System State ---");
        System.out.println("Current Inventory: " + inventory);
        System.out.println("Active Bookings: " + activeBookings);
        System.out.println("Rollback Stack (Released Rooms): " + releasedRoomStack);
        System.out.println("--------------------------\n");
    }

    public static void main(String[] args) {
        BookMyStayApp app = new BookMyStayApp();

        // Step 1: Simulate active state
        app.confirmBooking("RES-101", "Deluxe", "DX-101");
        app.confirmBooking("RES-102", "Deluxe", "DX-102");
        app.confirmBooking("RES-103", "Suite", "ST-201");

        // Step 2: Valid Cancellation
        app.cancelBooking("RES-102", "Deluxe");

        // Step 3: Invalid Cancellation (Already removed or never existed)
        app.cancelBooking("RES-999", "Suite");

        // Step 4: Final State Check
        app.displaySystemState();
    }
}