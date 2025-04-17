import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class Cart extends MouseAdapter {
    private Window window;
    private List<Vehicle> rentedVehicles;
    
    public Cart(Window window) {
        this.window = window;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        int mX = e.getX();
        int mY = e.getY();
        
        if (mX >= 600 && mX <= 800 && mY >= 600 && mY <= 650) {
            window.handleMouseListeners(App.STATE.RENTAL);
            App.setState(App.STATE.RENTAL);
        } else if (mX >= 600 && mX <= 800 && mY >= 500 && mY <= 550) {
            window.handleMouseListeners(App.STATE.PAYMENT);
            App.setState(App.STATE.PAYMENT);
        }
    }
    
    public void render(Graphics g) {
        rentedVehicles = Login.getActiveUser().getRentedVehicles();
        
        int windowWidth = window.getWidth();
        int windowHeight = window.getHeight();
        int contentWidth = 800;
        int contentHeight = 600;
        int offsetX = (windowWidth - contentWidth) / 2;
        int offsetY = (windowHeight - contentHeight) / 2;
        
        g.setColor(Color.WHITE);  
        g.fillRect(600, 600, 200, 50);
        
        g.fillRect(600, 500, 200, 50);
        
        FontMetrics fm = g.getFontMetrics();
        
        int textWidth = fm.stringWidth("Back");
        int textX = 600 + (200 - textWidth) / 2;
        int textY = 600 + (50 + fm.getAscent()) / 2;
        g.setColor(Color.BLACK);
        g.drawString("Back", textX, textY);
        
        textWidth = fm.stringWidth("Checkout");
        textX = 600 + (200 - textWidth) / 2;
        textY = 500 + (50 + fm.getAscent()) / 2;
        g.setColor(Color.BLACK);
        g.drawString("Checkout", textX, textY);
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Rented Vehicles:", offsetX + 100, offsetY + 80);
        
        g.setFont(new Font("Arial", Font.BOLD, 16));
        double insuranceBalance = Login.getActiveUser().getInsurance().getCoverage();
        g.drawString("Insurance Balance: $" + String.format("%.2f", insuranceBalance), 
                     offsetX + 500, offsetY + 80);
        
        g.setFont(new Font("Arial", Font.PLAIN, 14));
        
        if (rentedVehicles.isEmpty()) {
            g.drawString("No vehicles rented.", offsetX + 100, offsetY + 130);
        } else {
            g.setFont(new Font("Arial", Font.BOLD, 14));
            g.drawString("Vehicle Details", offsetX + 100, offsetY + 120);
            g.drawString("Start Date", offsetX + 400, offsetY + 120);
            g.drawString("Return Date", offsetX + 550, offsetY + 120);
            g.drawString("Rent Amount", offsetX + 700, offsetY + 120);
            g.setFont(new Font("Arial", Font.PLAIN, 14));
            g.drawLine(offsetX + 100, offsetY + 130, offsetX + 800, offsetY + 130);
            
            int y = offsetY + 160;
            double totalCost = 0;
            for (Vehicle vehicle : rentedVehicles) {
                if (!vehicle.isRented()) {
                    g.drawString("Regn No: " + vehicle.getRegnNumber(), offsetX + 100, y);
                    g.drawString("Fuel: " + vehicle.getFuelType(), offsetX + 100, y + 20);
                    g.drawString("Trans: " + vehicle.getTransmissionType(), offsetX + 100, y + 40);
                    g.drawString(vehicle.getRentalDate().toString(), offsetX + 400, y + 20);
                    g.drawString(vehicle.getReturnDate().toString(), offsetX + 550, y + 20);
                    
                    long days = java.time.temporal.ChronoUnit.DAYS.between(
                        vehicle.getRentalDate(), vehicle.getReturnDate());
                    double vehicleCost = days * vehicle.getPerDayRent();
                    totalCost += vehicleCost;
                    
                    g.drawString("$" + String.format("%.2f", vehicleCost), offsetX + 700, y + 20);
                    g.drawLine(offsetX + 100, y + 60, offsetX + 800, y + 60);
                    y += 80;
                }
            }
            
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.drawString("Total Cost: $" + String.format("%.2f", totalCost), 
                         offsetX + 600, y + 20);
        }
    }
}
