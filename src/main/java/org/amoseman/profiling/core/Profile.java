package org.amoseman.profiling.core;

public class Profile {
    public static long run(ProfileFunction function) {
        long start = System.nanoTime();
        function.run();
        long end = System.nanoTime();
        return end - start;
    }
}
