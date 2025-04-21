public class Truck extends Vehicle {
    enum TruckType {
        FLATBED, HEAVY_DUTY, LIGHT_DUTY, BOX
    }

    public Truck(String regnNumber, FuelType fuelType, TransmissionType transmissionType) {
        super(regnNumber, fuelType, transmissionType);
    }

    public Truck(FuelType fuelType, TransmissionType transmissionType, double perDayRent) {
        super(fuelType, transmissionType, perDayRent);
    }
}
