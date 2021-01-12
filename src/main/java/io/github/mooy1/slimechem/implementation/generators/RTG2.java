package io.github.mooy1.slimechem.implementation.generators;

import io.github.mooy1.slimechem.implementation.atomic.isotopes.Isotope;
import io.github.mooy1.slimechem.lists.Categories;
import io.github.mooy1.slimechem.lists.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.Set;

public class RTG2 extends ARTG {

    public RTG2() {
        super(Categories.MACHINES, Items.RTG_2, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[9]);
    }

    @Nonnull
    @Override
    public ItemStack getProgressBar() {
        return new ItemStack(Material.FLINT_AND_STEEL);
    }

    @Override
    protected void registerDefaultFuelTypes() {
        for (Set<Isotope> isotopeSet : Isotope.getIsotopes().values()) {
            for (Isotope isotope : isotopeSet) {
                registerFuel(isotope, true);
            }
        }
    }
}
