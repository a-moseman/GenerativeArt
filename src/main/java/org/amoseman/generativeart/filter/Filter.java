package org.amoseman.generativeart.filter;

import org.amoseman.generativeart.image.ImageData;

import java.util.Random;

public interface Filter {
    void apply(ImageData data, Random random);
}
