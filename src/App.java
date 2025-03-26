public class App {
    private static final String[] database = {"jdbc:mysql://localhost:3306/VehicleRentalSystem", "root", "Thealamo13"};

	enum STATE {
		LOGIN, RENTAL, VEHICLE, PAYMENT;
	}

    private static STATE state = STATE.LOGIN;
    
    public App() {
        new Window();
    }

    public static String[] getDatabase() {
        return database;
    }

    public static STATE getState() {
        return state;
    }

    public static void setState(STATE state) {
        App.state = state;
    }

    public static void main(String[] args) {
        new App();
    }
}
