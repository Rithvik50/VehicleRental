import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login extends MouseAdapter {
    private User activeUser;
    private boolean loggedIn = false;

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

        if (App.state == App.STATE.LOGIN) {
            if (pages == LOGIN_PAGES.MENU_PAGE) {
                if (mX >= 600 && mX <= 800 && mY >= 300 && mY <= 350) {
                    if (loggedIn) {
                        App.state = App.STATE.RENTAL;
                    } else {
                        pages = LOGIN_PAGES.LOGIN_PAGE;
                    }
                } else if (mX >= 600 && mX <= 800 && mY >= 400 && mY <= 450) {
                    pages = LOGIN_PAGES.REGISTER_PAGE;
                }
            } else if (pages == LOGIN_PAGES.LOGIN_PAGE) {
                if (mX >= 600 && mX <= 800 && mY >= 300 && mY <= 350) {
                    pages = LOGIN_PAGES.MENU_PAGE;
                }
            } else if (pages == LOGIN_PAGES.REGISTER_PAGE) {
                if (mX >= 600 && mX <= 800 && mY >= 300 && mY <= 350) {
                    pages = LOGIN_PAGES.MENU_PAGE;
                }
            }
        }
    }

    public void render(Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        int textWidth;
        int textX;
        int textY;

        g.setColor(Color.WHITE);
        if (pages == LOGIN_PAGES.MENU_PAGE) {
            g.fillRect(600, 300, 200, 50);
            g.fillRect(600, 400, 200, 50);
            if (loggedIn) {
                textWidth = fm.stringWidth("Enter");
                textX = 600 + (200 - textWidth) / 2;
                textY = 300 + (50 + fm.getAscent()) / 2;
                g.setColor(Color.BLACK);
                g.drawString("Enter", textX, textY);

                textWidth = fm.stringWidth("Log Off");
                textX = 600 + (200 - textWidth) / 2;
                textY = 400 + (50 + fm.getAscent()) / 2;
                g.drawString("Log Off", textX, textY);
            } else {
                textWidth = fm.stringWidth("Login");
                textX = 600 + (200 - textWidth) / 2;
                textY = 300 + (50 + fm.getAscent()) / 2;
                g.setColor(Color.BLACK);
                g.drawString("Login", textX, textY);

                textWidth = fm.stringWidth("Register");
                textX = 600 + (200 - textWidth) / 2;
                textY = 400 + (50 + fm.getAscent()) / 2;
                g.drawString("Register", textX, textY);
            }
        } else if (pages == LOGIN_PAGES.LOGIN_PAGE) {
            g.fillRect(600, 300, 200, 50);

            textWidth = fm.stringWidth("Back");
            textX = 600 + (200 - textWidth) / 2;
            textY = 300 + (50 + fm.getAscent()) / 2;

            g.setColor(Color.BLACK);
            g.drawString("Back", textX, textY);
        } else if (pages == LOGIN_PAGES.REGISTER_PAGE) {
            g.fillRect(600, 300, 200, 50);

            textWidth = fm.stringWidth("Create");
            textX = 600 + (200 - textWidth) / 2;
            textY = 300 + (50 + fm.getAscent()) / 2;

            g.setColor(Color.BLACK);
            g.drawString("Create", textX, textY);
        } 
    }
}
