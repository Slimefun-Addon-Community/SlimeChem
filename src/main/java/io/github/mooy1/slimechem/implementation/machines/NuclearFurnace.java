package io.github.mooy1.slimechem.implementation.machines;

import io.github.mooy1.slimechem.implementation.atomic.Ingredient;
import io.github.mooy1.slimechem.implementation.atomic.IngredientItem;
import io.github.mooy1.slimechem.implementation.machines.abstractmachines.Container;
import io.github.mooy1.slimechem.lists.Categories;
import io.github.mooy1.slimechem.lists.Items;
import io.github.mooy1.slimechem.setup.Registry;
import io.github.mooy1.slimechem.utils.Util;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.inventory.DirtyChestMenu;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NuclearFurnace extends Container implements RecipeDisplayItem {

    private static final int[] BACKGROUND = {0, 1, 2, 4, 5, 6, 7, 8, 9, 10, 11, 13, 14, 16, 17, 18, 19, 20, 22, 23, 24, 25, 26};
    private static final int FUEL = 21;
    private static final int INPUT = 3;
    private static final int OUTPUT = 15;
    private static final int STATUS = 12;

    private final Map<Material, ItemStack> recipes = new HashMap<>();
    private final Map<String, Integer> fuels = new HashMap<>();
    private final List<ItemStack> displayRecipes = new ArrayList<>();

    private final Map<Block, Integer> map = new HashMap<>();

    public NuclearFurnace() {
        super(Categories.MACHINES, Items.NUCLEAR_FURNACE, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
                
        });

        registerBlockHandler(getId(), (p, b, item1, reason) -> {
            BlockMenu menu = BlockStorage.getInventory(b);
            if (menu != null) {
                menu.dropItems(b.getLocation(), FUEL, INPUT, OUTPUT);
            }
            return true;
        });

        SlimefunPlugin.getMinecraftRecipeService().subscribe(snapshot -> {
            for (FurnaceRecipe furnaceRecipe : snapshot.getRecipes(FurnaceRecipe.class)) {
                RecipeChoice choice = furnaceRecipe.getInputChoice();

                if (choice instanceof RecipeChoice.MaterialChoice) {
                    for (Material input : ((RecipeChoice.MaterialChoice) choice).getChoices()) {
                        this.recipes.put(input, furnaceRecipe.getResult());
                    }
                }
            }
        });

        this.fuels.put(SlimefunItems.TINY_URANIUM.getItemId(), 3);
        this.fuels.put(SlimefunItems.SMALL_URANIUM.getItemId(), 36);
        this.fuels.put(SlimefunItems.URANIUM.getItemId(), 160);
        this.fuels.put(SlimefunItems.NEPTUNIUM.getItemId(), 320);
        this.fuels.put(SlimefunItems.PLUTONIUM.getItemId(), 480);
        this.fuels.put(SlimefunItems.BOOSTED_URANIUM.getItemId(), 720);
        for (Ingredient ingredient : Registry.getRadioactiveItems()) {
            this.fuels.put(ingredient.getItem().getItemId(), 20);
        }

        //display recipes
        for (Map.Entry<String, Integer> entry : this.fuels.entrySet()) {
            SlimefunItem sfItem = SlimefunItem.getByID(entry.getKey());
            if (sfItem != null) {
                ItemStack stack = sfItem.getItem().clone();
                ItemMeta meta = stack.getItemMeta();
                if (meta != null && meta.getLore() != null) {
                    List<String> lore = meta.getLore();
                    lore.add(" ");
                    lore.add(ChatColor.GOLD + "Smelts: " + entry.getValue() + " items");
                    meta.setLore(lore);
                    stack.setItemMeta(meta);
                    this.displayRecipes.add(stack);
                }
            }
        }
    }

    @Override
    public void setupMenu(@Nonnull BlockMenuPreset preset) {
        for (int i : BACKGROUND) {
            preset.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }
        preset.addMenuClickHandler(STATUS, ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    public void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
        this.map.put(b, 0);
        menu.replaceExistingItem(STATUS, getFuelItem(0));
    }

    @Override
    public void tick(@Nonnull Block b) {
        BlockMenu menu = BlockStorage.getInventory(b);
        if (menu == null) return;

        ItemStack input = menu.getItemInSlot(INPUT);

        if (input == null) return;

        int fuel = this.map.getOrDefault(b, 0);

        if (fuel < 1) {
            fuel = addFuel(menu, menu.getItemInSlot(FUEL));
            if (fuel < 1) return;
        }
        
        process(b, menu, input, fuel);

    }

    /**
     * returns amount of fuel added
     */
    private int addFuel(@Nonnull BlockMenu menu, @Nullable ItemStack fuel) {
        String id = Util.getItemID(fuel, false);

        if (id == null) return 0;

        int amount = this.fuels.getOrDefault(id, 0);

        if (amount < 1) return 0;

        menu.consumeItem(FUEL, 1);

        return amount;

    }

    private void process(@Nonnull Block b, @Nonnull BlockMenu menu, @Nonnull ItemStack input, int fuel) {
        ItemStack output = this.recipes.get(input.getType());

        if (output != null && menu.fits(output, OUTPUT)) {
            menu.pushItem(output.clone(), OUTPUT);
            menu.consumeItem(INPUT, 1);
            fuel--;
        }
        
        this.map.put(b, fuel);
        menu.replaceExistingItem(STATUS, getFuelItem(fuel), false);
        
    }

    @Nonnull
    @Override
    public int[] getTransportSlots(@Nonnull ItemTransportFlow flow) {
        if (flow == ItemTransportFlow.WITHDRAW) {
            return new int[] {OUTPUT};
        }
        return new int[] {INPUT};
    }

    @Nonnull
    @Override
    public int[] getTransportSlots(DirtyChestMenu menu, ItemTransportFlow flow, ItemStack item) {
        if (flow == ItemTransportFlow.WITHDRAW) {
            return new int[] {OUTPUT};
        }
        String id = Util.getItemID(item, false);
        if (id == null) {
            if (this.recipes.containsKey(item.getType())) {
                return new int[] {INPUT};
            }
        } else if (this.fuels.containsKey(id)) {
            return new int[] {FUEL};
        }
        return new int[0];
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        return this.displayRecipes;
    }

    @Nonnull
    @Override
    public String getRecipeSectionLabel(@Nonnull Player p) {
        return "&7Fuels";
    }

    @Nonnull
    private ItemStack getFuelItem(int fuel) {
        return new CustomItem(
                fuel < 1 ? Material.GRAY_STAINED_GLASS_PANE :
                        fuel < 36 ? Material.RED_STAINED_GLASS_PANE :
                                fuel < 161 ? Material.ORANGE_STAINED_GLASS_PANE :
                                        Material.YELLOW_STAINED_GLASS_PANE, "&6Fuel: " + fuel
        );
    }

}
