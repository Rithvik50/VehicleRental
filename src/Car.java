public class Car extends Vehicle {
    enum CarType {
        HATCHBACK, SEDAN, SUV
    }

    public Car(String regnNumber, FuelType fuelType, TransmissionType transmissionType) {
        super(regnNumber, fuelType, transmissionType);
    }

    public Car(FuelType fuelType, TransmissionType transmissionType, double perDayRent) {
        super(fuelType, transmissionType, perDayRent);
    }
}
