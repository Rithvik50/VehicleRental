package vehicles;
public class Car extends Vehicle {
    enum CarType {
        HATCHBACK, SEDAN, SUV
    }

    private CarType carType;
    private int numberOfSeats;

    public Car(String regnNumber, FuelType fuelType, TransmissionType transmissionType, double perDayRent, String rentalDate, 
                CarType carType, int numberOfSeats) {
        super(regnNumber, fuelType, transmissionType, perDayRent, rentalDate);
        this.carType = carType;
        this.numberOfSeats = numberOfSeats;
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
