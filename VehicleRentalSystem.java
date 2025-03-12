import javax.swing.*;
import java.awt.*;

public class VehicleRentalSystem {
    public static void main(String[] args) {
        Color background = new Color(8, 124, 168);
		JFrame w = new JFrame("Vehicle Rental System");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		w.setBounds(0, 0, (int)screenSize.getWidth(), (int)screenSize.getHeight());
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		w.getContentPane().setBackground(background);
		w.setLayout(new FlowLayout(FlowLayout.CENTER));
        w.setResizable(false);
		w.setVisible(true);
    }
}
