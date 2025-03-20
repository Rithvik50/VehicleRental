import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User {
    private String userId;
    private List<Vehicle> rentedVehicles;
    private boolean isAdmin = false;

    public User(String userId, boolean isAdmin) {
        this.userId = userId;
        this.isAdmin = isAdmin;
        this.rentedVehicles = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void rentVehicle(Vehicle vehicle) {
        rentedVehicles.add(vehicle);
    }

    public List<Vehicle> getRentedVehicles() {
        Collections.sort(rentedVehicles);
        return rentedVehicles;
    }

    public void displayRentedVehicles() {
        System.out.println("Vehicles rented by (sorted by rental date):");
        List<Vehicle> sortedVehicles = getRentedVehicles();
        if (sortedVehicles.isEmpty()) {
            System.out.println("No vehicles rented.");
        } else {
            for (Vehicle vehicle : sortedVehicles) {
                vehicle.displayDetails();
            }
        }
    }
}
