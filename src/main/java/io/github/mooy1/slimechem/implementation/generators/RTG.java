package io.github.mooy1.slimechem.implementation.generators;

import io.github.mooy1.slimechem.implementation.atomic.isotopes.Isotope;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetProvider;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import me.mrCookieSlime.Slimefun.cscorelib2.protection.ProtectableAction;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class RTG extends SlimefunItem implements EnergyNetProvider {

    private static final Map<Location, Integer> processing = new HashMap<>();
    private static final Map<Location, Integer> progress = new HashMap<>();

    protected static final Map<Location, ItemStack[]> byproducts = new HashMap<>();
    protected static final Map<Location, Integer> power = new HashMap<>();

    private static final int[] BACKGROUND = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 13, 31, 36, 37, 38, 39, 40, 41, 42, 43, 44};
    private static final int[] BORDER_IN = new int[]{9, 10, 11, 12, 18, 21, 27, 28, 29, 30};
    private static final int[] BORDER_OUT = new int[]{5, 6, 7, 8, 14, 23, 32, 41, 42, 43, 44};

    private static final int[] INPUT_SLOTS = new int[]{19, 20};
    private static final int[] OUTPUT_SLOTS = new int[]{15, 16, 17, 24, 25, 26, 33, 34, 35};

    public RTG(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);

        new BlockMenuPreset(getId(), getItemName()) {

            @Override
            public void newInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
            }

            @Override
            public void init() {
                setupMenu(this);
            }

            @Override
            public boolean canOpen(@Nonnull Block b, @Nonnull Player p) {
                return p.hasPermission("slimefun.inventory.bypass") ||
                    SlimefunPlugin.getProtectionManager().hasPermission(p, b.getLocation(), ProtectableAction.INTERACT_BLOCK);
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
                return getTransportSlots(flow);
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item) {
                return getTransportSlots(flow);
            }

            @Nullable
            @Override
            protected ItemStack onItemStackChange(@Nonnull DirtyChestMenu menu, int slot, @Nullable ItemStack previous, @Nullable ItemStack next) {
                return next;
            }
        };
    }

    public void setupMenu(@Nonnull BlockMenuPreset preset) {
        preset.drawBackground(BACKGROUND);
        for (int i : BORDER_IN) {
            preset.addItem(i, ChestMenuUtils.getInputSlotTexture());
        }
        for (int i : BORDER_OUT) {
            preset.addItem(i, ChestMenuUtils.getOutputSlotTexture());
        }
    }

    @Nonnull
    public int[] getTransportSlots(ItemTransportFlow flow) {
        if (flow == ItemTransportFlow.INSERT) return INPUT_SLOTS;
        if (flow == ItemTransportFlow.WITHDRAW) return OUTPUT_SLOTS;
        return new int[0];
    }

    @Override
    public int getGeneratedOutput(@Nonnull Location l, @Nonnull Config cfg) {
        BlockMenu menu = BlockStorage.getInventory(l);

        if (processing.containsKey(l)) {
            int timeleft = progress.get(l);

            if (timeleft > 0) {
                ChestMenuUtils.updateProgressbar(menu, 22, --timeleft, processing.get(l), new ItemStack(Material.FLINT_AND_STEEL));

                progress.put(l, timeleft);
                return power.get(l);
            } else {
                menu.replaceExistingItem(22, new CustomItem(Material.BLACK_STAINED_GLASS_PANE, " "));

                for (ItemStack item : byproducts.get(l)) {
                    menu.pushItem(item);
                }

                clearData(l);
                return 0;
            }
        } else {
            int slot = 0;
            Isotope isotope = Isotope.getByItem(menu.getItemInSlot(INPUT_SLOTS[slot]));
            if (isotope == null) {
                slot = 1;
                isotope = Isotope.getByItem(menu.getItemInSlot(INPUT_SLOTS[slot]));
                if (isotope == null) return 0;
            }

            // TODO finish
            return 0;
        }
    }

    private static void clearData(Location l) {
        byproducts.remove(l);
        processing.remove(l);
        progress.remove(l);
        power.remove(l);
    }

    @Override
    public int getCapacity() {
        return 0;
    }
}