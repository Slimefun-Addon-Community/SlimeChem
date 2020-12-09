package io.github.mooy1.slimechem.implementation.machines;

import io.github.mooy1.slimechem.implementation.atomic.Element;
import io.github.mooy1.slimechem.implementation.atomic.Ingredient;
import io.github.mooy1.slimechem.implementation.atomic.Isotope;
import io.github.mooy1.slimechem.implementation.atomic.Molecule;
import io.github.mooy1.slimechem.implementation.atomic.MoleculeIngredient;
import io.github.mooy1.slimechem.implementation.machines.abstractmachines.Machine;
import io.github.mooy1.slimechem.lists.Items;
import io.github.mooy1.slimechem.setup.Registry;
import io.github.mooy1.slimechem.utils.MathUtils;
import io.github.mooy1.slimechem.utils.PresetUtils;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Location;
import org.bukkit.Material;
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
 * TODO: remove SlimefunItem#getBtItem calls and some instance ofs
 * 
 */
public class ChemicalDissolver extends Machine {
    
    public static final int ENERGY = 9;
    private static final int[] inputSlots = {2, 3, 4, 5, 6};
    private static final int[] outputSlots = {38, 39, 40, 41, 42, 47, 48, 49, 50, 51};
    private static final int[] inputBorder = {1, 10, 11, 12, 13, 14, 15, 16, 7, 22};
    private static final int[] outputBorder = {28, 29, 30, 31, 32, 33, 34, 37, 43, 46, 52};
    private static final int[] background = {0, 8, 9, 17, 18, 19, 20, 21, 23, 24, 25, 26, 27, 35, 36, 44, 45, 53};
    
    private static final Map<Material, Map<Integer, MoleculeIngredient>> vanillaRecipes = new HashMap<>();
    private static final Map<String, Map<Integer, MoleculeIngredient>> slimefunRecipes = new HashMap<>();
    
    private final Registry registry;
    
    static {
        addRecipe("COPPER_INGOT", new int[] {30}, new MoleculeIngredient(Molecule.CARBON_DIOXIDE, 1));
        addRecipe(Material.DIRT, new int[] {25, 60, 1}, MoleculeIngredient.DIOXIDE, MoleculeIngredient.MONOXIDE, new MoleculeIngredient(Element.ACTINIUM, 5));
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

    public static void addRecipe(SlimefunItemStack slimefunItemStack, int[] chances, MoleculeIngredient... ingredients) {
        slimefunRecipes.put(slimefunItemStack.getItemId(), makeRecipe(chances, ingredients));
    }

    public ChemicalDissolver(@Nonnull Registry registry) {
        super(Items.CHEMICAL_DISSOLVER, ENERGY, ENERGY * 64, inputSlots, outputSlots, new ItemStack[] {
                
        });
        this.registry = registry;
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
        for (int slot : inputSlots) {
            ItemStack input = menu.getItemInSlot(slot);

            if (input == null) {
                continue;
            }

            int amount = 0;
            
            SlimefunItem item = SlimefunItem.getByItem(input);
            
            if (item != null) {

                Ingredient ingredient = this.registry.getItems().get(item);
                
                if (ingredient != null) {
                    if (ingredient instanceof Molecule) {
                        
                        Molecule molecule = (Molecule) ingredient;

                        loop: for (int i = 0 ; i < getMax(l, input) ; i++) {

                            for (MoleculeIngredient check : molecule.getIngredients()) {
                                ItemStack output = check.getNewItem();

                                if (menu.fits(output)) {
                                    menu.pushItem(output, outputSlots);
                                    amount++;
                                } else {
                                    break loop;
                                }
                            }
                        }
                    }
                } else {
                    Map<Integer, MoleculeIngredient> outputs = slimefunRecipes.get(item.getId());
                    
                    if (outputs != null) { //sf recipe
                        output(amount, menu, l, input, outputs);
                    } else {
                        continue;
                    }
                }
                
            } else {
                Map<Integer, MoleculeIngredient> outputs = vanillaRecipes.get(input.getType());

                if (outputs != null) { //vanilla recipe
                    amount = output(amount, menu, l, input, outputs);
                } else {
                    continue;
                }
            }

            menu.consumeItem(slot, amount);
            break;
        }
    }
    
    private int output(int amount, BlockMenu menu, Location l, ItemStack input, Map<Integer, MoleculeIngredient> outputs) {
        for (int i = 0 ; i < getMax(l, input) ; i++) {
            MoleculeIngredient ingredient = MathUtils.chooseRandom(outputs);
            amount++;
            
            if (ingredient != null) {
                ItemStack output = ingredient.getNewItem();

                if (menu.fits(output, outputSlots)) {
                    menu.pushItem(output, outputSlots);
                } else {
                    amount--;
                    break;
                }
            }
        }
        return amount;
    }
    
    private int getMax(Location l, ItemStack input) {
        return (int) Math.min(input.getAmount(), Math.floor((float) getCharge(l) / ENERGY));
    }
    
}
