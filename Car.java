public class Car extends Vehicle {
    enum CarType {
        HATCHBACK, SEDAN, SUV
    }

    private TransmissionType transmissionType;
    private CarType carType;
    private int numberOfSeats;

    public Car(String regnNumber, FuelType fuelType, double perDayRent, String rentalDate,
               TransmissionType transmissionType, CarType carType, int numberOfSeats) {
        super(regnNumber, fuelType, perDayRent, rentalDate);
        this.transmissionType = transmissionType;
        this.carType = carType;
        this.numberOfSeats = numberOfSeats;
    }

    @Override
    public void displayDetails() {
        System.out.println("CAR DETAILS:");
        System.out.println("Registration Number: " + getRegnNumber());
        System.out.println("Fuel Type: " + getFuelType());
        System.out.println("Per Day Rent: ₹" + getPerDayRent());
        System.out.println("Rental Date: " + getRentalDate());
        System.out.println("Transmission Type: " + transmissionType);
        System.out.println("Car Type: " + carType);
        System.out.println("Number of Seats: " + numberOfSeats);
        System.out.println();
    }
}
