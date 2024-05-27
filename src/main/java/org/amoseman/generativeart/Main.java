package org.amoseman.generativeart;

import org.amoseman.generativeart.filter.*;
import org.amoseman.generativeart.filter.Composite;
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
    private static final String AUTHOR = "a-moseman";

    public static void main(String[] args) {
        int width = 1920;
        int height = 1080;
        long seed = parseSeed(args).orElseGet(System::currentTimeMillis);
        Random random = new Random(seed);

        Piece piece = new Piece(width, height, seed);
        piece
                .addFilter(new Noise())
                .addFilter(new Blur());

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
