import java.util.*;

// Domain Model representing Room Details
class Room {
    private String type;
    private double price;
    private String amenities;

    public Room(String type, double price, String amenities) {
        this.type = type;
        this.price = price;
        this.amenities = amenities;
    }

    @Override
    public String toString() {
        return String.format("[%s] Price: $%.2f | Amenities: %s", type, price, amenities);
    }
}

// Search Service - Handles Read-Only Access
class SearchService {
    private Map<String, Integer> inventory;
    private Map<String, Room> roomDetails;

    public SearchService(Map<String, Integer> inventory, Map<String, Room> roomDetails) {
        this.inventory = inventory;
        this.roomDetails = roomDetails;
    }

    public void displayAvailableRooms() {
        System.out.println("--- Available Rooms ---");
        boolean found = false;

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            // Validation: Only display rooms with availability > 0
            if (entry.getValue() > 0) {
                Room details = roomDetails.get(entry.getKey());
                if (details != null) {
                    System.out.println(details + " | Units Available: " + entry.getValue());
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("No rooms available at this time.");
        }
        System.out.println("------------------------\n");
    }
}

// Class name updated to match the filename 'BookMyStayApp.java'
public class BookMyStayApp {
    public static void main(String[] args) {
        // 1. Initialize Inventory (System State)
        Map<String, Integer> inventory = new HashMap<>();
        inventory.put("Deluxe", 5);
        inventory.put("Suite", 2);
        inventory.put("Standard", 0); // This will be filtered out

        // 2. Initialize Room Metadata
        Map<String, Room> roomMetadata = new HashMap<>();
        roomMetadata.put("Deluxe", new Room("Deluxe", 150.0, "WiFi, King Bed"));
        roomMetadata.put("Suite", new Room("Suite", 300.0, "WiFi, Jacuzzi"));
        roomMetadata.put("Standard", new Room("Standard", 80.0, "WiFi, Single Bed"));

        // 3. Search Service Execution
        SearchService searchService = new SearchService(inventory, roomMetadata);
        searchService.displayAvailableRooms();

        // 4. Verify State remains unchanged (Read-Only Access check)
        System.out.println("Post-Search Verification: Deluxe count is still " + inventory.get("Deluxe"));
    }
}
