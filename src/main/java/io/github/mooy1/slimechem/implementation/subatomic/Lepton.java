package io.github.mooy1.slimechem.implementation.subatomic;

import io.github.mooy1.slimechem.implementation.atomic.DecayProduct;
import lombok.Getter;

/**
 * Enum of leptons
 *
 * @author Mooy1
 *
 */
@Getter
public enum Lepton implements Fermion, DecayProduct {

    ELECTRON(),
    ELECTRON_NEUTRINO(),
    MUON(),
    MUON_NEUTRINO(),
    TAU(),
    TAU_NEUTRINO();
    
}
