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
    private JComboBox<String> model, numberOfSeats, engineDisplacement, weight, numberOfAxles;
    private int count;
    private double rent;
    private JTextField countField, rentField;

    enum VEHICLE_PAGES {
        SELECT_PAGE, SPECIAL_DETAILS
    }

    private VEHICLE_PAGES pages;

    public VehicleHandler(JFrame frame, Window window) {
        this.frame = frame;
        this.window = window;
        specialDetails = new ArrayList<Object>();

        pages = VEHICLE_PAGES.SELECT_PAGE;

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

        t = new JComboBox<>(new String[]{"", "Manual", "Automatic"});
        t.setBounds(600, 430, 200, 30);
        t.setSelectedIndex(-1);
        t.setVisible(false);
        frame.add(t);

        model = new JComboBox<>();
        model.setBounds(400, 250, 200, 30);
        model.setSelectedIndex(-1);
        model.setVisible(false);
        frame.add(model);

        numberOfSeats = new JComboBox<>();
        numberOfSeats.setBounds(800, 250, 200, 30);
        numberOfSeats.setSelectedIndex(-1);
        numberOfSeats.setVisible(false);
        frame.add(numberOfSeats);

        engineDisplacement = new JComboBox<>();
        engineDisplacement.setBounds(800, 250, 200, 30);
        engineDisplacement.setSelectedIndex(-1);
        engineDisplacement.setVisible(false);
        frame.add(engineDisplacement);

        weight = new JComboBox<>();
        weight.setBounds(1100, 250, 200, 30);
        weight.setSelectedIndex(-1);
        weight.setVisible(false);
        frame.add(weight);

        numberOfAxles = new JComboBox<>();
        numberOfAxles.setBounds(800, 250, 200, 30);
        numberOfAxles.setSelectedIndex(-1);
        numberOfAxles.setVisible(false);
        frame.add(numberOfAxles);

        v.addActionListener(e -> {
            vehicleType = v.getSelectedItem().toString();
            updateSpecialDetails();
        });

        f.addActionListener(e -> {
            String fuel = f.getSelectedItem().toString();
            if (fuel.equals("Petrol")) {
                fuelType = FuelType.PETROL;
            } else if (fuel.equals("Diesel")) {
                fuelType = FuelType.DIESEL;
            } else if (fuel.equals("Electric")) {
                fuelType = FuelType.ELECTRIC;
            }
        });

        t.addActionListener(e -> {
            String transmission = t.getSelectedItem().toString();
            if (transmission.equals("Manual")) {
                transmissionType = TransmissionType.MANUAL;
            } else if (transmission.equals("Automatic")) {
                transmissionType = TransmissionType.AUTOMATIC;
            }
        });

        countField = new JTextField(15);
        countField.setBounds(600, 250, 200, 30);
        countField.setVisible(false);

        rentField = new JTextField(15);
        rentField.setBounds(600, 350, 200, 30);
        rentField.setVisible(false);
    }

    public void updateSpecialDetails() {
        pages = VEHICLE_PAGES.SPECIAL_DETAILS;

        specialDetails.clear();

        if (vehicleType.equals("Car")) {
            specialDetails.add("");
            specialDetails.add("");
        } else if (vehicleType.equals("Bike")) {
            specialDetails.add("");
            specialDetails.add("");
            specialDetails.add("");
        } else if (vehicleType.equals("Truck")) {
            specialDetails.add("");
            specialDetails.add("");
        }

        if (model == null) {
            model = new JComboBox<>();
            model.setBounds(400, 250, 200, 30);
            model.setSelectedIndex(-1);
            frame.add(model);
        } else {
            model.removeAllItems();
        }
        model.addItem("");

        if (numberOfSeats == null) {
            numberOfSeats = new JComboBox<>();
            numberOfSeats.setBounds(800, 250, 200, 30);
            numberOfSeats.setSelectedIndex(-1);
            frame.add(numberOfSeats);
        } else {
            numberOfSeats.removeAllItems();
        }
        numberOfSeats.addItem("");

        if (engineDisplacement == null) {
            engineDisplacement = new JComboBox<>();
            engineDisplacement.setBounds(800, 250, 200, 30);
            engineDisplacement.setSelectedIndex(-1);
            engineDisplacement.setVisible(false);
            frame.add(engineDisplacement);
        } else {
            engineDisplacement.removeAllItems();
        }
        engineDisplacement.addItem("");

        if (weight == null) {
            weight = new JComboBox<>();
            weight.setBounds(1100, 250, 200, 30);
            weight.setSelectedIndex(-1);
            weight.setVisible(false);
            frame.add(weight);
        } else {
            weight.removeAllItems();
        }
        weight.addItem("");

        model.removeAllItems();
        model.addItem("");
        if (vehicleType.equals("Car")) {
            model.addItem("SUV");
            model.addItem("Sedan");
            model.addItem("Hatchback");
        } else if (vehicleType.equals("Bike")) {
            model.addItem("Racing");
            model.addItem("Cruiser");
            model.addItem("City");
        } else if (vehicleType.equals("Truck")) {
            model.addItem("Flatbed");
            model.addItem("Heavy Duty");
            model.addItem("Light Duty");
            model.addItem("Box");
        }
        if (model.getItemCount() > 0) {
            model.setSelectedIndex(0);  
            model.setVisible(true);
        } else {
            model.setVisible(false);
        }

        numberOfSeats.removeAllItems();
        numberOfSeats.addItem("");
        if (vehicleType.equals("Car")) {
            numberOfSeats.addItem("2");
            numberOfSeats.addItem("4");
            numberOfSeats.addItem("5");
            numberOfSeats.setVisible(true);
        } else {
            numberOfSeats.setVisible(false);
        }
        numberOfSeats.setSelectedIndex(0);

        engineDisplacement.removeAllItems();
        engineDisplacement.addItem("");
        if (vehicleType.equals("Bike")) {
            engineDisplacement.addItem("1000cc");
            engineDisplacement.addItem("1500cc");
            engineDisplacement.addItem("2000cc");
            engineDisplacement.setVisible(true);
        } else {
            engineDisplacement.setVisible(false);
        }
        engineDisplacement.setSelectedIndex(0);

        weight.removeAllItems();
        weight.addItem("");
        if (vehicleType.equals("Bike")) {
            weight.addItem("1000kg");
            weight.addItem("1500kg");
            weight.addItem("2000kg");
            weight.setVisible(true);
        } else {
            weight.setVisible(false);
        }
        weight.setSelectedIndex(0);

        numberOfAxles.removeAllItems();
        numberOfAxles.addItem("");
        if (vehicleType.equals("Truck")) {
            numberOfAxles.addItem("2");
            numberOfAxles.addItem("3");
            numberOfAxles.addItem("4");
            numberOfAxles.setVisible(true);
        } else {
            numberOfAxles.setVisible(false);
        }
        numberOfAxles.setSelectedIndex(0);
    
        model.addActionListener(e -> {
            String selectedModel = (String) model.getSelectedItem();
            if (selectedModel != null) {
                specialDetails.set(0, selectedModel);
            }
        });
        
        numberOfSeats.addActionListener(e -> {
            String selectedSeats = (String) numberOfSeats.getSelectedItem();
            if (selectedSeats != null && vehicleType.equals("Car")) {
                specialDetails.set(1, selectedSeats);
            }
        });
        
        engineDisplacement.addActionListener(e -> {
            String selectedEngine = (String) engineDisplacement.getSelectedItem();
            if (selectedEngine != null && vehicleType.equals("Bike")) {
                specialDetails.set(1, selectedEngine);
            }
        });
        
        weight.addActionListener(e -> {
            String selectedWeight = (String) weight.getSelectedItem();
            if (selectedWeight != null && vehicleType.equals("Bike")) {
                specialDetails.set(2, selectedWeight);
            }
        });
        
        numberOfAxles.addActionListener(e -> {
            String selectedAxles = (String) numberOfAxles.getSelectedItem();
            if (selectedAxles != null && vehicleType.equals("Truck")) {
                specialDetails.set(1, selectedAxles);
            }
        });
        
    }
    
    public void finalizeVehicle() {
        if (specialDetails.isEmpty() || 
            (vehicleType.equals("Car") && specialDetails.size() < 2) ||
            (vehicleType.equals("Bike") && specialDetails.size() < 3) ||
            (vehicleType.equals("Truck") && specialDetails.size() < 2)) {
            System.out.println("Please select all options before finalizing the vehicle.");
            return;
        }

        if (vehicleType == null || fuelType == null || transmissionType == null) {
            System.out.println("Please select all fields.");
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
            vehicle.setSpecialDetails(specialDetails);
            App.setState(App.STATE.RENTAL);
            storeVehicle();
        }
    }

    public void storeVehicle() {
        if (vehicle == null) {
            System.out.println("No vehicle to store.");
            return;
        }

        String sql = "INSERT INTO RentedVehicles (user_id, vehicle_id, rental_date) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(App.getDatabase()[0], App.getDatabase()[1], App.getDatabase()[2]);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, Login.getActiveUser().getUserId());
            stmt.setString(2, vehicle.getRegnNumber());
            stmt.setTimestamp(3, java.sql.Timestamp.valueOf(LocalDate.now().atStartOfDay()));

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
                finalizeVehicle();
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
        } else if (pages == VEHICLE_PAGES.SPECIAL_DETAILS) {
            if (mX >= 800 && mX <= 1000 && mY >= 500 && mY <= 550) {
                model.setVisible(false);
                numberOfSeats.setVisible(false);
                engineDisplacement.setVisible(false);
                weight.setVisible(false);
                numberOfAxles.setVisible(false);

                model.setSelectedIndex(-1);
                numberOfSeats.setSelectedIndex(-1);
                engineDisplacement.setSelectedIndex(-1);
                weight.setSelectedIndex(-1);
                numberOfAxles.setSelectedIndex(-1);
            
                pages = VEHICLE_PAGES.SELECT_PAGE;
            }
        }
    }

    public void render(Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        int textWidth, textX, textY;

        if (pages == VEHICLE_PAGES.SELECT_PAGE) {
            v.setVisible(true);
            f.setVisible(true);
            t.setVisible(true);
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
        } else if (pages == VEHICLE_PAGES.SPECIAL_DETAILS) {
            v.setVisible(false);
            f.setVisible(false);
            t.setVisible(false);
            g.setColor(Color.WHITE);
            g.fillRect(400, 500, 200, 50);
            g.fillRect(800, 500, 200, 50);
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
