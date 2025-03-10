public abstract class Vehicle implements VehicleInfo, Comparable<Vehicle> {
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
