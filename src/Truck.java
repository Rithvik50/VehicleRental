public class Truck extends Vehicle {
    enum TruckType {
        FLATBED, HEAVY_DUTY, LIGHT_DUTY, BOX
    }

    private TruckType truckType;
    private int numberOfAxles;

    public Truck(FuelType fuelType, TransmissionType transmissionType) {
        super(fuelType, transmissionType);
    }

    public Truck(String regnNumber, FuelType fuelType, TransmissionType transmissionType, double perDayRent) {
        super(regnNumber, fuelType, transmissionType, perDayRent);
    }

    public Truck setTruckType(TruckType truckType) {
        this.truckType = truckType;
        return this;
    }

    public Truck setNumberOfAxles(int numberOfAxles) {
        this.numberOfAxles = numberOfAxles;
        return this;
    }

    @Override
    public void displayDetails() {
        System.out.println("TRUCK DETAILS:");
        super.displayDetails();
        System.out.println("Trucktype: " + truckType);
        System.out.println("Number of Axles: " + numberOfAxles);
        System.out.println();
    }
}
