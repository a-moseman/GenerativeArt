package org.amoseman.generativeart.filter.noise;

import org.amoseman.generativeart.image.ColorValue;

public class ProbableColorValue implements Comparable<ProbableColorValue> {
    public final ColorValue color;
    public final double probability;

    public ProbableColorValue(ColorValue color, double probability) {
        this.color = color;
        this.probability = probability;
    }


    @Override
    public int compareTo(ProbableColorValue o) {
        return (int) Math.signum(probability - o.probability);
    }
}
