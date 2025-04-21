public class Bike extends Vehicle {
    enum BikeType {
        RACING, CRUISER, CITY
    }

    public Bike(String regnNumber, FuelType fuelType, TransmissionType transmissionType) {
        super(regnNumber, fuelType, transmissionType);
    }

    public Bike(FuelType fuelType, TransmissionType transmissionType, double perDayRent) {
        super(fuelType, transmissionType, perDayRent);
    }
}
