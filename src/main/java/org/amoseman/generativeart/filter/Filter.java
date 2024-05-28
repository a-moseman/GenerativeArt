package org.amoseman.generativeart.filter;

import org.amoseman.generativeart.image.ImageData;

import java.util.random.RandomGenerator;

public interface Filter {
    void apply(ImageData data, RandomGenerator random);
}
