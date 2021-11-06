package utils;

public class Hsv {
    private double hue;
    private double saturation;
    private double value;

    public Hsv(double hue, double saturation, double value) {
        this.hue = hue;
        this.saturation = saturation;
        this.value = value;
    }

    public double getHue() {
        return hue;
    }
    public void settHue(Double hue) { this.hue = hue; }

    public double getSaturation() {
        return saturation;
    }
    public void setSaturation(Double saturation) { this.saturation = saturation; }

    public double getValue() {
        return value;
    }
    public void setValue(Double value) { this.value = value; }

}
