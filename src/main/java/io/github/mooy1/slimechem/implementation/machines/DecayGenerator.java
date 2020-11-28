package io.github.mooy1.slimechem.implementation.machines;

import io.github.mooy1.slimechem.lists.Categories;
import io.github.mooy1.slimechem.lists.Items;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetProvider;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class DecayGenerator extends SlimefunItem implements EnergyNetProvider {

    public static final int BUFFER = 1600;
    
    public DecayGenerator() {
        super(Categories.MACHINES, Items.DECAY_GENERATOR, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                
        });
    }
    
    @Override
    public int getGeneratedOutput(@Nonnull Location l, @Nonnull Config data) {
        return 0;
    }

    @Override
    public int getCapacity() {
        return BUFFER;
    }
    
}
