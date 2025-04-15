import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;

import javax.swing.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Map;

public class VehicleHandler extends MouseAdapter {
    private Window window;
    private JFrame frame;
    private Vehicle vehicle;
    private String vehicleType;
    private FuelType fuelType;
    private TransmissionType transmissionType;
    private ArrayList<Object> specialDetails;
    private JComboBox<String> v, f, t;
    private JComboBox<String> model, numberOfSeats, engineDisplacement, weight, numberOfAxles;
    private JTextField countField, rentField, regnNumberField, daysField;
    private String count, rent, regnNumber, days;

    enum VEHICLE_PAGES {
        SELECT_PAGE, SPECIAL_DETAILS
    }

    private VEHICLE_PAGES pages;

    public VehicleHandler(JFrame frame, Window window) {
        this.frame = frame;
        this.window = window;
        specialDetails = new ArrayList<Object>();

        pages = VEHICLE_PAGES.SELECT_PAGE;

        v = new JComboBox<>(new String[]{"Car", "Bike", "Truck"});
        v.setBounds(600, 150, 200, 30);
        v.setSelectedItem(null);
        v.setVisible(false);
        frame.add(v);

        f = new JComboBox<>(new String[]{"Petrol", "Diesel", "Electric"});
        f.setBounds(600, 390, 200, 30);
        f.setSelectedItem(null);
        f.setVisible(false);
        frame.add(f);

        t = new JComboBox<>(new String[]{"Manual", "Automatic"});
        t.setBounds(600, 430, 200, 30);
        t.setSelectedItem(null);
        t.setVisible(false);
        frame.add(t);

        model = new JComboBox<>();
        model.setBounds(400, 250, 200, 30);
        model.setSelectedItem(null);
        model.setVisible(false);
        frame.add(model);

        numberOfSeats = new JComboBox<>();
        numberOfSeats.setBounds(800, 250, 200, 30);
        numberOfSeats.setSelectedItem(null);
        numberOfSeats.setVisible(false);
        frame.add(numberOfSeats);

        engineDisplacement = new JComboBox<>();
        engineDisplacement.setBounds(600, 250, 200, 30);
        engineDisplacement.setSelectedItem(null);
        engineDisplacement.setVisible(false);
        frame.add(engineDisplacement);

        weight = new JComboBox<>();
        weight.setBounds(800, 250, 200, 30);
        weight.setSelectedItem(null);
        weight.setVisible(false);
        frame.add(weight);

        numberOfAxles = new JComboBox<>();
        numberOfAxles.setBounds(800, 250, 200, 30);
        numberOfAxles.setSelectedItem(null);
        numberOfAxles.setVisible(false);
        frame.add(numberOfAxles);

        v.addActionListener(e -> {
            if (v.getSelectedItem() != null) {
                String newVehicleType = v.getSelectedItem().toString();
                if (!newVehicleType.equals(vehicleType)) {
                    specialDetails.clear();
                }
                vehicleType = newVehicleType;
            }
        });
        
        f.addActionListener(e -> {
            if (f.getSelectedItem() != null) {
                String fuel = f.getSelectedItem().toString();
                if (fuel.equals("Petrol")) {
                    fuelType = FuelType.PETROL;
                } else if (fuel.equals("Diesel")) {
                    fuelType = FuelType.DIESEL;
                } else if (fuel.equals("Electric")) {
                    fuelType = FuelType.ELECTRIC;
                }
            }
        });
        
        t.addActionListener(e -> {
            if (t.getSelectedItem() != null) {
                String transmission = t.getSelectedItem().toString();
                if (transmission.equals("Manual")) {
                    transmissionType = TransmissionType.MANUAL;
                } else if (transmission.equals("Automatic")) {
                    transmissionType = TransmissionType.AUTOMATIC;
                }
            }
        });

        countField = new JTextField(15);
        countField.setBounds(600, 200, 200, 30);
        countField.setVisible(false);
        frame.add(countField);

        rentField = new JTextField(15);
        rentField.setBounds(600, 350, 200, 30);
        rentField.setVisible(false);
        frame.add(rentField);

        regnNumberField = new JTextField(15);
        regnNumberField.setBounds(300, 350, 200, 30);
        regnNumberField.setVisible(false);
        frame.add(regnNumberField);

        daysField = new JTextField(15);
        daysField.setBounds(950, 350, 200, 30);
        daysField.setVisible(false);
        frame.add(daysField);
    }

    public void updateSpecialDetails() {
        hideAllSpecialComponents();
                
        if (vehicleType != null && specialDetails.isEmpty()) {
            switch (vehicleType) {
                case "Car":
                    specialDetails.add("");
                    specialDetails.add("");
                    break;
                case "Bike":
                    specialDetails.add("");
                    specialDetails.add("");
                    specialDetails.add("");
                    break;
                case "Truck":
                    specialDetails.add("");
                    specialDetails.add("");
                    break;
            }
        }
    
        model.removeAllItems();
        numberOfSeats.removeAllItems();
        engineDisplacement.removeAllItems();
        weight.removeAllItems();
        numberOfAxles.removeAllItems();
    
        if (vehicleType == null) return;
        
        removeAllActionListeners();
        
        switch (vehicleType) {
            case "Car":
                model.addItem("SUV");
                model.addItem("Sedan");
                model.addItem("Hatchback");
    
                numberOfSeats.addItem("2");
                numberOfSeats.addItem("4");
                numberOfSeats.addItem("5");
    
                model.setVisible(true);
                numberOfSeats.setVisible(true);
                break;
    
            case "Bike":
                model.addItem("Racing");
                model.addItem("Cruiser");
                model.addItem("City");
    
                engineDisplacement.addItem("1000cc");
                engineDisplacement.addItem("1500cc");
                engineDisplacement.addItem("2000cc");
    
                weight.addItem("1000kg");
                weight.addItem("1500kg");
                weight.addItem("2000kg");
    
                model.setVisible(true);
                engineDisplacement.setVisible(true);
                weight.setVisible(true);
                break;
    
            case "Truck":
                model.addItem("Flatbed");
                model.addItem("Heavy Duty");
                model.addItem("Light Duty");
                model.addItem("Box");
    
                numberOfAxles.addItem("2");
                numberOfAxles.addItem("3");
                numberOfAxles.addItem("4");
    
                model.setVisible(true);
                numberOfAxles.setVisible(true);
                break;
                
            default:
                break;
        }
    
        restoreSpecialSelections();
        
        addAllActionListeners();
        
        // Debug log to verify the page state
        System.out.println("Updated special details page for " + vehicleType + ", values: " + specialDetails);
    }
    
    private void restoreSpecialSelections() {
        if (vehicleType == null) return;
        
        switch (vehicleType) {
            case "Car":
                if (specialDetails.size() > 0 && specialDetails.get(0) != null && !specialDetails.get(0).toString().isEmpty()) {
                    model.setSelectedItem(specialDetails.get(0).toString());
                }
                if (specialDetails.size() > 1 && specialDetails.get(1) != null && !specialDetails.get(1).toString().isEmpty()) {
                    numberOfSeats.setSelectedItem(specialDetails.get(1).toString());
                }
                break;
                
            case "Bike":
                if (specialDetails.size() > 0 && specialDetails.get(0) != null && !specialDetails.get(0).toString().isEmpty()) {
                    model.setSelectedItem(specialDetails.get(0).toString());
                }
                if (specialDetails.size() > 1 && specialDetails.get(1) != null && !specialDetails.get(1).toString().isEmpty()) {
                    engineDisplacement.setSelectedItem(specialDetails.get(1).toString());
                }
                if (specialDetails.size() > 2 && specialDetails.get(2) != null && !specialDetails.get(2).toString().isEmpty()) {
                    weight.setSelectedItem(specialDetails.get(2).toString());
                }
                break;
                
            case "Truck":
                if (specialDetails.size() > 0 && specialDetails.get(0) != null && !specialDetails.get(0).toString().isEmpty()) {
                    model.setSelectedItem(specialDetails.get(0).toString());
                }
                if (specialDetails.size() > 1 && specialDetails.get(1) != null && !specialDetails.get(1).toString().isEmpty()) {
                    numberOfAxles.setSelectedItem(specialDetails.get(1).toString());
                }
                break;
        }
        
        // Debug log to verify values are being set
        System.out.println("Restored special details for " + vehicleType + ": " + specialDetails);
    }
    
    private void removeAllActionListeners() {
        ActionListener[] modelListeners = model.getActionListeners();
        for (ActionListener listener : modelListeners) {
            model.removeActionListener(listener);
        }
        
        ActionListener[] seatsListeners = numberOfSeats.getActionListeners();
        for (ActionListener listener : seatsListeners) {
            numberOfSeats.removeActionListener(listener);
        }
        
        ActionListener[] engineListeners = engineDisplacement.getActionListeners();
        for (ActionListener listener : engineListeners) {
            engineDisplacement.removeActionListener(listener);
        }
        
        ActionListener[] weightListeners = weight.getActionListeners();
        for (ActionListener listener : weightListeners) {
            weight.removeActionListener(listener);
        }
        
        ActionListener[] axlesListeners = numberOfAxles.getActionListeners();
        for (ActionListener listener : axlesListeners) {
            numberOfAxles.removeActionListener(listener);
        }
    }
    
    private void addAllActionListeners() {
        model.addActionListener(e -> {
            String selectedModel = (String) model.getSelectedItem();
            if (selectedModel != null) {
                if (specialDetails.size() > 0) {
                    specialDetails.set(0, selectedModel);
                } else {
                    specialDetails.add(selectedModel);
                }
            }
        });
    
        numberOfSeats.addActionListener(e -> {
            if (vehicleType != null && vehicleType.equals("Car")) {
                String selectedSeats = (String) numberOfSeats.getSelectedItem();
                if (selectedSeats != null) {
                    if (specialDetails.size() > 1) {
                        specialDetails.set(1, selectedSeats);
                    } else if (specialDetails.size() == 1) {
                        specialDetails.add(selectedSeats);
                    }
                }
            }
        });
    
        engineDisplacement.addActionListener(e -> {
            if (vehicleType != null && vehicleType.equals("Bike")) {
                String selectedEngine = (String) engineDisplacement.getSelectedItem();
                if (selectedEngine != null) {
                    if (specialDetails.size() > 1) {
                        specialDetails.set(1, selectedEngine);
                    } else if (specialDetails.size() == 1) {
                        specialDetails.add(selectedEngine);
                    }
                }
            }
        });
    
        weight.addActionListener(e -> {
            if (vehicleType != null && vehicleType.equals("Bike")) {
                String selectedWeight = (String) weight.getSelectedItem();
                if (selectedWeight != null) {
                    if (specialDetails.size() > 2) {
                        specialDetails.set(2, selectedWeight);
                    } else if (specialDetails.size() == 2) {
                        specialDetails.add(selectedWeight);
                    }
                }
            }
        });
    
        numberOfAxles.addActionListener(e -> {
            if (vehicleType != null && vehicleType.equals("Truck")) {
                String selectedAxles = (String) numberOfAxles.getSelectedItem();
                if (selectedAxles != null) {
                    if (specialDetails.size() > 1) {
                        specialDetails.set(1, selectedAxles);
                    } else if (specialDetails.size() == 1) {
                        specialDetails.add(selectedAxles);
                    }
                }
            }
        });
    }
    
    private void restoreMainSelections() {
        if (vehicleType != null) {
            v.setSelectedItem(vehicleType);
        }
        
        if (fuelType != null) {
            switch (fuelType) {
                case PETROL:
                    f.setSelectedItem("Petrol");
                    break;
                case DIESEL:
                    f.setSelectedItem("Diesel");
                    break;
                case ELECTRIC:
                    f.setSelectedItem("Electric");
                    break;
            }
        }
        
        if (transmissionType != null) {
            switch (transmissionType) {
                case MANUAL:
                    t.setSelectedItem("Manual");
                    break;
                case AUTOMATIC:
                    t.setSelectedItem("Automatic");
                    break;
            }
        }
    }

    private void saveCurrentSpecialDetails() {
        if (vehicleType == null) return;
        
        switch (vehicleType) {
            case "Car":
                if (model.getSelectedItem() != null) {
                    if (specialDetails.size() > 0) {
                        specialDetails.set(0, model.getSelectedItem().toString());
                    } else {
                        specialDetails.add(model.getSelectedItem().toString());
                    }
                }
                
                if (numberOfSeats.getSelectedItem() != null) {
                    if (specialDetails.size() > 1) {
                        specialDetails.set(1, numberOfSeats.getSelectedItem().toString());
                    } else if (specialDetails.size() == 1) {
                        specialDetails.add(numberOfSeats.getSelectedItem().toString());
                    }
                }
                break;
                
            case "Bike":
                if (model.getSelectedItem() != null) {
                    if (specialDetails.size() > 0) {
                        specialDetails.set(0, model.getSelectedItem().toString());
                    } else {
                        specialDetails.add(model.getSelectedItem().toString());
                    }
                }
                
                if (engineDisplacement.getSelectedItem() != null) {
                    if (specialDetails.size() > 1) {
                        specialDetails.set(1, engineDisplacement.getSelectedItem().toString());
                    } else if (specialDetails.size() == 1) {
                        specialDetails.add(engineDisplacement.getSelectedItem().toString());
                    }
                }
                
                if (weight.getSelectedItem() != null) {
                    if (specialDetails.size() > 2) {
                        specialDetails.set(2, weight.getSelectedItem().toString());
                    } else if (specialDetails.size() == 2) {
                        specialDetails.add(weight.getSelectedItem().toString());
                    }
                }
                break;
                
            case "Truck":
                if (model.getSelectedItem() != null) {
                    if (specialDetails.size() > 0) {
                        specialDetails.set(0, model.getSelectedItem().toString());
                    } else {
                        specialDetails.add(model.getSelectedItem().toString());
                    }
                }
                
                if (numberOfAxles.getSelectedItem() != null) {
                    if (specialDetails.size() > 1) {
                        specialDetails.set(1, numberOfAxles.getSelectedItem().toString());
                    } else if (specialDetails.size() == 1) {
                        specialDetails.add(numberOfAxles.getSelectedItem().toString());
                    }
                }
                break;
        }
        
        // Debug log to verify values are being saved
        System.out.println("Saved special details: " + specialDetails);
    }
    
    public boolean finalizeVehicle() {
        count = countField.getText();
        rent = rentField.getText();
        regnNumber = regnNumberField.getText();
        days = daysField.getText();
    
        // Debugging logs
        System.out.println("Debug: vehicleType = " + vehicleType);
        System.out.println("Debug: fuelType = " + fuelType);
        System.out.println("Debug: transmissionType = " + transmissionType);
        System.out.println("Debug: specialDetails = " + specialDetails);
        System.out.println("Debug: regnNumber = " + regnNumber);
        System.out.println("Debug: days = " + days);
    
        if (vehicleType == null || fuelType == null || transmissionType == null) {
            JOptionPane.showMessageDialog(frame, "Please fill in all base fields!", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    
        if (specialDetails.isEmpty() || specialDetails.contains(null)) {
            JOptionPane.showMessageDialog(frame, "Please select all special details fields!", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    
        if (!Login.getActiveUser().isAdmin()) {
            try {
                int rentalDays = Integer.parseInt(days);
                if (rentalDays <= 0) {
                    JOptionPane.showMessageDialog(frame, "Number of days must be greater than 0!", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid number of days!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
    
        if (vehicleType.equals("Car")) {
            System.out.println("Creating Car object...");
            vehicle = new Car(regnNumber, fuelType, transmissionType)
                    .setSpecialDetails(specialDetails);
            vehicle.setRentalDate(LocalDate.now());
            if (!Login.getActiveUser().isAdmin()) {
                vehicle.setReturnDate(Integer.parseInt(days));
            }
        } else if (vehicleType.equals("Bike")) {
            System.out.println("Creating Bike object...");
            vehicle = new Bike(regnNumber, fuelType, transmissionType)
                    .setSpecialDetails(specialDetails);
            vehicle.setRentalDate(LocalDate.now());
            if (!Login.getActiveUser().isAdmin()) {
                vehicle.setReturnDate(Integer.parseInt(days));
            }
        } else if (vehicleType.equals("Truck")) {
            System.out.println("Creating Truck object...");
            vehicle = new Truck(regnNumber, fuelType, transmissionType)
                    .setSpecialDetails(specialDetails);
            vehicle.setRentalDate(LocalDate.now());
            if (!Login.getActiveUser().isAdmin()) {
                vehicle.setReturnDate(Integer.parseInt(days));
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid vehicle type selected!", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    
        if (vehicle == null) {
            JOptionPane.showMessageDialog(frame, "Failed to create vehicle object!", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (Login.getActiveUser().isAdmin()) {
            try {
                double rentValue = Double.parseDouble(rent);
                vehicle.setPerDayRent(rentValue);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid rent amount!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
    
        System.out.println("Vehicle rental request: " + vehicle.getRegnNumber());
        System.out.println("Special details set: " + specialDetails);

        return true;
    }

    public void resetFields() {
        vehicle = null;
        vehicleType = null;
        fuelType = null;
        transmissionType = null;
        specialDetails.clear();
        
        v.setSelectedItem(null);
        f.setSelectedItem(null);
        t.setSelectedItem(null);
        
        countField.setText("");
        rentField.setText("");
        regnNumberField.setText("");
        daysField.setText("");
        
        model.removeAllItems();
        numberOfSeats.removeAllItems();
        engineDisplacement.removeAllItems();
        weight.removeAllItems();
        numberOfAxles.removeAllItems();
        
        count = null;
        rent = null;
        regnNumber = null;
        days = null;
        
        pages = VEHICLE_PAGES.SELECT_PAGE;
    }

    public boolean rentVehicle() {
        if (vehicle == null) {
            System.out.println("No vehicle to rent.");
            return false;
        }
        
        try (Connection conn = DriverManager.getConnection(App.getDatabase()[0], App.getDatabase()[1], App.getDatabase()[2])) {
            PreparedStatement checkStmt;
            String checkSql;
            
            boolean useTypeColumn = false;
            try {
                Statement testStmt = conn.createStatement();
                testStmt.execute("SELECT type FROM Vehicle LIMIT 1");
                useTypeColumn = true;
            } catch (SQLException e) {
                System.out.println("Type column doesn't exist: " + e.getMessage());
            }
            
            if (useTypeColumn) {
                checkSql = "SELECT vehicle_id, count, special_details FROM Vehicle " +
                          "WHERE type = ? AND fuel_type = ? AND transmission_type = ? AND count > 0";
                checkStmt = conn.prepareStatement(checkSql);
                checkStmt.setString(1, vehicleType);
                checkStmt.setString(2, vehicle.getFuelType().toString());
                checkStmt.setString(3, vehicle.getTransmissionType().toString());
            } else {
                checkSql = "SELECT vehicle_id, count, special_details FROM Vehicle " +
                          "WHERE fuel_type = ? AND transmission_type = ? AND count > 0";
                checkStmt = conn.prepareStatement(checkSql);
                checkStmt.setString(1, vehicle.getFuelType().toString());
                checkStmt.setString(2, vehicle.getTransmissionType().toString());
            }
            
            ResultSet rs = checkStmt.executeQuery();
            
            System.out.println("Searching for vehicle:");
            System.out.println("Type: " + vehicleType);
            System.out.println("Fuel: " + vehicle.getFuelType());
            System.out.println("Transmission: " + vehicle.getTransmissionType());
            
            System.out.println("Special details we want:");
            for (int i = 0; i < specialDetails.size(); i++) {
                System.out.println("Detail " + i + ": " + specialDetails.get(i));
            }
            
            boolean foundMatch = false;
            int matchedVehicleId = -1;
            
            while (rs.next()) {
                int vehicleId = rs.getInt("vehicle_id");
                String dbSpecialDetails = rs.getString("special_details");
                
                System.out.println("\nChecking vehicle ID: " + vehicleId);
                System.out.println("DB Special details: " + dbSpecialDetails);
                
                boolean detailsMatch = false;
                
                if (vehicleType.equals("Car")) {
                    detailsMatch = dbSpecialDetails.contains(specialDetails.get(0).toString()) && 
                                   dbSpecialDetails.contains(specialDetails.get(1).toString());
                } else if (vehicleType.equals("Bike")) {
                    detailsMatch = dbSpecialDetails.contains(specialDetails.get(0).toString()) && 
                                   dbSpecialDetails.contains(specialDetails.get(1).toString()) &&
                                   dbSpecialDetails.contains(specialDetails.get(2).toString());
                } else if (vehicleType.equals("Truck")) {
                    detailsMatch = dbSpecialDetails.contains(specialDetails.get(0).toString()) && 
                                   dbSpecialDetails.contains(specialDetails.get(1).toString());
                }
                
                System.out.println("Details match: " + detailsMatch);
                
                if (detailsMatch) {
                    foundMatch = true;
                    matchedVehicleId = vehicleId;
                    break;
                }
            }
            
            if (foundMatch) {
                String updateSql = "UPDATE Vehicle SET count = count - 1 WHERE vehicle_id = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setInt(1, matchedVehicleId);
                
                int rowsAffected = updateStmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    
                    String rentalSql = "INSERT INTO RentalHistory (user_id, vehicle_id, rental_date, return_date, regn_number) " +
                                       "VALUES (?, ?, ?, ?, ?)";
                    
                    PreparedStatement rentalStmt = conn.prepareStatement(rentalSql);
                    rentalStmt.setString(1, Login.getActiveUser().getUserId());
                    rentalStmt.setInt(2, matchedVehicleId);
                    rentalStmt.setDate(3, Date.valueOf(vehicle.getRentalDate()));
                    rentalStmt.setDate(4, Date.valueOf(vehicle.getReturnDate()));
                    rentalStmt.setString(5, vehicle.getRegnNumber());
                    
                    rentalStmt.executeUpdate();
                    
                    JOptionPane.showMessageDialog(frame, "Vehicle rented successfully!", 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                    resetFields();
                    return true;
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to update vehicle inventory!", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } else {
                System.out.println("\nAll vehicles in database:");
                Statement stmt = conn.createStatement();
                ResultSet allVehicles = stmt.executeQuery("SELECT * FROM Vehicle");
                while (allVehicles.next()) {
                    System.out.println("ID: " + allVehicles.getInt("vehicle_id") + 
                                      ", Type: " + (useTypeColumn ? allVehicles.getString("type") : "N/A") +
                                      ", Fuel: " + allVehicles.getString("fuel_type") +
                                      ", Trans: " + allVehicles.getString("transmission_type") +
                                      ", Count: " + allVehicles.getInt("count") +
                                      ", Details: " + allVehicles.getString("special_details"));
                }
                
                JOptionPane.showMessageDialog(frame, "Sorry, this vehicle is not available in our inventory!", 
                    "Not Available", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Database error: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public void storeVehicle() {
        if (vehicle == null) {
            System.out.println("No vehicle to store.");
            return;
        }
        
        try (Connection conn = DriverManager.getConnection(App.getDatabase()[0], App.getDatabase()[1], App.getDatabase()[2])) {
            boolean typeColumnExists = false;
            try {
                Statement testStmt = conn.createStatement();
                testStmt.execute("SELECT type FROM Vehicle LIMIT 1");
                typeColumnExists = true;
            } catch (SQLException e) {
                System.out.println("Type column doesn't exist: " + e.getMessage());
            }
            
            StringBuilder jsonBuilder = new StringBuilder("{");
            
            if (vehicleType.equals("Car")) {
                jsonBuilder.append("\"carType\":\"").append(specialDetails.get(0)).append("\",");
                jsonBuilder.append("\"numberOfSeats\":\"").append(specialDetails.get(1)).append("\"");
            } else if (vehicleType.equals("Bike")) {
                jsonBuilder.append("\"bikeType\":\"").append(specialDetails.get(0)).append("\",");
                jsonBuilder.append("\"engineDisplacement\":\"").append(specialDetails.get(1)).append("\",");
                jsonBuilder.append("\"weight\":\"").append(specialDetails.get(2)).append("\"");
            } else if (vehicleType.equals("Truck")) {
                jsonBuilder.append("\"truckType\":\"").append(specialDetails.get(0)).append("\",");
                jsonBuilder.append("\"numberOfAxles\":\"").append(specialDetails.get(1)).append("\"");
            }
            
            jsonBuilder.append("}");
            String jsonDetails = jsonBuilder.toString();
            
            if (Login.getActiveUser().isAdmin()) {
                String checkSql;
                PreparedStatement checkStmt;
                
                if (typeColumnExists) {
                    checkSql = "SELECT vehicle_id, count FROM Vehicle " +
                              "WHERE type = ? AND fuel_type = ? AND transmission_type = ? " +
                              "AND ABS(rent - ?) < 0.01";
                    checkStmt = conn.prepareStatement(checkSql);
                    checkStmt.setString(1, vehicleType);
                    checkStmt.setString(2, vehicle.getFuelType().toString());
                    checkStmt.setString(3, vehicle.getTransmissionType().toString());
                    checkStmt.setDouble(4, vehicle.getPerDayRent());
                } else {
                    checkSql = "SELECT vehicle_id, count FROM Vehicle " +
                              "WHERE fuel_type = ? AND transmission_type = ? " +
                              "AND ABS(rent - ?) < 0.01";
                    checkStmt = conn.prepareStatement(checkSql);
                    checkStmt.setString(1, vehicle.getFuelType().toString());
                    checkStmt.setString(2, vehicle.getTransmissionType().toString());
                    checkStmt.setDouble(3, vehicle.getPerDayRent());
                }
                
                ResultSet rs = checkStmt.executeQuery();
                
                boolean foundMatch = false;
                int matchedVehicleId = -1;
                
                while (rs.next()) {
                    int vehicleId = rs.getInt("vehicle_id");
                    
                    PreparedStatement detailsStmt = conn.prepareStatement("SELECT special_details FROM Vehicle WHERE vehicle_id = ?");
                    detailsStmt.setInt(1, vehicleId);
                    ResultSet detailsRs = detailsStmt.executeQuery();
                    
                    if (detailsRs.next()) {
                        String dbSpecialDetails = detailsRs.getString("special_details");
                        
                        boolean detailsMatch = compareSpecialDetails(jsonDetails, dbSpecialDetails);
                        
                        if (detailsMatch) {
                            foundMatch = true;
                            matchedVehicleId = vehicleId;
                            break;
                        }
                    }
                    detailsRs.close();
                }
                
                if (foundMatch) {
                    String updateSql;
                    PreparedStatement updateStmt;
                    
                    updateSql = "UPDATE Vehicle SET count = count + ? WHERE vehicle_id = ?";
                    updateStmt = conn.prepareStatement(updateSql);
                    updateStmt.setInt(1, Integer.parseInt(count));
                    updateStmt.setInt(2, matchedVehicleId);
                    
                    int rowsAffected = updateStmt.executeUpdate();
                    
                    if (rowsAffected > 0) {
                        String getCurrentSql = "SELECT count FROM Vehicle WHERE vehicle_id = ?";
                        PreparedStatement getCurrentStmt = conn.prepareStatement(getCurrentSql);
                        getCurrentStmt.setInt(1, matchedVehicleId);
                        
                        ResultSet currentRs = getCurrentStmt.executeQuery();
                        currentRs.next();
                        int newTotal = currentRs.getInt("count");
                        
                        JOptionPane.showMessageDialog(frame, 
                            "Vehicle inventory updated successfully! Added " + count + " more vehicles. New total: " + newTotal, 
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                        resetFields();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Failed to update vehicle inventory!", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    PreparedStatement insertStmt;
                    String insertSql;
                    
                    if (typeColumnExists) {
                        insertSql = "INSERT INTO Vehicle (type, fuel_type, transmission_type, rent, special_details, count) " +
                                   "VALUES (?, ?, ?, ?, ?, ?)";
                        insertStmt = conn.prepareStatement(insertSql);
                        insertStmt.setString(1, vehicleType);
                        insertStmt.setString(2, vehicle.getFuelType().toString());
                        insertStmt.setString(3, vehicle.getTransmissionType().toString());
                        insertStmt.setDouble(4, vehicle.getPerDayRent());
                        insertStmt.setString(5, jsonDetails);
                        insertStmt.setInt(6, Integer.parseInt(count));
                    } else {
                        insertSql = "INSERT INTO Vehicle (fuel_type, transmission_type, rent, special_details, count) " +
                                   "VALUES (?, ?, ?, ?, ?)";
                        insertStmt = conn.prepareStatement(insertSql);
                        insertStmt.setString(1, vehicle.getFuelType().toString());
                        insertStmt.setString(2, vehicle.getTransmissionType().toString());
                        insertStmt.setDouble(3, vehicle.getPerDayRent());
                        insertStmt.setString(4, jsonDetails);
                        insertStmt.setInt(5, Integer.parseInt(count));
                    }
                    
                    int rowsAffected = insertStmt.executeUpdate();
                    
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(frame, "New vehicle added successfully! Count: " + count, 
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                        resetFields();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Failed to add vehicle!", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                PreparedStatement stmt;
                String sql;
                
                if (typeColumnExists) {
                    sql = "INSERT INTO Vehicle (regn_number, type, fuel_type, transmission_type, special_details, rental_date, user) VALUES (?, ?, ?, ?, ?, ?, ?)";
                    stmt = conn.prepareStatement(sql);
                    stmt.setString(1, vehicle.getRegnNumber());
                    stmt.setString(2, vehicleType);
                    stmt.setString(3, vehicle.getFuelType().toString());
                    stmt.setString(4, vehicle.getTransmissionType().toString());
                    stmt.setString(5, jsonDetails);
                    stmt.setTimestamp(6, new java.sql.Timestamp(System.currentTimeMillis()));
                    stmt.setString(7, Login.getActiveUser().getUserId());
                } else {
                    sql = "INSERT INTO Vehicle (regn_number, fuel_type, transmission_type, special_details, rental_date, user) VALUES (?, ?, ?, ?, ?, ?)";
                    stmt = conn.prepareStatement(sql);
                    stmt.setString(1, vehicle.getRegnNumber());
                    stmt.setString(2, vehicle.getFuelType().toString());
                    stmt.setString(3, vehicle.getTransmissionType().toString());
                    stmt.setString(4, jsonDetails);
                    stmt.setTimestamp(5, new java.sql.Timestamp(System.currentTimeMillis()));
                    stmt.setString(6, Login.getActiveUser().getUserId());
                }
                
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(frame, "Vehicle request submitted successfully!", 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                    resetFields();
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to submit vehicle request!", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Database error: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean compareSpecialDetails(String json1, String json2) {
        try {
            Gson gson = new Gson();
            Map<String, String> details1 = gson.fromJson(json1, new TypeToken<Map<String, String>>() {}.getType());
            Map<String, String> details2 = gson.fromJson(json2, new TypeToken<Map<String, String>>() {}.getType());

            return details1.equals(details2);
        } catch (Exception e) {
            System.out.println("Error comparing special details: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int mX = e.getX();
        int mY = e.getY();
    
        if (pages == VEHICLE_PAGES.SELECT_PAGE) {
            if (mX >= 600 && mX <= 800 && mY >= 250 && mY <= 300) {
                hideMainComponents();
                pages = VEHICLE_PAGES.SPECIAL_DETAILS;
                updateSpecialDetails();
            } else if (mX >= 400 && mX <= 600 && mY >= 500 && mY <= 550) {
                try {
                    if (finalizeVehicle()) {
                        if (Login.getActiveUser().isAdmin()) {
                            storeVehicle();
                        } else {
                            rentVehicle();
                        }
                        window.handleMouseListeners(App.STATE.RENTAL);
                        hideAllComponents();
                        App.setState(App.STATE.RENTAL);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Error creating vehicle: " + ex.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (mX >= 800 && mX <= 1000 && mY >= 500 && mY <= 550) {
                window.handleMouseListeners(App.STATE.RENTAL);
                hideAllComponents();
                resetFields();
                App.setState(App.STATE.RENTAL);
            }
        } else if (pages == VEHICLE_PAGES.SPECIAL_DETAILS) {
            if (mX >= 400 && mX <= 600 && mY >= 500 && mY <= 550) {
                saveCurrentSpecialDetails();
                hideAllSpecialComponents();
                pages = VEHICLE_PAGES.SELECT_PAGE;
                showMainComponents();
            } else if (mX >= 800 && mX <= 1000 && mY >= 500 && mY <= 550) {
                hideAllSpecialComponents();
                pages = VEHICLE_PAGES.SELECT_PAGE;
                showMainComponents();
            }
        }
    }
    
    private void hideAllComponents() {
        hideMainComponents();
        hideAllSpecialComponents();
    }
    
    private void hideMainComponents() {
        v.setVisible(false);
        f.setVisible(false);
        t.setVisible(false);
        countField.setVisible(false);
        rentField.setVisible(false);
        regnNumberField.setVisible(false);
        daysField.setVisible(false);
    }
    
    private void showMainComponents() {
        v.setVisible(true);
        f.setVisible(true);
        t.setVisible(true);
        if (Login.getActiveUser().isAdmin()) {
            countField.setVisible(true);
            rentField.setVisible(true);
            regnNumberField.setVisible(false);
            daysField.setVisible(false);
        } else {
            regnNumberField.setVisible(true);
            daysField.setVisible(true);
            countField.setVisible(false);
            rentField.setVisible(false);
        }
    }
    
    private void hideAllSpecialComponents() {
        model.setVisible(false);
        numberOfSeats.setVisible(false);
        engineDisplacement.setVisible(false);
        weight.setVisible(false);
        numberOfAxles.setVisible(false);
    }

    public void render(Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        int textWidth, textX, textY;

        if (pages == VEHICLE_PAGES.SELECT_PAGE) {
            restoreMainSelections();
            
            hideAllSpecialComponents();
            
            showMainComponents();

            g.setColor(Color.WHITE);
            g.fillRect(600, 250, 200, 50);
            g.fillRect(400, 500, 200, 50);
            g.fillRect(800, 500, 200, 50);

            g.drawString("Vehicle Type", 500, 170);
            g.drawString("Fuel Type", 500, 410);
            g.drawString("Transmission Type", 450, 450);
            
            if (Login.getActiveUser().isAdmin()) {
                g.drawString("Count", 550, 220);
                g.drawString("Rent", 550, 370);
            } else {
                g.drawString("Registration Number", 150, 370);
                g.drawString("Rent Days", 870, 370);
            }

            textWidth = fm.stringWidth("Special Details");
            textX = 600 + (200 - textWidth) / 2;
            textY = 250 + (50 + fm.getAscent()) / 2;
            g.setColor(Color.BLACK);
            g.drawString("Special Details", textX, textY);

            textWidth = fm.stringWidth("Add");
            textX = 400 + (200 - textWidth) / 2;
            textY = 500 + (50 + fm.getAscent()) / 2;
            g.setColor(Color.BLACK);
            g.drawString("Add", textX, textY);

            textWidth = fm.stringWidth("Back");
            textX = 800 + (200 - textWidth) / 2;
            textY = 500 + (50 + fm.getAscent()) / 2;
            g.setColor(Color.BLACK);
            g.drawString("Back", textX, textY);
        } else if (pages == VEHICLE_PAGES.SPECIAL_DETAILS) {
            hideMainComponents();

            g.setColor(Color.WHITE);
            g.fillRect(400, 500, 200, 50);
            g.fillRect(800, 500, 200, 50);
            
            if (vehicleType != null) {
                if (vehicleType.equals("Car")) {
                    g.drawString("Car Type", 400, 230);
                    g.drawString("Number of Seats", 800, 230);
                } else if (vehicleType.equals("Bike")) {
                    g.drawString("Bike Type", 400, 230);
                    g.drawString("Engine Displacement", 600, 230);
                    g.drawString("Weight", 800, 230);
                } else if (vehicleType.equals("Truck")) {
                    g.drawString("Truck Type", 400, 230);
                    g.drawString("Number of Axles", 800, 230);
                }
            }

            textWidth = fm.stringWidth("Set");
            textX = 400 + (200 - textWidth) / 2;
            textY = 500 + (50 + fm.getAscent()) / 2;
            g.setColor(Color.BLACK);
            g.drawString("Set", textX, textY);

            textWidth = fm.stringWidth("Back");
            textX = 800 + (200 - textWidth) / 2;
            textY = 500 + (50 + fm.getAscent()) / 2;
            g.setColor(Color.BLACK);
            g.drawString("Back", textX, textY);
        }
    }
}