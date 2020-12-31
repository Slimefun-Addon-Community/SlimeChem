package io.github.mooy1.slimechem.utils;

import io.github.mooy1.slimechem.lists.Constants;
import io.github.thebusybiscuit.slimefun4.core.services.CustomItemDataService;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.chat.ChatColors;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
    
    private static final String PREFIX = ChatColors.color("&7[&bSlimeChem&7]&f ");
    
    /**
     * broadcasts a message with prefix
     */
    public static void broadcast(@Nonnull String message) {
        Bukkit.broadcastMessage(PREFIX + message);
    }
    
    /**
     * sends a player a message with prefix
     */
    public static void message(@Nonnull Player p, @Nonnull String message) {
        p.sendMessage(PREFIX + message);
    }

    private static final CustomItemDataService dataService = Constants.isTestingEnvironment ? null : SlimefunPlugin.getItemDataService();
    
    /**
     * Gets the slimefun item id of an item, otherwise if vanilla true returns the material id
     */
    @Nullable
    public static String getItemID(@Nullable ItemStack item, boolean vanilla) {

        if (item instanceof SlimefunItemStack) {
            return ((SlimefunItemStack) item).getItemId();
        }

        Optional<String> itemID = dataService.getItemData(item);

        if (itemID.isPresent()) {
            return itemID.get();
        }
        
        if (vanilla && item != null) {
            return item.getType().toString();
        }

        return null;
    }

    public static <T> void trimList(List<T> list, int size) {
        while(list.size() > size) {
            list.remove(list.size() - 1);
        }
    }
}
