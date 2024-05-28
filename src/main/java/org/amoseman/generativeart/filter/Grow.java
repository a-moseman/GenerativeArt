package org.amoseman.generativeart.filter;

import org.amoseman.generativeart.image.ColorValue;
import org.amoseman.generativeart.image.ImageData;
import org.amoseman.generativeart.vector.Vector;

import java.util.Random;
import java.util.random.RandomGenerator;

public class Grow implements Filter {
    private final int n;
    private final double ignoreThreshold;
    private final ColorValue[] ignore;
    private final Vector[] directions = new Vector[]{Vector.UP, Vector.DOWN, Vector.LEFT, Vector.RIGHT};

    public Grow(int n, double ignoreThreshold, ColorValue... ignore) {
        this.n = n;
        this.ignoreThreshold = ignoreThreshold;
        this.ignore = ignore;
    }

    public Grow(int n, ColorValue... ignore) {
        this.n = n;
        this.ignoreThreshold = 0;
        this.ignore = ignore;
    }

    @Override
    public void apply(ImageData data, RandomGenerator random) {
        int i = 0;
        while (i < n) {
            int x = random.nextInt(data.getWidth());
            int y = random.nextInt(data.getHeight());
            boolean flag = false;
            for (ColorValue ign : ignore) {
                if (data.get(x, y).distance(ign) < ignoreThreshold) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                continue;
            }

            Vector growth = randomGrowthPosition(data, random, new Vector(x, y));
            ColorValue v = data.get(x, y);
            data.set((int) growth.x, (int) growth.y, v);
            i++;
        }
    }

    private Vector randomGrowthPosition(ImageData data, RandomGenerator random, Vector origin) {
        return origin.add(directions[random.nextInt(4)]);
    }
}
