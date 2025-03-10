public class Motorcycle extends Vehicle {
    private int engineDisplacement;
    private double weight;

    public Motorcycle(String regnNumber, FuelType fuelType, double perDayRent, String rentalDate,
                      int engineDisplacement, double weight) {
        super(regnNumber, fuelType, perDayRent, rentalDate);
        this.engineDisplacement = engineDisplacement;
        this.weight = weight;
    }

    @Override
    public void displayDetails() {
        System.out.println("MOTORCYCLE DETAILS:");
        System.out.println("Registration Number: " + getRegnNumber());
        System.out.println("Fuel Type: " + getFuelType());
        System.out.println("Per Day Rent: ₹" + getPerDayRent());
        System.out.println("Rental Date: " + getRentalDate());
        System.out.println("Engine Displacement: " + engineDisplacement + "cc");
        System.out.println("Weight: " + weight + "kg");
        System.out.println();
    }
}