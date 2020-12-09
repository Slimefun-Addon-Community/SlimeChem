package io.github.mooy1.slimechem.implementation.subatomic;

import io.github.mooy1.slimechem.implementation.atomic.DecayProduct;
import lombok.Getter;

import javax.annotation.Nonnull;
import io.github.mooy1.slimechem.implementation.atomic.Element;

/**
 * Enum of nucleons
 * 
 * @author Mooy1
 * 
 * @see Element
 * @see Quark
 * 
 */
@Getter
public enum Nucleon implements DecayProduct {

    NEUTRON(Quark.UP, Quark.DOWN, Quark.DOWN),
    PROTON(Quark.UP, Quark.UP, Quark.DOWN);
    
    private final Quark[] quarks;
    
    Nucleon(@Nonnull Quark... quarks) {
        this.quarks = quarks;
    }
    
}
