import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import java.time.LocalDate;
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
        
        if (vehicleType == null || fuelType == null || transmissionType == null || (regnNumber.isEmpty() && !Login.getActiveUser().isAdmin())) {
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
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(frame, "Rent must be a valid number!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            if (vehicleType.equals("Car")) {
                Car.CarType carType = specialDetails.get(0) == null ? null : (Car.CarType) specialDetails.get(0);
                int numberOfSeats = specialDetails.get(1) == null ? 0 : Integer.parseInt((String) specialDetails.get(1));
                vehicle = new Car(fuelType, transmissionType, Double.parseDouble(rent))
                        .setCarType(carType).setNumberOfSeats(numberOfSeats).setSpecialDetails(specialDetails);
            } else if (vehicleType.equals("Bike")) {
                Bike.BikeType bikeType = specialDetails.get(0) == null ? null : (Bike.BikeType) specialDetails.get(0);
                int engineDisplacement = specialDetails.get(1) == null ? 0 : Integer.parseInt((String) specialDetails.get(1));
                double weight = specialDetails.get(2) == null ? 0 : Double.parseDouble((String) specialDetails.get(2));
                vehicle = new Bike(fuelType, transmissionType, Double.parseDouble(rent))
                        .setBikeType(bikeType).setEngineDisplacement(engineDisplacement).setWeight(weight).setSpecialDetails(specialDetails);
            } else if (vehicleType.equals("Truck")) {
                Truck.TruckType truckType = specialDetails.get(0) == null ? null : (Truck.TruckType) specialDetails.get(0);
                int numberOfAxles = specialDetails.get(1) == null ? 0 : Integer.parseInt((String) specialDetails.get(1));
                vehicle = new Truck(fuelType, transmissionType, Double.parseDouble(rent))
                        .setTruckType(truckType).setNumberOfAxles(numberOfAxles).setSpecialDetails(specialDetails);
            }
        } else {
            if (vehicleType.equals("Car")) {
                vehicle = new Car(regnNumber, fuelType, transmissionType).setSpecialDetails(specialDetails);
            } else if (vehicleType.equals("Bike")) {
                vehicle = new Bike(regnNumber, fuelType, transmissionType).setSpecialDetails(specialDetails);
            } else if (vehicleType.equals("Truck")) {
                vehicle = new Truck(regnNumber, fuelType, transmissionType).setSpecialDetails(specialDetails);
            }
        }
    
        if (vehicle != null) {
            System.out.println("Vehicle finalized: " + vehicle.getRegnNumber());
            vehicle.setSpecialDetails(specialDetails);
            storeVehicle();
            return true;
        }
        
        return false;
    }

    public void storeVehicle() {
        if (vehicle == null) {
            System.out.println("No vehicle to store.");
            return;
        }
        
        try (Connection conn = DriverManager.getConnection(App.getDatabase()[0], App.getDatabase()[1], App.getDatabase()[2])) {
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
            
            if (Login.getActiveUser().isAdmin()) {
                sql = "INSERT INTO Vehicle (fuel_type, transmission_type, rent, " +
                             "special_details, count) VALUES (?, ?, ?, ?, ?)";
                             
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, vehicle.getFuelType().toString());
                stmt.setString(2, vehicle.getTransmissionType().toString());
                stmt.setDouble(3, vehicle.getPerDayRent());
                stmt.setString(4, jsonDetails);
                stmt.setInt(5, Integer.parseInt(count));
                
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(frame, "Vehicle added successfully!", 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to add vehicle!", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                sql = "INSERT INTO Vehicle (regn_number, fuel_type, transmission_type, " +
                             "special_details, rental_date, user) VALUES (?, ?, ?, ?, ?, ?)";
                             
                stmt = conn.prepareStatement(sql);

                stmt.setString(1, vehicle.getRegnNumber());
                stmt.setString(2, vehicle.getFuelType().toString());
                stmt.setString(3, vehicle.getTransmissionType().toString());
                stmt.setString(4, jsonDetails);
                stmt.setDate(5, java.sql.Date.valueOf(LocalDate.now()));
                stmt.setString(6, Login.getActiveUser().getUserId());
                
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(frame, "Vehicle request submitted successfully!", 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
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
