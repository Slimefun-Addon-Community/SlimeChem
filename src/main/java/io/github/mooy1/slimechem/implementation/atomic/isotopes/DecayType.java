package io.github.mooy1.slimechem.implementation.atomic.isotopes;

import lombok.AllArgsConstructor;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public enum DecayType {

    ALPHA("alpha"),
    BETA_PLUS("beta+"),
    BETA_MINUS("beta-"),
    DOUBLE_BETA_MINUS("double beta-"),
    PROTON("p"),
    PROTON_2("2p"),
    PROTON_3("3p"),
    NEUTRON("n"),
    NEUTRON_2("2n"),
    NEUTRON_3("3n"),
    NEUTRON_4("4n"),
    ELECTRON_CAPTURE("electron capture"),
    DOUBLE_ELECTRON_CAPTURE("double electron capture"),
    STABLE("stable");

    private static final Map<String, DecayType> reprmap = new HashMap<>();
    private final String representation;

    @Nonnull
    public static DecayType getByRepresentation(String representation) {
        if (reprmap.isEmpty()) {
            for (DecayType decayType : values()) {
                reprmap.put(decayType.representation, decayType);
            }
        }

        DecayType decayType = reprmap.get(representation);
        if (decayType == null) {
            throw new IllegalArgumentException("Invalid representation, got: " + representation);
        }

        return decayType;
    }
}
