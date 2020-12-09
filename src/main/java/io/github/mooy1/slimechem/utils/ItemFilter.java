package io.github.mooy1.slimechem.utils;

import lombok.Getter;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

/**
 * A utility class for testing if items fit a 'filter' for each other
 * 
 * @author Mooy1
 * 
 */
@Getter
public class ItemFilter {
    
    @Nullable
    private final Material material;
    @Nullable
    private final String id;
    private final int amount;
    @Nonnull
    private final ItemStack item;
    
    public ItemFilter(@Nonnull SlimefunItemStack stack) {
        this.material = null;
        this.id = stack.getItemId();
        this.amount = stack.getAmount();
        this.item = stack;
    }
    
    public ItemFilter(@Nonnull SlimefunItem item) {
        this.material = null;
        this.id = item.getId();
        this.amount = 1;
        this.item = item.getItem();
    }
    
    public ItemFilter(@Nonnull ItemStack stack) {
        String id = Util.getItemID(stack, false);
        if (id != null) {
            this.material = null;
            this.id = id;
        } else {
            this.material = stack.getType();
            this.id = null;
        }
        this.amount = stack.getAmount();
        this.item = stack;
    }
    
    public ItemFilter(@Nonnull Material material) {
        this.material = material;
        this.id = null;
        this.amount = 1;
        this.item = new ItemStack(material, 1);
    }
    
    public ItemFilter(@Nonnull String id) {
        this.material = null;
        this.id = id;
        this.amount = 1;
        this.item = Objects.requireNonNull(SlimefunItem.getByID(id)).getItem();
    }
    
    @Nonnull
    public static ItemFilter[] getArray(@Nonnull BlockMenu menu, @Nonnull int[] slots, int size) {
        ItemFilter[] array = new ItemFilter[size];

        for (int i = 0 ; i < size ; i++) {
            ItemStack item = menu.getItemInSlot(slots[i]);
            if (item != null) {
                array[i] = new ItemFilter(item);
            }
        }
        
        return array;
    }
    
    public static boolean arrayMatches(@Nonnull ItemFilter[] recipe, @Nonnull ItemFilter[] input, @Nonnull Type type) {
        if (recipe.length != input.length) return false;
        for (int i = 0 ; i < recipe.length ; i ++) {
            if (!matches(recipe[i], input[i], type)) return false;
        }
        return true;
    }
    
    public static boolean matches(@Nullable ItemFilter recipe, @Nullable ItemFilter input, @Nonnull Type type) {
        if ((recipe == null) != (input == null) ) return false;
        if (recipe == null) return true;
        return recipe.matches(input, type);
    }
    
    public boolean matches(@Nullable ItemFilter input, @Nonnull Type type) {
        if (input == null) {
            return false;
        }
        if ((getMaterial() == null) != (input.getMaterial() == null)) {
            return false;
        }
        if (getMaterial() != null) {
            return getMaterial() == input.getMaterial() && typeBoolean(type, getAmount(), input.getAmount());
        } else {
            return Objects.equals(getId(), input.getId()) && typeBoolean(type, getAmount(), input.getAmount());
        }
    }
    
    private static boolean typeBoolean(@Nonnull Type type, int recipe, int input) {
        return type == Type.IGNORE_AMOUNT || (type == Type.MIN_AMOUNT && recipe <= input) || (type == Type.EQUAL_AMOUNT && recipe == input);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ItemFilter)) {
            return false;
        }
        return matches((ItemFilter) obj, Type.EQUAL_AMOUNT);
    }

    @Override
    public int hashCode() {
        return 31 * (this.amount + Objects.hashCode(this.material) + Objects.hashCode(this.id));
    }

    @Override
    public String toString() {
        return "ItemFilter {material=" + this.material + ", id=" + this.id + ", amount=" + this.amount + "}";
    }

    public enum Type {

        /**
         * ignores amount in each filter
         */
        IGNORE_AMOUNT(),

        /**
         * the input filter must have atlas the amount of the filter
         */
        MIN_AMOUNT(),

        /**
         * the filters must have equal amounts
         */
        EQUAL_AMOUNT()
        
    }

}
