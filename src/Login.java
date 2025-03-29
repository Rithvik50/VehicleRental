import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.*;
import java.util.Base64;

public class Login extends MouseAdapter {
    private static User activeUser;
    private boolean loggedIn = false;

    private JTextField usernameField, insuranceField;
    private JPasswordField passwordField;
    private JFrame frame;

    private Window window;

    enum LOGIN_PAGES {
        MENU_PAGE, LOGIN_PAGE, REGISTER_PAGE
    }

    private LOGIN_PAGES pages;

    public Login(JFrame frame, Window window) {
        this.frame = frame;
        this.window = window;

        pages = LOGIN_PAGES.MENU_PAGE;

        usernameField = new JTextField(15);
        usernameField.setBounds(600, 250, 200, 30);
        usernameField.setVisible(false);

        insuranceField = new JTextField(15);
        insuranceField.setBounds(600, 350, 200, 30);
        insuranceField.setVisible(false);

        passwordField = new JPasswordField(15);
        passwordField.setBounds(600, 300, 200, 30);
        passwordField.setVisible(false);

        frame.add(usernameField);
        frame.add(insuranceField);
        frame.add(passwordField);
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
    
        try (Connection conn = DriverManager.getConnection(App.getDatabase()[0], App.getDatabase()[1], App.getDatabase()[2])) {
            String query = "SELECT hash, salt, admin, insurance, coverage FROM User WHERE username = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();
    
                if (rs.next()) {
                    String storedHash = rs.getString("hash");
                    String storedSalt = rs.getString("salt");
    
                    String computedHash = hashPassword(password, storedSalt);
                    if (computedHash.equals(storedHash)) {
                        loggedIn = true;
                        activeUser = new User(username, rs.getBoolean("admin"), new Insurance(rs.getString("insurance"), rs.getDouble("coverage")));
                        JOptionPane.showMessageDialog(frame, "Login successful! Welcome, " + username + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        pages = LOGIN_PAGES.MENU_PAGE;
    
                        usernameField.setVisible(false);
                        insuranceField.setVisible(false);
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
    }    

    private void handleRegister() {
        String username = usernameField.getText();
        String insurance = insuranceField.getText();
        String password = new String(passwordField.getPassword());
    
        try (Connection conn = DriverManager.getConnection(App.getDatabase()[0], App.getDatabase()[1], App.getDatabase()[2])) {
            String checkQuery = "SELECT username FROM User WHERE username = ?";
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
    
            String insertQuery = "INSERT INTO User (username, hash, salt, insurance) VALUES (?, ?, ?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                insertStmt.setString(1, username);
                insertStmt.setString(2, hash);
                insertStmt.setString(3, salt);
                insertStmt.setString(4, insurance);
                insertStmt.executeUpdate();
                JOptionPane.showMessageDialog(frame, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                pages = LOGIN_PAGES.MENU_PAGE;
    
                usernameField.setVisible(false);
                insuranceField.setVisible(false);
                passwordField.setVisible(false);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Database error!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    
        usernameField.setText("");
        insuranceField.setText("");
        passwordField.setText("");
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
    }

    public static User getActiveUser() {
        return activeUser;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int mX = e.getX();
        int mY = e.getY();

        if (pages == LOGIN_PAGES.MENU_PAGE) {
            if (loggedIn) {
                if (mX >= 600 && mX <= 800 && mY >= 300 && mY <= 350) {
                    window.handleMouseListeners(App.STATE.RENTAL);
                    App.setState(App.STATE.RENTAL);
                } else if (mX >= 600 && mX <= 800 && mY >= 400 && mY <= 450) {
                    handleLogOff();
                }
            } else {
                if (mX >= 600 && mX <= 800 && mY >= 300 && mY <= 350) {
                    pages = LOGIN_PAGES.LOGIN_PAGE;
                    usernameField.setVisible(true);
                    passwordField.setVisible(true);
                    insuranceField.setVisible(false); // Hide insurance field for login
                } else if (mX >= 600 && mX <= 800 && mY >= 400 && mY <= 450) {
                    pages = LOGIN_PAGES.REGISTER_PAGE;
                    usernameField.setVisible(true);
                    insuranceField.setVisible(true);
                    passwordField.setVisible(true);
                }
            }
        } else if (pages == LOGIN_PAGES.REGISTER_PAGE) {
            if (mX >= 400 && mX <= 600 && mY >= 400 && mY <= 450) { // Corrected position for "Enter"
                handleRegister(); // Call register function
            } else if (mX >= 800 && mX <= 1000 && mY >= 400 && mY <= 450) {
                pages = LOGIN_PAGES.MENU_PAGE;
                clearFields();
            }
        } else if (pages == LOGIN_PAGES.LOGIN_PAGE) {
            if (mX >= 400 && mX <= 600 && mY >= 400 && mY <= 450) { // Corrected position for "Enter"
                handleLogin(); // Call login function
            } else if (mX >= 800 && mX <= 1000 && mY >= 400 && mY <= 450) {
                pages = LOGIN_PAGES.MENU_PAGE;
                clearFields();
            }
        }
    }

    private void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
        insuranceField.setText("");
        usernameField.setVisible(false);
        insuranceField.setVisible(false);
        passwordField.setVisible(false);
    }

    public void render(Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        int textWidth, textX, textY;

        g.setColor(Color.WHITE);
        if (pages == LOGIN_PAGES.MENU_PAGE) {
            g.setColor(Color.WHITE);
            Font font = new Font("Arial", Font.BOLD, 30);
            g.setFont(font);
            g.drawString("Vehicle Rental System", 535, 200);
            g.setColor(Color.BLACK);
            g.setFont(fm.getFont());

            if (loggedIn) {
                g.setColor(Color.WHITE);
                g.fillRect(600, 300, 200, 50);
                g.fillRect(600, 400, 200, 50);

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
                g.setColor(Color.WHITE);
                g.fillRect(600, 300, 200, 50);
                g.fillRect(600, 400, 200, 50);

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
            g.fillRect(400, 400, 200, 50);
            g.fillRect(800, 400, 200, 50);

            textWidth = fm.stringWidth("Enter");
            textX = 400 + (200 - textWidth) / 2;
            textY = 400 + (50 + fm.getAscent()) / 2;
            g.setColor(Color.BLACK);
            g.drawString("Enter", textX, textY);

            textWidth = fm.stringWidth("Back");
            textX = 800 + (200 - textWidth) / 2;
            textY = 400 + (50 + fm.getAscent()) / 2;
            g.setColor(Color.BLACK);
            g.drawString("Back", textX, textY);

            g.setColor(Color.WHITE);
            g.drawString("Username:", 600, 240);
            g.drawString("Password:", 600, 290);
        } else if (pages == LOGIN_PAGES.REGISTER_PAGE) {
            g.fillRect(400, 400, 200, 50);
            g.fillRect(800, 400, 200, 50);

            textWidth = fm.stringWidth("Enter");
            textX = 400 + (200 - textWidth) / 2;
            textY = 400 + (50 + fm.getAscent()) / 2;
            g.setColor(Color.BLACK);
            g.drawString("Enter", textX, textY);

            textWidth = fm.stringWidth("Back");
            textX = 800 + (200 - textWidth) / 2;
            textY = 400 + (50 + fm.getAscent()) / 2;
            g.setColor(Color.BLACK);
            g.drawString("Back", textX, textY);

            g.setColor(Color.WHITE);
            g.drawString("Username:", 600, 240);
            g.drawString("Insurance:", 600, 340);
            g.drawString("Password:", 600, 290);
        }
    }
}
