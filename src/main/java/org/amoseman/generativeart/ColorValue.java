package org.amoseman.generativeart;

public class ColorValue {
    private static final float DEFAULT_ALPHA = 1f;
    private final float r;
    private final float g;
    private final float b;
    private final float a;

    private void validateRange(float... values) {
        for (float v : values) {
            if (v < 0 || v > 1) {
                throw new RuntimeException("Color value outside range [0, 1]");
            }
        }
    }

    public ColorValue(float r, float g, float b, float a) {
        validateRange(r, g, b, a);
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public ColorValue(float r, float g, float b) {
        validateRange(r, g, b);
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = DEFAULT_ALPHA;
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
}
