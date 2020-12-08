package io.github.mooy1.slimechem.lists;

import io.github.mooy1.slimechem.SlimeChem;
import io.github.mooy1.slimechem.implementation.machines.ChemicalCombiner;
import io.github.mooy1.slimechem.implementation.machines.ChemicalDissolver;
import io.github.mooy1.slimechem.utils.LoreUtils;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineTier;
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineType;
import io.github.thebusybiscuit.slimefun4.utils.HeadTexture;
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder;
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
    public static final SlimefunItemStack RTG_1 = new SlimefunItemStack(
        "RTG_1",
        HeadTexture.NUCLEAR_REACTOR,
        "&4Radioisotope Thermoelectric Generator I",
        "",
        LoreBuilder.machine(MachineTier.ADVANCED, MachineType.GENERATOR),
        LoreBuilder.powerPerSecond(160),
        LoreBuilder.powerCharged(0, 32)
    );
    public static final SlimefunItemStack RTG_2 = new SlimefunItemStack(
        "RTG_2",
        HeadTexture.NUCLEAR_REACTOR,
        "&4Radioisotope Thermoelectric Generator II",
        "",
        "&7Also captures the radiation",
        LoreBuilder.machine(MachineTier.END_GAME, MachineType.GENERATOR),
        LoreBuilder.powerPerSecond(320),
        LoreBuilder.powerCharged(0, 64)
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
