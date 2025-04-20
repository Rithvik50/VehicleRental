public class App {
    private static final String[] DATABASE = {"jdbc:mysql://localhost:3306/VehicleRentalSystem", "root", "Thealamo13"};

	enum STATE {
		LOGIN, RENTAL, VEHICLE, PAYMENT, CART;
	}

    private static STATE state = STATE.LOGIN;

    public static String[] getDatabase() {
        return DATABASE;
    }

    public static STATE getState() {
        return state;
    }

    public static void setState(STATE state) {
        App.state = state;
    }

    public static void main(String[] args) {
        new Window();
    }
}
