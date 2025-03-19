import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

public class Login extends MouseAdapter {
    private String activeUser;
    private String activePassword;
    private boolean loggedIn = false;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JFrame frame;

    // Simulated user database
    private HashMap<String, String> userDatabase = new HashMap<>();

    enum LOGIN_PAGES {
        MENU_PAGE, LOGIN_PAGE, REGISTER_PAGE
    }

    private LOGIN_PAGES pages;

    public Login(JFrame frame) {
        this.frame = frame;
        pages = LOGIN_PAGES.MENU_PAGE;

        usernameField = new JTextField(15);
        usernameField.setBounds(600, 250, 200, 30);
        usernameField.setVisible(false);

        passwordField = new JPasswordField(15);
        passwordField.setBounds(600, 300, 200, 30);
        passwordField.setVisible(false);

        passwordField.addActionListener(e -> {
            if (pages == LOGIN_PAGES.LOGIN_PAGE) {
                handleLogin();
            } else if (pages == LOGIN_PAGES.REGISTER_PAGE) {
                handleRegister();
            }
        });

        frame.add(usernameField);
        frame.add(passwordField);
    }

    private void handleLogin() {
        activeUser = usernameField.getText();
        activePassword = new String(passwordField.getPassword());
    
        if (userDatabase.containsKey(activeUser) && userDatabase.get(activeUser).equals(activePassword)) {
            loggedIn = true;
            JOptionPane.showMessageDialog(frame, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            pages = LOGIN_PAGES.MENU_PAGE;
            clearFields();
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid username or password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
            usernameField.setText("");
            passwordField.setText("");
        }
    
        frame.repaint();
    }
    

    private void handleRegister() {
        String newUser = usernameField.getText();
        String newPass = new String(passwordField.getPassword());
    
        if (userDatabase.containsKey(newUser)) {
            JOptionPane.showMessageDialog(frame, "Username already exists!", "Registration Failed", JOptionPane.ERROR_MESSAGE);
            usernameField.setText("");
            passwordField.setText("");
        } else {
            userDatabase.put(newUser, newPass);
            JOptionPane.showMessageDialog(frame, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            pages = LOGIN_PAGES.MENU_PAGE;
            clearFields();
        }
    
        frame.repaint();
    }
    

    private void handleLogOff() {
        loggedIn = false;
        activeUser = null;
        activePassword = null;
        pages = LOGIN_PAGES.MENU_PAGE;
        JOptionPane.showMessageDialog(frame, "You have been logged off.", "Log Off", JOptionPane.INFORMATION_MESSAGE);
        frame.repaint();
    }

    private void clearFields() {
        usernameField.setVisible(false);
        passwordField.setVisible(false);
        usernameField.setText("");
        passwordField.setText("");
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
                    usernameField.setVisible(true);
                    passwordField.setVisible(true);
                }
            } else if (mX >= 600 && mX <= 800 && mY >= 400 && mY <= 450) {
                if (loggedIn) {
                    handleLogOff();
                } else {
                    pages = LOGIN_PAGES.REGISTER_PAGE;
                    usernameField.setVisible(true);
                    passwordField.setVisible(true);
                }
            }
        } else if (pages == LOGIN_PAGES.LOGIN_PAGE || pages == LOGIN_PAGES.REGISTER_PAGE) {
            if (mX >= 600 && mX <= 800 && mY >= 400 && mY <= 450) {
                pages = LOGIN_PAGES.MENU_PAGE;
                clearFields();
            }
        }
    }
    frame.repaint();
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
            g.fillRect(600, 400, 200, 50);

            textWidth = fm.stringWidth("Back");
            textX = 600 + (200 - textWidth) / 2;
            textY = 400 + (50 + fm.getAscent()) / 2;
            g.setColor(Color.BLACK);
            g.drawString("Back", textX, textY);

            g.setColor(Color.WHITE);
            g.drawString("Username:", 600, 240);
            g.drawString("Password:", 600, 290);
        } else if (pages == LOGIN_PAGES.REGISTER_PAGE) {
            g.fillRect(600, 400, 200, 50);

            textWidth = fm.stringWidth("Back");
            textX = 600 + (200 - textWidth) / 2;
            textY = 400 + (50 + fm.getAscent()) / 2;
            g.setColor(Color.BLACK);
            g.drawString("Back", textX, textY);

            g.setColor(Color.WHITE);
            g.drawString("Username:", 600, 240);
            g.drawString("Password:", 600, 290);
        }
    }
}
