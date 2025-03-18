public class VehicleRentalSystem {
	enum STATE {
		LOGIN, RENTAL, VEHICLE, PAYMENT;
	}

    public static STATE state = STATE.LOGIN;
    
    public VehicleRentalSystem() {
        new Window();
    }

    public static void main(String[] args) {
        new VehicleRentalSystem();
    }
}
