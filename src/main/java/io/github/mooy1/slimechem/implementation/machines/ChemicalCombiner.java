package io.github.mooy1.slimechem.implementation.machines;

import io.github.mooy1.slimechem.implementation.atomic.Element;
import io.github.mooy1.slimechem.implementation.atomic.Isotope;
import io.github.mooy1.slimechem.implementation.atomic.Molecule;
import io.github.mooy1.slimechem.implementation.atomic.MoleculeIngredient;
import io.github.mooy1.slimechem.implementation.machines.abstractmachines.Machine;
import io.github.mooy1.slimechem.lists.Items;
import io.github.mooy1.infinitylib.filter.FilterType;
import io.github.mooy1.infinitylib.filter.ItemFilter;
import io.github.mooy1.infinitylib.filter.MultiFilter;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Combines element and molecules into molecules and substances
 * 
 * @author Mooy1
 * 
 * TODO: add button that opens a guide that u can click to pull item from inv to slots
 * 
 * TODO: target slot barrier when empty and add recipe book
 *
 */
public class ChemicalCombiner extends Machine {

    public static final int ENERGY = 15;
    
    private static final ItemStack keyItem = new CustomItem(Material.LIME_STAINED_GLASS_PANE,
            "&aTarget Item",
            "&7A target item is needed to craft more than 1 item at a time",
            "&7Cargo will fill the correct slots to craft this item",
            "&7The target item must have the exact amount that the recipe creates"
    );
    private static final ItemStack emptyKey = new CustomItem(Material.BARRIER, "&cNo Target", "&7Place a item here to set it as the target");
    
    private static final int[] inputSlots = {10, 11, 12, 19, 20, 21};
    private static final int[] outputSlots = {25};
    private static final int keySlot = 16;
    private static final int[] keySlots = {6, 7, 8, 15, 17};
    private static final int[] inputBorder = {0, 1, 2, 3, 4, 9, 13, 18, 22, 27, 28, 29, 30, 31};
    private static final int[] outputBorder = {24, 26, 33, 34, 35};
    private static final int[] background = {5, 14, 23, 32};

    private final BiMap<ItemFilter, MultiFilter> recipes = HashBiMap.create(Molecule.values().length * 2);
    private final Map<Location, MultiFilter> targetCache = new HashMap<>();

    public ChemicalCombiner() {
        super(Items.CHEMICAL_COMBINER, ENERGY, ENERGY * 64, inputSlots, outputSlots, new ItemStack[] {
                
        });
        
        for (Molecule m : Molecule.values()) {
            this.recipes.put(new ItemFilter(m.getItem()), m.toFilter(6));
        }

        addRecipe(new SlimefunItemStack(SlimefunItems.COPPER_INGOT, 2), null, MoleculeIngredient.DIHYDROGEN);
        
    }

    @Override
    public void breakHandler(@Nonnull Player p, @Nonnull Block b, @Nonnull BlockMenu menu) {
        menu.dropItems(b.getLocation(), keySlot);
        this.targetCache.remove(b.getLocation());
    }
    
    public void addRecipe(@Nonnull ItemStack item, @Nonnull MoleculeIngredient... is) {
        this.recipes.put(new ItemFilter(item), MoleculeIngredient.getMultiFilter(is, 6));
    }

    @Override
    public void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
        menu.addMenuClickHandler(keySlot, new ChestMenu.AdvancedMenuClickHandler() {
            @Override
            public boolean onClick(InventoryClickEvent inventoryClickEvent, Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                if (inventoryClickEvent.getAction() != InventoryAction.HOTBAR_SWAP) {
                    ItemStack item = player.getItemOnCursor();
                    if (updateCache(b, item)) {
                        menu.replaceExistingItem(keySlot, makeUnique(item));
                    } else {
                        menu.replaceExistingItem(keySlot, emptyKey);
                    }
                }
                return false;
            }

            @Override
            public boolean onClick(Player player, int i, ItemStack itemStack, ClickAction clickAction) {
                return false;
            }
        });

        // add to cache
        ItemStack current = menu.getItemInSlot(keySlot);
        if (current == null || !updateCache(b, current)) {
            menu.replaceExistingItem(keySlot, emptyKey, false);
        }
    }

    private final NamespacedKey key = new NamespacedKey(SlimeChem.getInstance(), "target_item");

    @Nonnull
    private ItemStack makeUnique(@Nonnull ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.getPersistentDataContainer().set(this.key, PersistentDataType.BYTE, (byte) 1);
        }
        ItemStack unique = item.clone();
        unique.setItemMeta(meta);
        return unique;
    }

    private boolean updateCache(@Nonnull Block b, @Nonnull ItemStack item) {
        if (item.getType() == Material.AIR) {
            this.targetCache.put(b.getLocation(), null);
        } else {
            MultiFilter filter = this.recipes.get(new ItemFilter(item));
            if (filter != null) {
                this.targetCache.put(b.getLocation(), filter);
                return true;
            }
        }
        return false;
    }

    @Nonnull
    @Override
    public int[] getTransportSlots(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item) {
        if (flow == ItemTransportFlow.WITHDRAW) {
            return outputSlots;
        }
        @Nullable MultiFilter recipe = this.targetCache.get(((BlockMenu) menu).getLocation());
        if (recipe != null) {
            int i = recipe.indexOf(new ItemFilter(item), FilterType.IGNORE_AMOUNT);
            if (i > -1) return new int[] {inputSlots[i]};
        }
        return new int[0];
    }

    @Override
    public void setupMenu(@Nonnull BlockMenuPreset preset) {
        for (int i : inputBorder) {
            preset.addItem(i, MenuPreset.borderItemInput, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : outputBorder) {
            preset.addItem(i, MenuPreset.borderItemOutput, ChestMenuUtils.getEmptyClickHandler());
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
        
        MultiFilter input = MultiFilter.fromMenu(menu, inputSlots);

        if (input.hashCode() == 21) return;

        MultiFilter recipe = this.targetCache.get(l);

        if (recipe == null ) { // we look for a new recipe

            @Nullable ItemFilter output = this.recipes.inverse().get(input);

            if (output != null) { // found a matching recipe

                craft(input, output.getItem(), menu, l);
                menu.replaceExistingItem(keySlot, makeUnique(output.getItem()), false);
                updateCache(b, output.getItem());
            }

        } else if (recipe.matches(input, FilterType.MIN_AMOUNT)) { // check if input matches

            craft(recipe, menu.getItemInSlot(keySlot), menu, l);

        }
    }
    
    private void craft(@Nonnull MultiFilter recipe, @Nonnull ItemStack output, @Nonnull BlockMenu menu, @Nonnull Location l) {
        
        ItemStack target = menu.getItemInSlot(outputSlots[0]);

        if (target != null) {
            if (output.getAmount() + target.getAmount() <= target.getType().getMaxStackSize() && new ItemFilter(target).matches(new ItemFilter(output), FilterType.IGNORE_AMOUNT)) {
                target.setAmount(target.getAmount() + output.getAmount());
            } else {
                return;
            }
        } else {
            menu.replaceExistingItem(outputSlots[0], output);
        }

        for (int i = 0 ; i < recipe.size() ; i++) {
            int amount = recipe.getAmounts()[i];
            if (amount > 0) {
                menu.consumeItem(inputSlots[i], amount);
            }
        }
        
        removeCharge(l, ENERGY);

    }
    
}

