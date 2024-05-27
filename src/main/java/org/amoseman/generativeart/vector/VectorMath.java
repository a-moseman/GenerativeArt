package org.amoseman.generativeart.vector;

public class VectorMath {
    public static Vector rotate(Vector target, Vector center, float radians) {
        double s = Math.sin(radians);
        double c = Math.cos(radians);

        double x1 = target.x - center.x;
        double y1 = target.y - center.y;

        double x2 = x1 * c - y1 * s;
        double y2 = x1 * s + y1 * c;

        x2 = x2 + center.x;
        y2 = y2 + center.y;
        return new Vector(x2, y2);
    }
}
