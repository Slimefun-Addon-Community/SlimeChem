package io.github.mooy1.slimechem.implementation.subatomic;

import io.github.mooy1.slimechem.implementation.attributes.Itemable;
import io.github.mooy1.slimechem.utils.StringUtil;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;

/**
 * Enum of leptons
 *
 * @author Mooy1
 */
@Getter
public enum Lepton implements Fermion, Itemable {

    ELECTRON(),
    ELECTRON_NEUTRINO(),
    MUON(),
    MUON_NEUTRINO(),
    TAU(),
    TAU_NEUTRINO();

    private final SlimefunItemStack item;

    Lepton() {
        String name = this.toString();
        item = new SlimefunItemStack(
            name,
            Material.YELLOW_DYE,
            "&7" + StringUtil.enumNameToTitleCaseString(name),
            "&7Type: lepton",
            "&7This particle does not interact via the strong force"
        );
    }
}
