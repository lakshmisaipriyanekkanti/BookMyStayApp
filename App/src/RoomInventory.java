import java.util.HashMap;
import java.util.Map;

public class RoomInventory {
    // Key: Room Type (String), Value: Available Count (Integer)
    private Map<String, Integer> inventory;

    public RoomInventory() {
        this.inventory = new HashMap<>();
    }

    // Method to add or update room availability
    public void addRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Method to get availability for a specific type
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Method to display the whole inventory (The state)
    public void displayInventory() {
        System.out.println("--- Current Room Inventory Status ---");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println("Room Type: " + entry.getKey() + " | Available: " + entry.getValue());
        }
    }
}
