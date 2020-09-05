package name.robertburrelldonkin.personal.fetchpop.app;

import java.util.Random;

import static java.lang.Math.abs;

public class NumberSequence {

    private static final Random random = new Random();

    public static final int nextInt() {
        return random.nextInt();
    }

    public static final int nextPositiveInt() {
        final int result = abs(nextInt());
        return result > 0 ? result : nextPositiveInt();
    }
}
