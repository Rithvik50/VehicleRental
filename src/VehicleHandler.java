import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VehicleHandler extends MouseAdapter {
    private Window window;

    public VehicleHandler(Window window) {
        this.window = window;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int mX = e.getX();
        int mY = e.getY();

        if (mX >= 600 && mX <= 800 && mY >= 300 && mY <= 350) {
            window.handleMouseListeners(App.STATE.RENTAL);
            App.setState(App.STATE.RENTAL);
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(600, 300, 200, 50);
    }
}
