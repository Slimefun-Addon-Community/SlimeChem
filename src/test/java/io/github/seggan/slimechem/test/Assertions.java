package io.github.seggan.slimechem.test;

import org.junit.Assert;

public class Assertions extends Assert {

    public static void assertThrows(Runnable r, Class<? extends Exception> excpetion) {
        try {
            r.run();
            throw new AssertionError("Exception " + excpetion.getName() + " was not thrown!");
        } catch (Exception e) {
            if (e.getClass() != excpetion) {
                throw new AssertionError(
                    "Expected exception " + excpetion.getName() + ", got " + e.getClass().getName());
            }
        }
    }
}
