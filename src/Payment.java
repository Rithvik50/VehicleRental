import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

import javax.swing.JOptionPane;

public class Payment extends MouseAdapter {
    private JFrame frame;
    private Window window;

    public Payment(JFrame frame, Window window) {
        this.frame = frame;
        this.window = window;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int mX = e.getX();
        int mY = e.getY();

        if (mX >= 600 && mX <= 800 && mY >= 300 && mY <= 350) {
            window.handleMouseListeners(App.STATE.RENTAL);
            JOptionPane.showMessageDialog(frame, "Payment successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            App.setState(App.STATE.RENTAL);
        }
    }

    public int calculateTotalCost() {
        return 1;
    }

    public void rentVehicles() {
        if (Login.getActiveUser().getInsurance().getCoverage() >= calculateTotalCost()) {

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
