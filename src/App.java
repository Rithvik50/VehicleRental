import java.sql.*;

public class App {
	enum STATE {
		LOGIN, RENTAL, VEHICLE, PAYMENT;
	}

    public static STATE state = STATE.LOGIN;
    
    public App() {
        new Window();
    }

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/university_fest";
        String user = "root";
        String password = "Thealamo13";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            if (conn != null) {
                System.out.println("Connected to the database!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        new App();
    }
}