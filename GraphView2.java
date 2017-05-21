import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

/**
 * One object of this class graphs the LinkedList country data.
 */
public class GraphView2 extends JPanel {
    private Font font;
    private int numYears;
    private int plottedXmin, plottedXmax, plottedYmin, plottedYmax;

    final private LegendPanel pointLegends = new LegendPanel();
    final private LinkedList<ColoredPoint> graphPoints = new LinkedList<>();  // STRUCTURE SAME AS LEGENDS???

    // Data plotting parameters
    private int dataMinX, dataMaxX;
    private double dataMinY = 0.0, dataMaxY;
    final private int YEARS_FOR_MAX = 10;
    final private boolean CONNECT_DOTS = true;
    final private double TOP_Y_VALUE_DEFAULT = 200.0;
    final int MARGIN = 40, TICK_SIZE = 14, LINE_WIDTH = 2;
    final int POINT_SIZE = 8, HALF_POINT = POINT_SIZE / 2;
    final int Y_LBL_SHIFT_X = 38, Y_LBL_SHIFT_Y = 5, X_LBL_SHIFT_Y = 13;
    final int MAX_X_INTERVALS = 10, NUM_Y_INTERVALS = 10;

    // Legend color scheme
    final private static Color[] colorArray = {Color.black, Color.blue, Color.cyan,
            Color.darkGray, Color.green, Color.lightGray, Color.magenta,
            Color.orange, Color.pink, Color.red, Color.yellow};

    /**
     * Constructor.
     * @param width frame width.
     * @param height frame height.
     * @param countries LinkeList of Country objects for graphing.
     */
    public GraphView2(int width, int height, LinkedList<Country> countries) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setOpaque(true);
        //setForeground(Color.BLACK);
        setBounds(0,0, width, height);
        setPreferredSize(new Dimension(width, height)); // sets size for pack()
        font = new Font("Serif", Font.PLAIN, 11);

        setMapAndAxisParameters(width, height, countries);
        mapValues(countries);

        JPanel myPanel2 = new GraphPanel();
        add(myPanel2);
        //add(myPanel3);
    }

    private void setMapAndAxisParameters(int width, int height, LinkedList<Country> countries) {
        // map method parameters
        plottedXmin = MARGIN;
        plottedYmin = height - MARGIN;
        plottedXmax = width - MARGIN;
        plottedYmax = MARGIN;

        // Set X-axis min and max
        dataMinX = countries.getIndex(0).getStartYear();
        dataMaxX = countries.getIndex(0).getEndYear();
        numYears = dataMaxX - dataMinX + 1;

        // Set Y-axis max (Y min already set as 0.0)
        int yearsToSkip = numYears - Math.min(numYears, YEARS_FOR_MAX);
        double dataMax = 0.0;
        for(Country country : countries) {
            SubscriptionYear[] subYear = country.getSubscriptions();
            for(int i = yearsToSkip; i < numYears; ++i)
                dataMax = Math.max(dataMax, subYear[i].getSubscriptions());
        }
        dataMaxY = findTopYValue(dataMax); // "round up" to next whole value
    }

    private void mapValues(LinkedList<Country> countries) {
        int countryCntr = 0;
        for(Country country : countries) {
            SubscriptionYear[] subYears = country.getSubscriptions();
            Color pntColor = colorArray[countryCntr % colorArray.length];
            for(SubscriptionYear subYear : subYears) {
                double mappedX = map(subYear.getYear(), dataMinX, dataMaxX, plottedXmin, plottedXmax) - HALF_POINT;
                double mappedY = map(subYear.getSubscriptions(), dataMinY, dataMaxY, plottedYmin, plottedYmax) - HALF_POINT;
                ColoredPoint tempPnt = new ColoredPoint(pntColor, mappedX, mappedY, subYear.getYear(), subYear.getSubscriptions());
                graphPoints.add(tempPnt);
            }
            pointLegends.add(country.getName(), pntColor);
            ++countryCntr;
        }
    }

    static public final double map(double value, double dataMin, double dataMax, double plottedMin, double plottedMax) {
        return plottedMin + (plottedMax - plottedMin) * ((value - dataMin) / (dataMax - dataMin));
    }

    /**
     * One object of this class graphs the country cellular subscription and
     * adds its color legend.
     */
    class GraphPanel extends JPanel {
        public GraphPanel() {
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createLineBorder(Color.BLACK));
            //setOpaque(true);
            //setForeground(Color.BLACK);
            setBounds(0,0, getWidth(), getHeight());
            //JPanel myPanel3 = new MyPanel3();
            //add(myPanel3,BorderLayout.CENTER); // <--------------- Legends to added like this
            add(pointLegends, BorderLayout.CENTER); // adds legend panel to graph panel
        }

        /**
         * Paints axes and plots points for cellular subscription graph.
         * @param g graphics environment.
         */
        @Override
        public void paintComponent(Graphics g) {
//            super.paintComponent(g);
//            setBounds(300,300, LBL_WIDTH*2, LBL_HEIGHT);
//            g.setColor(Color.RED);
//            g.fillOval(0, 0, 50, 50);
//            myPanel3.repaint();
            Graphics2D g2 = (Graphics2D) g;
//            Rectangle2D rect = new Rectangle2D.Double(0,0,50,50); //325
//            g2.setPaint(Color.RED);
//            g2.fill(rect);
//            g2.draw(rect);
//            g2.drawString("Warning!",400,400);

            // draw x-axis and ticks
            g2.setPaint(Color.BLACK);
            g2.drawLine(plottedXmin, plottedYmin, plottedXmax, plottedYmin);
            DrawXAxisTicksAndLabels(numYears, dataMinX, g2); // dataMinX is startYear

            // draw y-axis and ticks
            g2.drawLine(plottedXmin, plottedYmin, plottedXmin, plottedYmax);
            int numIntervals = findNumberYearIntervals(numYears);
            DrawYAxisTicksAndLabels(numIntervals, dataMaxY, g2, false);

            // graphs points in graphPoints LinkedList
            int startX = 0, startY = 0;
            Color colorFlag = Color.white;
            Color oldColor = g.getColor();
            g2.setStroke(new BasicStroke(LINE_WIDTH));
            for (ColoredPoint currPnt : graphPoints) {
                g2.setColor(currPnt.getColor());
                boolean firstPoint = !colorFlag.equals(currPnt.getColor()); // flags next plot
                g2.fillOval((int)currPnt.getX(), (int)currPnt.getY(), POINT_SIZE, POINT_SIZE);
                if (CONNECT_DOTS) {
                    if (!firstPoint)
                        g2.draw(new Line2D.Float(startX + HALF_POINT, startY + HALF_POINT,
                                (int)currPnt.getX() + HALF_POINT, (int)currPnt.getY() + HALF_POINT));
//                        g2.drawLine(startX + HALF_POINT, startY + HALF_POINT,
//                                (int)currPnt.getX() + HALF_POINT, (int)currPnt.getY() + HALF_POINT);
                    else
                        colorFlag = currPnt.getColor();

                    startX = (int)currPnt.getX();
                    startY = (int)currPnt.getY();
                }
            }

            //pointLegends.graphLegends(g);
            //add(pointLegends); // doesn't add legend
            g2.setColor(oldColor);
        }

//        @Override
//        public Dimension getPreferredSize() { return new Dimension(500,500); }
    }

    /**
     * Draws X-axis with tick marks and year labels.
     * @param numYears Number of years for X-axis.
     * @param startYear Starting year for X-axis.
     * @param g2 graphics environment.
     */
    private void DrawXAxisTicksAndLabels(int numYears, int startYear, Graphics2D g2) {
        if (numYears <= 1)
            return;

        int numYearLabels =  findNumberYearIntervals(numYears);
        int yearsPerInterval = findXIntervalSize(numYears, numYearLabels);
        double spacing = (plottedXmax - plottedXmin) / ((double)numYearLabels);
        int tickTop = plottedYmin - TICK_SIZE / 2;
        int tickBtm = plottedYmin + TICK_SIZE / 2;

        for (int i = 0; i <= numYearLabels; ++i) {
            int xPos = plottedXmin + (int) (i * spacing);
            g2.drawLine(xPos, tickTop, xPos, tickBtm);
            g2.drawString(Integer.toString(startYear + i * yearsPerInterval), xPos - X_LBL_SHIFT_Y, plottedYmin + 2*TICK_SIZE);
        }
    }

    /**
     * Returns the number of year intervals for the X-axis such that it is a
     * multiple of 2 or 5 (which includes 10) but no more than MAX_X_INTERVALS.
     * @param numYears Number of years to be represented on X-axis.
     * @return specified integer.
     */
    private int findNumberYearIntervals(int numYears) {
        int numIntervals = numYears - 1;
        if (numIntervals <= MAX_X_INTERVALS)
            return numIntervals;

        while (numIntervals > MAX_X_INTERVALS) {
            int tempIntval = numIntervals / 2;
            if (tempIntval < MAX_X_INTERVALS || (tempIntval == MAX_X_INTERVALS && numIntervals % 2 == 0))
                return numIntervals / 2 + ((numYears - 1) % ((numYears - 1) / (numIntervals / 2)) == 0 ? 0 : 1);

            tempIntval = numIntervals / 5;
            if (tempIntval < MAX_X_INTERVALS || (tempIntval == MAX_X_INTERVALS && numIntervals % 5 == 0))
                return numIntervals / 5 + ((numYears - 1) % ((numYears - 1) / (numIntervals / 5)) == 0 ? 0 : 1);

            numIntervals /= 10;
        }
        return numIntervals + ((numYears - 1) % ((numYears - 1) / numIntervals) == 0 ? 0 : 1); // FIX THIS, LIKE THE OTHERS
    }

    /**
     * Returns number of years in each interval.
     * @param numYears total number of years to be represented.
     * @param numIntervals number of intervals required.
     * @return specified integer.
     */
    private int findXIntervalSize(int numYears, int numIntervals) {
        int interval = (numYears - 1) / numIntervals;
        if ((numYears - 1) % numIntervals == 0)
            return interval;
        return (int)Math.round(interval * (1.0 + 1.0/numIntervals) + 0.5);
    }

    /**
     *
     * @param numIntervals Number of intervals for the Y-axis.
     * @param maxY either the largest Y value in the data set if findTop is to
     *             be true; or the maximum value labeled on Y-axis if findTop
     *             is to be false.
     * @param g2 graphics environment.
     * @param findTop boolean, if true then a whole number value greater than
     *               maxY will be used as the largest Y-axis value; if false,
     *               then maxY is used as the largest Y-axis value.
     */
    private void DrawYAxisTicksAndLabels(int numIntervals, double maxY, Graphics2D g2, boolean findTop) {
        if (numIntervals <= 1)
            return;

        double topYValue = maxY;
        if (findTop)
            topYValue = findTopYValue(maxY);

        double spacing = (plottedYmin - plottedYmax) / ((double)NUM_Y_INTERVALS);
        double yDelta = topYValue / ((double)NUM_Y_INTERVALS);
        int tickLeft = plottedXmin - TICK_SIZE / 2;
        int tickRight = plottedXmin + TICK_SIZE / 2;
        String formatStr = makeFormatString(topYValue);

        for (int i = 0; i <= NUM_Y_INTERVALS; ++i) {
            int yPos = plottedYmin - (int)(i * spacing);
            g2.drawLine(tickLeft, yPos, tickRight, yPos);
            String dataStr = String.format(formatStr, yDelta * i);
            g2.drawString(dataStr, plottedXmin - Y_LBL_SHIFT_X, yPos + Y_LBL_SHIFT_Y);
        }
    }

    /**
     * Returns String to be used as a printf format which determines the number
     * of decimal places to print based on the magnitude of maxY. The larger the
     * value of maxY the fewer the decimal places in order fit in finite space.
     * @param maxY value used to determine number of decimal places in returned
     *             String format.
     * @return specified String.
     */
    private String makeFormatString(double maxY) {
        final double MAX_LOG = 3.0;

        if (maxY <= 0.0) // can't take log of a negative maxY
            return "%%5.1f";

        return String.format("%%5.%df", Math.log10(maxY) >= MAX_LOG ? 0 : 1 );
    }

    /**
     * Returns a 'rounded up' value close to, but larger than max. If a non-positive
     * value is passed then TOP_Y_VALUE_DEFAULT is returned.
     * @param max value to determine a 'rounded up' value from.
     * @return specified double.
     */
    private double findTopYValue(double max) {
        if (max <= 0.0)
            return TOP_Y_VALUE_DEFAULT;

        double increment = Math.pow(10.0, Math.floor(Math.log10(max)));
        return (1.0 + Math.floor(max / increment)) * increment;
    }

    /**
     * Generates color dot test pattern. Useful for debugging.
     * @param g2 Graphics2D graph object.
     */
    public void TestColorDots(Graphics2D g2) {
        // masked attributes
        int plottedXmax = getWidth() * 4 / 5;
        int plottedYmin = getHeight() *4 / 5;

        Color oldColor = g2.getColor();
        for (int i = 0; i < colorArray.length; ++i) {
            g2.setColor(colorArray[i]);
            g2.fillOval(plottedXmax + POINT_SIZE, plottedYmin - (i + 3) * 3 * POINT_SIZE, POINT_SIZE, POINT_SIZE);

            // complimentary color
            g2.setColor(getComplimentColor(colorArray[i]));
            g2.fillOval(plottedXmax + 2 * POINT_SIZE, plottedYmin - (i + 3) * 3 * POINT_SIZE, POINT_SIZE, POINT_SIZE);
        }
        g2.setColor(oldColor);
    }

    /**
     * Returns the complimentary (opposite) color.
     * Modified and adapted from: http://www.java2s.com/Code/Android/2D-Graphics/
     * Returnsthecomplimentaryoppositecolor.htm
     * @param color Color RGB color to return the compliment of
     * @return Color RGB of compliment color
     */
    public static Color getComplimentColor(Color color) {
        int red = (~color.getRed()) & 0xff;
        int blue = (~color.getBlue()) & 0xff;
        int green = (~color.getGreen()) & 0xff;

        return new Color(red, green, blue);
    }

    /**
     * One object of this class creates an orange square. TEST PURPOSES ONLY.
     */
    class MyPanel3 extends JPanel {
        final int LBL_WIDTH = 100, LBL_HEIGHT = 50;

        public MyPanel3() {
            setBorder(BorderFactory.createLineBorder(Color.ORANGE));
            setOpaque(true);
            setForeground(Color.BLACK);
            setBounds(200,200, LBL_WIDTH*3, LBL_HEIGHT);
        }

        @Override
        public void paintComponent(Graphics g) { // Panel3 - the child panel
//            super.paintComponent(g);
//            setBounds(200,200, LBL_WIDTH*2, LBL_HEIGHT);
//            g.setColor(Color.YELLOW);
//            g.fillRect(50, 50, 25, 25);
            Graphics2D g2 = (Graphics2D) g;
//            //setBounds(200,200, LBL_WIDTH*3, LBL_HEIGHT);
//            Rectangle2D rect = new Rectangle2D.Double(LBL_HEIGHT/4,LBL_HEIGHT/4, LBL_WIDTH, LBL_HEIGHT/2);
//            g2.setPaint(Color.YELLOW);
//            g2.fill(rect);
//            g2.draw(rect);

            TestColorDots(g2);
        }

//        @Override
//        public Dimension getPreferredSize() { return new Dimension(500,500); }
    }
}
