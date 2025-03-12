import java.util.ArrayList;
import java.util.List;

import vehicles.Vehicle;

public class RentalSystem {
    private List<User> users;
    private List<Vehicle> availableVehicles;

    public RentalSystem() {
        users = new ArrayList<>();
        availableVehicles = new ArrayList<>();
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void addVehicle(Vehicle vehicle) {
        availableVehicles.add(vehicle);
    }

    public void rentVehicle(String userId, String regnNumber) {
        User user = findUser(userId);
        Vehicle vehicle = findVehicle(regnNumber);

        if (user != null && vehicle != null) {
            user.rentVehicle(vehicle);
            availableVehicles.remove(vehicle);
            System.out.println("Vehicle with registration number " + regnNumber + 
                               " rented successfully to " + user.getName());
        } else {
            System.out.println("Rental failed. User or vehicle not found.");
        }
    }

    private User findUser(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    private Vehicle findVehicle(String regnNumber) {
        for (Vehicle vehicle : availableVehicles) {
            if (vehicle.getRegnNumber().equals(regnNumber)) {
                return vehicle;
            }
        }
        return null;
    }

    public void displayUserRentals(String userId) {
        User user = findUser(userId);
        if (user != null) {
            user.displayRentedVehicles();
        } else {
            System.out.println("User not found.");
        }
    }

    public void displayAvailableVehicles() {
        System.out.println("Available Vehicles:");
        if (availableVehicles.isEmpty()) {
            System.out.println("No vehicles available for rent.");
        } else {
            for (Vehicle vehicle : availableVehicles) {
                vehicle.displayDetails();
            }
        }
    }
}
