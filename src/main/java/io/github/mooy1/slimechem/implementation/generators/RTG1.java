package io.github.mooy1.slimechem.implementation.generators;

import io.github.mooy1.slimechem.implementation.atomic.DecayProduct;
import io.github.mooy1.slimechem.implementation.atomic.Element;
import io.github.mooy1.slimechem.implementation.atomic.Ingredient;
import io.github.mooy1.slimechem.implementation.atomic.Isotope;
import io.github.mooy1.slimechem.lists.Categories;
import io.github.mooy1.slimechem.lists.Items;
import io.github.mooy1.slimechem.setup.Registry;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineFuel;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

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
        for (Ingredient item : Registry.getRadioactiveItems()) {
            List<DecayProduct> decayProducts;
            if (item instanceof Element) {
                decayProducts = Arrays.asList(((Element) item).getDecayProducts());
            } else if (item instanceof Isotope) {
                decayProducts = Arrays.asList(((Isotope) item).getDecayProducts());
            } else {
                throw new IllegalArgumentException("Radioactive molecules?!");
            }

            // Remove all non-isotopes and elements
            decayProducts.removeIf(decayProduct -> !(decayProduct instanceof Ingredient));

            ItemStack[] products = new ItemStack[decayProducts.size()];
            for (int i = 0; i < decayProducts.size(); i++) {
                products[i] = decayProducts.get(i).getItem();
            }
            registerFuel(new MachineFuel(1, item.getItem()), products);
        }
    }
}
