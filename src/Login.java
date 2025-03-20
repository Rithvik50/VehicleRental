import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.*;
import java.util.Base64;

public class Login extends MouseAdapter {
    private String activeUser;
    private boolean loggedIn = false;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JFrame frame;

    private final String DB_URL = "jdbc:mysql://localhost:3306/VehicleRentalSystem";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "Thealamo13";

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
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
    
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT hash, salt FROM Users WHERE username = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();
    
                if (rs.next()) {
                    String storedHash = rs.getString("hash");
                    String storedSalt = rs.getString("salt");
    
                    String computedHash = hashPassword(password, storedSalt);
                    if (computedHash.equals(storedHash)) {
                        loggedIn = true;
                        activeUser = username;
                        JOptionPane.showMessageDialog(frame, "Login successful! Welcome, " + username + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        pages = LOGIN_PAGES.MENU_PAGE;
    
                        usernameField.setVisible(false);
                        passwordField.setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid username or password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Database error!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    
        usernameField.setText("");
        passwordField.setText("");
        frame.repaint();
    }    

    private void handleRegister() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
    
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String checkQuery = "SELECT username FROM Users WHERE username = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                checkStmt.setString(1, username);
                ResultSet rs = checkStmt.executeQuery();
    
                if (rs.next()) {
                    JOptionPane.showMessageDialog(frame, "Username already exists!", "Registration Failed", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            String salt = generateSalt();
            String hash = hashPassword(password, salt);
    
            String insertQuery = "INSERT INTO Users (username, hash, salt) VALUES (?, ?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                insertStmt.setString(1, username);
                insertStmt.setString(2, hash);
                insertStmt.setString(3, salt);
                insertStmt.executeUpdate();
                JOptionPane.showMessageDialog(frame, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                pages = LOGIN_PAGES.MENU_PAGE;
    
                usernameField.setVisible(false);
                passwordField.setVisible(false);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Database error!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    
        usernameField.setText("");
        passwordField.setText("");
        frame.repaint();
    }    

    private String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    // Hashes the password using SHA-256 with salt
    private String hashPassword(String password, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest((password + salt).getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    private void handleLogOff() {
        loggedIn = false;
        activeUser = null;
        pages = LOGIN_PAGES.MENU_PAGE;
        JOptionPane.showMessageDialog(frame, "You have been logged off.", "Log Off", JOptionPane.INFORMATION_MESSAGE);
        frame.repaint();
    }

    public User getActiveUser() {
        return loggedIn ? new User(activeUser, "") : null;
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
                    usernameField.setVisible(false);
                    passwordField.setVisible(false);
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
            g.setColor(Color.WHITE);
            Font font = new Font("Arial", Font.BOLD, 30);
            g.setFont(font);
            g.drawString("Vehicle Rental System", 535, 200);
            g.setColor(Color.BLACK);
            g.setFont(fm.getFont());
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
