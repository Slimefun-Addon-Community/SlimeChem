package io.github.seggan.slimechem.test;

import org.junit.Assert;

public class Assertions extends Assert {

    public static void assertThrows(Runnable r, Class<? extends Throwable> throwable) {
        try {
            r.run();
            fail("Exception " + throwable.getName() + " was not thrown!");
        } catch (Throwable e) {
            if (e.getClass() != throwable) {
                fail("Expected exception " + throwable.getName() + ", got " + e.getClass().getName());
            }
        }
    }
}
