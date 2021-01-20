import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Screen {

    private Snake snake = new Snake();

    private final int WINDOW_WIDTH = 500;
    private final int WINDOW_HEIGHT = 497;
    private static JFrame frame;

    public Screen() {
        frame = new JFrame("Snake");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT); //497 as the snake doesnt fit correctly otherwise
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(snake);
        frame.setVisible(true);
    }

    public static void closeScreen() {
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
}