package io.github.mooy1.slimechem.implementation.subatomic;

import io.github.mooy1.slimechem.implementation.atomic.DecayProduct;
import lombok.Getter;

/**
 * Enum of bosons
 *
 * @author Mooy1
 *
 */
@Getter
public enum Boson implements DecayProduct {

    PHOTON(),
    GLUON(),
    Z_BOSON(),
    W_BOSON(),

    HIGGS_BOSON();
    
}
