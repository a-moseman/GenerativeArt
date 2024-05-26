package org.amoseman.generativeart.filter;

import org.amoseman.generativeart.ColorMath;
import org.amoseman.generativeart.image.ImageData;

import java.util.Random;

public class Composite implements Filter {
    private final ImageData other;
    private final float[] chroma;
    private final double threshhold;

    public Composite(ImageData other, float[] chroma, double threshhold) {
        this.other = other;
        this.chroma = chroma;
        this.threshhold = threshhold;
    }

    public Composite(ImageData other, float[] chroma) {
        this.other = other;
        this.chroma = chroma;
        this.threshhold = 0;
    }

    @Override
    public void apply(ImageData data, Random random) {
        if (data.getSize() != other.getSize() || data.getWidth() != other.getWidth() || data.getHeight() != other.getHeight()) {
            throw new RuntimeException("Data must be of the same size and shape");
        }
        for (int i = 0; i < data.getSize(); i++) {
            float[] current = data.get(i);
            if (ColorMath.distance(current, chroma) > threshhold) {
                continue;
            }
            data.set(i, other.get(i));
        }
    }
}
