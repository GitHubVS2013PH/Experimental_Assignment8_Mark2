import java.awt.*;

/**
 * One object of this class contains color and position information for a data point.
 */
public class ColoredPoint extends Point {
    private Color color;
    private double originalX, originalY;

    /**
     * Constructor.
     * @param color of plotted data point.
     * @param mappedX mapped X coordinate of data point.
     * @param mappedY mapped Y coordinate of data point.
     * @param originalX original X coordinate.
     * @param originalY original Y coordinate.
     */
    public ColoredPoint(Color color, double mappedX, double mappedY, double originalX, double originalY) {
        super((int)mappedX, (int)mappedY);
        this.color = color;
        this.originalX = originalX;   // USE AS 'VALUE' IN MAP METHOD OR 'USED - PAST TENSE???'
        this.originalY = originalY;   // USE AS 'VALUE' IN MAP METHOD OR 'USED - PAST TENSE???'
    }

    /**
     * Returns Color of ColorPoint object.
     * @return specified Color.
     */
    public Color getColor() { return color; }

    /**
     * Returns String containing (X,Y) coordinates of ColorPoint object.
     * @return specified String.
     */
    public String getLabel() { return String.format("(%d, %.1f)", (int)originalX, originalY); }
}
