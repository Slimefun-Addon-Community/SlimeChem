package io.github.mooy1.slimechem.utils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Collection of math and random number utility methods
 * 
 * @author Mooy1
 * 
 */
public final class MathUtils {

    /**
     * This method returns a random int from the given range, inclusive
     *
     * @param min minimum int, inclusive
     * @param max maximum int, inclusive
     * @return random int in range
     */
    public static int randomFromRange(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    /**
     * This method will return true 1 / chance times.
     *
     * @param chance average tries to return true
     * @return true if chance
     */
    public static boolean chanceIn(int chance) {
        return randomFromRange(1, chance) == chance;
    }

    /**
     * Chooses a random T object from the valueSet according to its % chance in the keySet
     * 
     * @param map map to choose from.
     * @param <T> This is the object that will be returned and is the type of the valueSet.
     * @return a random T object from the valueSet according to its % chance in the keySet
     */
    @Nullable
    public static <T> T chooseRandom(@Nonnull Map<Integer, T> map) {
        return chooseRandom(map, 100);
    }

    /**
     * Chooses a random T object from the valueSet according to its chance out of total in the keySet
     * 
     * @param map map to choose from.
     * @param total chance that keys are out of. for %, it would be 100. Defaults to 100.
     * @param <T> This is the object that will be returned and is the type of the valueSet.
     * @return a random T object from the valueSet according to its chance out of total in the keySet
     */
    @Nullable
    public static <T> T chooseRandom(@Nonnull Map<Integer, T> map, int total) {

        int random = ThreadLocalRandom.current().nextInt(1, total + 1);

        for (Map.Entry<Integer, T> entry : map.entrySet()) {
            if (random <= entry.getKey()) {
                return entry.getValue();
            }
            random -= entry.getKey();
        }

        return null;
    }
    
}
