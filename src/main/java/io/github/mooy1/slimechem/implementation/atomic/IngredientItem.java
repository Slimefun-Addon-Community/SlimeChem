package io.github.mooy1.slimechem.implementation.atomic;

import io.github.thebusybiscuit.slimefun4.api.player.PlayerProfile;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import io.github.thebusybiscuit.slimefun4.core.attributes.ProtectionType;
import io.github.thebusybiscuit.slimefun4.core.handlers.EntityInteractHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * A {@link SlimefunItem} to hold any class implementing {@link Ingredient}
 *
 * @author Seggan
 */

public class IngredientItem extends SlimefunItem implements NotPlaceable {

    @Getter
    private final Ingredient ingredient;
    private static final Set<Ingredient> radioactiveItems = new HashSet<>();
    private static final Map<Ingredient, Consumer<Player>> interactActions;
    private static final Map<Ingredient, BiConsumer<Entity, Player>> entityInteractActions;

    static {
        // Add all elements with atomic number > 82 (higher than lead)
        for (Element element : Element.values()) {
            if (element.getNumber() > 82) {
                radioactiveItems.add(element);
            }
        }
        // Technetium and promethium
        radioactiveItems.add(Element.TECHNETIUM);
        radioactiveItems.add(Element.PROMETHIUM);
        // Radioactive isotopes
        for (Isotope isotope : Isotope.values()) {
            if (isotope.isRadioactive()) {
                radioactiveItems.add(isotope);
            }
        }


        interactActions = getActions();
        entityInteractActions = getEntityActions();
    }

    public IngredientItem(Category category, Ingredient ingredient, RecipeType recipeType, ItemStack[] recipe) {
        super(category, ingredient.getItem(), recipeType, recipe);

        this.ingredient = ingredient;
        addItemHandler(onUse());
        addItemHandler(onEntityUse());
    }

    private EntityInteractHandler onEntityUse() {
        return (e, i, b) -> {
            e.setCancelled(true);
            Player p = e.getPlayer();
            BiConsumer<Entity, Player> run = entityInteractActions.get(ingredient);
            if (run != null) {
                PlayerInventory inv = p.getInventory();
                if (p.getGameMode() != GameMode.CREATIVE) {
                    inv.setItemInMainHand(consumeItem(inv.getItemInMainHand()));
                }
                run.accept(e.getRightClicked(), p);
            }
        };
    }

    private ItemUseHandler onUse() {
        return e -> {
            e.cancel();
            Player p = e.getPlayer();
            Consumer<Player> run = interactActions.get(ingredient);
            Bukkit.getLogger().info(Integer.toString(interactActions.size()));
            if (run != null) {
                PlayerInventory inv = p.getInventory();
                if (p.getGameMode() != GameMode.CREATIVE) {
                    inv.setItemInMainHand(consumeItem(inv.getItemInMainHand()));
                }
                run.accept(p);
            }
        };
    }

    private static Map<Ingredient, Consumer<Player>> getActions() {
        Map<Ingredient, Consumer<Player>> actions = new HashMap<>();

        for (Ingredient ingredient : radioactiveItems) {
            actions.put(ingredient, p -> {
                for (Entity e : p.getNearbyEntities(5, 5, 5)) {
                    if (e instanceof LivingEntity) {
                        if (e instanceof Player) {
                            Player player = (Player) e;
                            Optional<PlayerProfile> optionalPlayerProfile = PlayerProfile.find(player);
                            if (optionalPlayerProfile.isPresent()) {
                                if (optionalPlayerProfile.get().hasFullProtectionAgainst(ProtectionType.RADIATION)) {
                                    continue;
                                }
                            } else {
                                PlayerProfile.request(player);
                            }
                        }
                        e.setFireTicks(200);
                    }
                }
            });
        }

        return actions;
    }

    private static Map<Ingredient, BiConsumer<Entity, Player>> getEntityActions() {
        Map<Ingredient, BiConsumer<Entity, Player>> actions = new HashMap<>();

        for (Ingredient ingredient : radioactiveItems) {
            actions.put(ingredient, (e, p) -> {
                if (e instanceof LivingEntity) {
                    if (e instanceof Player) {
                        Player player = (Player) e;
                        Optional<PlayerProfile> optionalPlayerProfile = PlayerProfile.find(player);
                        if (optionalPlayerProfile.isPresent()) {
                            if (optionalPlayerProfile.get().hasFullProtectionAgainst(ProtectionType.RADIATION)) {
                                return;
                            }
                        } else {
                            PlayerProfile.request(player);
                        }
                    }
                    e.setFireTicks(200);
                }
            });
        }

        return actions;
    }

    private static ItemStack consumeItem(ItemStack stack) {
        ItemStack newStack = stack.clone();
        newStack.setAmount(stack.getAmount() - 1);
        return newStack;
    }

}
