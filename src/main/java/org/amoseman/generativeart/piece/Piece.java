package org.amoseman.generativeart.piece;

import org.amoseman.generativeart.filter.Filter;
import org.amoseman.generativeart.image.ImageData;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

public class Piece {
    private ImageData data;
    private long seed;
    private RandomGenerator random;
    private static final String RANDOM_ALGORITHM = "Xoshiro256PlusPlus";

    private static RandomGenerator buildRandom(long seed) {
        return RandomGeneratorFactory.of(RANDOM_ALGORITHM).create(seed);
    }

    public Piece(int width, int height, long seed) {
        this.data = new ImageData(width, height);
        this.seed = seed;
        this.random = buildRandom(seed);
    }

    public Piece(int width, int height) {
        this.data = new ImageData(width, height);
        this.seed = new SecureRandom().nextLong();
        this.random = buildRandom(seed);
    }

    public Piece addFilter(Filter filter) {
        data.filter(random, filter);
        return this;
    }

    public Piece addFilter(int cycles, Filter... filters) {
        data.filter(random, cycles, filters);
        return this;
    }

    public void build(String format) {
        String name = String.format("./pieces/%d.%s", seed, format);
        try {
            ImageIO.write(data.asImage(), format, new File(name));
        }
        catch (IOException e) {
            System.err.println("Failed to write piece to file");
            return;
        }
        System.out.printf("Wrote %s to file with %d as the seed", name, seed);
    }

    public ImageData getData() {
        return data;
    }

    public RandomGenerator getRandom() {
        return random;
    }
}
