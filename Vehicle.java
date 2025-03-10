public abstract class Vehicle implements VehicleInfo, Comparable<Vehicle> {
    private String regnNumber;
    private FuelType fuelType;
    private double perDayRent;
    private String rentalDate;
    private TransmissionType transmissionType;

    public Vehicle(String regnNumber, FuelType fuelType, TransmissionType transmissionType, double perDayRent, String rentalDate) {
        this.regnNumber = regnNumber;
        this.fuelType = fuelType;
        this.transmissionType = transmissionType;
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

    protected TransmissionType getTransmissionType() {
        return transmissionType;
    }

    protected double getPerDayRent() {
        return perDayRent;
    }

    public void displayDetails() {
        System.out.println("Registration Number: " + getRegnNumber());
        System.out.println("Fuel Type: " + getFuelType());
        System.out.println("Transmission Type: " + getTransmissionType());
        System.out.println("Per Day Rent: ₹" + getPerDayRent());
        System.out.println("Rental Date: " + getRentalDate());
    }

    @Override
    public int compareTo(Vehicle other) {
        return this.rentalDate.compareTo(other.rentalDate);
    }
}
