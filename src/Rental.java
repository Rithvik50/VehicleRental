import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Rental extends MouseAdapter {
    private Window window;

    public Rental(Window window) {
        this.window = window;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int mX = e.getX();
        int mY = e.getY();

        if (Login.getActiveUser().isAdmin()) {
            if (mX >= 600 && mX <= 800 && mY >= 300 && mY <= 350) {
                window.handleMouseListeners(App.STATE.VEHICLE);
                App.setState(App.STATE.VEHICLE);
            } else if (mX >= 600 && mX <= 800 && mY >= 400 && mY <= 450) {
                window.handleMouseListeners(App.STATE.LOGIN);
                App.setState(App.STATE.LOGIN);
            }
        } else {
            if (mX >= 600 && mX <= 800 && mY >= 300 && mY <= 350) {
                window.handleMouseListeners(App.STATE.VEHICLE);
                App.setState(App.STATE.VEHICLE);
            } else if (mX >= 600 && mX <= 800 && mY >= 400 && mY <= 450) {
                window.handleMouseListeners(App.STATE.PAYMENT);
                App.setState(App.STATE.PAYMENT);
            } else if (mX >= 600 && mX <= 800 && mY >= 500 && mY <= 550) {
                window.handleMouseListeners(App.STATE.CART);
                App.setState(App.STATE.CART);
            } else if (mX >= 600 && mX <= 800 && mY >= 600 && mY <= 650) {
                window.handleMouseListeners(App.STATE.LOGIN);
                App.setState(App.STATE.LOGIN);
            }
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        FontMetrics fm = g.getFontMetrics();
        int textWidth, textX, textY;
        if (Login.getActiveUser().isAdmin()) {
            g.fillRect(600, 300, 200, 50);
            g.fillRect(600, 400, 200, 50);

            textWidth = fm.stringWidth("Add Vehicle");
            textX = 600 + (200 - textWidth) / 2;
            textY = 300 + (50 + fm.getAscent()) / 2;
            g.setColor(Color.BLACK);
            g.drawString("Add Vehicle", textX, textY);

            textWidth = fm.stringWidth("Back");
            textX = 600 + (200 - textWidth) / 2;
            textY = 400 + (50 + fm.getAscent()) / 2;
            g.setColor(Color.BLACK);
            g.drawString("Back", textX, textY);
        } else {
            g.fillRect(600, 300, 200, 50);
            g.fillRect(600, 400, 200, 50);
            g.fillRect(600, 500, 200, 50);
            g.fillRect(600, 600, 200, 50);

            textWidth = fm.stringWidth("Select Vehicles");
            textX = 600 + (200 - textWidth) / 2;
            textY = 300 + (50 + fm.getAscent()) / 2;
            g.setColor(Color.BLACK);
            g.drawString("Select Vehicles", textX, textY);

            textWidth = fm.stringWidth("Payment");
            textX = 600 + (200 - textWidth) / 2;
            textY = 400 + (50 + fm.getAscent()) / 2;
            g.drawString("Payment", textX, textY);

            textWidth = fm.stringWidth("View Cart");
            textX = 600 + (200 - textWidth) / 2;
            textY = 500 + (50 + fm.getAscent()) / 2;
            g.setColor(Color.BLACK);
            g.drawString("View Cart", textX, textY);

            textWidth = fm.stringWidth("Back");
            textX = 600 + (200 - textWidth) / 2;
            textY = 600 + (50 + fm.getAscent()) / 2;
            g.setColor(Color.BLACK);
            g.drawString("Back", textX, textY);
        }
    }
}
