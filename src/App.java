public class App {
	enum STATE {
		LOGIN, RENTAL, VEHICLE, PAYMENT;
	}

    private static STATE state = STATE.LOGIN;
    
    public App() {
        new Window();
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
