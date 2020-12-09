package io.github.mooy1.slimechem.implementation.generators;

import io.github.mooy1.slimechem.implementation.atomic.Element;
import io.github.mooy1.slimechem.implementation.atomic.Isotope;
import io.github.mooy1.slimechem.lists.Categories;
import io.github.mooy1.slimechem.lists.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineFuel;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class RTG1 extends AByproductGenerator {

    public RTG1() {
        super(Categories.MACHINES, Items.RTG_1, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[0]);
    }

    @Nonnull
    @Override
    public ItemStack getProgressBar() {
        return new ItemStack(Material.FLINT_AND_STEEL);
    }

    @Override
    protected void registerDefaultFuelTypes() {
        registerFuel(new MachineFuel(3, Element.URANIUM.getItem()), new ItemStack[]{Isotope.URANIUM_235.getItem(),
        Element.HELIUM.getItem()});

        registerFuel(new MachineFuel(3, Element.PLUTONIUM.getItem()));
    }
}
