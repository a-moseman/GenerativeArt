package org.amoseman.generativeart.filter.combine;

import org.amoseman.generativeart.filter.Filter;
import org.amoseman.generativeart.image.ColorValue;
import org.amoseman.generativeart.image.ImageData;

import java.util.random.RandomGenerator;

public class Composite implements Filter {
    private final ImageData other;
    private final ColorValue chroma;
    private final double threshhold;

    public Composite(ImageData other, ColorValue chroma, double threshhold) {
        this.other = other;
        this.chroma = chroma;
        this.threshhold = threshhold;
    }

    public Composite(ImageData other, ColorValue chroma) {
        this.other = other;
        this.chroma = chroma;
        this.threshhold = 0;
    }

    @Override
    public void apply(ImageData data, RandomGenerator random) {
        if (data.getSize() != other.getSize() || data.getWidth() != other.getWidth() || data.getHeight() != other.getHeight()) {
            throw new RuntimeException("Data must be of the same size and shape");
        }
        for (int i = 0; i < data.getSize(); i++) {
            ColorValue current = data.get(i);
            if (current.distance(chroma) > threshhold) {
                continue;
            }
            data.set(i, other.get(i));
        }
    }
}
