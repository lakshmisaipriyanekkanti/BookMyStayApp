import java.util.*;

// 1. Represents an individual optional offering
class Service {
    String serviceName;
    double price;

    public Service(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    @Override
    public String toString() {
        return serviceName + " ($" + price + ")";
    }
}

public class BookMyStayApp {
    // Existing structures from UC5 & UC6
    private Map<String, Integer> inventory = new HashMap<>();
    private Map<String, Set<String>> allocatedRooms = new HashMap<>();

    // UC7: Mapping Reservation ID to a List of Services (One-to-Many)
    private Map<String, List<Service>> addOnManager = new HashMap<>();

    public BookMyStayApp() {
        // Setup inventory
        inventory.put("Deluxe", 5);
        allocatedRooms.put("Deluxe", new HashSet<>());
    }

    /**
     * UC7: Goal is to attach services to a reservation ID
     * without modifying core inventory logic.
     */
    public void addServiceToReservation(String reservationId, Service service) {
        // If it's the first service for this ID, initialize the list
        addOnManager.putIfAbsent(reservationId, new ArrayList<>());

        // Add the service to the list
        addOnManager.get(reservationId).add(service);
        System.out.println("Service Added: " + service.serviceName + " to Reservation " + reservationId);
    }

    public void calculateTotalCost(String reservationId, double baseRoomPrice) {
        double totalAddOnCost = 0;
        List<Service> services = addOnManager.getOrDefault(reservationId, new ArrayList<>());

        System.out.println("\n--- Billing Summary for " + reservationId + " ---");
        System.out.println("Base Room Price: $" + baseRoomPrice);

        for (Service s : services) {
            System.out.println("+ " + s);
            totalAddOnCost += s.price;
        }

        double finalTotal = baseRoomPrice + totalAddOnCost;
        System.out.println("Total Additional Charges: $" + totalAddOnCost);
        System.out.println("Grand Total: $" + finalTotal);
        System.out.println("-------------------------------------------\n");
    }

    public static void main(String[] args) {
        BookMyStayApp app = new BookMyStayApp();

        // Define available services
        Service breakfast = new Service("Buffet Breakfast", 25.0);
        Service spa = new Service("Spa Treatment", 120.0);
        Service wifi = new Service("Premium WiFi", 15.0);

        // Simulation: Alice has Reservation ID "RES-101"
        String aliceResId = "RES-101";

        // Alice selects multiple services
        app.addServiceToReservation(aliceResId, breakfast);
        app.addServiceToReservation(aliceResId, spa);

        // Calculate costs (Assuming base room price is $200)
        app.calculateTotalCost(aliceResId, 200.0);

        // Simulation: Bob has Reservation ID "RES-102"
        String bobResId = "RES-102";
        app.addServiceToReservation(bobResId, wifi);
        app.calculateTotalCost(bobResId, 150.0);
    }
}