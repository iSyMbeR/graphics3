package utils;

import java.awt.*;

public class ColorConverter {

    public static Color convertToRgb(Cmyk cmyk) {
        return new Color(getRed(cmyk), getGreen(cmyk), getBlue(cmyk));
    }

    private static int getRed(Cmyk cmyk) {
        return (int) (255 * (1 - cmyk.getCyan()) * (1 - cmyk.getBlack()));
    }

    private static int getGreen(Cmyk cmyk) {
        return (int) (255 * (1 - cmyk.getMagenta()) * (1 - cmyk.getBlack()));
    }

    private static int getBlue(Cmyk cmyk) {
        return (int) (255 * (1 - cmyk.getYellow()) * (1 - cmyk.getBlack()));
    }

    public static Cmyk convertToCmyk(Color rgb) {
        double black = getBlack(rgb);
        return new Cmyk(getCyan(rgb, black), getMagenta(rgb, black), getYellow(rgb, black), black);
    }

    private static double getCyan(Color rgb, double black) {
        return (1 - (rgb.getRed() / 255.0) - black) / (1 - black);
    }

    private static double getMagenta(Color rgb, double black) {
        return (1 - (rgb.getGreen() / 255.0) - black) / (1 - black);
    }

    private static double getYellow(Color rgb, double black) {
        return (1 - (rgb.getBlue() / 255.0) - black) / (1 - black);
    }

    private static double getBlack(Color rgb) {
        return 1 - max(rgb.getGreen() / 255.0, rgb.getRed() / 255.0, rgb.getBlue() / 255.0);
    }

    private static double max(double x, double y, double z) {
        return x > y ? Math.max(x, z) : Math.max(y, z);
    }
}
