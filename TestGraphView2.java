import javax.swing.*;
import java.awt.*;


public class TestGraphView2 {
    public static void main(String[] args) {
        final int WIDTH = 800, HEIGHT = 600;

        EventQueue.invokeLater(() -> {
            System.out.println("Testing GraphView2");

            GraphFrame2 myFrame = new GraphFrame2("World Development Indicators", WIDTH, HEIGHT);
            myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            myFrame.setVisible(true);
        });
    }
}
