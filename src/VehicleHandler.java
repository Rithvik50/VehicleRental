import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class VehicleHandler extends MouseAdapter {
    private Window window;
    private JFrame frame;
    private Vehicle vehicle;
    private String vehicleType, fuelType, transmissionType;
    private List specialDetails;
    private JComboBox<String> v, f, t;
    private JComboBox<String> model;
    private JComboBox<String> carT;
    private JComboBox<String> bikeT;
    private JComboBox<String> truckT;

    enum VEHICLE_PAGES {
        SELECT_PAGE, ADD_PAGE
    }

    private VEHICLE_PAGES pages;

    public VehicleHandler(JFrame frame, Window window) {
        this.frame = frame;
        this.window = window;
        specialDetails = new List();

        if (Login.getActiveUser() != null && Login.getActiveUser().isAdmin()) {
            pages = VEHICLE_PAGES.ADD_PAGE;
        } else {
            pages = VEHICLE_PAGES.SELECT_PAGE;
        }

        v = new JComboBox<>(new String[]{"Car", "Bike", "Truck"});
        v.setBounds(600, 250, 200, 30);
        v.setSelectedIndex(-1);
        v.setVisible(false);
        frame.add(v);

        f = new JComboBox<>(new String[]{"Petrol", "Diesel", "Electric"});
        f.setBounds(600, 390, 200, 30);
        f.setSelectedIndex(-1);
        f.setVisible(false);
        frame.add(f);

        t = new JComboBox<>(new String[]{"Automatic", "Manual"});
        t.setBounds(600, 430, 200, 30);
        t.setSelectedIndex(-1);
        t.setVisible(false);
        frame.add(t);

        model = new JComboBox<>();
        model.setBounds(600, 350, 200, 30);
        model.setSelectedIndex(-1);
        model.setVisible(false);
        frame.add(model);

        v.addActionListener(e -> {
            vehicleType = v.getSelectedItem().toString();
        });

        f.addActionListener(e -> {
            fuelType = f.getSelectedItem().toString();
        });

        t.addActionListener(e -> {
            transmissionType = t.getSelectedItem().toString();
        });
    }

    public void addSpecialDetails() {
        if (vehicleType.equals("Car")) {
            model.setVisible(true);
        } else if (vehicleType.equals("Bike")) {

        } else if (vehicleType.equals("Truck")) {

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int mX = e.getX();
        int mY = e.getY();

        if (mX >= 400 && mX <= 600 && mY >= 500 && mY <= 550) {
            window.handleMouseListeners(App.STATE.RENTAL);
            v.setVisible(false);
            f.setVisible(false);
            t.setVisible(false);
            Login.getActiveUser().getRentedVehicles().add(vehicle);
            App.setState(App.STATE.RENTAL);
        } else if (mX >= 800 && mX <= 1000 && mY >= 500 && mY <= 550) {
            window.handleMouseListeners(App.STATE.RENTAL);
            v.setVisible(false);
            f.setVisible(false);
            t.setVisible(false);
            vehicleType = null; fuelType = null; transmissionType = null;
            specialDetails.removeAll();
            App.setState(App.STATE.RENTAL);
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
