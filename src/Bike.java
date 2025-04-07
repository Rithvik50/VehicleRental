public class Bike extends Vehicle {
    enum BikeType {
        RACING, CRUISER, CITY
    }

    private BikeType bikeType;
    private int engineDisplacement;
    private double weight;

    public Bike(String regnNumber, FuelType fuelType, TransmissionType transmissionType) {
        super(regnNumber, fuelType, transmissionType);
    }

    public Bike(FuelType fuelType, TransmissionType transmissionType, double perDayRent) {
        super(fuelType, transmissionType, perDayRent);
    }

    public Bike setBikeType(BikeType bikeType) {
        this.bikeType = bikeType;
        return this;
    }

    public Bike setEngineDisplacement(int engineDisplacement) {
        this.engineDisplacement = engineDisplacement;
        return this;
    }

    public Bike setWeight(double weight) {
        this.weight = weight;
        return this;
    }

    @Override
    public void displayDetails() {
        System.out.println("BIKE DETAILS:");
        super.displayDetails();
        System.out.println("Biketype: " + bikeType);
        System.out.println("Engine Displacement: " + engineDisplacement + "cc");
        System.out.println("Weight: " + weight + "kg");
        System.out.println();
    }
}
