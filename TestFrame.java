import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.geom.Rectangle2D;


public class TestFrame  extends JFrame {
//    private JLayeredPane layeredPane;
//    private JLabel label;
//    final int OFFSET = 35;

    public TestFrame(String title, int width, int height) {
        setSize(width, height);
        setLocationRelativeTo(null);

        JPanel myPanel2 = new MyPanel2();
        add(myPanel2);
        //add(myPanel3);

//        Container myPane = getContentPane();
//        myPane.setLayout(new BoxLayout(myPane, BoxLayout.PAGE_AXIS));
//
//        layeredPane = new JLayeredPane();
//        layeredPane.setPreferredSize(new Dimension(500, 500));
//        layeredPane.setBorder(BorderFactory.createTitledBorder(title));
//
//        Point origin = new Point(10, 20);
//        label = createColoredLabel("Label 1", Color.RED, origin);
//        layeredPane.add(label, new Integer(1));
//
//        origin.x += OFFSET;
//        origin.y += OFFSET;
//        label = createColoredLabel("Label 0", Color.BLUE, origin);
//        layeredPane.add(label, new Integer(0));
//
//        JPanel myPanel = new MyPanel();
//        layeredPane.add(myPanel, new Integer(2));
//
//        JPanel myPanel2 = new MyPanel2();
//        layeredPane.add(myPanel2, new Integer(3));
//
//        add(layeredPane);
    }

    //Create and set up a colored label.
    private JLabel createColoredLabel(String text,
                                      Color color,
                                      Point origin) {
        JLabel label = new JLabel(text);
        label.setVerticalAlignment(JLabel.TOP);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setOpaque(true);
        label.setBackground(color);
        label.setForeground(Color.black);
        label.setBorder(BorderFactory.createLineBorder(Color.black));
        label.setBounds(origin.x, origin.y, 140 + 5 * origin.x, 140 + 5 * origin.y);
        return label;
    }

//    class MyPanel extends JPanel {
//        final int LBL_WIDTH = 100, LBL_HEIGHT = 50;
//
//        public MyPanel() {
//            setBorder(BorderFactory.createLineBorder(Color.BLACK));
//            setOpaque(true);
//            setForeground(Color.BLACK);
//            setBounds(200,200, LBL_WIDTH*3, LBL_HEIGHT);
//        }
//
//        @Override
//        public void paintComponent(Graphics g) {         // INVOKED AUTOMATICALLY WHEN ADDED OR RESIZED OR ...
//            super.paintComponent(g);                     // See p.558 in Core Java for explanation
//            g.setColor(Color.YELLOW);
//            g.fillRect(LBL_HEIGHT/4,LBL_HEIGHT/4, LBL_WIDTH, LBL_HEIGHT/2);
//        }
//
//        @Override
//        public Dimension getPreferredSize() { return new Dimension(500,500); }
//    }

    class MyPanel2 extends JPanel {
        final int LBL_WIDTH = 100, LBL_HEIGHT = 50;
        JPanel myPanel3;

        public MyPanel2() {
            setLayout(new BorderLayout());  // new
            setBorder(BorderFactory.createLineBorder(Color.CYAN));
            setOpaque(true);
            setForeground(Color.BLACK);
            setBounds(300,300, LBL_WIDTH*2, LBL_HEIGHT);
            myPanel3 = new MyPanel3();
            add(myPanel3,BorderLayout.CENTER);
        }

        @Override
        public void paintComponent(Graphics g) {
//            super.paintComponent(g);
//            setBounds(300,300, LBL_WIDTH*2, LBL_HEIGHT);
//            g.setColor(Color.RED);
//            g.fillOval(0, 0, 50, 50);
//            myPanel3.repaint();
            Graphics2D g2 = (Graphics2D) g;
            setBounds(300,300, LBL_WIDTH*2, LBL_HEIGHT);
            Rectangle2D rect = new Rectangle2D.Double(0,0,50,50); //325
            g2.setPaint(Color.RED);
            g2.fill(rect);
            g2.draw(rect);
            g2.drawString("Warning!",400,400);
        }

        @Override
        public Dimension getPreferredSize() { return new Dimension(500,500); }
    }

    class MyPanel3 extends JPanel {
        final int LBL_WIDTH = 100, LBL_HEIGHT = 50;

        public MyPanel3() {
            setBorder(BorderFactory.createLineBorder(Color.ORANGE));
            setOpaque(true);
            setForeground(Color.BLACK);
            setBounds(200,200, LBL_WIDTH*3, LBL_HEIGHT);
        }

        @Override
        public void paintComponent(Graphics g) {
//            super.paintComponent(g);
//            setBounds(200,200, LBL_WIDTH*2, LBL_HEIGHT);
//            g.setColor(Color.YELLOW);
//            g.fillRect(50, 50, 25, 25);
            Graphics2D g2 = (Graphics2D) g;
            //setBounds(200,200, LBL_WIDTH*3, LBL_HEIGHT);
            Rectangle2D rect = new Rectangle2D.Double(LBL_HEIGHT/4,LBL_HEIGHT/4, LBL_WIDTH, LBL_HEIGHT/2);
            g2.setPaint(Color.YELLOW);
            g2.fill(rect);
            g2.draw(rect);
        }

        @Override
        public Dimension getPreferredSize() { return new Dimension(500,500); }
    }
}
