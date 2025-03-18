public class Truck extends Vehicle {
    enum TruckType {
        FLATBED, HEAVY_DUTY, LIGHT_DUTY, BOX
    }

    private TruckType truckType;
    private int numberOfAxles;

    public Truck(String regnNumber, FuelType fuelType, TransmissionType transmissionType, double perDayRent, String rentalDate,
                 int numberOfAxles, TruckType truckType) {
        super(regnNumber, fuelType, transmissionType, perDayRent, rentalDate);
        this.truckType = truckType;
        this.numberOfAxles = numberOfAxles;
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
