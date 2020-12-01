package io.github.mooy1.slimechem.setup;

import io.github.mooy1.slimechem.SlimeChem;
import io.github.mooy1.slimechem.implementation.atomic.Element;
import io.github.thebusybiscuit.slimefun4.api.player.PlayerProfile;
import io.github.thebusybiscuit.slimefun4.core.categories.FlexCategory;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideImplementation;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideLayout;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunPlugin;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Periodic table flex category
 * 
 * @author Mooy1
 * 
 */
public class ElementCategory extends FlexCategory implements Listener {
    
    private final ItemStack BACKGROUND = new CustomItem(Material.BLACK_STAINED_GLASS_PANE, "");
    private final SlimefunGuideImplementation GUIDE = SlimefunPlugin.getRegistry().getGuideLayout(SlimefunGuideLayout.CHEST);
    private final ChestMenu[] menus = new ChestMenu[4];
    private final Map<Player, Integer> history;
    
    public ElementCategory(SlimeChem plugin) {
        super(new NamespacedKey(plugin, "periodic_table"), new CustomItem(Material.DIAMOND, "Periodic Table"), 3);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.history = new HashMap<>();
        menus[0] = makeTopMenu();
        menus[1] = makeBottomMenu();
        menus[2] = makeMetalMenu();
        menus[3] = makeRadioactiveMenu();
    }
    
    @Override
    public boolean isVisible(@Nonnull Player p, @Nonnull PlayerProfile profile, @Nonnull SlimefunGuideLayout layout) {
        return layout != SlimefunGuideLayout.CHEAT_SHEET;
    }
    
    @Override
    public void open(Player p, PlayerProfile profile, SlimefunGuideLayout layout) {
        profile.getGuideHistory().add(this, 0);
        menus[history.getOrDefault(p, 0)].open(p);
    }
    
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        history.remove(e.getPlayer());
    }
    
    @Nonnull
    private ChestMenu makeTopMenu() {
        ChestMenu menu = makeMenu("Periodic Table", 0);
        
        addElements(menu, 18, 1, 1);
        
        addElements(menu, 26, 2, 4);
        
        addElements(menu, 30, 5, 12);
        
        addElements(menu, 39, 13, 18);
        
        return menu;
    }
    
    @Nonnull
    private ChestMenu makeBottomMenu() {
        ChestMenu menu = makeMenu("Periodic Table", 1);
        
        addElements(menu, 9, 19, 20);
        
        addElementWithHandler(menu, 11, 30, 2, "Transition Metals");
        
        addElements(menu, 12, 31, 38);
        
        addElementWithHandler(menu, 20, 48, 2, "Transition Metals");
        
        addElements(menu, 21, 49, 56);
        
        addElementWithHandler(menu, 29, 80, 2, "Transition Metals");
        
        addElements(menu, 30, 81, 88);
        
        addElementWithHandler(menu, 38, 112, 2, "Transition Metals");
        
        addElements(menu, 39, 113, 118);

        addElements(menu, 46, 119, 119);
        
        return menu;
    }
    
    @Nonnull
    private ChestMenu makeMetalMenu() {
        ChestMenu menu = makeMenu("Transition Metals", 2);
        
        addElements(menu, 9, 21, 29);
        
        addElements(menu, 18, 39, 47);
        
        addElementWithHandler(menu, 27, 57, 3, "Lanthanoids & Actinoids");
        
        addElements(menu, 28, 72, 79);
        
        addElementWithHandler(menu, 36, 89, 3, "Lanthanoids & Actinoids");
        
        addElements(menu, 37, 104, 111);
        
        return menu;
    }
    
    @Nonnull
    private ChestMenu makeRadioactiveMenu() {
        ChestMenu menu = makeMenu("Lanthanoids & Actinoids", 3);
        
        addElements(menu, 9, 58, 71);
        
        addElements(menu, 27, 89, 103);
        
        return menu;
    }
    private void addElements(ChestMenu menu, int slot, int min, int max) {
        for (int i = min ; i <= max ; i++, slot++) {
            menu.addItem(slot, makeItem(Element.getByNumber(i)), ChestMenuUtils.getEmptyClickHandler());
        }
    }
    
    private void addElementWithHandler(ChestMenu menu, int slot, int element, int page, String name) {
        menu.addItem(slot, makeItem(Element.getByNumber(element), ChatColor.GREEN + "> Click to go to " + name), (player, i1, itemStack, clickAction) -> {
            menus[page].open(player);
            return false;
        });
    }
    
    @Nonnull
    private ItemStack makeItem(Element e) {
        return new CustomItem(
                Objects.requireNonNull(Material.getMaterial(e.getSeries().getColor() + "_STAINED_GLASS_PANE")),
                ChatColor.AQUA + "" + e.getSymbol() + " " + e.getNumber(),
                ChatColor.AQUA + e.getName(),
                ChatColor.AQUA + "Mass: " + e.getMass()
        );
    }
    
    @Nonnull
    private ItemStack makeItem(Element e, String lore) {
        return new CustomItem(
                Objects.requireNonNull(Material.getMaterial(e.getSeries().getColor() + "_STAINED_GLASS_PANE")),
                ChatColor.AQUA + "" + e.getSymbol() + " " + e.getNumber(),
                ChatColor.AQUA + e.getName(),
                ChatColor.AQUA + "Mass: " + e.getMass(),
                "",
                lore
        );
    }
    
    @Nonnull
    private ChestMenu makeMenu(@Nonnull String name, int page) {
        ChestMenu menu = new ChestMenu(name);
        
        menu.setEmptySlotsClickable(false);
        menu.setPlayerInventoryClickable(false);
        
        menu.addMenuCloseHandler(player -> history.put(player, page));
    
        for (int i = 0 ; i < 9 ; i++) {
            menu.addItem(i, BACKGROUND, ChestMenuUtils.getEmptyClickHandler());
        }
    
        for (int i = 45 ; i < 54 ; i++) {
            menu.addItem(i, BACKGROUND, ChestMenuUtils.getEmptyClickHandler());
        }
        
        menu.addMenuOpeningHandler(player -> {
            player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1, 1);
    
            menu.replaceExistingItem(0, ChestMenuUtils.getBackButton(player, "", ChatColor.GRAY + SlimefunPlugin.getLocalization().getMessage(player, "guide.back.guide")));
    
            menu.replaceExistingItem(45, ChestMenuUtils.getPreviousButton(player, page + 1, 4));
    
            menu.replaceExistingItem(53, ChestMenuUtils.getNextButton(player, page + 1, 4));
            
        });
        
        menu.addMenuClickHandler(0, (p, slot, item, action) -> {
            Optional<PlayerProfile> optional = PlayerProfile.find(p);
            optional.ifPresent(playerProfile -> playerProfile.getGuideHistory().goBack(GUIDE));
            return false;
        });
        
        menu.addMenuClickHandler(45, (p, i, itemStack, clickAction) -> {
            if (page > 0) {
                menus[page - 1].open(p);
            }
            return false;
        });
        
        menu.addMenuClickHandler(53, (p, i, itemStack, clickAction) -> {
            if (page < 3) {
                menus[page + 1].open(p);
            }
            return false;
        });
        
        return menu;
    }
}
