package io.github.mooy1.slimechem.implementation.machines.abstractmachine;

import io.github.mooy1.slimechem.lists.Categories;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public abstract class Machine extends TickerBlock implements EnergyNetComponent {
    
    private static final ItemStack LOW_ENERGY = new CustomItem(Material.RED_STAINED_GLASS_PANE, "&cNot enough energy!");
    
    private final int energy;
    private final int[] inputSlots;
    private final int[] outputSlots;
    private final int statusSlot;
    
    public Machine(SlimefunItemStack item, int energy, int[] inputSlots, int[] outputSlots, int statusSlot, ItemStack[] recipe) {
        super(Categories.MACHINES, item, RecipeType.ENHANCED_CRAFTING_TABLE, recipe);
        this.energy = energy;
        this.inputSlots = inputSlots;
        this.outputSlots = outputSlots;
        this.statusSlot = statusSlot;
        
        registerBlockHandler(getId(), (p, b, item1, reason) -> {
            BlockMenu menu = BlockStorage.getInventory(b);
            if (menu != null) {
                menu.dropItems(b.getLocation(), inputSlots);
                menu.dropItems(b.getLocation(), outputSlots);
            }
            return true;
        });
    }
    
    public void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
        //can be overridden
    }
    
    public void setupInv(@Nonnull BlockMenuPreset preset) {
        preset.addItem(statusSlot, LOW_ENERGY, ChestMenuUtils.getEmptyClickHandler());
        //override and call super to add the rest
    }
    
    public abstract void setupMenu(@Nonnull BlockMenuPreset preset);
    
    public void tick(@Nonnull Block b) {
        Location l = b.getLocation();
        BlockMenu menu = BlockStorage.getInventory(l);
        if (menu == null) return;
        
        if (getCharge(l) < energy) {
            if (menu.hasViewer()) {
                menu.replaceExistingItem(statusSlot, LOW_ENERGY);
            }
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
        return energy * 2;
    }
    
    @Nonnull
    @Override
    public int[] getTransportSlots(@Nonnull ItemTransportFlow flow) {
        if (flow == ItemTransportFlow.INSERT) return inputSlots;
        if (flow == ItemTransportFlow.WITHDRAW) return outputSlots;
        return new int[0];
    }
    
}
