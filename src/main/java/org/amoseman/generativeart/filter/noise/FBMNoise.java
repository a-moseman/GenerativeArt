package org.amoseman.generativeart.filter.noise;

import org.amoseman.generativeart.image.ColorValue;
import org.amoseman.generativeart.filter.Filter;
import org.amoseman.generativeart.image.ImageData;
import org.spongepowered.noise.LatticeOrientation;
import org.spongepowered.noise.Noise;
import org.spongepowered.noise.NoiseQualitySimplex;

import java.util.random.RandomGenerator;

public class FBMNoise implements Filter {
    private final ColorValue a;
    private final ColorValue b;
    private final int seed;
    private final int octaves;
    private final double frequency;
    private final double amplitude;
    private final double lacunarity;
    private final double persistence;

    public FBMNoise(ColorValue a, ColorValue b, long seed, int octaves, double frequency, double amplitude, double lacunarity, double persistence) {
        this.a = a;
        this.b = b;
        this.seed = (int) seed;
        this.octaves = octaves;
        this.frequency = frequency;
        this.amplitude = amplitude;
        this.lacunarity = lacunarity;
        this.persistence = persistence;
    }

    @Override
    public void apply(ImageData data, RandomGenerator random) {
        double[][] heightMap = new double[data.getWidth()][data.getHeight()];

        double freq = frequency;
        double amp = amplitude;
        for (int o = 0; o < octaves; o++) {
            int s = seed + random.nextInt();
            for (int x = 0; x < data.getWidth(); x++) {
                for (int y = 0; y < data.getHeight(); y++) {
                    double x_d = ((double) x / data.getWidth()) * freq;
                    double y_d = ((double) y / data.getHeight()) * freq;
                    double height = Noise.simplexStyleGradientCoherentNoise3D(x_d, y_d, 0, s, LatticeOrientation.XY_BEFORE_Z, NoiseQualitySimplex.STANDARD) * amp;
                    heightMap[x][y] += height;
                }
            }
            freq *= lacunarity;
            amp *= persistence;
        }

        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for (int x = 0; x < data.getWidth(); x++) {
            for (int y = 0; y < data.getHeight(); y++) {
                double h = heightMap[x][y];
                if (h < min) {
                    min = h;
                }
                if (h > max) {
                    max = h;
                }
            }
        }
        for (int x = 0; x < data.getWidth(); x++) {
            for (int y = 0; y < data.getHeight(); y++) {
                heightMap[x][y] = (1d / (max - min)) * (heightMap[x][y] - min);
            }
        }
        for (int x = 0; x < data.getWidth(); x++) {
            for (int y = 0; y < data.getHeight(); y++) {
                data.draw(x, y, ColorValue.lerp(a, b, (float) heightMap[x][y]));
            }
        }
    }
}