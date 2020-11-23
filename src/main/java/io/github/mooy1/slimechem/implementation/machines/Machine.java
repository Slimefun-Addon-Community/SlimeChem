package io.github.mooy1.slimechem.implementation.machines;

import io.github.mooy1.slimechem.lists.Categories;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.networks.energy.EnergyNetComponentType;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.BlockTicker;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectableAction;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public abstract class Machine extends SlimefunItem implements EnergyNetComponent {
    
    private static final ItemStack LOW_ENERGY = new CustomItem(Material.RED_STAINED_GLASS_PANE, "&cNot enough energy!");
    
    private final int energy;
    private final int[] inputSlots;
    private final int[] outputSlots;
    private final int statusSlot;
    
    public Machine(SlimefunItemStack item, ItemStack[] recipe, int energy, int[] inputSlots, int[] outputSlots, int statusSlot) {
        super(Categories.MACHINES, item, RecipeType.ENHANCED_CRAFTING_TABLE, recipe);
        this.energy = energy;
        this.inputSlots = inputSlots;
        this.outputSlots = outputSlots;
        this.statusSlot = statusSlot;
        
        new BlockMenuPreset(getId(), getItemName()) {
    
            @Override
            public void newInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
                onNewInstance(menu, b);
            }
            
            @Override
            public void init() {
                addItem(statusSlot, LOW_ENERGY, ChestMenuUtils.getEmptyClickHandler());
                setupMenu(this);
            }
    
            @Override
            public boolean canOpen(@Nonnull Block b, @Nonnull Player p) {
                return p.hasPermission("slimefun.inventory.bypass") ||
                        SlimefunPlugin.getProtectionManager().hasPermission(p, b.getLocation(), ProtectableAction.ACCESS_INVENTORIES);
            }
    
            @Override
            public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
                if (flow == ItemTransportFlow.INSERT) return inputSlots;
                if (flow == ItemTransportFlow.WITHDRAW) return outputSlots;
                return new int[0];
            }
        };
        
        registerBlockHandler(getId(), (p, b, item1, reason) -> {
            BlockMenu menu = BlockStorage.getInventory(b);
            if (menu != null) {
                menu.dropItems(b.getLocation(), inputSlots);
                menu.dropItems(b.getLocation(), outputSlots);
            }
            return true;
        });
    }
    
    @Override
    public void preRegister() {
        addItemHandler(new BlockTicker() {
            @Override
            public boolean isSynchronized() {
                return false;
            }
            @Override
            public void tick(Block b, SlimefunItem item, Config data) {
                Machine.this.tick(b);
            }
        });
    }
    
    public void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
        //can be overridden
    }
    
    public abstract void setupMenu(@Nonnull BlockMenuPreset preset);
    
    private void tick(Block b) {
        Location l = b.getLocation();
        BlockMenu menu = BlockStorage.getInventory(l);
        if (menu == null) return;
        
        if (getCharge(l) < energy) {
            if (menu.hasViewer()) {
                menu.replaceExistingItem(statusSlot, LOW_ENERGY);
            }
            return;
        }
    }
    
    @Nonnull
    @Override
    public EnergyNetComponentType getEnergyComponentType() {
        return EnergyNetComponentType.CONSUMER;
    }
    
    @Override
    public int getCapacity() {
        return energy * 2;
    }
}
