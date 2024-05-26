package org.amoseman.generativeart.filter;

import org.amoseman.generativeart.ColorMath;
import org.amoseman.generativeart.image.ImageData;

import java.security.SecureRandom;
import java.util.Random;

public class Grow implements Filter {
    private final int n;
    private final double ignoreThreshold;
    private final float[][] ignore;

    public Grow(int n, double ignoreThreshold, float[]... ignore) {
        this.n = n;
        this.ignoreThreshold = ignoreThreshold;
        this.ignore = ignore;
    }

    public Grow(int n, float[]... ignore) {
        this.n = n;
        this.ignoreThreshold = 0;
        this.ignore = ignore;
    }

    @Override
    public void apply(ImageData data, Random random) {
        int i = 0;
        while (i < n) {
            int x = random.nextInt(data.getWidth());
            int y = random.nextInt(data.getHeight());
            boolean flag = false;
            for (float[] ign : ignore) {
                if (ColorMath.distance(data.get(x, y), ign) < ignoreThreshold) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                continue;
            }

            int dx = 0;
            int dy = 0;
            do {
                switch (random.nextInt(4)) {
                    case 0:
                        dx = 1;
                        dy = 0;
                        break;
                    case 1:
                        dx = -1;
                        dy = 0;
                        break;
                    case 2:
                        dx = 0;
                        dy = 1;
                        break;
                    case 3:
                        dx = 0;
                        dy = -1;
                        break;
                }
            } while (x + dx < 0 || x + dx >= data.getWidth() || y + dy < 0 || y + dy >= data.getHeight());
            float[] v = data.get(x, y);
            data.set(x + dx, y + dy, v);
            i++;
        }
    }
}
