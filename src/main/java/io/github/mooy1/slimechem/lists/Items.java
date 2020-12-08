package io.github.mooy1.slimechem.lists;

import io.github.mooy1.slimechem.SlimeChem;
import io.github.mooy1.slimechem.implementation.machines.ChemicalCombiner;
import io.github.mooy1.slimechem.implementation.machines.ChemicalDissolver;
import io.github.mooy1.slimechem.implementation.machines.RTG;
import io.github.mooy1.slimechem.utils.LoreUtils;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;

import java.util.Locale;

public final class Items {

    public static final SlimefunItemStack CHEMICAL_DISSOLVER = new SlimefunItemStack(
            "CHEMICAL_DISSOLVER",
            Material.DIAMOND_BLOCK,
            "&bChemical Dissolver",
            "&7Dissolves materials into their chemical parts and molecules into elements",
            "",
            LoreUtils.energy(ChemicalDissolver.ENERGY) + "per item"
    );
    public static final SlimefunItemStack CHEMICAL_COMBINER = new SlimefunItemStack(
            "CHEMICAL_COMBINER",
            Material.EMERALD_BLOCK,
            "&bChemical Combiner",
            "",
            LoreUtils.energy(ChemicalCombiner.ENERGY) + "per item"
    );
    
    public static final SlimefunItemStack URANIUM_RTG = new SlimefunItemStack(
            "URANIUM_RTG",
            Material.POLISHED_BLACKSTONE,
            "&aUranium RTG",
            "&7Constantly generates power from the decay of uranium-238",
            "",
            LoreUtils.energyPerSecond(RTG.URANIUM_ENERGY)
    );
    public static final SlimefunItemStack PLUTONIUM_RTG = new SlimefunItemStack(
            "PLUTONIUM_RTG",
            Material.POLISHED_BLACKSTONE,
            "&aUranium RTG",
            "&7Constantly generates power from the decay of plutonium-238",
            "",
            LoreUtils.energyPerSecond(RTG.PLUTONIUM_ENERGY)
    );
    public static final SlimefunItemStack AMERICIUM_RTG = new SlimefunItemStack(
            "AMERICIUM_RTG",
            Material.POLISHED_BLACKSTONE,
            "&aUranium RTG",
            "&7Constantly generates power from the decay of americium-241",
            "",
            LoreUtils.energyPerSecond(RTG.AMERICIUM_ENERGY)
    );
    public static final SlimefunItemStack CALIFORNIUM_RTG = new SlimefunItemStack(
            "CALIFORNIUM_RTG",
            Material.POLISHED_BLACKSTONE,
            "&aUranium RTG",
            "&7Constantly generates power from the decay of californium-250",
            "",
            LoreUtils.energyPerSecond(RTG.CALIFORNIUM_ENERGY)
    );

    public static final SlimefunItemStack ADDON_INFO = new SlimefunItemStack(
            SlimeChem.getInstance().getName().toUpperCase(Locale.ROOT) + "_ADDON_INFO",
            Material.NETHER_STAR,
            "&bAddon Info",
            "&fVersion: &7" + SlimeChem.getInstance().getPluginVersion(),
            "",
            "&fMooy's Discord: &b@&7Riley&8#5911",
            "&fSeggan's Discord: &b@&7Seggan&8#8111",
            "&7discord.gg/slimefun",
            "",
            "&fMooy's Github: &b@&8&7Mooy1",
            "&fSeggan's Github: &b@&8&7Seggan",
            "&7" + SlimeChem.getInstance().getBugTrackerURL()
    );
    
    public static final SlimefunItemStack NUCLEAR_FURNACE = new SlimefunItemStack(
            "NUCLEAR_FURNACE",
            Material.FURNACE,
            "&2Nuclear Furnace",
            "&7Smelts items using nuclear substances as fuel"
    );
    
}
