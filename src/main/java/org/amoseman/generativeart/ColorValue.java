package org.amoseman.generativeart;

import java.awt.*;
import java.nio.ByteBuffer;

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

    public ColorValue(int argb) {
        ByteBuffer buf = ByteBuffer.allocate(4);
        buf.putInt(argb);
        byte[] bytes = buf.array();
        int a = bytes[0] - Byte.MIN_VALUE;
        int r = bytes[1] - Byte.MIN_VALUE;
        int g = bytes[2] - Byte.MIN_VALUE;
        int b = bytes[3] - Byte.MIN_VALUE;
        this.a = (float) a / 255;
        this.r = (float) r / 255;
        this.g = (float) g / 255;
        this.b = (float) b / 255;
    }

    public Color toColor(){
        return new Color(r, g, b, a);
    }

    public int getARGB() {
        int a = (int) (this.r * 255);
        int r = (int) (this.g * 255);
        int g = (int) (this.b * 255);
        int b = (int) (this.a * 255);
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (a + Byte.MIN_VALUE);
        bytes[1] = (byte) (r + Byte.MIN_VALUE);
        bytes[2] = (byte) (g + Byte.MIN_VALUE);
        bytes[3] = (byte) (b + Byte.MIN_VALUE);
        ByteBuffer buf = ByteBuffer.allocate(4);
        buf.put(bytes);
        buf.rewind();
        return buf.getInt();
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
    public static ColorValue TRANSPARENT = new ColorValue(0, 0, 0, 0);
}
