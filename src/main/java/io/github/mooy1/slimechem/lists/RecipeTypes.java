package io.github.mooy1.slimechem.lists;

import io.github.mooy1.slimechem.SlimeChem;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

public class RecipeTypes {
    
    private static final SlimeChem instance = SlimeChem.getInstance();

    public static final RecipeType RTG = new RecipeType(new NamespacedKey(instance, "rtg"), new CustomItem(Material.EMERALD_BLOCK, "test"));
    public static final RecipeType FUSION = new RecipeType(new NamespacedKey(instance, "fusion"), new CustomItem(Material.EMERALD_BLOCK, "test"));
    public static final RecipeType DISSOLVER = new RecipeType(new NamespacedKey(instance, "dissolver"), Items.CHEMICAL_DISSOLVER);
    public static final RecipeType COMBINER = new RecipeType(new NamespacedKey(instance, "combiner"), new CustomItem(Material.EMERALD_BLOCK, "test"));
    
}
