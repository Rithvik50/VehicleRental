import java.time.LocalDate;
import java.util.ArrayList;

public class Vehicle {
    private String regnNumber;
    private FuelType fuelType;
    private double perDayRent;
    private LocalDate startDate;
    private TransmissionType transmissionType;
    private ArrayList<Object> specialDetails;

    public Vehicle(String regnNumber, FuelType fuelType, TransmissionType transmissionType) {
        this.regnNumber = regnNumber;
        this.fuelType = fuelType;
        this.transmissionType = transmissionType;
        this.perDayRent = 0.0;
    }

    public Vehicle(FuelType fuelType, TransmissionType transmissionType, double perDayRent) {
        this.regnNumber = null;
        this.fuelType = fuelType;
        this.transmissionType = transmissionType;
        this.perDayRent = perDayRent;
    }

    public String getRegnNumber() {
        return regnNumber;
    }

    public void setRegnNumber(String regnNumber) {
        this.regnNumber = regnNumber;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public TransmissionType getTransmissionType() {
        return transmissionType;
    }

    public void setTransmissionType(TransmissionType transmissionType) {
        this.transmissionType = transmissionType;
    }

    public LocalDate getRentalDate() {
        return startDate;
    }

    public LocalDate getReturnDate() {
        return startDate.plusMonths(1);
    }

    public double getPerDayRent() {
        return perDayRent;
    }

    public ArrayList<Object> getSpecialDetails() {
        return specialDetails;
    }

    public Vehicle setSpecialDetails(ArrayList<Object> specialDetails) {
        this.specialDetails = specialDetails;
        return this;
    }

    public void displayDetails() {
        System.out.println("Registration Number: " + getRegnNumber());
        System.out.println("Fuel Type: " + getFuelType());
        System.out.println("Transmission Type: " + getTransmissionType());
        System.out.println("Per Day Rent: ₹" + getPerDayRent());
        System.out.println("Rental Date: " + getRentalDate());
    }
}
