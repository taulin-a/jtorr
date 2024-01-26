package org.jtorr.component.generator;

import java.util.Random;

public class TransactionIdGenerator {
    private static final int DEFAULT_ORIGIN = 0;
    private static final int DEFAULT_BOUND = 255;
    private final Random random;

    public TransactionIdGenerator() {
        random = new Random();
    }

    public int generate() {
        return random.nextInt(DEFAULT_ORIGIN, DEFAULT_BOUND);
    }
}
