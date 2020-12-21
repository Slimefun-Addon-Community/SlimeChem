package io.github.mooy1.slimechem.implementation.machines.abstractmachines;

import io.github.mooy1.slimechem.lists.Categories;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class Machine extends Container implements EnergyNetComponent {
    
    private final int energy;
    private final int buffer;
    private final int[] inputSlots;
    private final int[] outputSlots;
    
    public Machine(SlimefunItemStack item, int energy, int buffer, int[] inputSlots, int[] outputSlots, ItemStack[] recipe) {
        super(Categories.MACHINES, item, RecipeType.ENHANCED_CRAFTING_TABLE, recipe);
        this.energy = energy;
        this.buffer = buffer;
        this.inputSlots = inputSlots;
        this.outputSlots = outputSlots;
        
        registerBlockHandler(getId(), (p, b, item1, reason) -> {
            BlockMenu menu = BlockStorage.getInventory(b);
            if (menu != null) {
                menu.dropItems(b.getLocation(), this.inputSlots);
                menu.dropItems(b.getLocation(), this.outputSlots);
                breakHandler(p, b, menu);
            }
            return true;
        });
        
    }

    
    public void breakHandler(@Nonnull Player p, @Nonnull Block b, @Nonnull BlockMenu menu) {
        // can be overridden
    }
    
    @Override
    public void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
        // can be overridden
    }

    @Override
    public void onMenuItemChange(@Nonnull DirtyChestMenu menu, int slot, @Nullable ItemStack previous, @Nullable ItemStack next) {
        // can be overridden
    }

    public abstract void setupMenu(@Nonnull BlockMenuPreset preset);
    
    public void tick(@Nonnull Block b) {
        Location l = b.getLocation();
        BlockMenu menu = BlockStorage.getInventory(l);
        if (menu == null) return;
        
        if (getCharge(l) < this.energy) {
            return;
        }

        process(menu, b, l);
    }
    
    public abstract void process(@Nonnull BlockMenu menu, @Nonnull Block b, @Nonnull Location l);
    
    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }
    
    @Override
    public int getCapacity() {
        return this.buffer;
    }
    
    @Nonnull
    @Override
    public int[] getTransportSlots(@Nonnull ItemTransportFlow flow) {
        if (flow == ItemTransportFlow.INSERT) return this.inputSlots;
        if (flow == ItemTransportFlow.WITHDRAW) return this.outputSlots;
        return new int[0];
    }
    
}
