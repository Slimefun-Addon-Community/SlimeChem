package io.github.mooy1.slimechem.implementation.machines;

import io.github.mooy1.slimechem.implementation.atomic.Element;
import io.github.mooy1.slimechem.implementation.atomic.Isotope;
import io.github.mooy1.slimechem.implementation.atomic.Molecule;
import io.github.mooy1.slimechem.implementation.atomic.MoleculeIngredient;
import io.github.mooy1.slimechem.implementation.machines.abstractmachines.Machine;
import io.github.mooy1.slimechem.lists.Items;
import io.github.mooy1.slimechem.utils.ItemFilter;
import io.github.mooy1.slimechem.utils.PresetUtils;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * Combines element and molecules into molecules and substances
 * 
 * @author Mooy1
 * 
 * TODO: add button that opens a guide that u can click to pull item from inv to slots
 * 
 */
public class ChemicalCombiner extends Machine {

    public static final int ENERGY = 15;
    private static final int[] inputSlots = {10, 11, 12, 19, 20, 21, 28, 29, 30};
    private static final int[] outputSlots = {25};
    
    private static final ItemStack keyItem = new CustomItem(Material.LIME_STAINED_GLASS_PANE,
            "&aTarget Item",
            "&7A target item is needed to craft more than 1 item at a time",
            "&7Place the exact amount needed for that item, then use that item here",
            "&7Cargo will fill the correct slots to craft this item",
            "&7The target item must have the exact amount that the recipe creates"
    );
    private static final int keySlot = 23;
    private static final int[] keySlots = {14, 32};
    private static final int[] inputBorder = {0, 1, 2, 3, 4, 9, 13, 18, 22, 27, 31, 36, 37, 38, 39, 40};
    private static final int[] outputBorder = {15, 16, 17, 24, 26, 33, 34, 35};
    private static final int[] background = {5, 6, 7, 8,    41, 42, 43, 44};
    
    private final Map<ItemFilter, ItemFilter[]> recipes = new HashMap<>();

    public ChemicalCombiner() {
        super(Items.CHEMICAL_COMBINER, ENERGY, ENERGY * 64, inputSlots, outputSlots, new ItemStack[] {
                
        });
        
        for (Molecule m : Molecule.values()) {
            this.recipes.put(new ItemFilter(m.getItem()), m.toFilter(9));
        }
        
        addRecipe(new ItemStack(Material.GLASS), MoleculeIngredient.MONOXIDE, new MoleculeIngredient(Element.ARGON, 4), null, new MoleculeIngredient(Isotope.AMERICIUM_241, 2));
        addRecipe(new SlimefunItemStack(SlimefunItems.COPPER_INGOT, 2), null, MoleculeIngredient.DIHYDROGEN);
        
    }

    @Override
    public void breakHandler(@Nonnull Player p, @Nonnull Block b, @Nonnull BlockMenu menu) {
        menu.dropItems(b.getLocation(), keySlot);
    }
    
    public void addRecipe(@Nonnull ItemStack item, @Nonnull MoleculeIngredient... is) {
        this.recipes.put(new ItemFilter(item), MoleculeIngredient.getFilter(is, 9));
    }

    @Nonnull
    @Override
    public int[] getTransportSlots(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item) {
        if (flow == ItemTransportFlow.WITHDRAW) {
            return outputSlots;
        }
        
        ItemStack key = menu.getItemInSlot(keySlot);

        if (key != null ) {
            ItemFilter[] recipe = this.recipes.get(new ItemFilter(key));
            if (recipe != null) {
                ItemFilter filter = new ItemFilter(item);
                for (int i = 0 ; i < recipe.length ; i++) {
                    ItemFilter match = recipe[i];
                    if (match != null && match.matches(filter, ItemFilter.Type.IGNORE_AMOUNT)) return new int[] {i};
                }
            }
        }

        return new int[0];
    }

    @Override
    public void setupMenu(@Nonnull BlockMenuPreset preset) {
        for (int i : inputBorder) {
            preset.addItem(i, PresetUtils.borderItemInput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : outputBorder) {
            preset.addItem(i, PresetUtils.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : background) {
            preset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : keySlots) {
            preset.addItem(i, keyItem, ChestMenuUtils.getEmptyClickHandler());
        }
    }

    @Override
    public void process(@Nonnull BlockMenu menu, @Nonnull Block b, @Nonnull Location l) {
        
        ItemFilter[] input = ItemFilter.getArray(menu, inputSlots, 9);

        ItemStack key = menu.getItemInSlot(keySlot);

        if (key != null ) {
            ItemFilter filter = new ItemFilter(key);
            ItemFilter[] recipe = this.recipes.get(filter);
            if (recipe != null) {
                outputIfMatches(input, recipe, filter.getItem(), menu, l, false);
            }
        } else for (Map.Entry<ItemFilter, ItemFilter[]> entry : this.recipes.entrySet()) {
            if (outputIfMatches(input, entry.getValue(), entry.getKey().getItem(), menu, l, true)) {
                break;
            }
        }
    }
    
    private boolean outputIfMatches(@Nonnull ItemFilter[] input, @Nonnull ItemFilter[] recipe, @Nonnull ItemStack output, @Nonnull BlockMenu menu, @Nonnull Location l, boolean tryKey) {
        if (!ItemFilter.arrayMatches(recipe, input, tryKey ? ItemFilter.Type.EQUAL_AMOUNT : ItemFilter.Type.MIN_AMOUNT) || !menu.fits(output, outputSlots)) return false;
        
        for (int i = 0 ; i < recipe.length ; i++) {
            ItemFilter filter = recipe[i];
            if (filter != null) {
                menu.consumeItem(inputSlots[i], filter.getAmount());
            }
        }

        if (tryKey && menu.getItemInSlot(keySlot) == null) {
            menu.pushItem(output.clone(), keySlot);
        } else {
            menu.pushItem(output.clone(), outputSlots);
        }
        
        removeCharge(l, ENERGY);
        return true;
        
    }
    
}

