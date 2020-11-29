package io.github.mooy1.slimechem.lists;

import io.github.mooy1.slimechem.SlimeChem;
import io.github.mooy1.slimechem.implementation.machines.ChemicalDissolver;
import io.github.mooy1.slimechem.implementation.machines.RTG;
import io.github.mooy1.slimechem.utils.LoreUtils;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;

public final class Items {

    public static final SlimefunItemStack CHEMICAL_DISSOLVER = new SlimefunItemStack(
            "CHEMICAL_DISSOLVER",
            Material.DIAMOND_BLOCK,
            "&bChemical Dissolver",
            "",
            LoreUtils.energy(ChemicalDissolver.ENERGY) + "per item"
    );

    public static final SlimefunItemStack DECAY_GENERATOR = new SlimefunItemStack(
            "DECAY_GENERATOR",
            Material.NETHERITE_BLOCK,
            "&8Radioisotope Thermoelectric Generator",
            "Generates power from the decay of radioactive blocks",
            "",
            LoreUtils.energyBuffer(RTG.BUFFER)
    );

    public static final SlimefunItemStack SLIMECHEM_ADDON_INFO = new SlimefunItemStack(
            "SLIMECHEM_ADDON_INFO",
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
    
}
