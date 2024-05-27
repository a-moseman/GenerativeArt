package org.amoseman.generativeart;

import org.amoseman.generativeart.filter.ColorToAlpha;
import org.amoseman.generativeart.filter.Composite;
import org.amoseman.generativeart.filter.Grow;
import org.amoseman.generativeart.filter.Merge;
import org.amoseman.generativeart.filter.draw.Rectangle;
import org.amoseman.generativeart.filter.draw.Text;
import org.amoseman.generativeart.filter.noise.FBMNoise;
import org.amoseman.generativeart.filter.noise.Noise;
import org.amoseman.generativeart.image.ImageData;
import org.amoseman.generativeart.piece.Piece;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Random;

public class Main {

    private static final String SIGNATURE_FORMAT = "%s %s";
    private static final String AUTHOR = "amoseman";

    public static void main(String[] args) {
        int width = 1920;
        int height = 1080;

        long seed;
        Optional<Long> maybe = parseSeed(args);
        seed = maybe.orElseGet(System::currentTimeMillis);
        Random random = new Random(seed);
        Piece piece = new Piece(width, height, seed);


        piece.addFilter(new Rectangle(0, 0, width, height, 0, new ColorValue(0.52f, 0f, 0.251f, 1f), true));

        piece.addFilter(new Rectangle(width / 2 - 800 / 2, height / 2 - 800 / 2, 800, 800, 0, ColorValue.MAGENTA, true));
        ImageData stars = new ImageData(width, height);
        stars.filter(random, new Noise(
                ColorValue.BLACK,
                ColorValue.BLACK,
                ColorValue.BLACK,
                ColorValue.BLACK,
                ColorValue.BLACK,
                ColorValue.BLACK,
                ColorValue.BLACK,
                ColorValue.BLACK,
                ColorValue.BLACK,
                ColorValue.BLACK,
                ColorValue.BLACK,
                ColorValue.BLACK,
                ColorValue.BLACK,
                ColorValue.BLACK,
                ColorValue.BLACK,
                ColorValue.WHITE
        ));
        stars.filter(random, new Grow(1_000_000));
        piece.addFilter(new Composite(stars, ColorValue.MAGENTA));

        int rectW = 560;
        int rectH = 560;
        int rectX = width / 2 - rectW / 2;
        int rectY = height / 2 - rectH / 2;
        piece.addFilter(new Rectangle(rectX, rectY, rectW, rectH, 0.7853982f, ColorValue.GREEN, true));
        piece.addFilter(new Rectangle(rectX, rectY, rectW, rectH, 0.7853982f, ColorValue.BLACK, false, 2));

        ImageData noise = new ImageData(width, height);
        noise.filter(random, new FBMNoise(
                ColorValue.RED,
                ColorValue.BLACK,
                seed,
                13,
                1,
                1,
                2,
                0.9
        ));
        piece.addFilter(new Composite(noise, ColorValue.GREEN));

        for (int i = 0; i < 128; i++) {
            float s = 800f / (i + 1);
            float r = (float) (Math.PI * 2) / (i + 1);
            piece.addFilter(new Rectangle(
                    width / 2 - s / 2,
                    height / 2 - s / 2,
                    s,
                    s,
                    r,
                    ColorValue.WHITE,
                    false,
                    2
            ));
        }

        LocalDateTime now = LocalDateTime.now();
        String signature = String.format(SIGNATURE_FORMAT, AUTHOR, now.format(DateTimeFormatter.ISO_LOCAL_DATE));
        piece.addFilter(new Text(64, height - 64, signature, new ColorValue(1, 1, 1, 0.5f), new Font("Monospaced", Font.PLAIN, 24)));
        piece.build("png");
    }

    private static Optional<Long> parseSeed(String[] args) {
        if (0 == args.length) {
            return Optional.empty();
        }
        try {
            return Optional.of(Long.parseLong(args[0]));
        }
        catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
