import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.*;

public class VehicleHandler extends MouseAdapter {
    private Window window;
    private JFrame frame;
    private Vehicle vehicle;
    private String vehicleType;
    private FuelType fuelType;
    private TransmissionType transmissionType;
    private ArrayList<Object> specialDetails;
    private JComboBox<String> v, f, t;
    private JComboBox<String> model;

    enum VEHICLE_PAGES {
        SELECT_PAGE, ADD_PAGE
    }

    private VEHICLE_PAGES pages;

    public VehicleHandler(JFrame frame, Window window) {
        this.frame = frame;
        this.window = window;
        specialDetails = new ArrayList<Object>();

        if (Login.getActiveUser() != null && Login.getActiveUser().isAdmin()) {
            pages = VEHICLE_PAGES.ADD_PAGE;
        } else {
            pages = VEHICLE_PAGES.SELECT_PAGE;
        }

        v = new JComboBox<>(new String[]{"", "Car", "Bike", "Truck"});
        v.setBounds(600, 150, 200, 30);
        v.setSelectedIndex(0);
        v.setVisible(false);
        frame.add(v);

        f = new JComboBox<>(new String[]{"", "Petrol", "Diesel", "Electric"});
        f.setBounds(600, 390, 200, 30);
        f.setSelectedIndex(0);
        f.setVisible(false);
        frame.add(f);

        t = new JComboBox<>(new String[]{"", "Automatic", "Manual"});
        t.setBounds(600, 430, 200, 30);
        t.setSelectedIndex(0);
        t.setVisible(false);
        frame.add(t);

        model = new JComboBox<>();
        model.setBounds(400, 250, 200, 30);
        model.setSelectedIndex(-1);
        model.setVisible(false);
        frame.add(model);

        v.addActionListener(e -> {
            vehicleType = v.getSelectedItem().toString();
            updateSpecialDetails();
        });

        f.addActionListener(e -> {
            String fuel = f.getSelectedItem().toString();
            if (fuel == "Petrol") {
                fuelType = FuelType.PETROL;
            } else if (fuel == "Diesel") {
                fuelType = FuelType.DIESEL;
            } else if (fuel == "Electric") {
                fuelType = FuelType.ELECTRIC;
            }
        });

        t.addActionListener(e -> {
            String transmission = t.getSelectedItem().toString();
            if (transmission == "Automatic") {
                transmissionType = TransmissionType.AUTOMATIC;
            } else if (transmission == "Manual") {
                transmissionType = TransmissionType.MANUAL;
            }
        });
    }

    public void updateSpecialDetails() {
        if (model == null) {
            model = new JComboBox<>();
            model.setBounds(400, 250, 200, 30);
            model.setSelectedIndex(-1);
            frame.add(model);
        } else {
            model.removeAllItems();
        }

        model.addItem("");

        if (vehicleType == "Car") {
            model.addItem("SUV");
            model.addItem("Sedan");
            model.addItem("Hatchback");
        } else if (vehicleType == "Bike") {
            model.addItem("Racing");
            model.addItem("Cruiser");
            model.addItem("City");
        } else if (vehicleType == "Truck") {
            model.addItem("Flatbed");
            model.addItem("Heavy Duty");
            model.addItem("Light Duty");
            model.addItem("Box");
        }
    
        model.addActionListener(e -> {
            String selectedModel = (String) model.getSelectedItem();
            if (selectedModel != null) {
                if (specialDetails.isEmpty()) {
                    specialDetails.add(selectedModel);
                } else {
                    specialDetails.set(0, selectedModel);
                }
            }
        });
        model.setSelectedIndex(0);
        model.setVisible(true);
    }
    
    public void finalizeVehicle() {
        if (vehicleType == null || fuelType == null || transmissionType == null) {
            System.out.println("Please select all options before finalizing the vehicle.");
            return;
        }
    
        if (vehicleType.equals("Car")) {
            vehicle = new Car("8998", fuelType, transmissionType, 1000.0);
        } else if (vehicleType.equals("Bike")) {
            vehicle = new Bike("8998", fuelType, transmissionType, 500.0);
        } else if (vehicleType.equals("Truck")) {
            vehicle = new Truck("8998", fuelType, transmissionType, 2000.0);
        }
    
        if (vehicle != null) {
            System.out.println("Vehicle finalized: " + vehicle.getRegnNumber());
            storeRentedVehicle();
        }
    }

    public void storeRentedVehicle() {
        String sql = "INSERT INTO RentedVehicles (user_id, vehicle_id, rental_date, fuel_type, transmission_type) VALUES (?, ?, ?, ?, ?)";
    
        try (Connection conn = DriverManager.getConnection(App.getDatabase()[0], App.getDatabase()[1], App.getDatabase()[2]);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setString(1, Login.getActiveUser().getUserId());
            stmt.setString(2, vehicle.getRegnNumber());
            stmt.setTimestamp(3, java.sql.Timestamp.valueOf(LocalDate.now().atStartOfDay()));
            
            stmt.setInt(4, vehicle.getFuelType().getValue());
            stmt.setInt(5, vehicle.getTransmissionType().getValue());
    
            stmt.executeUpdate();
            System.out.println("Vehicle stored in database successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int mX = e.getX();
        int mY = e.getY();

        if (pages == VEHICLE_PAGES.SELECT_PAGE) {
            if (mX >= 400 && mX <= 600 && mY >= 500 && mY <= 550) {
                window.handleMouseListeners(App.STATE.RENTAL);
                v.setVisible(false);
                f.setVisible(false);
                t.setVisible(false);
                model.setVisible(false);
                v.setSelectedIndex(0);
                f.setSelectedIndex(0);
                t.setSelectedIndex(0);
                Login.getActiveUser().getRentedVehicles().add(vehicle);
                App.setState(App.STATE.RENTAL);
            } else if (mX >= 800 && mX <= 1000 && mY >= 500 && mY <= 550) {
                window.handleMouseListeners(App.STATE.RENTAL);
                v.setVisible(false);
                f.setVisible(false);
                t.setVisible(false);
                model.setVisible(false);
                vehicleType = null; fuelType = null; transmissionType = null;
                specialDetails.clear();
                App.setState(App.STATE.RENTAL);
            }
        } else if (pages == VEHICLE_PAGES.ADD_PAGE) {

        }
    }

    public void render(Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        int textWidth, textX, textY;

        v.setVisible(true);
        f.setVisible(true);
        t.setVisible(true);

        if (pages == VEHICLE_PAGES.SELECT_PAGE) {
            g.setColor(Color.WHITE);
            g.fillRect(400, 500, 200, 50);
            g.fillRect(800, 500, 200, 50);
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
        } else if (pages == VEHICLE_PAGES.ADD_PAGE) {
            g.setColor(Color.WHITE);
            g.fillRect(400, 500, 200, 50);
            g.fillRect(800, 500, 200, 50);
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
        }
    }
}
