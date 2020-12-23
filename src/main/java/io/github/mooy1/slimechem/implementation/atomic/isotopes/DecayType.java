package io.github.mooy1.slimechem.implementation.atomic.isotopes;

import lombok.AllArgsConstructor;

import javax.annotation.Nonnull;

@AllArgsConstructor
public enum DecayType {
    ALPHA("alpha"),
    BETA_PLUS("beta+"),
    BETA_MINUS("beta-"),
    DOUBLE_BETA_MINUS("beta-beta-"),
    PROTON("p"),
    PROTON_2("2p"),
    PROTON_3("3p"),
    NEUTRON("n"),
    NEUTRON_2("2n"),
    NEUTRON_3("3n"),
    NEUTRON_4("4n"),
    ELECTRON_CAPTURE("capture"),
    DOUBLE_ELECTRON_CAPTURE("Double"),
    STABLE("stable");

    private final String representation;

    @Nonnull
    public static DecayType getByRepresentation(String representation) {
        for (DecayType decayType : DecayType.values()) {
            if (decayType.representation.equals(representation)) {
                return decayType;
            }
        }

        throw new IllegalArgumentException("Invalid representation, got: " + representation);
    }
}
