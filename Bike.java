public class Bike extends Vehicle {
    enum BikeType {
        RACING, CRUISER, CITY
    }

    private BikeType bikeType;
    private int engineDisplacement;
    private double weight;

    public Bike(String regnNumber, FuelType fuelType, TransmissionType transmissionType, double perDayRent, String rentalDate,
                      int engineDisplacement, double weight, BikeType bikeType) {
        super(regnNumber, fuelType, transmissionType, perDayRent, rentalDate);
        this.bikeType = bikeType;
        this.engineDisplacement = engineDisplacement;
        this.weight = weight;
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
