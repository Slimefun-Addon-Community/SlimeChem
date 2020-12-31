package io.github.mooy1.slimechem.utils;

import io.github.mooy1.slimechem.lists.Constants;
import io.github.thebusybiscuit.slimefun4.core.services.CustomItemDataService;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.chat.ChatColors;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.apache.commons.lang.WordUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.List;
import java.util.Optional;

/**
 * General utility methods
 * 
 * @author Seggan
 * @author Mooy1
 * 
 */
public final class Util {

    /**
     * sends a player a message with prefix
     */
    public static void message(@Nonnull Player p, @Nonnull String message) {
        p.sendMessage(PREFIX + message);
    }

    private static final CustomItemDataService dataService = Constants.isTestingEnvironment ? null : SlimefunPlugin.getItemDataService();
    
    /**
     * Gets the slimefun item id of an item, otherwise if vanilla true returns the material id
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

    public static <T> void trimList(List<T> list, int size) {
        while(list.size() > size) {
            list.remove(list.size() - 1);
        }
    }
}
