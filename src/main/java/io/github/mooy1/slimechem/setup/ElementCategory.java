package io.github.mooy1.slimechem.setup;

import io.github.mooy1.slimechem.SlimeChem;
import io.github.mooy1.slimechem.implementation.Element;
import io.github.thebusybiscuit.slimefun4.api.player.PlayerProfile;
import io.github.thebusybiscuit.slimefun4.core.categories.FlexCategory;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideLayout;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ElementCategory extends FlexCategory implements Listener {
    
    private static final ItemStack BACKGROUND = new CustomItem(Material.BLACK_STAINED_GLASS_PANE, "");
    
    private final ChestMenu[] menus = new ChestMenu[4];
    private final Map<Player, Integer> history;
    
    public ElementCategory(SlimeChem plugin) {
        super(new NamespacedKey(plugin, "periodic_table"), new CustomItem(Material.DIAMOND, "table"), 3);
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
        
        return menu;
    }
    
    @Nonnull
    private ChestMenu makeMetalMenu() {
        ChestMenu menu = makeMenu("Transition Metals", 2);
        
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
            Element e = Element.getByNumber(i);
            menu.addItem(slot, makeItem(e), ChestMenuUtils.getEmptyClickHandler());
        }
    }
    
    @Nonnull
    private ItemStack makeItem(Element e) {
        return new CustomItem(
                Objects.requireNonNull(Material.getMaterial(e.getSeries().getColor() + "_STAINED_GLASS_PANE")),
                ChatColor.AQUA + "" + e.getAtomicNumber() + " " + e.getSymbol(),
                ChatColor.AQUA + e.getName(),
                ChatColor.AQUA + "Atomic Mass: " + e.getAtomicMass()
        );
    }
    
    @Nonnull
    private ChestMenu makeMenu(@Nonnull String name, int page) {
        ChestMenu menu = new ChestMenu(name);
        
        menu.setEmptySlotsClickable(false);
        menu.setPlayerInventoryClickable(false);
        
        menu.addMenuOpeningHandler(player -> {
            history.put(player, page);
            menu.addItem(0, ChestMenuUtils.getPreviousButton(player, page + 1, 4));
            menu.addItem(8, ChestMenuUtils.getNextButton(player, page + 1, 4));
        });
        
        menu.addMenuClickHandler(0, (player, i, itemStack, clickAction) -> {
            if (page > 0) {
                menus[page - 1].open();
            }
            return false;
        });
        
        menu.addMenuClickHandler(8, (player, i, itemStack, clickAction) -> {
            if (page < 3) {
                menus[page + 1].open(player);
            }
            return false;
        });
    
        for (int i = 1 ; i < 8 ; i++) {
            menu.addItem(i, BACKGROUND, ChestMenuUtils.getEmptyClickHandler());
        }
        
        return menu;
    }
}
