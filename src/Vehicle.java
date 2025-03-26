import java.time.LocalDate;

public class Vehicle {
    private String regnNumber;
    private FuelType fuelType;
    private double perDayRent;
    private LocalDate startDate;
    private LocalDate endDate;
    private TransmissionType transmissionType;

    public Vehicle() {
        
    }

    public Vehicle(String regnNumber, FuelType fuelType, TransmissionType transmissionType, double perDayRent) {
        this.regnNumber = regnNumber;
        this.fuelType = fuelType;
        this.transmissionType = transmissionType;
        this.perDayRent = perDayRent;
        ;
    }

    public String getRegnNumber() {
        return regnNumber;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public TransmissionType getTransmissionType() {
        return transmissionType;
    }

    public LocalDate getRentalDate() {
        return startDate;
    }

    public double getPerDayRent() {
        return perDayRent;
    }

    public void displayDetails() {
        System.out.println("Registration Number: " + getRegnNumber());
        System.out.println("Fuel Type: " + getFuelType());
        System.out.println("Transmission Type: " + getTransmissionType());
        System.out.println("Per Day Rent: ₹" + getPerDayRent());
        System.out.println("Rental Date: " + getRentalDate());
    }
}
