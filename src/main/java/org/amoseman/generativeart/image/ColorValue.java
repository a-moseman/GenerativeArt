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
