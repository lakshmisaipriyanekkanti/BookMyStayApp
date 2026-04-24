import java.util.*;

public class BookMyStayApp {
    // Shared Mutable State
    private Map<String, Integer> inventory = new HashMap<>();
    private Queue<String> requestQueue = new LinkedList<>();
    private final Object lock = new Object(); // Explicit lock object for synchronization

    public BookMyStayApp() {
        // Only 1 room available to test race condition prevention
        inventory.put("Deluxe", 1);
    }

    /**
     * UC11: Thread-safe Request Intake
     */
    public void submitRequest(String guestName) {
        synchronized (lock) {
            requestQueue.add(guestName);
            System.out.println(guestName + " submitted a request.");
        }
    }

    /**
     * UC11: Thread-safe Allocation (Critical Section)
     * Synchronized ensures that only one thread can decrement inventory at a time.
     */
    public void processBooking(String threadName) {
        synchronized (lock) {
            if (!requestQueue.isEmpty()) {
                String guest = requestQueue.poll();
                String type = "Deluxe";

                System.out.println("[" + threadName + "] Processing for: " + guest);

                if (inventory.get(type) > 0) {
                    // Simulate processing time to increase chance of race condition
                    try { Thread.sleep(100); } catch (InterruptedException e) {}

                    inventory.put(type, inventory.get(type) - 1);
                    System.out.println("[SUCCESS] " + guest + " secured the last Deluxe room.");
                } else {
                    System.out.println("[FAILED] " + guest + " - No rooms left.");
                }
            }
        }
    }

    public static void main(String[] args) {
        BookMyStayApp app = new BookMyStayApp();

        // 1. Submit requests
        app.submitRequest("Alice");
        app.submitRequest("Bob");

        // 2. Simulate concurrent processing using Threads
        Thread thread1 = new Thread(() -> app.processBooking("Processor-Thread-1"));
        Thread thread2 = new Thread(() -> app.processBooking("Processor-Thread-2"));

        System.out.println("\n--- Starting Concurrent Simulation ---");
        thread1.start();
        thread2.start();

        // Wait for threads to finish
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\nFinal Inventory State: " + app.inventory);
    }
}