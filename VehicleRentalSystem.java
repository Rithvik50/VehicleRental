import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

interface VehicleInfo {
    public String getRegnNumber();
    public String getRentalDate();
}

enum FuelType {
    ELECTRIC, PETROL, DIESEL
}

abstract class Vehicle implements VehicleInfo, Comparable<Vehicle> {
    private String regnNumber;
    private FuelType fuelType;
    private double perDayRent;
    private String rentalDate;

    public Vehicle(String regnNumber, FuelType fuelType, double perDayRent, String rentalDate) {
        this.regnNumber = regnNumber;
        this.fuelType = fuelType;
        this.perDayRent = perDayRent;
        this.rentalDate = rentalDate;
    }

    @Override
    public String getRegnNumber() {
        return regnNumber;
    }

    @Override
    public String getRentalDate() {
        return rentalDate;
    }

    protected FuelType getFuelType() {
        return fuelType;
    }

    protected double getPerDayRent() {
        return perDayRent;
    }

    public abstract void displayDetails();

    @Override
    public int compareTo(Vehicle other) {
        return this.rentalDate.compareTo(other.rentalDate);
    }
}

class Car extends Vehicle {
    enum TransmissionType {
        MANUAL, AUTOMATIC
    }

    enum CarType {
        HATCHBACK, SEDAN, SUV
    }

    private TransmissionType transmissionType;
    private CarType carType;
    private int numberOfSeats;

    public Car(String regnNumber, FuelType fuelType, double perDayRent, String rentalDate,
               TransmissionType transmissionType, CarType carType, int numberOfSeats) {
        super(regnNumber, fuelType, perDayRent, rentalDate);
        this.transmissionType = transmissionType;
        this.carType = carType;
        this.numberOfSeats = numberOfSeats;
    }

    @Override
    public void displayDetails() {
        System.out.println("CAR DETAILS:");
        System.out.println("Registration Number: " + getRegnNumber());
        System.out.println("Fuel Type: " + getFuelType());
        System.out.println("Per Day Rent: ₹" + getPerDayRent());
        System.out.println("Rental Date: " + getRentalDate());
        System.out.println("Transmission Type: " + transmissionType);
        System.out.println("Car Type: " + carType);
        System.out.println("Number of Seats: " + numberOfSeats);
        System.out.println();
    }
}

class Motorcycle extends Vehicle {
    private int engineDisplacement;
    private double weight;

    public Motorcycle(String regnNumber, FuelType fuelType, double perDayRent, String rentalDate,
                      int engineDisplacement, double weight) {
        super(regnNumber, fuelType, perDayRent, rentalDate);
        this.engineDisplacement = engineDisplacement;
        this.weight = weight;
    }

    @Override
    public void displayDetails() {
        System.out.println("MOTORCYCLE DETAILS:");
        System.out.println("Registration Number: " + getRegnNumber());
        System.out.println("Fuel Type: " + getFuelType());
        System.out.println("Per Day Rent: ₹" + getPerDayRent());
        System.out.println("Rental Date: " + getRentalDate());
        System.out.println("Engine Displacement: " + engineDisplacement + "cc");
        System.out.println("Weight: " + weight + "kg");
        System.out.println();
    }
}

class User {
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

class RentalSystem {
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

public class VehicleRentalSystem {
    public static void main(String[] args) {
        RentalSystem rentalSystem = new RentalSystem();

        User user1 = new User("U001", "Rahul Sharma");
        User user2 = new User("U002", "Priya Patel");
        rentalSystem.addUser(user1);
        rentalSystem.addUser(user2);

        Car car1 = new Car("DL01AB1234", FuelType.PETROL, 1500.0, "2023-05-15", 
                         Car.TransmissionType.AUTOMATIC, Car.CarType.SEDAN, 4);
        
        Car car2 = new Car("MH02CD5678", FuelType.DIESEL, 2000.0, "2023-05-10", 
                         Car.TransmissionType.MANUAL, Car.CarType.SUV, 6);
        
        Motorcycle bike1 = new Motorcycle("KA03EF9012", FuelType.PETROL, 800.0, "2023-05-20", 
                                       150, 140.5);
        
        Motorcycle bike2 = new Motorcycle("TN04GH3456", FuelType.ELECTRIC, 1000.0, "2023-05-05", 
                                       250, 160.0);
        
        rentalSystem.addVehicle(car1);
        rentalSystem.addVehicle(car2);
        rentalSystem.addVehicle(bike1);
        rentalSystem.addVehicle(bike2);

        rentalSystem.displayAvailableVehicles();

        rentalSystem.rentVehicle("U001", "DL01AB1234");
        rentalSystem.rentVehicle("U001", "KA03EF9012");
        rentalSystem.rentVehicle("U002", "MH02CD5678");

        rentalSystem.displayUserRentals("U001");
        rentalSystem.displayUserRentals("U002");

        rentalSystem.displayAvailableVehicles();
    }
}