package org.amoseman.generativeart.filter.noise;

import org.amoseman.generativeart.image.ColorValue;
import org.amoseman.generativeart.filter.Filter;
import org.amoseman.generativeart.image.ImageData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;

public class Noise implements Filter {
    private final List<ProbableColorValue> palette;

    public Noise() {
        this.palette = new ArrayList<>();
    }

    public Noise addColor(ColorValue color, double probability) {
        palette.add(new ProbableColorValue(color, probability));
        return this;
    }

    @Override
    public void apply(ImageData data, RandomGenerator random) {
        Collections.sort(palette);
        double sum = 0;
        for (ProbableColorValue pcv : palette) {
            sum += pcv.probability;
        }
        if (sum < 1 || sum > 1) {
            throw new RuntimeException("Probabilities must add up to 1");
        }

        for (int i = 0; i < data.getSize(); i++) {
            ColorValue a = data.get(i);
            ColorValue b = randomColor(random);
            ColorValue c = a.add(b);
            data.set(i, c);
        }
    }

    private ColorValue randomColor(RandomGenerator random) {
        if (palette.isEmpty()) {
            return new ColorValue(
                    random.nextFloat(),
                    random.nextFloat(),
                    random.nextFloat(),
                    1
            );
        }
        double roll = random.nextDouble();
        double shift = 0;
        int index = 0;
        while (index < palette.size()) {
            ProbableColorValue pcv = palette.get(index);
            if (roll < pcv.probability + shift) {
                return pcv.color;
            }
            shift += pcv.probability;
            index++;
        }
        throw new RuntimeException("Invalid pallet");
    }
}
