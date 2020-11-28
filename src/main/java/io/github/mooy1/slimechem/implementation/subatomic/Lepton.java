package io.github.mooy1.slimechem.implementation.subatomic;

import lombok.Getter;

/**
 * Enum of leptons
 *
 * @author Mooy1
 *
 */
@Getter
public enum Lepton implements Fermion {

    ELECTRON(),
    ELECTRON_NEUTRINO(),
    MUON(),
    MUON_NEUTRINO(),
    TAU(),
    TAU_NEUTRINO();
    
}
