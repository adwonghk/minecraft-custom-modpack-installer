package components;

import javax.swing.border.Border;
import java.awt.*;

public class RoundedBorder implements Border {

    private int radius;
    private Color color;
    private int stroke;

    public RoundedBorder(int radius, Color color, int stroke) {
        this.radius = radius;
        this.color = color;
        this.stroke = stroke;
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
    }


    public boolean isBorderOpaque() {
        return true;
    }


    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(stroke));
        g2d.drawRoundRect(x, y, width-1, height-1, radius, radius);
    }
}