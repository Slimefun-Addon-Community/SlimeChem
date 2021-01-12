package io.github.mooy1.slimechem.objects;

import io.github.mooy1.slimechem.implementation.attributes.Ingredient;
import io.github.thebusybiscuit.slimefun4.core.attributes.Radioactive;
import io.github.thebusybiscuit.slimefun4.core.attributes.Radioactivity;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import org.bukkit.inventory.ItemStack;

@Getter
public class RadioactiveItem extends IngredientItem implements Radioactive {

    private final Radioactivity radioactivity;

    public RadioactiveItem(Category category, Ingredient ingredient, RecipeType recipeType, ItemStack[] recipe, Radioactivity radioactivity) {
        super(category, ingredient, recipeType, recipe);

        this.radioactivity = radioactivity;
    }
}
