public class VehicleRentalSystem {
	enum STATE {
		LOGIN, RENTAL, VEHICLE, PAYMENT;
	}

    public static STATE state;
    
    public VehicleRentalSystem() {
        new Window().setState(STATE.LOGIN);
    }

    public static void main(String[] args) {
        new VehicleRentalSystem();
    }
}
