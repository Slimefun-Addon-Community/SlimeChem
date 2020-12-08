package io.github.mooy1.slimechem.implementation.machines;

import io.github.mooy1.slimechem.implementation.atomic.Ingredient;
import io.github.mooy1.slimechem.implementation.atomic.Molecule;
import io.github.mooy1.slimechem.implementation.atomic.MoleculeIngredient;
import io.github.mooy1.slimechem.implementation.machines.abstractmachines.Machine;
import io.github.mooy1.slimechem.lists.Items;
import io.github.mooy1.slimechem.utils.PresetUtils;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChemicalCombiner extends Machine {

    public static final int ENERGY = 30;
    private static final int[] inputSlots = {10, 11, 12, 19, 20, 21, 28, 29, 30};
    private static final int[] outputSlots = {25};
    private static final int[] inputBorder = {0, 1, 2, 3, 4, 9, 13, 18, 22, 27, 31, 36, 37, 38, 39, 40};
    private static final int[] outputBorder = {15, 16, 17, 24, 26, 33, 34, 35};
    private static final int[] background = {5, 6, 7, 8, 14, 23, 32, 41, 42, 43, 44};

    public ChemicalCombiner() {
        super(Items.CHEMICAL_COMBINER, ENERGY, ENERGY, inputSlots, outputSlots, new ItemStack[] {
                
        });
    }

    @Override
    public void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {
        // TODO: 12/3/2020 add button to toggle cargo mode, keeps 1 of each type of item in slots, override get accessible slots as well
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
        List<Molecule> possible = Arrays.asList(Molecule.values());
        
        // for each slot the list of possible matching molecules will be narrowed down until there is 1 or 0
        for (int i = 0 ; i < inputSlots.length ; i++) {
            ItemStack input = menu.getItemInSlot(inputSlots[i]);
            
            if (input == null) {
                return;
            }

            SlimefunItem item = SlimefunItem.getByItem(input);

            if (!(item instanceof Ingredient)) {
                return;
            }
            
            Ingredient ingredient = (Ingredient) item;
            int amount = input.getAmount();
            
            if (possible.size() == 1) {

                Molecule molecule = possible.get(0);
                MoleculeIngredient check = molecule.getIngredient(i);
                
                // wrong input
                if (ingredient != check.getIngredient() || amount < check.getAmount()) {
                    return;
                }
                
                // on last ingredient
                if (molecule.size() == i + 1) {
                    break;
                }
                
            } else {
                
                List<Molecule> list = new ArrayList<>(possible.size() / 2);
                
                for (Molecule molecule : possible) {
                    MoleculeIngredient check = molecule.getIngredient(i);
                    if (ingredient == check.getIngredient() && amount >= check.getAmount()) {
                        list.add(molecule);
                    }
                }
                
                if (list.size() > 0) {
                    possible = list;
                } else {
                    return;
                }
            }
        }
        
        if (possible.size() == 1) {
            
            Molecule molecule = possible.get(0);

            ItemStack output = molecule.getItem();

            if (menu.fits(output, outputSlots)) {
                menu.pushItem(output, outputSlots);
                for (int i = 0 ; i < molecule.getIngredients().size() ; i++) {
                    menu.consumeItem(inputSlots[i], molecule.getIngredient(i).getAmount());
                }
            }
        }
    }
}
