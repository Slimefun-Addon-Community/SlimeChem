package io.github.mooy1.slimechem.implementation;

import io.github.mooy1.slimechem.lists.Categories;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.inventory.ItemStack;

public class Material extends SlimefunItem implements NotPlaceable {
    public Material(Type type) {
        super(Categories.MACHINES, type.getItem(), type.getRecipeType(), type.getRecipe());
    }
    
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {
        
        
        
        ;
        
        private final SlimefunItemStack item;
        private final RecipeType recipeType;
        private final ItemStack[] recipe;
        
    }
}
