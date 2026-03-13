public class SingleRoom extends Room {
    public SingleRoom() { super("Single Room", 1, 1200.0); }
    @Override
    public void displayFeatures() { System.out.println("Feature: Standard AC, Single Bed."); }
}