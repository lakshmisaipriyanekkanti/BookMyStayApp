import java.util.*;

// 1. New Entity for this Use Case
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "Reservation [Guest: " + guestName + ", Room Type: " + roomType + "]";
    }
}

// 2. New Service to handle the Queue
class BookingService {
    // Queue to hold booking requests in arrival order (FIFO)
    private Queue<Reservation> requestQueue = new LinkedList<>();

    public void addBookingRequest(String guestName, String roomType) {
        Reservation request = new Reservation(guestName, roomType);
        requestQueue.add(request);
        System.out.println(">>> Added to Queue: " + guestName + " requested " + roomType);
    }

    public void displayPendingRequests() {
        System.out.println("\n--- Current Booking Request Queue (First-Come-First-Served) ---");
        if (requestQueue.isEmpty()) {
            System.out.println("No pending requests.");
        } else {
            for (Reservation res : requestQueue) {
                System.out.println(res);
            }
        }
        System.out.println("--------------------------------------------------------------\n");
    }
}

// 3. Updated Main Class based on your IDE screenshot
public class BookMyStayApp {
    public static void main(String[] args) {
        // --- Setup from previous Use Cases ---
        Map<String, Integer> inventory = new HashMap<>();
        inventory.put("Deluxe", 5);
        inventory.put("Suite", 2);
        inventory.put("Standard", 0);

        // --- Use Case 5 Implementation ---
        BookingService bookingService = new BookingService();

        System.out.println("System: Receiving booking requests during peak hours...");

        // Simulating guests arriving at different times
        bookingService.addBookingRequest("Alice", "Deluxe");
        bookingService.addBookingRequest("Bob", "Standard");
        bookingService.addBookingRequest("Charlie", "Suite");
        bookingService.addBookingRequest("Diana", "Deluxe");

        // Displaying the queue to prove order preservation
        bookingService.displayPendingRequests();

        System.out.println("Notice: No rooms have been deducted from inventory yet.");
        System.out.println("Inventory remains: " + inventory);
    }
}
