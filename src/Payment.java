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
    
            try (Connection conn = DriverManager.getConnection(App.getDatabase()[0], App.getDatabase()[1], App.getDatabase()[2])) {
                conn.setAutoCommit(false);
    
                try {
                    String updateRentalHistorySql = "UPDATE RentalHistory SET rented = 1 " +
                        "WHERE user_id = ? AND rented = 0";
                    PreparedStatement updateRentalStmt = conn.prepareStatement(updateRentalHistorySql);
                    updateRentalStmt.setString(1, Login.getActiveUser().getUserId());
                    updateRentalStmt.executeUpdate();
    
                    String updateInsuranceSql = "UPDATE User SET insurance = ? WHERE username = ?";
                    PreparedStatement updateInsuranceStmt = conn.prepareStatement(updateInsuranceSql);
                    updateInsuranceStmt.setDouble(1, coverage - totalRent);
                    updateInsuranceStmt.setString(2, Login.getActiveUser().getUserId());
                    updateInsuranceStmt.executeUpdate();
    
                    conn.commit();
        
                    JOptionPane.showMessageDialog(frame,
                        "Vehicles rented successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException e) {
                    conn.rollback();
                    throw e;
                } finally {
                    conn.setAutoCommit(true);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame,
                    "Database error: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
    
            window.handleMouseListeners(App.STATE.RENTAL);
            App.setState(App.STATE.RENTAL);
        } else {
            JOptionPane.showMessageDialog(frame,
                "Insufficient coverage for renting vehicles.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int mX = e.getX();
        int mY = e.getY();

        if (mX >= 600 && mX <= 800 && mY >= 300 && mY <= 350) {
            rentVehicles();
        } else if (mX >= 600 && mX <= 800 && mY >= 400 && mY <= 450) {
            window.handleMouseListeners(App.STATE.RENTAL);
            App.setState(App.STATE.RENTAL);
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(600, 300, 200, 50);
        g.fillRect(600, 400, 200, 50);
        
        double insuranceBalance = Login.getActiveUser().getInsurance().getCoverage();
        double totalCost = calculateTotalRent();
        
        FontMetrics fm = g.getFontMetrics();
        g.setColor(Color.WHITE);
        
        String balanceText = "Insurance Balance: $" + String.format("%.2f", insuranceBalance);
        String costText = "Total Cost: $" + String.format("%.2f", totalCost);
        
        int balanceWidth = fm.stringWidth(balanceText);
        int costWidth = fm.stringWidth(costText);
        
        int balanceX = 600 + (200 - balanceWidth) / 2;
        int costX = 600 + (200 - costWidth) / 2;
        
        g.drawString(balanceText, balanceX, 240);
        g.drawString(costText, costX, 270);
        
        int textWidth, textX, textY;
        textWidth = fm.stringWidth("Pay");
        textX = 600 + (200 - textWidth) / 2;
        textY = 300 + (50 + fm.getAscent()) / 2;
        g.setColor(Color.BLACK);
        g.drawString("Pay", textX, textY);
        
        textWidth = fm.stringWidth("Back");
        textX = 600 + (200 - textWidth) / 2;
        textY = 400 + (50 + fm.getAscent()) / 2;
        g.setColor(Color.BLACK);
        g.drawString("Back", textX, textY);
    }
}
