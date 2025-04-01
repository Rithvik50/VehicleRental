import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

public class Cart extends MouseAdapter {
    private JFrame frame;
    private Window window;

    public Cart(JFrame frame, Window window) {
        this.frame = frame;
        this.window = window;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int mX = e.getX();
        int mY = e.getY();
    }

    public void render(Graphics g) {

    }
}
