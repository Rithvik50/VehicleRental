import java.awt.*;
import java.awt.event.*;

import javax.swing.JComboBox;
import javax.swing.JFrame;

public class VehicleHandler extends MouseAdapter {
    private Window window;
    private JFrame frame;
    private Vehicle vehicle;
    private String vehicleType, regnNumber, fuelType, transmissionType;

    enum VEHICLE_PAGES {
        SELECT_PAGE, ADD_PAGE
    }

    private VEHICLE_PAGES pages;

    public VehicleHandler(JFrame frame, Window window) {
        this.frame = frame;
        this.window = window;

        if (Login.getActiveUser() != null && Login.getActiveUser().isAdmin()) {
            pages = VEHICLE_PAGES.ADD_PAGE;
        } else {
            pages = VEHICLE_PAGES.SELECT_PAGE;
        }

        
        String[] vehicleType = {"Car", "Bike", "Truck"}; 
         
        JComboBox<String> selector = new JComboBox<>(vehicleType);
        selector.setBounds(600, 250, 200, 30);
        selector.setVisible(false);
        selector.addActionListener(new ActionListener() { 
            @Override 
            public void actionPerformed(ActionEvent e) { 
                String selectedItem = (String) selector.getSelectedItem(); 
                System.out.println("Selected: " + selectedItem); 
            } 
        });

        frame.add(selector);
    }

    public void setPage(VEHICLE_PAGES page) {
        pages = page;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int mX = e.getX();
        int mY = e.getY();

        if (mX >= 600 && mX <= 800 && mY >= 500 && mY <= 550) {
            window.handleMouseListeners(App.STATE.RENTAL);
            App.setState(App.STATE.RENTAL);
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(600, 500, 200, 50);
        FontMetrics fm = g.getFontMetrics();
        int textWidth, textX, textY;
        if (pages == VEHICLE_PAGES.SELECT_PAGE) {
            textWidth = fm.stringWidth("Vehicle Type");
            textX = 600 + (200 - textWidth) / 2;
            textY = 500 + (50 + fm.getAscent()) / 2;
            g.setColor(Color.BLACK);
            g.drawString("Vehicle Type", textX, textY);
        } else if (pages == VEHICLE_PAGES.ADD_PAGE) {

        }
    }
}
