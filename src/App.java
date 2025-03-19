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
        new App();
    }
}