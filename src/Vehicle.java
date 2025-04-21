import java.time.LocalDate;
import java.util.ArrayList;

public class Vehicle {
    private String regnNumber;
    private FuelType fuelType;
    private double perDayRent;
    private LocalDate startDate, endDate;
    private TransmissionType transmissionType;
    private ArrayList<Object> specialDetails;
    private boolean rented = false;

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

    public void setRentalDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getReturnDate() {
        return endDate;
    }

    public Vehicle setReturnDate(int days) {
        endDate = startDate.plusDays(days);
        return this;
    }

    public double getPerDayRent() {
        return perDayRent;
    }

    public void setPerDayRent(double perDayRent) {
        this.perDayRent = perDayRent;
    }

    public ArrayList<Object> getSpecialDetails() {
        return specialDetails;
    }

    public Vehicle setSpecialDetails(ArrayList<Object> specialDetails) {
        this.specialDetails = specialDetails;
        return this;
    }

    public boolean isRented() {
        return rented;
    }
    
    public void setRented(boolean rented) {
        this.rented = rented;
    }
}
