import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JPanel implements ActionListener {
    private JFrame frame;
    private Login login;
    private RentalSystem rs;
    private double angle = 0;
    private Timer timer;
    
    public Window() {
        frame = new JFrame("Vehicle Rental System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize().width,
		Toolkit.getDefaultToolkit().getScreenSize().height);
        frame.setResizable(false);

        login = new Login(frame, this);
        rs = new RentalSystem(this);

        this.addMouseListener(login);

        this.setFocusable(true);
        this.requestFocus();
        this.setOpaque(false);

        timer = new Timer(50, this);
        timer.start();
        
        frame.add(this);
        frame.setVisible(true);
    }

    public void handleMouseListeners(App.STATE state) {
        if (state == App.STATE.LOGIN) {
            this.addMouseListener(login);
            this.removeMouseListener(rs);
        } else if (state == App.STATE.RENTAL) {
            this.addMouseListener(rs);
            this.removeMouseListener(login);
        }
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

        if (App.getState() == App.STATE.LOGIN) {
            login.render(g);
        } else if (App.getState() == App.STATE.RENTAL) {
            rs.render(g);
        }
    }
        
    @Override
    public void actionPerformed(ActionEvent e) {
        angle += 0.05;
        repaint();
    }
}
