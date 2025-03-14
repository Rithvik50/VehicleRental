import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Window {
    private JFrame frame;
    private GradientPanel gradientPanel;
    private Login login;
    private RentalSystem rs;
    
    public Window() {
        frame = new JFrame("Vehicle Rental System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize().width,
		Toolkit.getDefaultToolkit().getScreenSize().height);
        frame.setResizable(false);
        
        gradientPanel = new GradientPanel();
        frame.add(gradientPanel);
        frame.setVisible(true);
    }

    // private void handleMouseClick(int x, int y) {
    //     if (VehicleRentalSystem.state == VehicleRentalSystem.STATE.RENTAL) {
    //         if (x >= 200 && x <= 400 && y >= 100 && y <= 150) {
    //             currentScreen = "SelectVehicles";
    //         } else if (x >= 200 && x <= 400 && y >= 200 && y <= 250) {
    //             currentScreen = "Pay";
    //         }
    //     } else {
    //         if (x >= 50 && x <= 150 && y >= 300 && y <= 350) {
    //             currentScreen = "Menu";
    //         }
    //     }
    //     gradientPanel.repaint();
    // }

    private class GradientPanel extends JPanel implements ActionListener {
        private double angle = 0;
        private Timer timer;
        
        public GradientPanel() {
            timer = new Timer(50, this);
            timer.start();
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            int width = getWidth();
            int height = getHeight();
            int centerX = width / 2;
            int centerY = height / 2;
            int radius = Math.min(width, height) / 3;
            
            int xOffset = (int) (centerX + radius * Math.cos(angle)) - width / 4;
            int yOffset = (int) (centerY + radius * Math.sin(angle)) - height / 4;
            
            Color color1 = Color.BLACK;
            Color color2 = Color.RED;
            GradientPaint gp = new GradientPaint(xOffset, yOffset, color1, xOffset + width / 2, yOffset + height / 2, color2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, width, height);

            g.setColor(Color.WHITE);
            if (VehicleRentalSystem.state == VehicleRentalSystem.STATE.LOGIN) {
                login.render(g);
            } else if (VehicleRentalSystem.state == VehicleRentalSystem.STATE.RENTAL) {
                rs.render(g);
            }
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            angle += 0.05;
            repaint();
        }
    }
}
