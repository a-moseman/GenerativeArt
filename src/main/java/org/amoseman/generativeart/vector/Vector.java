package org.amoseman.generativeart.vector;

public final class Vector {
    public final double x;
    public final double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector add(Vector other) {
        return new Vector(x + other.x, y + other.y);
    }

    public Vector subtract(Vector other) {
        return new Vector(x - other.x, y - other.y);
    }

    public Vector rotate(Vector center, double radians) {
        double s = Math.sin(radians);
        double c = Math.cos(radians);

        Vector p1 = subtract(center);
        Vector p2 = new Vector(
                p1.x * c - p1.y * s,
                p1.x * s + p1.y * c
        );
        p2 = p2.add(center);
        return p2;
    }
}
