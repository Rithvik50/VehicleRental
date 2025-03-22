import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VehicleHandler extends MouseAdapter {
    private Window window;

    enum VEHICLE_PAGES {
        SELECT_PAGE, ADD_PAGE
    }

    private VEHICLE_PAGES pages;

    public VehicleHandler(Window window) {
        this.window = window;
        if (Login.getActiveUser() != null && Login.getActiveUser().isAdmin()) {
            pages = VEHICLE_PAGES.ADD_PAGE;
        } else {
            pages = VEHICLE_PAGES.SELECT_PAGE;
        }
    }

    public void setPage(VEHICLE_PAGES page) {
        pages = page;
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
        FontMetrics fm = g.getFontMetrics();
        int textWidth, textX, textY;
        if (pages == VEHICLE_PAGES.SELECT_PAGE) {
            textWidth = fm.stringWidth("Vehicle Type");
            textX = 600 + (200 - textWidth) / 2;
            textY = 300 + (50 + fm.getAscent()) / 2;
            g.setColor(Color.BLACK);
            g.drawString("Vehicle Type", textX, textY);
        } else if (pages == VEHICLE_PAGES.ADD_PAGE) {

        }
    }
}
