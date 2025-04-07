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
        
        String sql = "SELECT regn_number, fuel_type, transmission_type, rent, special_details, rental_date FROM Vehicle " +
                    "WHERE user_id = ? ORDER BY rental_date ASC";

        try (Connection conn = DriverManager.getConnection(App.getDatabase()[0], App.getDatabase()[1], App.getDatabase()[2]);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();
    
            while (rs.next()) {
                Vehicle vehicle = null; // Deal with this
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
