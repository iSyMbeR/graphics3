package utils;

public class Cmyk {
    private double cyan;
    private double magenta;
    private double yellow;
    private double black;

    public Cmyk(double cyan, double magenta, double yellow, double black) {
        this.cyan = cyan;
        this.magenta = magenta;
        this.yellow = yellow;
        this.black = black;
    }

    public double getCyan() {
        return cyan;
    }

    public void setCyan(double cyan) {
        this.cyan = cyan;
    }

    public double getMagenta() {
        return magenta;
    }

    public void setMagenta(double magenta) {
        this.magenta = magenta;
    }

    public double getYellow() {
        return yellow;
    }

    public void setYellow(double yellow) {
        this.yellow = yellow;
    }

    public double getBlack() {
        return black;
    }

    public void setBlack(double black) {
        this.black = black;
    }
}
