import java.util.*;

// Custom Exception for domain-specific errors
class BookingException extends Exception {
    public BookingException(String message) {
        super(message);
    }
}

public class BookMyStayApp {
    private Map<String, Integer> inventory = new HashMap<>();

    public BookMyStayApp() {
        // Note: Inventory keys are case-sensitive
        inventory.put("Deluxe", 1);
        inventory.put("Suite", 2);
    }

    /**
     * UC9: Validation Logic
     * Ensures input is valid BEFORE any system state changes occur.
     */
    public void validateRequest(String roomType) throws BookingException {
        // 1. Check for null or empty input
        if (roomType == null || roomType.trim().isEmpty()) {
            throw new BookingException("Validation Error: Room type cannot be empty.");
        }

        // 2. Check for existence (Case-Sensitive check)
        if (!inventory.containsKey(roomType)) {
            throw new BookingException("Validation Error: Room type '" + roomType + "' does not exist (Check case sensitivity).");
        }

        // 3. Check for exhaustion
        if (inventory.get(roomType) <= 0) {
            throw new BookingException("Availability Error: No rooms available for type '" + roomType + "'.");
        }
    }

    public void processBooking(String guestName, String roomType) {
        System.out.println("Processing request for " + guestName + " [" + roomType + "]...");
        try {
            // Guarding the system state
            validateRequest(roomType);

            // If we reach here, input is valid - proceed with allocation
            inventory.put(roomType, inventory.get(roomType) - 1);
            System.out.println("[SUCCESS] Booking confirmed for " + guestName);

        } catch (BookingException e) {
            // Graceful Failure Handling
            System.err.println("[REJECTED] " + e.getMessage());
        } catch (Exception e) {
            System.err.println("[CRITICAL ERROR] An unexpected error occurred: " + e.getMessage());
        } finally {
            System.out.println("System remains stable and ready for next request.\n");
        }
    }

    public static void main(String[] args) {
        BookMyStayApp app = new BookMyStayApp();

        // Scenario 1: Valid request
        app.processBooking("Alice", "Deluxe");

        // Scenario 2: Invalid Room Type (Case Sensitivity)
        app.processBooking("Bob", "deluxe");

        // Scenario 3: Room Type does not exist
        app.processBooking("Charlie", "Penthouse");

        // Scenario 4: Inventory Exhausted
        app.processBooking("Diana", "Deluxe");

        // Scenario 5: Invalid Input
        app.processBooking("Eve", "");
    }
}