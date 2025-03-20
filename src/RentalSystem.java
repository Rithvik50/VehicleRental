import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RentalSystem extends MouseAdapter {
    private List<User> users;
    private List<Vehicle> availableVehicles;

    public RentalSystem() {
        users = new ArrayList<>();
        availableVehicles = new ArrayList<>();
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void addVehicle(Vehicle vehicle) {
        availableVehicles.add(vehicle);
    }

    public void rentVehicle(String userId, String regnNumber) {
        User user = findUser(userId);
        Vehicle vehicle = findVehicle(regnNumber);

        if (user != null && vehicle != null) {
            user.rentVehicle(vehicle);
            availableVehicles.remove(vehicle);
            System.out.println("Vehicle with registration number " + regnNumber + 
                               " rented successfully to ");
        } else {
            System.out.println("Rental failed. User or vehicle not found.");
        }
    }

    private User findUser(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    private Vehicle findVehicle(String regnNumber) {
        for (Vehicle vehicle : availableVehicles) {
            if (vehicle.getRegnNumber().equals(regnNumber)) {
                return vehicle;
            }
        }
        return null;
    }

    public void displayUserRentals(String userId) {
        User user = findUser(userId);
        if (user != null) {
            user.displayRentedVehicles();
        } else {
            System.out.println("User not found.");
        }
    }

    public void displayAvailableVehicles() {
        System.out.println("Available Vehicles:");
        if (availableVehicles.isEmpty()) {
            System.out.println("No vehicles available for rent.");
        } else {
            for (Vehicle vehicle : availableVehicles) {
                vehicle.displayDetails();
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int mX = e.getX();
        int mY = e.getY();

        if (App.getState() == App.STATE.RENTAL) {
            if (mX >= 600 && mX <= 800 && mY >= 300 && mY <= 350) {
                App.setState(App.STATE.LOGIN);
            } else if (mX >= 600 && mX <= 800 && mY >= 400 && mY <= 450) {
                System.out.println("Pay clicked");
            }
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(600, 300, 200, 50);
        FontMetrics fm = g.getFontMetrics();
        int textWidth, textX, textY;
        if (Login.getActiveUser().isAdmin()) {
            textWidth = fm.stringWidth("Add Vehicle");
            textX = 600 + (200 - textWidth) / 2;
            textY = 300 + (50 + fm.getAscent()) / 2;
            g.setColor(Color.BLACK);
            g.drawString("Add Vehicle", textX, textY);
        } else {
            g.fillRect(600, 400, 200, 50);

            textWidth = fm.stringWidth("Select Vehicles");
            textX = 600 + (200 - textWidth) / 2;
            textY = 300 + (50 + fm.getAscent()) / 2;
            g.setColor(Color.BLACK);
            g.drawString("Select Vehicles", textX, textY);

            textWidth = fm.stringWidth("Pay");
            textX = 600 + (200 - textWidth) / 2;
            textY = 400 + (50 + fm.getAscent()) / 2;
            g.drawString("Pay", textX, textY);
        }
    }
}
