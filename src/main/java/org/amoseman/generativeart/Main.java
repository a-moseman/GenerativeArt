package org.amoseman.generativeart;

import org.amoseman.generativeart.filter.*;
import org.amoseman.generativeart.filter.Composite;
import org.amoseman.generativeart.filter.draw.Ellipse;
import org.amoseman.generativeart.filter.draw.Text;
import org.amoseman.generativeart.filter.noise.FBMNoise;
import org.amoseman.generativeart.filter.noise.Noise;
import org.amoseman.generativeart.image.ImageData;
import org.amoseman.generativeart.piece.Piece;

import java.awt.*;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        //long seed = System.currentTimeMillis();
        long seed = 1716741432348L;
        Piece piece = new Piece(640, 640, seed);
        piece.addFilter(new FBMNoise(
                new float[]{0, 0, 0, 1},
                new float[]{1f, 0, 0, 1},
                seed,
                13,
                1,
                3,
                1.33,
                .8
        ));
        piece.addFilter(1,
                new Degranulate(8),
                new Scrambulation(4, 0.05, Direction.HORIZONTAL),
                new Scrambulation(4, 0.05, Direction.VERTICAL),
                new Grow(250_000)
                );
        Random random = new Random(seed);
        for (int i = 0; i < 128; i++) {
            double t = Math.PI * 2 * random.nextDouble();
            double r = random.nextDouble() * 64;
            float x = (float) (320 + Math.sin(t) * r);
            float y = (float) (320 + Math.cos(t) * r);
            float[] color;
            double roll = random.nextDouble();
            if (roll < 0.1) {
                color = new float[]{1, 1, 0, 0.5f};
            }
            else if (roll < 0.2){
                color = new float[]{0, 0, 0, 0.5f};
            }
            else {
                color = new float[]{1, 1, 1, 0.5f};
            }
            piece.addFilter(
                    new Ellipse(x, y, 128, 128, color, 100, false)
            );
        }
        piece
                .addFilter(new Ellipse(320, 320, 128, 128, new float[]{0, 1, 0, 1f}, 100, true))
                .addFilter(new RemoveTransparency())
                .addFilter(new ColorToAlpha(new float[]{0, 1, 0, 1f}, 0.0001));
        ImageData stars = new ImageData(640, 640);
        stars.filter(random, new Noise(
                new float[]{1, 1, 1, 1},
                new float[]{0, 0, 0, 1},
                new float[]{0, 0, 0, 1},
                new float[]{0, 0, 0, 1},
                new float[]{0, 0, 0, 1},
                new float[]{0, 0, 0, 1},
                new float[]{0, 0, 0, 1},
                new float[]{0, 0, 0, 1},
                new float[]{0, 0, 0, 1},
                new float[]{0, 0, 0, 1},
                new float[]{0, 0, 0, 1},
                new float[]{0, 0, 0, 1},
                new float[]{0, 0, 0, 1},
                new float[]{0, 0, 0, 1},
                new float[]{0, 0, 0, 1},
                new float[]{0, 0, 0, 1},
                new float[]{0, 0, 0, 1},
                new float[]{0, 0, 0, 1},
                new float[]{0, 0, 0, 1},
                new float[]{0, 0, 0, 1},
                new float[]{0, 0, 0, 1},
                new float[]{0, 0, 0, 1},
                new float[]{0, 0, 0, 1},
                new float[]{0, 0, 0, 1},
                new float[]{0, 0, 0, 1}
        ));
        stars.filter(random, new Degranulate(2));
        stars.filter(random, new Grow(100_000));
        piece.addFilter(new Composite(stars, new float[]{0, 0, 0, 0}, 0));
        piece.addFilter(new Scrambulation(4, 0.045, Direction.VERTICAL));

        piece.addFilter(new Text(20, 620, "amoseman 2024-05-25", new float[]{1, 1, 1, 0.5f}, new Font("Monospaced", Font.PLAIN, 12)));
        piece.build("bmp");
    }
}
