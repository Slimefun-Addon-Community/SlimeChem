package io.github.mooy1.slimechem.implementation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.inventory.ItemStack;

/**
 * enum of crafting materials for machines
 * 
 * @author Mooy1
 * 
 */
@Getter
@AllArgsConstructor
public enum Materials {

    ;

    private final SlimefunItemStack item;
    private final RecipeType recipeType;
    private final ItemStack[] recipe;
    
}
