import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VehicleRentalSystem {
    private JFrame frame;
    private GradientPanel gradientPanel;
    private String currentScreen = "Menu";
    
    public VehicleRentalSystem() {
        frame = new JFrame("Vehicle Rental System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        
        gradientPanel = new GradientPanel();
        gradientPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e.getX(), e.getY());
            }
        });
        
        frame.add(gradientPanel);
        frame.setVisible(true);
    }

    private void handleMouseClick(int x, int y) {
        if (currentScreen.equals("Menu")) {
            if (x >= 200 && x <= 400 && y >= 100 && y <= 150) {
                currentScreen = "SelectVehicles";
            } else if (x >= 200 && x <= 400 && y >= 200 && y <= 250) {
                currentScreen = "Pay";
            }
        } else {
            if (x >= 50 && x <= 150 && y >= 300 && y <= 350) {
                currentScreen = "Menu";
            }
        }
        gradientPanel.repaint();
    }

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
            if (currentScreen.equals("Menu")) {
                g.fillRect(200, 100, 200, 50);
                g.fillRect(200, 200, 200, 50);
                g.setColor(Color.BLACK);
                g.drawString("Select Vehicles", 250, 130);
                g.drawString("Pay", 290, 230);
            } else {
                g.fillRect(50, 300, 100, 50);
                g.setColor(Color.BLACK);
                g.drawString("Back", 90, 330);
                
                g.setColor(Color.WHITE);
                g.drawString(currentScreen.equals("SelectVehicles") ? "Enter Vehicle Details" : "Proceed to Payment", 200, 180);
            }
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            angle += 0.05; // Smooth swirling motion
            repaint();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VehicleRentalSystem::new);
    }
}
