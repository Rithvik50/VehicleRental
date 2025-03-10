public class VehicleRentalSystem {
    public static void main(String[] args) {
        RentalSystem rentalSystem = new RentalSystem();

        User user1 = new User("U001", "Rahul Sharma");
        User user2 = new User("U002", "Priya Patel");
        rentalSystem.addUser(user1);
        rentalSystem.addUser(user2);

        Car car1 = new Car("DL01AB1234", FuelType.PETROL, 1500.0, "2023-05-15", 
                        TransmissionType.AUTOMATIC, Car.CarType.SEDAN, 4);
        
        Car car2 = new Car("MH02CD5678", FuelType.DIESEL, 2000.0, "2023-05-10", 
                        TransmissionType.MANUAL, Car.CarType.SUV, 6);
        
        Motorcycle bike1 = new Motorcycle("KA03EF9012", FuelType.PETROL, 800.0, "2023-05-20", 
                                       150, 140.5);
        
        Motorcycle bike2 = new Motorcycle("TN04GH3456", FuelType.ELECTRIC, 1000.0, "2023-05-05", 
                                       250, 160.0);
        
        rentalSystem.addVehicle(car1);
        rentalSystem.addVehicle(car2);
        rentalSystem.addVehicle(bike1);
        rentalSystem.addVehicle(bike2);

        rentalSystem.displayAvailableVehicles();

        rentalSystem.rentVehicle("U001", "DL01AB1234");
        rentalSystem.rentVehicle("U001", "KA03EF9012");
        rentalSystem.rentVehicle("U002", "MH02CD5678");

        rentalSystem.displayUserRentals("U001");
        rentalSystem.displayUserRentals("U002");

        rentalSystem.displayAvailableVehicles();
    }
}