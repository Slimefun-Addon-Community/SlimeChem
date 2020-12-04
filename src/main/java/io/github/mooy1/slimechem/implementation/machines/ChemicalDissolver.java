package io.github.mooy1.slimechem.implementation.machines;

import io.github.mooy1.slimechem.setup.Registry;
import io.github.mooy1.slimechem.implementation.atomic.Element;
import io.github.mooy1.slimechem.implementation.atomic.Ingredient;
import io.github.mooy1.slimechem.implementation.atomic.Isotope;
import io.github.mooy1.slimechem.implementation.atomic.Molecule;
import io.github.mooy1.slimechem.implementation.atomic.MoleculeIngredient;
import io.github.mooy1.slimechem.implementation.machines.abstractmachines.Machine;
import io.github.mooy1.slimechem.lists.Items;
import io.github.mooy1.slimechem.utils.MathUtils;
import io.github.mooy1.slimechem.utils.PresetUtils;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.github.thebusybiscuit.slimefun4.utils.tags.SlimefunTag;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.apache.commons.lang.mutable.MutableInt;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * Dissolves {@link Molecule}s, {@link Material}s, and {@link SlimefunItemStack}s
 * into {@link Element}s, {@link Molecule}s, {@link Isotope}s
 * 
 * @author Mooy1
 * 
 */
public class ChemicalDissolver extends Machine {
    
    public static final int ENERGY = 30;
    private static final int[] inputSlots = {2, 3, 4, 5, 6};
    private static final int[] outputSlots = {38, 39, 40, 41, 42, 47, 48, 49, 50, 51};
    private static final int[] inputBorder = {1, 10, 11, 12, 13, 14, 15, 16, 7, 22};
    private static final int[] outputBorder = {28, 29, 30, 31, 32, 33, 34, 37, 43, 46, 52};
    private static final int[] background = {0, 8, 9, 17, 18, 19, 20, 21, 23, 24, 25, 26, 27, 35, 36, 44, 45, 53};
    
    private static final Map<Material, Map<Integer, MoleculeIngredient>> vanillaRecipes = new HashMap<>();
    private static final Map<String, Map<Integer, MoleculeIngredient>> slimefunRecipes = new HashMap<>();
    
    static {
        // Ores
        addRecipe(Material.COAL_ORE, new int[] {90, 10},
            new MoleculeIngredient(Element.CARBON, 12),
            new MoleculeIngredient(Molecule.SILICON_DIOXIDE, 5));
        addRecipe(Material.IRON_ORE, new int[] {50, 30, 10, 6, 4},
            new MoleculeIngredient(Molecule.IRON_III_OXIDE, 4),
            new MoleculeIngredient(Molecule.IRON_II_OXIDE, 4),
            new MoleculeIngredient(Molecule.SILICON_DIOXIDE, 5),
            new MoleculeIngredient(Molecule.IRON_PERSULFIDE, 7),
            new MoleculeIngredient(Molecule.COPPER_IRON_SULFIDE, 6));
        addRecipe(Material.GOLD_ORE, new int[] {70, 20, 10},
            new MoleculeIngredient(Molecule.GOLD_TELLURIDE, 4),
            new MoleculeIngredient(Molecule.GOLD_ANTIMONIDE, 4),
            new MoleculeIngredient(Molecule.SILICON_DIOXIDE, 5));
        addRecipe(Material.REDSTONE_ORE, new int[] {60, 20, 10, 10},
            new MoleculeIngredient(Molecule.COPPER_I_OXIDE, 4),
            new MoleculeIngredient(Molecule.PENTACOPPER_IRON_TETRASULFIDE, 3),
            new MoleculeIngredient(Molecule.COPPER_II_OXIDE, 3),
            new MoleculeIngredient(Molecule.SILICON_DIOXIDE, 5));
        addRecipe(Material.EMERALD_ORE, new int[]{90, 10},
            new MoleculeIngredient(Molecule.BERYLLIUM_ALUMINUM_CYCLOSILICATE, 6),
            new MoleculeIngredient(Molecule.SILICATE, 9));
        addRecipe(Material.ANCIENT_DEBRIS, new int[] {80, 20},
            new MoleculeIngredient(Molecule.SEABORGIUM_III_OXIDE, 4),
            new MoleculeIngredient(Molecule.SILICON_DIOXIDE, 25));

        // Plant matter

        // Wood
        // Standard: 1 plank = 2 cellulose
        // So 1 stick = 1 cellulose, 1 log = 8 cellulose, etc
        for (Material mat : Tag.LOGS.getValues()) {
            addRecipe(mat, new int[]{100}, new MoleculeIngredient(Molecule.CELLULOSE, 8));
        }
        for (Material mat : Tag.PLANKS.getValues()) {
            addRecipe(mat, new int[]{100}, new MoleculeIngredient(Molecule.CELLULOSE, 2));
        }
        // Loss of 1 cellulose
        for (Material mat : Tag.SIGNS.getValues()) {
            addRecipe(mat, new int[]{100}, new MoleculeIngredient(Molecule.CELLULOSE, 4));
        }
        for (Material mat : Tag.ITEMS_BOATS.getValues()) {
            addRecipe(mat, new int[]{100}, new MoleculeIngredient(Molecule.CELLULOSE, 10));
        }
        for (Material mat : Tag.WOODEN_BUTTONS.getValues()) {
            addRecipe(mat, new int[]{100}, new MoleculeIngredient(Molecule.CELLULOSE, 2));
        }
        for (Material mat : Tag.WOODEN_SLABS.getValues()) {
            addRecipe(mat, new int[]{100}, new MoleculeIngredient(Molecule.CELLULOSE));
        }
        for (Material mat : Tag.WOODEN_DOORS.getValues()) {
            addRecipe(mat, new int[]{100}, new MoleculeIngredient(Molecule.CELLULOSE, 2));
        }
        for (Material mat : Tag.WOODEN_PRESSURE_PLATES.getValues()) {
            addRecipe(mat, new int[]{100}, new MoleculeIngredient(Molecule.CELLULOSE, 4));
        }
        for (Material mat : Tag.WOODEN_STAIRS.getValues()) {
            addRecipe(mat, new int[]{100}, new MoleculeIngredient(Molecule.CELLULOSE, 3));
        }
        // loss of 1 cellulose
        for (Material mat : Tag.WOODEN_FENCES.getValues()) {
            addRecipe(mat, new int[]{100}, new MoleculeIngredient(Molecule.CELLULOSE, 3));
        }
        for (Material mat : Tag.FENCE_GATES.getValues()) {
            addRecipe(mat, new int[]{100}, new MoleculeIngredient(Molecule.CELLULOSE, 8));
        }
        addRecipe(Material.STICK, new int[]{100}, new MoleculeIngredient(Molecule.CELLULOSE));
    }
    
    private static Map<Integer, MoleculeIngredient> makeRecipe(int[] chances, MoleculeIngredient... ingredients) {
        Map<Integer, MoleculeIngredient> map = new HashMap<>();
        for (int i = 0 ; i < chances.length ; i++) {
            map.put(chances[i], ingredients[i]);
        }
        return map;
    }
    
    public static void addRecipe(Material mat, int[] chances, MoleculeIngredient... ingredients) {
        vanillaRecipes.put(mat, makeRecipe(chances, ingredients));
    }

    public static void addRecipe(String id, int[] chances, MoleculeIngredient... ingredients) {
        slimefunRecipes.put(id, makeRecipe(chances, ingredients));
    }


    // No idea what thi will be used for, but seems useful
    public static void addRecipe(Ingredient ingredient, int[] chances, MoleculeIngredient... ingredients) {
        slimefunRecipes.put(ingredient.getItem().getItemId(), makeRecipe(chances, ingredients));
    }

    public static void addRecipe(@Nonnull SlimefunItemStack slimefunItemStack, int[] chances, MoleculeIngredient... ingredients) {
        slimefunRecipes.put(slimefunItemStack.getItemId(), makeRecipe(chances, ingredients));
    }

    public ChemicalDissolver() {
        super(Items.CHEMICAL_DISSOLVER, ENERGY, ENERGY * 64, inputSlots, outputSlots, new ItemStack[] {
                
        });
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
    }

    @Override
    public void process(@Nonnull BlockMenu menu, @Nonnull Block b, @Nonnull Location l) {
        MutableInt amount = new MutableInt(0);
        
        for (int slot : inputSlots) {
            ItemStack input = menu.getItemInSlot(slot);

            if (input == null) {
                continue;
            }
            
            SlimefunItem item = SlimefunItem.getByItem(input);
            
            if (item != null) {
                if (Registry.ITEMS.containsKey(item)) { 
                    Ingredient type = Registry.ITEMS.get(item);

                    if (type instanceof Molecule) { //molecule recipe
                        Molecule molecule = (Molecule) type;

                        loop: for (int i = 0 ; i < getMax(l, input) ; i++) {
                            
                            for (MoleculeIngredient ingredient : molecule.getIngredients()) {
                                ItemStack output = ingredient.getNewItem();
                                
                                if (menu.fits(output)) {
                                    menu.pushItem(output, outputSlots);
                                    amount.increment();
                                } else {
                                    break loop;
                                }
                            }
                        }
                    }
                    
                } else if (slimefunRecipes.containsKey(item.getId())) { //sf recipe
                    output(amount, menu, l, input, slimefunRecipes.get(item.getId()));
                } else {
                    continue;
                }
                
            } else if (vanillaRecipes.containsKey(input.getType())) { //vanilla recipe
                output(amount, menu, l, input, vanillaRecipes.get(input.getType()));
            } else {
                continue;
            }

            menu.consumeItem(slot, amount.intValue());
            break;
        }
    }
    
    private void output(MutableInt amount, BlockMenu menu, Location l, ItemStack input, Map<Integer, MoleculeIngredient> outputs) {
        for (int i = 0 ; i < getMax(l, input) ; i++) {
            MoleculeIngredient ingredient = MathUtils.chooseRandom(outputs);
            amount.increment();
            
            if (ingredient != null) {
                ItemStack output = ingredient.getNewItem();

                if (menu.fits(output, outputSlots)) {
                    menu.pushItem(output, outputSlots);
                } else {
                    amount.decrement();
                    break;
                }
            }
        }
    }
    
    private int getMax(Location l, ItemStack input) {
        return (int) Math.min(input.getAmount(), Math.floor((float) getCharge(l) / ENERGY)) / 2;
    }
    
}
