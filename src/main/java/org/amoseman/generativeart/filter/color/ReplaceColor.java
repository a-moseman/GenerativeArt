package org.amoseman.generativeart.filter.color;

import org.amoseman.generativeart.filter.Filter;
import org.amoseman.generativeart.image.ColorValue;
import org.amoseman.generativeart.image.ImageData;

import java.util.random.RandomGenerator;

public class ReplaceColor implements Filter {
    private final ColorValue target;
    private final ColorValue replacement;
    private final double threshold;

    public ReplaceColor(ColorValue target, ColorValue replacement, double threshold) {
        this.target = target;
        this.replacement = replacement;
        this.threshold = threshold;
    }

    @Override
    public void apply(ImageData data, RandomGenerator random) {
        for (int i = 0; i < data.getSize(); i++) {
            ColorValue current = data.get(i);
            if (current.distance(target) < threshold) {
                data.set(i, replacement);
            }
        }
    }
}
