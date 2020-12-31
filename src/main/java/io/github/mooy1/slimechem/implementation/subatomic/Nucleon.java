package io.github.mooy1.slimechem.implementation.subatomic;

import io.github.mooy1.slimechem.implementation.atomic.Element;
import io.github.mooy1.slimechem.implementation.attributes.Itemable;
import io.github.mooy1.slimechem.lists.Constants;
import io.github.mooy1.slimechem.utils.StringUtil;
import lombok.Getter;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import org.bukkit.Material;

import javax.annotation.Nonnull;

/**
 * Enum of nucleons
 *
 * @author Mooy1
 * @see Element
 * @see Quark
 */
@Getter
public enum Nucleon implements Itemable {

    NEUTRON(Quark.UP, Quark.DOWN, Quark.DOWN),
    PROTON(Quark.UP, Quark.UP, Quark.DOWN);

    private final Quark[] quarks;
    private final SlimefunItemStack item;

    Nucleon(@Nonnull Quark... quarks) {
        this.quarks = quarks;

        if (!Constants.isTestingEnvironment) {
            String name = this.toString();
            item = new SlimefunItemStack(
                name,
                Material.WHITE_DYE,
                "&7" + StringUtil.enumNameToTitleCaseString(name),
                "&7Type: nucleon",
                "&7This particle does not interact via the strong force"
            );
        } else {
            item = null;
        }
    }

}
