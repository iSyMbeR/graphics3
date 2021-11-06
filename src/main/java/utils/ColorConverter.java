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

    public static Color convertHsvToRgb(Hsv hsv) {
        int h = (int)(hsv.getHue()/360 * 6);
        if (h > 5 ) h = 5;
        float f = (float) (hsv.getHue()/360 * 6 - h);
        float p = (float) ((hsv.getValue()/100 * (1 - hsv.getSaturation()/100)))*255;
        float q = (float) ((hsv.getValue()/100 * (1 - f * hsv.getSaturation()/100)))*255;
        float t = (float) ((hsv.getValue()/100 * (1 - (1 - f) * hsv.getSaturation()/100)))*255;

        switch (h) {
            case 0: return new Color((int)hsv.getValue(), (int)t, (int)p);
            case 1: return new Color((int)q, (int)hsv.getValue(), (int)p);
            case 2: return new Color((int)p, (int)hsv.getValue(), (int)t);
            case 3: return new Color((int)p, (int)q, (int)hsv.getValue());
            case 4: return new Color((int)t, (int)p, (int)hsv.getValue());
            case 5: return new Color((int)hsv.getValue(), (int)p, (int)q);
            default: throw new RuntimeException("Something went wrong when converting from HSV to RGB.");
        }
    }

    public static Hsv convertToHsv(Color rgb)
    {
        double r = rgb.getRed() / 255.0;
        double g = rgb.getGreen() / 255.0;
        double b = rgb.getBlue() / 255.0;

        double cMax = Math.max(r, Math.max(g, b));
        double cMin = Math.min(r, Math.min(g, b));
        double diff = cMax - cMin;
        double h = -1, s = -1;

        if (cMax == cMin)
            h = 0;

        else if (cMax == r)
            h = (60 * ((g - b) / diff) + 360) % 360;

        else if (cMax == g)
            h = (60 * ((b - r) / diff) + 120) % 360;

        else if (cMax == b)
            h = (60 * ((r - g) / diff) + 240) % 360;

        if (cMax == 0)
            s = 0;
        else
            s = (diff / cMax) * 100;

        double v = cMax * 100;
        return new Hsv(h, s, v);
    }


    private static double max(double x, double y, double z) {
        return x > y ? Math.max(x, z) : Math.max(y, z);
    }
}
