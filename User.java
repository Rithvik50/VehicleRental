import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import vehicles.Vehicle;

public class User {
    private String userId;
    private String name;
    private List<Vehicle> rentedVehicles;

    public User(String userId, String name) {
        this.userId = userId;
        this.name = name;
        this.rentedVehicles = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public void rentVehicle(Vehicle vehicle) {
        rentedVehicles.add(vehicle);
    }

    public List<Vehicle> getRentedVehicles() {
        Collections.sort(rentedVehicles);
        return rentedVehicles;
    }

    public void displayRentedVehicles() {
        System.out.println("Vehicles rented by " + name + " (sorted by rental date):");
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
