public class App {
    private static final String[] DATABASE = {"jdbc:mysql://localhost:3306/VehicleRentalSystem", "root", "Thealamo13"};

	enum STATE {
		LOGIN, RENTAL, VEHICLE, PAYMENT, CART;
	}

    private static STATE SYSTEM_STATE = STATE.LOGIN;

    public static String[] getDatabase() {
        return DATABASE;
    }

    public static STATE getState() {
        return SYSTEM_STATE;
    }

    public static void setState(STATE state) {
        App.SYSTEM_STATE = state;
    }

    public static void main(String[] args) {
        new Window();
    }
}
