package io.github.mooy1.slimechem.implementation.generators;

import io.github.mooy1.slimechem.implementation.atomic.isotopes.DecayType;
import io.github.mooy1.slimechem.implementation.atomic.isotopes.Isotope;
import io.github.mooy1.slimechem.lists.Categories;
import io.github.mooy1.slimechem.lists.Items;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineFuel;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.Set;

public class RTG1 extends ARTG {

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
        for (Set<Isotope> isotopeSet : Isotope.getIsotopes().values()) {
            for (Isotope isotope : isotopeSet) {
                DecayType decayType = isotope.getDecayType();
                if (decayType == DecayType.STABLE) {
                    continue;
                }

                // Radioactive isotopes always have decay products
                @SuppressWarnings("OptionalGetWithoutIsPresent")
                Isotope decayProduct = isotope.getDecayProduct().get();

                registerFuel(
                    new MachineFuel(10, isotope.isMainIsotope() ? isotope.getCorrespondingElement().getItem() : isotope.getItem()),
                    new SlimefunItemStack[]{decayProduct.isMainIsotope() ? decayProduct.getCorrespondingElement().getItem() : decayProduct.getItem()}
                );
            }
        }
    }
}
