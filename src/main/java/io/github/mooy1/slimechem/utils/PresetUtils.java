package io.github.mooy1.slimechem.utils;

import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Collection of {@link ItemStack}s for creating BlockMenuPresets
 *
 * @author Mooy1
 */
public final class PresetUtils {
    
    public static final ItemStack loadingItemRed = new CustomItem(
            Material.RED_STAINED_GLASS_PANE,
            "&cLoading...");
    public static final ItemStack invalidInput = new CustomItem(
            Material.BARRIER,
            "&cInvalid Input!");
    public static final ItemStack invisibleBackground = new CustomItem(
            Material.LIGHT_GRAY_STAINED_GLASS_PANE,
            " ");
    public static final ItemStack craftedIn =  new CustomItem(
            Material.CYAN_STAINED_GLASS_PANE,
            "&3Crafted in");
    public static final ItemStack loadingItemBarrier = new CustomItem(
            Material.BARRIER,
            "&cLoading...");
    public static final ItemStack inputAnItem = new CustomItem(
            Material.BLUE_STAINED_GLASS_PANE,
            "&9Input an item");
    public static final ItemStack invalidRecipe = new CustomItem(
            Material.BARRIER,
            "&cInvalid Recipe!");
    public static final ItemStack notEnoughEnergy = new CustomItem(
            Material.RED_STAINED_GLASS_PANE,
            "&cNot enough energy!");
    public static final ItemStack notEnoughRoom = new CustomItem(
            Material.ORANGE_STAINED_GLASS_PANE,
            "&6Not enough room!");
    public static final ItemStack borderItemInput = new CustomItem(
            Material.BLUE_STAINED_GLASS_PANE,
            "&9Input");
    public static final ItemStack borderItemOutput = new CustomItem(
            Material.ORANGE_STAINED_GLASS_PANE,
            "&6Output");
    public static final ItemStack borderItemStatus = new CustomItem(
            Material.CYAN_STAINED_GLASS_PANE,
            "&3Status");
    public static final ItemStack connectToEnergyNet = new CustomItem(
            Material.RED_STAINED_GLASS_PANE,
            "&cConnect to an energy network!");
    
}
