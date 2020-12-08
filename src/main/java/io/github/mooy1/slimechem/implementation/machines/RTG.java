package io.github.mooy1.slimechem.implementation.machines;

import io.github.mooy1.slimechem.lists.Categories;
import io.github.mooy1.slimechem.lists.Items;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetProvider;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class RTG extends SlimefunItem implements EnergyNetProvider {
    
    public static final int URANIUM_ENERGY = 6;
    public static final int PLUTONIUM_ENERGY = 36;
    public static final int AMERICIUM_ENERGY = 120;
    public static final int CALIFORNIUM_ENERGY = 360;
    
    private final Type type;
    
    public RTG(Type type) {
        super(Categories.MACHINES, type.getItem(), RecipeType.ENHANCED_CRAFTING_TABLE, type.getRecipe());
        this.type = type;
    }
    
    @Override
    public int getGeneratedOutput(@Nonnull Location l, @Nonnull Config data) {
        return type.getEnergy();
    }

    @Override
    public int getCapacity() {
        return type.getEnergy() * 8;
    }
    
    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum Type {
        
        URANIUM(URANIUM_ENERGY, Items.URANIUM_RTG, new ItemStack[] {
                
        }),
        PLUTONIUM(PLUTONIUM_ENERGY, Items.PLUTONIUM_RTG, new ItemStack[] {

        }),
        AMERICIUM(AMERICIUM_ENERGY, Items.AMERICIUM_RTG, new ItemStack[] {

        }),
        CALIFORNIUM(CALIFORNIUM_ENERGY, Items.CALIFORNIUM_RTG, new ItemStack[] {

        }),
        ;
        
        private final int energy;
        @Nonnull
        private final SlimefunItemStack item;
        @Nonnull
        private final ItemStack[] recipe;
    }
    
}
