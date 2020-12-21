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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RTG2 extends AByproductGenerator {

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
        for (Ingredient item : Registry.getRadioactiveItems()) {
            List<DecayProduct> decayProducts = null;
            if (item instanceof Element) {
                DecayProduct[] list = ((Element) item).getDecayProducts();
                if (list != null) {
                    decayProducts = new ArrayList<>(Arrays.asList(list));
                }
            } else if (item instanceof Isotope) {
                DecayProduct[] list = ((Isotope) item).getDecayProducts();
                if (list != null) {
                    decayProducts = new ArrayList<>(Arrays.asList(list));
                }
            } else {
                throw new IllegalArgumentException("Radioactive molecules?!");
            }

            if (decayProducts == null) {
                continue;
            }

            ItemStack[] products = new ItemStack[decayProducts.size()];
            for (int i = 0; i < decayProducts.size(); i++) {
                products[i] = decayProducts.get(i).getItem();
            }
            registerFuel(new MachineFuel(10, item.getItem()), products);
        }
    }
}
