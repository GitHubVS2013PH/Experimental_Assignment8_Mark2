import javax.swing.*;
import java.awt.*;

/**
 * An object of type LegendPanel provides a visual guide to the set of random country elements graphed.
 */
public class LegendPanel extends JPanel {
    // Legend graphing parameters
    final int LBL_DX = 5, LBL_DY = 14; // text offset wrt label rectangle
    final int LBL_WIDTH = 180, LBL_HEIGHT = 20, MONO_SIZE = 8; // label rectangle dimensions
    final int LEG__X = 60, LEG_START_Y = 40, NEXT_LEG_Y = 25; // legend positions

    private int maxNameLength = 0;
    private LinkedList<Country_Legend> legendList = new LinkedList<>();

    public boolean add(String name, Color color) {
        name = name.trim();
        if (name.length() > maxNameLength)
            maxNameLength = name.length();

        return legendList.add(new Country_Legend(name, color));
    }

    public void graphLegends(Graphics2D g2) {
        // graph Legend
        Font oldFont = g2.getFont();
        g2.setFont(new Font("MONOSPACED",Font.PLAIN, 14));

        int countryCounter = 0;
        int lblWidth = MONO_SIZE * maxNameLength + 2 * LBL_DX;
        for (Country_Legend legend : legendList) {
            int legYCurr = LEG_START_Y + NEXT_LEG_Y * countryCounter;
            g2.setColor(legend.getColor());
            g2.fillRect(LEG__X, legYCurr, lblWidth, LBL_HEIGHT);
            g2.setColor(getComplimentColor(legend.getColor()));
            g2.drawString(legend.getName(),LEG__X + LBL_DX,legYCurr + LBL_DY);
            ++countryCounter;
        }
        g2.setFont(oldFont);
    }

    @Override
    public void paintComponent(Graphics g) {         // INVOKED AUTOMATICALLY WHEN ADDED OR RESIZED OR ...
        //super.paintComponent(g);                     // See p.558 in Core Java for explanation
        Graphics2D g2 = (Graphics2D) g;
        graphLegends(g2);
    }

    /**
     * Returns the complimentary (opposite) color.
     * Modified and adapted from: http://www.java2s.com/Code/Android/2D-Graphics/
     * Returnsthecomplimentaryoppositecolor.htm
     * @param color Color RGB color to return the compliment of
     * @return Color RGB of compliment color
     */
    public static Color getComplimentColor(Color color) {
        // get existing colors
        int red = color.getRed();
        int blue = color.getBlue();
        int green = color.getGreen();

        // find compliments
        red = (~red) & 0xff;
        blue = (~blue) & 0xff;
        green = (~green) & 0xff;

        return new Color(red, green, blue);
    }

    // inner class
    private class Country_Legend {
        private String name;
        private Color color;

        Country_Legend(String name, Color color) {
            this.name = name;
            this.color = color;
        }

        public String getName() { return name; }

        public Color getColor() { return color; }
    }
}
