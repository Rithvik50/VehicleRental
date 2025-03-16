import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
        int mX = e.getX();
        int mY = e.getY();
        if (VehicleRentalSystem.state == VehicleRentalSystem.STATE.LOGIN) {
            if (mX >= 600 && mX <= 800 && mY >= 100 && mY <= 150) {
                System.out.println("Login clicked");
                Window window = (Window) e.getComponent();
                window.setState(VehicleRentalSystem.STATE.RENTAL);
            } else if (mX >= 600 && mX <= 800 && mY >= 200 && mY <= 250) {
                VehicleRentalSystem.state = VehicleRentalSystem.STATE.RENTAL;
            }
        }
    }

    public void render(Graphics g) {
        g.fillRect(600, 100, 200, 50);
        g.fillRect(600, 200, 200, 50);

        FontMetrics fm = g.getFontMetrics();

        int loginTextWidth = fm.stringWidth("Login");
        int loginX = 600 + (200 - loginTextWidth) / 2;
        int loginY = 100 + (50 + fm.getAscent()) / 2;
        g.setColor(Color.BLACK);
        g.drawString("Login", loginX, loginY);

        int registerTextWidth = fm.stringWidth("Register");
        int registerX = 600 + (200 - registerTextWidth) / 2;
        int registerY = 200 + (50 + fm.getAscent()) / 2;
        g.drawString("Register", registerX, registerY);
    }
}
