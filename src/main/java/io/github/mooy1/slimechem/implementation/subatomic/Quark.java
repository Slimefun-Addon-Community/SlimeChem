package io.github.mooy1.slimechem.implementation.subatomic;

import lombok.Getter;

/**
 * Enum of quarks
 *
 * @author Mooy1
 * 
 * @see Nucleon
 *
 */
@Getter
public enum Quark implements Fermion {

    UP(),
    DOWN(),
    CHARM(),
    STRANGE(),
    TOP(),
    BOTTOM();
    
}
