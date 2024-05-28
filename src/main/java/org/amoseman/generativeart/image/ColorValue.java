package org.amoseman.generativeart.image;

import java.awt.*;

public class ColorValue {
    private static final float DEFAULT_ALPHA = 1f;
    private final float r;
    private final float g;
    private final float b;
    private final float a;
    private final int argb;

    private void validateRange(float... values) {
        for (float v : values) {
            if (v < 0 || v > 1) {
                throw new RuntimeException(String.format("Color value of %.2f outside range [0, 1]", v));
            }
        }
    }

    private int getARGB(int a, int r, int g, int b) {
        return  ((a & 0xFF) << 24) |
                ((r & 0xFF) << 16) |
                ((g & 0xFF) << 8) |
                ((b & 0xFF) << 0);
    }

    public ColorValue(float r, float g, float b, float a) {
        //validateRange(r, g, b, a);
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        this.argb = getARGB(
                (int) (a * 255 + 0.5),
                (int) (r * 255 + 0.5),
                (int) (g * 255 + 0.5),
                (int) (b * 255 + 0.5)
        );
    }

    public ColorValue(float r, float g, float b) {
        //validateRange(r, g, b);
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = DEFAULT_ALPHA;
        this.argb = getARGB(
                (int) (a * 255 + 0.5),
                (int) (r * 255 + 0.5),
                (int) (g * 255 + 0.5),
                (int) (b * 255 + 0.5)
        );
    }

    public ColorValue(Color color) {
        float[] components = new float[3];
        components = color.getRGBColorComponents(components);
        //validateRange(components);
        this.a = 1f;
        this.r = components[0];
        this.g = components[1];
        this.b = components[2];
        this.argb = getARGB(
                (int) (a * 255 + 0.5),
                (int) (r * 255 + 0.5),
                (int) (g * 255 + 0.5),
                (int) (b * 255 + 0.5)
        );

    }

    public Color toColor(){
        return new Color(
                Math.max(Math.min(r, 1), 0),
                Math.max(Math.min(g, 1), 0),
                Math.max(Math.min(b, 1), 0),
                Math.max(Math.min(a, 1), 0)
        );
    }

    public int getARGB() {
        return  ((((int) (a * 255)) & 0xFF) << 24) |
                ((((int) (r * 255)) & 0xFF) << 16) |
                ((((int) (g * 255)) & 0xFF) << 8) |
                ((((int) (b * 255)) & 0xFF) << 0);
    }

    public float distance(ColorValue other) {
        return (float) Math.sqrt(
                Math.pow(r - other.r, 2) +
                Math.pow(g - other.g, 2) +
                Math.pow(b - other.b, 2) +
                Math.pow(a - other.a, 2)
        );
    }

    public ColorValue add(ColorValue other) {
        float r2 = 0;
        float g2 = 0;
        float b2 = 0;
        float a2 = 1f - (1f - other.a) * (1f - a);
        if (a2 < 1.0e-6) {
            // red, green, blue values insignificant, so don't calculate
            return new ColorValue(r2, g2, b2, a2);
        }
        r2 = other.r * other.a / a + r * a * (1f - other.a) / a;
        g2 = other.g * other.a / a + g * a * (1f - other.a) / a;
        b2 = other.b * other.a / a + b * a * (1f - other.a) / a;
        return new ColorValue(r2, g2, b2, a2);
    }

    public ColorValue sum(ColorValue other) {
        return new ColorValue(r + other.r, g + other.g, b + other.b, a + other.a);
    }

    public ColorValue difference(ColorValue other) {
        return new ColorValue(r - other.r, g - other.g, b - other.b, a - other.a);
    }

    public ColorValue scale(double scalar) {
        return new ColorValue(
                (float) (r * scalar),
                (float) (g * scalar),
                (float) (b * scalar),
                (float) (a * scalar)
        );
    }

    public static ColorValue lerp(ColorValue a, ColorValue b, float t) {
        if (t > 1 || t < 0) {
            throw new RuntimeException("t must be in the range [0, 1]");
        }
        return new ColorValue(
                b.r * t + a.r * (1f - t),
                b.g * t + a.g * (1f - t),
                b.b * t + a.b * (1f - t),
                b.a * t + a.a * (1f - t)
        );
    }

    public float getRed() {
        return r;
    }

    public float getGreen() {
        return g;
    }

    public float getBlue() {
        return b;
    }

    public float getAlpha() {
        return a;
    }

    public ColorValue complementaryColor() {
        return new ColorValue(
                1f - r,
                1f - g,
                1f - b,
                a
        );
    }

    public ColorValue monochromaticColor() {
        double blackDistance = distance(ColorValue.BLACK);
        double whiteDistance = distance(ColorValue.WHITE);
        if (blackDistance == whiteDistance) {
            return this;
        }
        else if (blackDistance < whiteDistance) {
            return this.sum(ColorValue.BLACK).scale(0.5);
        }
        else {
            return this.sum(ColorValue.WHITE).scale(0.5);
        }
    }

    private double[] toHSL() {
        double min = Math.min(r, Math.min(g, b));
        double max = Math.max(r, Math.max(g, b));
        double luminance = (max + min) / 2;
        double saturation;
        if (luminance <= 0.5) {
            saturation = (max - min) / (max + min);
        }
        else {
            saturation = (max - min) / (2.0 - max - min);
        }
        double hue;
        if (max == r) {
            hue = (g - b) / (max - min);
        }
        else if (max == g) {
            hue = 2.0 + (b - r) / (max - min);
        }
        else if (max == b) {
            hue = 4.0 + (r - g) / (max - min);
        }
        else {
            throw new RuntimeException("Issue with identifying max color channel");
        }
        hue *= 60;
        if (hue < 0) {
            hue += 360;
        }
        return new double[]{luminance, saturation, hue};
    }

    public static ColorValue fromHSL(double[] hsl) {
        double temp1;
        if (hsl[0] < 0.5) {
            temp1 = hsl[0] * (1.0 + hsl[1]);
        }
        else {
            temp1 = hsl[0] + hsl[1] - hsl[0] * hsl[1];
        }
        double temp2 = 2.0 * hsl[0] - temp1;
        double hue = hsl[2] / 360;

        double tempR = hue + 0.333;
        if (tempR > 1) {
            tempR -= 1;
        }
        if (tempR < 0) {
            tempR += 1;
        }
        double tempG = hue;
        if (tempG > 1) {
            tempG -= 1;
        }
        if (tempG < 0) {
            tempG += 1;
        }
        double tempB = hue - 0.333;
        if (tempB > 1) {
            tempB -= 1;
        }
        if (tempB < 0) {
            tempB += 1;
        }
        double r = convertHSLColorChannel(tempR, temp1, temp2);
        double g = convertHSLColorChannel(tempG, temp1, temp2);
        double b = convertHSLColorChannel(tempB, temp1, temp2);
        return new ColorValue((float) r, (float) g, (float) b, 1);
    }

    private static double convertHSLColorChannel(double tempV, double temp1, double temp2) {
        if (6.0 * tempV < 1) {
            return temp2 + (temp1 - temp2) * 6 * tempV;
        }
        if (2 * tempV < 1) {
            return temp1;
        }
        if (3 * tempV < 2) {
            return temp2 + (temp1 - temp2) * (0.666 - tempV) * 6;
        }
        return temp2;
    }

    public ColorValue[] analogousColors() {
        double[] hsl = toHSL();
        double hue = hsl[2];
        double[] right = new double[]{hsl[0], hsl[1], (hue + 30) % 360};
        double[] left = new double[]{hsl[0], hsl[1], hue - 30 < 0 ? 360 + (hue - 30) : hue - 30};
        return new ColorValue[]{ColorValue.fromHSL(right), ColorValue.fromHSL(left)};
    }

    public static ColorValue RED = new ColorValue(1, 0, 0);
    public static ColorValue GREEN = new ColorValue(0, 1, 0);
    public static ColorValue BLUE = new ColorValue(0, 0, 1);
    public static ColorValue WHITE = new ColorValue(1, 1, 1);
    public static ColorValue BLACK = new ColorValue(0, 0, 0);
    public static ColorValue YELLOW = new ColorValue(1, 1, 0);
    public static ColorValue MAGENTA = new ColorValue(1, 0, 1);
    public static ColorValue CYAN = new ColorValue(0, 1, 1);
    public static ColorValue TRANSPARENT = new ColorValue(0, 0, 0, 0);
}
