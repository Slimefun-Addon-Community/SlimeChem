package io.github.mooy1.slimechem.implementation;

/**
 * Enum of subatomic particles
 * 
 * @author Mooy1
 * 
 */
public enum Particle {

    NEUTRON(),
    PROTON();
    
    public enum Lepton {
        
        ELECTRON(),
        ELECTRON_NEUTRINO(),
        MUON(),
        MUON_NEUTRINO(),
        TAU(),
        TAU_NEUTRINO();
    }
    
    public enum Quark {
        
        UP(),
        DOWN(),
        CHARM(),
        STRANGE(),
        TOP(),
        BOTTOM();
    }
    
    public enum Boson {
        
        PHOTON(),
        GLUON(),
        Z_BOSON(),
        W_BOSON(),
        
        HIGGS_BOSON();
    }
}
