package io.github.mooy1.slimechem.implementation.atomic.isotopes;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.mooy1.slimechem.utils.StringUtil;
import io.github.mooy1.slimechem.utils.SuperNum;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.github.mooy1.slimechem.utils.StringUtil.NumberAndString;

public class IsotopeLoader {
    private final String isotopeJSON;
    Map<Isotope, NumberAndString> decayProductMap = new HashMap<>();

    private static final class IsotopeJSONObject {
        public int mass = 0;
        public String element = "";
    }

    private static final class DecayJSONObject {
        public int mass = 0;
        public String element = "";
    }

    public IsotopeLoader() {
        try {
            isotopeJSON = StringUtil.getResourceAsString("isotopes.json");
        } catch (IOException e) {
            throw new JsonIOException("Failed to load isotope file", e);
        }
    }

    public void load() {

        decayProductMap.clear();

        JsonArray jsonArray = new JsonParser().parse(isotopeJSON).getAsJsonArray();

        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
            int mass = jsonObject.get("mass").getAsInt();
            String abbr = jsonObject.get("element").getAsString();
            DecayType decayType;
            try {
                decayType = DecayType.getByRepresentation(
                    jsonObject.get("decay").getAsString()
                );
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(
                    "Failed to load decay type for isotope " + SuperNum.fromInt(mass) + abbr, e);
            }
            Isotope isotope = Isotope.addIsotope(mass, abbr, decayType);
            if (decayType != DecayType.STABLE) {
                decayProductMap.put(isotope, new NumberAndString(
                    jsonObject.get("pmass").getAsInt(),
                    jsonObject.get("pelement").getAsString()
                ));
            }
        }
    }

    public void loadDecayProducts() {
        for (Isotope isotope : decayProductMap.keySet()) {
            NumberAndString decayData = decayProductMap.get(isotope);

            try {
                isotope.loadDecayProduct(decayData.getNumber(), decayData.getString());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(String.format(
                    "Failed to load decay product for isotope %s",
                    isotope.toString()
                ), e);
            }
        }
    }
}
