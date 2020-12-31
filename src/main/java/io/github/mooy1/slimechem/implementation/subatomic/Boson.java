package io.github.mooy1.slimechem.implementation.subatomic;

import io.github.mooy1.slimechem.implementation.attributes.Itemable;
import io.github.mooy1.slimechem.lists.Constants;
import io.github.mooy1.slimechem.utils.StringUtil;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;

/**
 * Enum of bosons
 *
 * @author Mooy1
 *
 */
@Getter
public enum Boson implements Itemable {

    PHOTON(),
    GLUON(),
    Z_BOSON(),
    W_BOSON(),

    HIGGS_BOSON();

    private final SlimefunItemStack item;

    Boson() {
        String name = this.toString();
        if (!Constants.isTestingEnvironment) {
            item = new SlimefunItemStack(
                name,
                Material.LIGHT_BLUE_DYE,
                "&7" + StringUtil.enumNameToTitleCaseString(name),
                "&7Type: boson",
                "&7A force-carrying particle"
            );
        } else {
            item = null;
        }
    }
}
