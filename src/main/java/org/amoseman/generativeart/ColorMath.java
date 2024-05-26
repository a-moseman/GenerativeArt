package org.amoseman.generativeart;

public class ColorMath {
    public static float[] add(float[] background, float[] foreground) {
        if (foreground.length != 4 || background.length != 4) {
            throw new RuntimeException("Color values must be of length 4");
        }
        float[] color = new float[4];
        color[3] = 1f - (1f - foreground[3]) * (1f - background[3]);
        if (color[3] < 1.0e-6) {
            // red, green, blue values insignificant, so don't calculate
            return color;
        }
        for (int i = 0; i < 3; i++) {
            color[i] = foreground[i] * foreground[3] / color[3] + background[i] * background[3] * (1f - foreground[3]) / color[3];
        }
        return color;
    }

    public static float[] lerp(float[] a, float[] b, float t) {
        if (a.length != 4 || b.length != 4) {
            throw new RuntimeException("Color values must be of length 4");
        }
        if (t > 1 || t < 0) {
            throw new RuntimeException("t must be in the range [0, 1]");
        }
        return new float[] {
                b[0] * t + a[0] * (1 - t),
                b[1] * t + a[1] * (1 - t),
                b[2] * t + a[2] * (1 - t),
                b[3] * t + a[3] * (1 - t),
        };
    }

    public static double distance(float[] a, float[] b) {
        if (a.length != b.length) {
            throw new RuntimeException("Cannot calculate distance between values of different lengths");
        }
        if (a.length == 3) {
            return Math.sqrt(Math.pow(a[0] - b[0], 2) + Math.pow(a[1] - b[1], 2) + Math.pow(a[2] - b[2], 2));
        }
        if (a.length == 4) {
            return Math.sqrt(Math.pow(a[0] - b[0], 2) + Math.pow(a[1] - b[1], 2) + Math.pow(a[2] - b[2], 2) + Math.pow(a[3] - b[3], 2));
        }
        return 0; // shouldn't happen: todo: refactor to avoid
    }
}
