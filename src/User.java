import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class User {
    private String userId;
    private List<Vehicle> rentedVehicles;
    private Insurance insurance;
    private boolean admin = false;

    public User(String userId, boolean admin, Insurance insurance) {
        this.userId = userId;
        this.admin = admin;
        this.insurance = insurance;

        this.rentedVehicles = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void rentVehicle(Vehicle vehicle) {
        rentedVehicles.add(vehicle);
    }

    public List<Vehicle> getRentedVehicles() {
        rentedVehicles.clear();
        
        String sql = "SELECT rh.vehicle_id, rh.regn_number, rh.rental_date, rh.return_date, rh.rented, " +
                     "v.type, v.fuel_type, v.transmission_type, v.special_details, v.rent " +
                     "FROM RentalHistory rh " +
                     "JOIN Vehicle v ON rh.vehicle_id = v.vehicle_id " +
                     "WHERE rh.user_id = ? " +
                     "ORDER BY rh.rental_date DESC";
        
        try (Connection conn = DriverManager.getConnection(App.getDatabase()[0], App.getDatabase()[1], App.getDatabase()[2]);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                String vehicleType = rs.getString("type");
                String regnNumber = rs.getString("regn_number");
                FuelType fuelType = FuelType.valueOf(rs.getString("fuel_type"));
                TransmissionType transmissionType = TransmissionType.valueOf(rs.getString("transmission_type"));
                
                Vehicle vehicle;
                
                if ("Car".equals(vehicleType)) {
                    vehicle = new Car(regnNumber, fuelType, transmissionType);
                } else if ("Bike".equals(vehicleType)) {
                    vehicle = new Bike(regnNumber, fuelType, transmissionType);
                } else if ("Truck".equals(vehicleType)) {
                    vehicle = new Truck(regnNumber, fuelType, transmissionType);
                } else {
                    vehicle = new Vehicle(regnNumber, fuelType, transmissionType);
                }
                
                vehicle.setRentalDate(rs.getDate("rental_date").toLocalDate());
                
                if (rs.getDate("return_date") != null) {
                    java.time.LocalDate rentalDate = rs.getDate("rental_date").toLocalDate();
                    java.time.LocalDate returnDate = rs.getDate("return_date").toLocalDate();
                    int days = java.time.Period.between(rentalDate, returnDate).getDays();
                    vehicle.setReturnDate(days);
                }
                
                vehicle.setPerDayRent(rs.getDouble("rent"));
                
                String specialDetailsJson = rs.getString("special_details");
                
                boolean isRented = rs.getBoolean("rented");
                vehicle.setRented(isRented);

                try {
                    if (specialDetailsJson != null && !specialDetailsJson.isEmpty()) {
                        Gson gson = new Gson();
                        java.lang.reflect.Type mapType = new TypeToken<java.util.Map<String, String>>(){}.getType();
                        java.util.Map<String, String> detailsMap = gson.fromJson(specialDetailsJson, mapType);
                        
                        ArrayList<Object> detailsList = new ArrayList<>();
                        if ("Car".equals(vehicleType)) {
                            detailsList.add(detailsMap.get("carType"));
                            detailsList.add(detailsMap.get("numberOfSeats"));
                        } else if ("Bike".equals(vehicleType)) {
                            detailsList.add(detailsMap.get("bikeType"));
                            detailsList.add(detailsMap.get("engineDisplacement"));
                            detailsList.add(detailsMap.get("weight"));
                        } else if ("Truck".equals(vehicleType)) {
                            detailsList.add(detailsMap.get("truckType"));
                            detailsList.add(detailsMap.get("numberOfAxles"));
                        }
                        
                        vehicle.setSpecialDetails(detailsList);
                    } else {
                        vehicle.setSpecialDetails(new ArrayList<>());
                    }
                } catch (Exception e) {
                    System.out.println("Error parsing special details: " + e.getMessage());
                    vehicle.setSpecialDetails(new ArrayList<>());
                }
                
                rentedVehicles.add(vehicle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error retrieving rented vehicles: " + e.getMessage());
        }
        
        return rentedVehicles;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public void displayRentedVehicles() {
        System.out.println("Vehicles rented by (sorted by rental date):");
        List<Vehicle> sortedVehicles = getRentedVehicles();
        if (sortedVehicles.isEmpty()) {
            System.out.println("No vehicles rented.");
        } else {
            for (Vehicle vehicle : sortedVehicles) {
                vehicle.displayDetails();
            }
        }
    }
}
