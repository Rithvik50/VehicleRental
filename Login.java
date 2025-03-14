import java.awt.*;

public class Login {
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
