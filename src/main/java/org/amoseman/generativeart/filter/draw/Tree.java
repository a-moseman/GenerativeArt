package org.amoseman.generativeart.filter.draw;

import org.amoseman.generativeart.image.ColorValue;
import org.amoseman.generativeart.filter.Filter;
import org.amoseman.generativeart.image.ImageData;

import java.util.Random;
import java.util.random.RandomGenerator;

public class Tree implements Filter {
    private final ColorValue value;
    private final float x0;
    private final float y0;
    private final float branches;
    private final float branchDecay;
    private final float branchSurvivalProbability;
    private final float branchSurvivalProbabilityDecay;
    private final int maxDepth;
    private final float angle;
    private final float angleDeviation;
    private final float angleDeviationDecay;
    private final float branchLength;
    private final float branchLengthDeviation;
    private final float branchLengthDecay;
    private final float branchLengthDeviationDecay;

    public Tree(ColorValue value, float x0, float y0, float branches, float branchDecay, float branchSurvivalProbability, float branchSurvivalProbabilityDecay, int maxDepth, float angle, float angleDeviation, float angleDeviationDecay, float branchLength, float branchLengthDeviation, float branchLengthDecay, float branchLengthDeviationDecay) {
        this.value = value;
        this.x0 = x0;
        this.y0 = y0;
        this.branches = branches;
        this.branchDecay = branchDecay;
        this.branchSurvivalProbability = branchSurvivalProbability;
        this.branchSurvivalProbabilityDecay = branchSurvivalProbabilityDecay;
        this.maxDepth = maxDepth;
        this.angle = angle;
        this.angleDeviation = angleDeviation;
        this.angleDeviationDecay = angleDeviationDecay;
        this.branchLength = branchLength;
        this.branchLengthDeviation = branchLengthDeviation;
        this.branchLengthDecay = branchLengthDecay;
        this.branchLengthDeviationDecay = branchLengthDeviationDecay;
    }

    @Override
    public void apply(ImageData data, RandomGenerator random) {
        step(data, random, x0, y0, maxDepth, branches, branchSurvivalProbability, angle, angleDeviation, branchLength, branchLengthDeviation);
    }

    private void step(ImageData data, RandomGenerator random, float x, float y, int depth, float branches, float branchSurvivalProbability, float angle, float angleDeviation, float branchLength, float branchLengthDeviation) {
        int b = (int) branches;
        if (depth == 0 || b == 0 || branchSurvivalProbability < random.nextFloat()) {
            return;
        }
        for (int branch = 0; branch < b; branch++) {
            float theta = (float) ((random.nextFloat() - 0.5f) * Math.PI * 2) * (angleDeviation) + angle;
            float length = (random.nextFloat() - 0.5f) * branchLengthDeviation + branchLength;
            float x2 = (float) (length * Math.cos(theta) + x);
            float y2 = (float) (length * Math.sin(theta) + y);
            new Line(x, y, x2, y2, value).apply(data, random);
            step(data, random, x2, y2, depth - 1, branches * branchDecay, branchSurvivalProbability * branchSurvivalProbabilityDecay, theta, angleDeviation * angleDeviationDecay, branchLength * branchLengthDecay, branchLengthDeviation * branchLengthDeviationDecay);
        }
    }
}
