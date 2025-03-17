import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
        int mX = e.getX();
        int mY = e.getY();

        if (VehicleRentalSystem.state == VehicleRentalSystem.STATE.LOGIN) {
            if (mX >= 600 && mX <= 800 && mY >= 300 && mY <= 350) {
                System.out.println("Login clicked");

                Component source = e.getComponent();
                while (!(source instanceof Window) && source != null) {
                    source = source.getParent();
                }

                if (source instanceof Window) {
                    Window window = (Window) source;
                    window.setState(VehicleRentalSystem.STATE.RENTAL);
                    window.revalidate();  // Ensure UI updates properly
                    window.repaint();
                }
            } else if (mX >= 600 && mX <= 800 && mY >= 400 && mY <= 450) {
                System.out.println("Register clicked");

                VehicleRentalSystem.state = VehicleRentalSystem.STATE.RENTAL;
            }
        }
    }

    public void render(Graphics g) {
        g.fillRect(600, 300, 200, 50);
        g.fillRect(600, 400, 200, 50);

        FontMetrics fm = g.getFontMetrics();

        int loginTextWidth = fm.stringWidth("Login");
        int loginX = 600 + (200 - loginTextWidth) / 2;
        int loginY = 300 + (50 + fm.getAscent()) / 2;
        g.setColor(Color.BLACK);
        g.drawString("Login", loginX, loginY);

        int registerTextWidth = fm.stringWidth("Register");
        int registerX = 600 + (200 - registerTextWidth) / 2;
        int registerY = 400 + (50 + fm.getAscent()) / 2;
        g.drawString("Register", registerX, registerY);
    }
}
