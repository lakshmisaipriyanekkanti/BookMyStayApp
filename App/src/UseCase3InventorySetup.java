public class UseCase3InventorySetup {
    public static void main(String[] args) {
        // 1. Initialize the Centralized Inventory
        RoomInventory myInventory = new RoomInventory();

        // 2. Register room types and counts (Replacing scattered variables)
        myInventory.addRoomType("Single Room", 10);
        myInventory.addRoomType("Double Room", 5);
        myInventory.addRoomType("Suite Room", 2);

        // 3. Display the initial state
        System.out.println("BookMyStayApp Version 3.0 - Centralized Management\n");
        myInventory.displayInventory();

        // 4. Demonstrate a controlled update (Simulating a booking)
        System.out.println("\n--- Simulating a booking for a Double Room ---");
        int currentDouble = myInventory.getAvailability("Double Room");
        myInventory.addRoomType("Double Room", currentDouble - 1);

        // 5. Final Display
        myInventory.displayInventory();
    }
}
