import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import javax.swing.*;

public class Payment extends MouseAdapter {
    private JFrame frame;
    private Window window;

    public Payment(JFrame frame, Window window) {
        this.frame = frame;
        this.window = window;
    }

    public double calculateTotalRent() {
        double totalCost = 0;
        for (Vehicle v : Login.getActiveUser().getRentedVehicles()) {
            if (v.getRentalDate() != null && v.getReturnDate() != null && !v.isRented()) {
                long days = java.time.temporal.ChronoUnit.DAYS.between(v.getRentalDate(), v.getReturnDate());
                totalCost += days * v.getPerDayRent();
            }
        }
        return totalCost;
    }

    public void rentVehicles() {
        double coverage = Login.getActiveUser().getInsurance().getCoverage();
        double totalRent = calculateTotalRent();
        if (coverage >= totalRent) {
            Login.getActiveUser().getInsurance().setCoverage(coverage - totalRent);
            JOptionPane.showMessageDialog(frame, "Vehicles rented successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

            String sql = "INSERT INTO RentalHistory (user_id, vehicle_id, rental_date, return_date) VALUES (?, ?, ?, ?)";
            try (Connection conn = DriverManager.getConnection(App.getDatabase()[0], App.getDatabase()[1], App.getDatabase()[2])) {
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
            App.setState(App.STATE.RENTAL);
        } else {
            JOptionPane.showMessageDialog(frame, "Insufficient coverage for renting vehicles.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int mX = e.getX();
        int mY = e.getY();

        if (mX >= 600 && mX <= 800 && mY >= 300 && mY <= 350) {
            window.handleMouseListeners(App.STATE.RENTAL);
            App.setState(App.STATE.RENTAL);
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(600, 300, 200, 50);
        FontMetrics fm = g.getFontMetrics();
        int textWidth, textX, textY;
        textWidth = fm.stringWidth("Pay");
        textX = 600 + (200 - textWidth) / 2;
        textY = 300 + (50 + fm.getAscent()) / 2;
        g.setColor(Color.BLACK);
        g.drawString("Pay", textX, textY);
    }
}
