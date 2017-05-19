import java.awt.*;


public class ColoredPoint extends Point {
    private Color color;
    private double originalX, originalY;

    public ColoredPoint(Color color, double mappedX, double mappedY, double originalX, double originalY) {
        super((int)mappedX, (int)mappedY);
        this.color = color;
        this.originalX = originalX;   // USE AS 'VALUE' IN MAP METHOD OR 'USED - PAST TENSE???'
        this.originalY = originalY;   // USE AS 'VALUE' IN MAP METHOD OR 'USED - PAST TENSE???'
    }

    public Color getColor() { return color; }

    public String getLabel() { return String.format("(%d, %.1f)", (int)originalX, originalY); }
}
