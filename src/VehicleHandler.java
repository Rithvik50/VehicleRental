import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import java.util.ArrayList;

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
    private JTextField countField, rentField, regnNumberField;
    private String count, rent, regnNumber;

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
        regnNumberField.setBounds(600, 350, 200, 30);
        regnNumberField.setVisible(false);
        frame.add(regnNumberField);
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
        
        if (vehicleType == null || fuelType == null || transmissionType == null) {
            JOptionPane.showMessageDialog(frame, "Please fill in all base fields!", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (specialDetails.isEmpty() || 
            (vehicleType.equals("Car") && specialDetails.size() < 2) ||
            (vehicleType.equals("Bike") && specialDetails.size() < 3) ||
            (vehicleType.equals("Truck") && specialDetails.size() < 2)) {
            JOptionPane.showMessageDialog(frame, "Please select all special details fields!", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        boolean hasEmptyValue = false;
        for (Object detail : specialDetails) {
            if (detail == null || detail.toString().isEmpty()) {
                hasEmptyValue = true;
                break;
            }
        }
        
        if (hasEmptyValue) {
            JOptionPane.showMessageDialog(frame, "Please select all special details fields!", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    
        if (Login.getActiveUser().isAdmin()) {
            if (count.isEmpty() || rent.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in all administrative fields!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            try {
                Double.parseDouble(rent);
                Integer.parseInt(count);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Rent and count must be valid numbers!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            if (vehicleType.equals("Car")) {
                String carTypeStr = specialDetails.get(0).toString();
                Car.CarType carType = null;
                try {
                    carType = Car.CarType.valueOf(carTypeStr.toUpperCase());
                } catch (IllegalArgumentException e) {
                    if (carTypeStr.equals("SUV")) {
                        carType = Car.CarType.SUV;
                    } else if (carTypeStr.equals("Sedan")) {
                        carType = Car.CarType.SEDAN;
                    } else if (carTypeStr.equals("Hatchback")) {
                        carType = Car.CarType.HATCHBACK;
                    }
                }
                
                int numberOfSeats = Integer.parseInt(specialDetails.get(1).toString());
                vehicle = new Car(fuelType, transmissionType, Double.parseDouble(rent))
                        .setCarType(carType).setNumberOfSeats(numberOfSeats).setSpecialDetails(specialDetails);
            } else if (vehicleType.equals("Bike")) {
                String bikeTypeStr = specialDetails.get(0).toString();
                Bike.BikeType bikeType = null;
                try {
                    bikeType = Bike.BikeType.valueOf(bikeTypeStr.toUpperCase());
                } catch (IllegalArgumentException e) {
                    if (bikeTypeStr.equals("Racing")) {
                        bikeType = Bike.BikeType.RACING;
                    } else if (bikeTypeStr.equals("Cruiser")) {
                        bikeType = Bike.BikeType.CRUISER;
                    } else if (bikeTypeStr.equals("City")) {
                        bikeType = Bike.BikeType.CITY;
                    }
                }
                
                String engineStr = specialDetails.get(1).toString();
                int engineDisplacement = Integer.parseInt(engineStr.replace("cc", ""));
                
                String weightStr = specialDetails.get(2).toString();
                double weight = Double.parseDouble(weightStr.replace("kg", ""));
                
                vehicle = new Bike(fuelType, transmissionType, Double.parseDouble(rent))
                        .setBikeType(bikeType).setEngineDisplacement(engineDisplacement).setWeight(weight).setSpecialDetails(specialDetails);
            } else if (vehicleType.equals("Truck")) {
                String truckTypeStr = specialDetails.get(0).toString();
                Truck.TruckType truckType = null;
                try {
                    truckType = Truck.TruckType.valueOf(truckTypeStr.toUpperCase().replace(" ", "_"));
                } catch (IllegalArgumentException e) {
                    if (truckTypeStr.equals("Flatbed")) {
                        truckType = Truck.TruckType.FLATBED;
                    } else if (truckTypeStr.equals("Heavy Duty")) {
                        truckType = Truck.TruckType.HEAVY_DUTY;
                    } else if (truckTypeStr.equals("Light Duty")) {
                        truckType = Truck.TruckType.LIGHT_DUTY;
                    } else if (truckTypeStr.equals("Box")) {
                        truckType = Truck.TruckType.BOX;
                    }
                }
                
                int numberOfAxles = Integer.parseInt(specialDetails.get(1).toString());
                vehicle = new Truck(fuelType, transmissionType, Double.parseDouble(rent))
                        .setTruckType(truckType).setNumberOfAxles(numberOfAxles).setSpecialDetails(specialDetails);
            }
            
            // For admin, store the new vehicle
            if (vehicle != null) {
                System.out.println("Vehicle finalized: " + vehicle.getRegnNumber());
                vehicle.setSpecialDetails(specialDetails);
                storeVehicle();
                return true;
            }
        } else {
            // For non-admin users, rent an existing vehicle
            if (regnNumber.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter a registration number!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            if (vehicleType.equals("Car")) {
                vehicle = new Car(regnNumber, fuelType, transmissionType).setSpecialDetails(specialDetails);
            } else if (vehicleType.equals("Bike")) {
                vehicle = new Bike(regnNumber, fuelType, transmissionType).setSpecialDetails(specialDetails);
            } else if (vehicleType.equals("Truck")) {
                vehicle = new Truck(regnNumber, fuelType, transmissionType).setSpecialDetails(specialDetails);
            }
            
            if (vehicle != null) {
                System.out.println("Vehicle rental request: " + vehicle.getRegnNumber());
                vehicle.setSpecialDetails(specialDetails);
                return rentVehicle(); // Call the new rent method for non-admin users
            }
        }
        
        return false;
    }

    public boolean rentVehicle() {
        if (vehicle == null) {
            System.out.println("No vehicle to rent.");
            return false;
        }
        
        try (Connection conn = DriverManager.getConnection(App.getDatabase()[0], App.getDatabase()[1], App.getDatabase()[2])) {
            // First, fetch all vehicles with matching basic properties and count > 0
            PreparedStatement checkStmt;
            String checkSql;
            
            // Check if type column exists
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
            
            // For debugging: print what we're looking for
            System.out.println("Searching for vehicle:");
            System.out.println("Type: " + vehicleType);
            System.out.println("Fuel: " + vehicle.getFuelType());
            System.out.println("Transmission: " + vehicle.getTransmissionType());
            
            // Print the special details we want
            System.out.println("Special details we want:");
            for (int i = 0; i < specialDetails.size(); i++) {
                System.out.println("Detail " + i + ": " + specialDetails.get(i));
            }
            
            boolean foundMatch = false;
            int matchedVehicleId = -1;
            int availableCount = 0;
            
            while (rs.next()) {
                int vehicleId = rs.getInt("vehicle_id");
                int count = rs.getInt("count");
                String dbSpecialDetails = rs.getString("special_details");
                
                System.out.println("\nChecking vehicle ID: " + vehicleId);
                System.out.println("DB Special details: " + dbSpecialDetails);
                
                // Now manually check if special details match
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
                    availableCount = count;
                    break;
                }
            }
            
            if (foundMatch) {
                // Update vehicle count
                String updateSql = "UPDATE Vehicle SET count = count - 1 WHERE vehicle_id = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setInt(1, matchedVehicleId);
                
                int rowsAffected = updateStmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    // Create rental history table if it doesn't exist
                    String createTableSql = "CREATE TABLE IF NOT EXISTS RentalHistory (" +
                                            "id INT PRIMARY KEY AUTO_INCREMENT, " +
                                            "user_id VARCHAR(45) NOT NULL, " +
                                            "vehicle_id INT NOT NULL, " +
                                            "rental_date DATETIME NOT NULL, " +
                                            "regn_number VARCHAR(45) NOT NULL, " +
                                            "return_date DATETIME, " +
                                            "FOREIGN KEY (user_id) REFERENCES User(username), " +
                                            "FOREIGN KEY (vehicle_id) REFERENCES Vehicle(vehicle_id))";
                    
                    PreparedStatement createTableStmt = conn.prepareStatement(createTableSql);
                    createTableStmt.executeUpdate();
                    
                    // Insert rental record
                    String rentalSql = "INSERT INTO RentalHistory (user_id, vehicle_id, rental_date, regn_number) " +
                                       "VALUES (?, ?, ?, ?)";
                    
                    PreparedStatement rentalStmt = conn.prepareStatement(rentalSql);
                    rentalStmt.setString(1, Login.getActiveUser().getUserId());
                    rentalStmt.setInt(2, matchedVehicleId);
                    rentalStmt.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
                    rentalStmt.setString(4, vehicle.getRegnNumber());
                    
                    rentalStmt.executeUpdate();
                    
                    JOptionPane.showMessageDialog(frame, "Vehicle rented successfully!", 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                    return true;
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to update vehicle inventory!", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } else {
                // Optional: Dump all vehicles in database for debugging
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
            // First, check if the type column exists, and add it if it doesn't
            try {
                DatabaseMetaData md = conn.getMetaData();
                ResultSet rs = md.getColumns(null, null, "Vehicle", "type");
                
                if (!rs.next()) {
                    // The 'type' column doesn't exist, so add it
                    Statement alterStmt = conn.createStatement();
                    alterStmt.execute("ALTER TABLE Vehicle ADD COLUMN type VARCHAR(45)");
                    
                    // Update existing records with appropriate vehicle types based on special_details
                    Statement updateStmt = conn.createStatement();
                    updateStmt.execute("UPDATE Vehicle SET type = 'Car' WHERE special_details LIKE '%carType%'");
                    updateStmt.execute("UPDATE Vehicle SET type = 'Bike' WHERE special_details LIKE '%bikeType%'");
                    updateStmt.execute("UPDATE Vehicle SET type = 'Truck' WHERE special_details LIKE '%truckType%'");
                    
                    System.out.println("Added 'type' column to Vehicle table");
                }
            } catch (SQLException e) {
                System.out.println("Error checking or adding 'type' column: " + e.getMessage());
                // Continue with original query without type column
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
    
            PreparedStatement stmt = null;
            String sql = "";
            
            boolean typeColumnExists = false;
            try {
                Statement testStmt = conn.createStatement();
                testStmt.execute("SELECT type FROM Vehicle LIMIT 1");
                typeColumnExists = true;
            } catch (SQLException e) {
                // Type column doesn't exist
                System.out.println("Type column doesn't exist: " + e.getMessage());
            }
            
            if (Login.getActiveUser().isAdmin()) {
                if (typeColumnExists) {
                    sql = "INSERT INTO Vehicle (type, fuel_type, transmission_type, rent, special_details, count) VALUES (?, ?, ?, ?, ?, ?)";
                    stmt = conn.prepareStatement(sql);
                    stmt.setString(1, vehicleType);
                    stmt.setString(2, vehicle.getFuelType().toString());
                    stmt.setString(3, vehicle.getTransmissionType().toString());
                    stmt.setDouble(4, vehicle.getPerDayRent());
                    stmt.setString(5, jsonDetails);
                    stmt.setInt(6, Integer.parseInt(count));
                } else {
                    sql = "INSERT INTO Vehicle (fuel_type, transmission_type, rent, special_details, count) VALUES (?, ?, ?, ?, ?)";
                    stmt = conn.prepareStatement(sql);
                    stmt.setString(1, vehicle.getFuelType().toString());
                    stmt.setString(2, vehicle.getTransmissionType().toString());
                    stmt.setDouble(3, vehicle.getPerDayRent());
                    stmt.setString(4, jsonDetails);
                    stmt.setInt(5, Integer.parseInt(count));
                }
            } else {
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
            }
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                if (Login.getActiveUser().isAdmin()) {
                    JOptionPane.showMessageDialog(frame, "Vehicle added successfully!", 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Vehicle request submitted successfully!", 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                if (Login.getActiveUser().isAdmin()) {
                    JOptionPane.showMessageDialog(frame, "Failed to add vehicle!", 
                        "Error", JOptionPane.ERROR_MESSAGE);
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
                vehicleType = null;
                fuelType = null;
                transmissionType = null;
                specialDetails.clear();
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
    }
    
    private void showMainComponents() {
        v.setVisible(true);
        f.setVisible(true);
        t.setVisible(true);
        if (Login.getActiveUser().isAdmin()) {
            countField.setVisible(true);
            rentField.setVisible(true);
        } else {
            regnNumberField.setVisible(true);
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
                g.drawString("Registration Number", 450, 370);
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
