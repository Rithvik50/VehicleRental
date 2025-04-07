public class Car extends Vehicle {
    enum CarType {
        HATCHBACK, SEDAN, SUV
    }

    private CarType carType;
    private int numberOfSeats;

    public Car(String regnNumber, FuelType fuelType, TransmissionType transmissionType) {
        super(regnNumber, fuelType, transmissionType);
    }

    public Car(FuelType fuelType, TransmissionType transmissionType, double perDayRent) {
        super(fuelType, transmissionType, perDayRent);
    }

    public Car setCarType(CarType carType) {
        this.carType = carType;
        return this;
    }

    public Car setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
        return this;
    }

    @Override
    public void displayDetails() {
        System.out.println("CAR DETAILS:");
        super.displayDetails();
        System.out.println("Car Type: " + carType);
        System.out.println("Number of Seats: " + numberOfSeats);
        System.out.println();
    }
}
