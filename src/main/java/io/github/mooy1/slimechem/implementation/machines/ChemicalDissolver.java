package io.github.mooy1.slimechem.implementation.machines;

import io.github.mooy1.slimechem.implementation.machines.abstractmachine.Machine;
import io.github.mooy1.slimechem.lists.Items;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class ChemicalDissolver extends Machine {
    
    public static final int ENERGY = 30;
    private static final int[] inputSlots = {};
    private static final int[] outputSlots = {};
    private static final int statusSlot = 14;
    
    public ChemicalDissolver() {
        super(Items.CHEMICAL_DISSOLVER, ENERGY, inputSlots, outputSlots, statusSlot, new ItemStack[] {
                
        });
    }

    @Override
    public void setupMenu(@Nonnull BlockMenuPreset preset) {
        
    }

    @Override
    public void process(@Nonnull BlockMenu menu, @Nonnull Block b, @Nonnull Location l) {
        
        for (int slot : inputSlots) {
            
            ItemStack input = menu.getItemInSlot(slot);
            
        }
        
    }
    
}
