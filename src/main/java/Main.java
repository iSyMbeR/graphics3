import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import utils.Cmyk;
import utils.ColorConverter;
import utils.GradientPainter;
import utils.Hsv;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main {
    private static final BufferedImage image =
            new BufferedImage(150, 150, BufferedImage.TYPE_INT_RGB);
    private static final JTextField rgbRed = new JTextField();
    private static final JTextField rgbGreen = new JTextField();
    private static final JTextField rgbBlue = new JTextField();
    private static final JTextField cmykCyan = new JTextField();
    private static final JTextField cmykMagenta = new JTextField();
    private static final JTextField cmykYellow = new JTextField();
    private static final JTextField cmykBlack = new JTextField();
    private static final JTextField hsvHue = new JTextField();
    private static final JTextField hsvSaturation = new JTextField();
    private static final JTextField hsvValue = new JTextField();
    private static final List<JTextField> rgbFields = List.of(rgbRed, rgbGreen, rgbBlue);
    private static final List<JTextField> cmykFields =
            List.of(cmykCyan, cmykMagenta, cmykYellow, cmykBlack);
    private static final List<JTextField> hsvFields = List.of(hsvHue, hsvSaturation, hsvValue);
    static Icon previewIcon = new PreviewIcon();
    static final JLabel previewLabel = new JLabel(previewIcon);
    static Icon pickerIcon = new PickerIcon();
    static final JLabel pickerLabel = new JLabel(pickerIcon);
    private static Color color = new Color(168, 50, 50);
    private static Color previewColor = new Color(168, 50, 50);
    private static JSlider jSlider;
    private static Point selector = new Point(75, 75);
    private static Cmyk colorCmyk;
    private static Hsv colorHsv;

    public static void updateRgb() {
        rgbRed.setText(String.valueOf(previewColor.getRed()));
        rgbGreen.setText(String.valueOf(previewColor.getGreen()));
        rgbBlue.setText(String.valueOf(previewColor.getBlue()));
    }

    public static void updateCmyk() {
        colorCmyk = ColorConverter.convertToCmyk(previewColor);
        cmykCyan.setText(String.valueOf(Math.round(colorCmyk.getCyan() * 100)));
        cmykMagenta.setText(String.valueOf(Math.round(colorCmyk.getMagenta() * 100)));
        cmykYellow.setText(String.valueOf(Math.round(colorCmyk.getYellow() * 100)));
        cmykBlack.setText(String.valueOf(Math.round(colorCmyk.getBlack() * 100)));
        CubeLibre.setR(previewColor.getRed());
        CubeLibre.setG(previewColor.getGreen());
        CubeLibre.setB(previewColor.getBlue());
    }

    public static void updateHsv() {
        colorHsv = ColorConverter.convertToHsv(previewColor);
        hsvHue.setText(String.valueOf(Math.round(colorHsv.getHue())));
        hsvSaturation.setText(String.valueOf(Math.round(colorHsv.getSaturation())));
        hsvValue.setText(String.valueOf(Math.round(colorHsv.getValue())));
    }

    public static Cmyk readCmyk() {
        final String text = cmykCyan.getText();
        final String text1 = cmykMagenta.getText();
        final String text2 = cmykYellow.getText();
        final String text3 = cmykBlack.getText();
        CubeLibre.setR(previewColor.getRed());
        CubeLibre.setG(previewColor.getGreen());
        CubeLibre.setB(previewColor.getBlue());
        return new Cmyk(
                Double.parseDouble(text) / 100,
                Double.parseDouble(text1) / 100,
                Double.parseDouble(text2) / 100,
                Double.parseDouble(text3) / 100);
    }

    public static Hsv readHsv() {
        final String hue = hsvHue.getText();
        final String saturation = hsvSaturation.getText();
        final String value = hsvValue.getText();
        return new Hsv(
                Double.parseDouble(hue),
                Double.parseDouble(saturation),
                Double.parseDouble(value));
    }

    public static void main(String[] args) {
        JFrame window = new JFrame("Zadanie 3 rgb&picker");
        window.setLayout(new FlowLayout());
        window.setResizable(false);
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);

        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        CubeLibre cubeLibre = new CubeLibre();

        glcanvas.addGLEventListener(cubeLibre);
        glcanvas.setSize(400, 400);
        final JPanel frame = new JPanel();
        frame.add(glcanvas);
        frame.setSize(frame.getPreferredSize());
        frame.setVisible(true);
        final FPSAnimator animator = new FPSAnimator(glcanvas, 300, true);
        animator.start();
        List<JLabel> labels = new ArrayList<>();
        labels.add(previewLabel);
        labels.add(pickerLabel);
        ColorUpdater colorUpdater = new ColorUpdater(labels);
        JPanel textFields = new JPanel();
        textFields.setLayout(new GridLayout(3, 8, 1, 1));

        JLabel redLabel = new JLabel("R");
        textFields.add(redLabel);
        rgbRed.addKeyListener(new TextFieldUpdater());
        textFields.add(rgbRed);

        JLabel greenLabel = new JLabel("G");
        textFields.add(greenLabel);
        rgbGreen.addKeyListener(new TextFieldUpdater());
        textFields.add(rgbGreen);

        JLabel blueLabel = new JLabel("B");
        textFields.add(blueLabel);
        rgbBlue.addKeyListener(new TextFieldUpdater());
        textFields.add(rgbBlue);

        textFields.add(new JLabel(""));
        textFields.add(new JLabel(""));

        JLabel cyanLabel = new JLabel("C");
        textFields.add(cyanLabel);
        textFields.add(cmykCyan);
        cmykCyan.addKeyListener(new TextFieldUpdater());

        JLabel magentaLabel = new JLabel("M");
        textFields.add(magentaLabel);
        cmykMagenta.addKeyListener(new TextFieldUpdater());
        textFields.add(cmykMagenta);

        JLabel yellowLabel = new JLabel("Y");
        textFields.add(yellowLabel);
        cmykYellow.addKeyListener(new TextFieldUpdater());
        textFields.add(cmykYellow);

        JLabel blackLabel = new JLabel("K");
        textFields.add(blackLabel);
        cmykBlack.addKeyListener(new TextFieldUpdater());
        textFields.add(cmykBlack);

        JLabel hueLabel = new JLabel("H");
        textFields.add(hueLabel);
        hsvHue.addKeyListener(new TextFieldUpdater());
        textFields.add(hsvHue);

        JLabel saturationLabel = new JLabel("S");
        textFields.add(saturationLabel);
        hsvSaturation.addKeyListener(new TextFieldUpdater());
        textFields.add(hsvSaturation);

        JLabel valueLabel = new JLabel("V");
        textFields.add(valueLabel);
        hsvValue.addKeyListener(new TextFieldUpdater());
        textFields.add(hsvValue);

        window.add(frame);
        jSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        window.add(jSlider);
        jSlider.setPreferredSize(new Dimension(200, 75));
        window.add(pickerLabel);
        jSlider.addChangeListener(colorUpdater);
        window.add(previewLabel);
        window.add(textFields);
        updateRgb();
        updateHsv();
        updateCmyk();

        pickerLabel.addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (pickerLabel.contains(e.getPoint())) {
                            previewColor = new Color(image.getRGB(e.getX(), e.getY()));
                            selector = e.getPoint();
                            labels.forEach(JLabel::repaint);
                            updateRgb();
                            updateCmyk();
                            updateHsv();
                        }
                    }
                });

        pickerLabel.addMouseMotionListener(
                new MouseAdapter() {
                    @Override
                    public void mouseDragged(MouseEvent e) {
                        if (pickerLabel.contains(e.getPoint())) {
                            previewColor = new Color(image.getRGB(e.getX(), e.getY()));
                            selector = e.getPoint();
                            labels.forEach(JLabel::repaint);
                            updateRgb();
                            updateCmyk();
                            updateHsv();
                        }
                    }
                });
        glcanvas.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                moveCube(e.getKeyChar());
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        window.setSize(new Dimension(1200, 500));
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static Color readRgb() {
        final String text = rgbRed.getText();
        final String text1 = rgbGreen.getText();
        final String text2 = rgbBlue.getText();
        CubeLibre.setR(previewColor.getRed());
        CubeLibre.setG(previewColor.getGreen());
        CubeLibre.setB(previewColor.getBlue());
        return new Color(
                Integer.parseInt(Optional.of(text).filter(s -> !s.isEmpty()).orElse("0")),
                Integer.parseInt(Optional.of(text1).filter(s1 -> !s1.isEmpty()).orElse("0")),
                Integer.parseInt(Optional.of(text2).filter(s2 -> !s2.isEmpty()).orElse("0")));
    }

    static class ColorUpdater implements ChangeListener {
        private final List<JLabel> labels;

        public ColorUpdater(List<JLabel> labels) {
            this.labels = labels;
        }

        public void stateChanged(ChangeEvent ev) {
            calculateColor();
            if (selector != null) {
                previewColor = new Color(image.getRGB(selector.x, selector.y));
            }
            labels.forEach(JLabel::repaint);
            updateRgb();
            updateCmyk();
            updateHsv();
        }

        private void calculateColor() {
            if (jSlider.getValue() < 17) {

                final int g = mapIntoValue(jSlider.getValue());
                color = new Color(255, g, 0);

            } else if (jSlider.getValue() >= 17 && jSlider.getValue() < 34) {

                final int r = 255 - mapIntoValue(jSlider.getValue());
                color = new Color(r, 255, 0);

            } else if (jSlider.getValue() >= 34 && jSlider.getValue() < 51) {

                final int b = mapIntoValue(jSlider.getValue());
                color = new Color(0, 255, b);

            } else if (jSlider.getValue() >= 51 && jSlider.getValue() < 68) {

                final int g = 255 - mapIntoValue(jSlider.getValue());
                color = new Color(0, g, 255);

            } else if (jSlider.getValue() >= 68 && jSlider.getValue() < 85) {

                final int r = mapIntoValue(jSlider.getValue());
                color = new Color(r, 0, 255);

            } else if (jSlider.getValue() >= 85 && jSlider.getValue() <= 100) {

                final int b = 255 - mapIntoValue(jSlider.getValue());
                color = new Color(255, 0, b);
            }
        }

        private int mapIntoValue(int x) {
            return x % 17 * 255 / 16;
        }
    }

    static class TextFieldUpdater extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            super.keyReleased(e);
            if (rgbFields.contains(((JTextField) e.getSource()))) {
                previewColor = readRgb();
                color = new Color(previewColor.getRed(), previewColor.getGreen(), previewColor.getBlue());
                colorCmyk = ColorConverter.convertToCmyk(previewColor);
                updateCmyk();
                updateHsv();
                previewLabel.repaint();
                pickerLabel.repaint();
            } else if (cmykFields.contains(((JTextField) e.getSource()))) {
                colorCmyk = readCmyk();
                previewColor = ColorConverter.convertToRgb(colorCmyk);
                color = new Color(previewColor.getRed(), previewColor.getGreen(), previewColor.getBlue());
                updateRgb();
                updateHsv();
                previewLabel.repaint();
                pickerLabel.repaint();
            } else if (hsvFields.contains(((JTextField) e.getSource()))) {
                colorHsv = readHsv();
                previewColor = ColorConverter.convertHsvToRgb(colorHsv);
                color = new Color(previewColor.getRed(), previewColor.getGreen(), previewColor.getBlue());
                updateRgb();
                updateCmyk();
                previewLabel.repaint();
                pickerLabel.repaint();
            }
        }
    }

    private static void moveCube(char keyChar) {
        if (keyChar == 'w' || keyChar == 's') {
            if (keyChar == 'w') {
                CubeLibre.setAngleHorizontal(15.0f);
            } else {
                CubeLibre.setAngleHorizontal(-15.0f);
            }
        }
        if (keyChar == 'a' || keyChar == 'd') {
            if (keyChar == 'a') {
                CubeLibre.setAngleVertical(-15.0f);
            } else {
                CubeLibre.setAngleVertical(15.0f);

            }
        }
    }

    static class PreviewIcon implements Icon {
        public int getIconWidth() {
            return 50;
        }

        public int getIconHeight() {
            return 150;
        }

        public void paintIcon(Component c, Graphics g, int x, int y) {
            final Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(previewColor);
            g2d.fillRect(0, 0, getIconWidth(), getIconHeight());
        }
    }

    static class PickerIcon implements Icon {

        public int getIconWidth() {
            return 150;
        }

        public int getIconHeight() {
            return 150;
        }

        public void paintIcon(Component c, Graphics g, int x, int y) {
            final Graphics2D g2d = (Graphics2D) g;
            GradientPainter.threeGradient(150, g2d, color);
            if (selector != null) {
                g2d.setColor(Color.BLACK);
                g2d.drawOval(selector.x, selector.y, 6, 6);
            }
            final Graphics2D graphics = image.createGraphics();
            GradientPainter.threeGradient(150, graphics, color);
        }
    }
}
