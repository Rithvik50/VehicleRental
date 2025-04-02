import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Cart extends MouseAdapter {
    private Window window;

    public Cart(Window window) {
        this.window = window;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int mX = e.getX();
        int mY = e.getY();

        if (mX >= 600 && mX <= 800 && mY >= 600 && mY <= 650) {
            window.handleMouseListeners(App.STATE.RENTAL);
            App.setState(App.STATE.RENTAL);
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(600, 600, 200, 50);
        FontMetrics fm = g.getFontMetrics();
        int textWidth, textX, textY;
        textWidth = fm.stringWidth("Back");
        textX = 600 + (200 - textWidth) / 2;
        textY = 600 + (50 + fm.getAscent()) / 2;
        g.setColor(Color.BLACK);
        g.drawString("Back", textX, textY);
    }
}
