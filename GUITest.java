import javax.swing.*;
import java.awt.*;


public class GUITest {
    public static void main(String[] args) {
        final int WIDTH = 800, HEIGHT = 600;

        EventQueue.invokeLater(() -> {
            System.out.println("Testing TestFrame");

            TestFrame myFrame = new TestFrame("GUI Test Frame", WIDTH, HEIGHT);
            myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            myFrame.setVisible(true);
        });
    }
}
