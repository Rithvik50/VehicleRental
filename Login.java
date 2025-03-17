import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login extends MouseAdapter {
    private boolean loggedIn;

    enum LOGIN_PAGES {
        MENU_PAGE, LOGIN_PAGE, REGISTER_PAGE
    }

    private LOGIN_PAGES pages;

    public Login() {
        pages = LOGIN_PAGES.MENU_PAGE;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int mX = e.getX();
        int mY = e.getY();

        if (VehicleRentalSystem.state == VehicleRentalSystem.STATE.LOGIN) {
            Component source = e.getComponent();
            while (!(source instanceof Window) && source != null) {
                source = source.getParent();
            }

            if (pages == LOGIN_PAGES.MENU_PAGE) {
                if (mX >= 600 && mX <= 800 && mY >= 300 && mY <= 350) {
                    pages = LOGIN_PAGES.LOGIN_PAGE;
    
                    if (source instanceof Window) {
                        Window window = (Window) source;
                        window.repaint();
                    }
                } else if (mX >= 600 && mX <= 800 && mY >= 400 && mY <= 450) {
                    pages = LOGIN_PAGES.REGISTER_PAGE;

                    if (source instanceof Window) {
                        Window window = (Window) source;
                        window.repaint();
                    }
                }
            } else if (pages == LOGIN_PAGES.LOGIN_PAGE) {
                if (mX >= 600 && mX <= 800 && mY >= 300 && mY <= 350) {
                    pages = LOGIN_PAGES.MENU_PAGE;
    
                    if (source instanceof Window) {
                        Window window = (Window) source;
                        window.repaint();
                    }
                }
            } else if (pages == LOGIN_PAGES.REGISTER_PAGE) {
                if (mX >= 600 && mX <= 800 && mY >= 300 && mY <= 350) {
                    pages = LOGIN_PAGES.MENU_PAGE;
    
                    if (source instanceof Window) {
                        Window window = (Window) source;
                        window.repaint();
                    }
                }
            }
        }
    }

    public void render(Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        if (pages == LOGIN_PAGES.MENU_PAGE) {
            g.fillRect(600, 300, 200, 50);
            g.fillRect(600, 400, 200, 50);

            int loginTextWidth = fm.stringWidth("Login");
            int loginX = 600 + (200 - loginTextWidth) / 2;
            int loginY = 300 + (50 + fm.getAscent()) / 2;
            g.setColor(Color.BLACK);
            g.drawString("Login", loginX, loginY);

            int registerTextWidth = fm.stringWidth("Register");
            int registerX = 600 + (200 - registerTextWidth) / 2;
            int registerY = 400 + (50 + fm.getAscent()) / 2;
            g.drawString("Register", registerX, registerY);
        } else if (pages == LOGIN_PAGES.LOGIN_PAGE) {
            g.fillRect(600, 300, 200, 50);

            int loginTextWidth = fm.stringWidth("Back");
            int loginX = 600 + (200 - loginTextWidth) / 2;
            int loginY = 300 + (50 + fm.getAscent()) / 2;

            g.setColor(Color.BLACK);
            g.drawString("Back", loginX, loginY);
        } else if (pages == LOGIN_PAGES.REGISTER_PAGE) {
            g.fillRect(600, 300, 200, 50);

            int loginTextWidth = fm.stringWidth("Back");
            int loginX = 600 + (200 - loginTextWidth) / 2;
            int loginY = 300 + (50 + fm.getAscent()) / 2;

            g.setColor(Color.BLACK);
            g.drawString("Back", loginX, loginY);
            g.drawString("On Rental Page", 500, 100);
        } 
    }
}
