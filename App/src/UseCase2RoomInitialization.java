public class UseCase2RoomInitialization {
    public static void main(String[] args) {
        // Room initialization (Polymorphism)
        Room s1 = new SingleRoom();
        Room d1 = new DoubleRoom();

        // Static availability (as per requirements)
        int singleAvailable = 8;
        int doubleAvailable = 4;

        System.out.println("--- Use Case 2: Room Inventory (v2.0) ---");

        System.out.println("\nType: " + s1.getRoomType() + " | Price: " + s1.getPrice());
        s1.displayFeatures();
        System.out.println("Availability: " + singleAvailable);

        System.out.println("\nType: " + d1.getRoomType() + " | Price: " + d1.getPrice());
        d1.displayFeatures();
        System.out.println("Availability: " + doubleAvailable);
    }
}