package utils;

import java.awt.*;

public class GradientPainter {

    public static void threeGradient(int size, Graphics2D graphics2D, Color primaryColor) {
        GradientPaint primary = new GradientPaint(0f, 0f, Color.WHITE, size, 0f, primaryColor);
        int rC = Color.BLACK.getRed();
        int gC = Color.BLACK.getGreen();
        int bC = Color.BLACK.getBlue();
        GradientPaint shade =
                new GradientPaint(0f, 0f, new Color(rC, gC, bC, 0), 0f, size, Color.BLACK);
        graphics2D.setPaint(primary);
        graphics2D.fillRect(0, 0, size, size);
        graphics2D.setPaint(shade);
        graphics2D.fillRect(0, 0, size, size);
    }
}
