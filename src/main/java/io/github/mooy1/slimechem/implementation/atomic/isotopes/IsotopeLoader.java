package io.github.mooy1.slimechem.implementation.atomic.isotopes;

import io.github.mooy1.slimechem.implementation.atomic.Element;
import io.github.mooy1.slimechem.lists.Constants;
import io.github.mooy1.slimechem.utils.StringUtil;
import static io.github.mooy1.slimechem.utils.StringUtil.NumberAndString;

import io.github.mooy1.slimechem.utils.SuperNum;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class IsotopeLoader {
    private final JSONArray isotopeJSON;
    Map<Isotope, NumberAndString> decayProductMap = new HashMap<>();

    public IsotopeLoader() {
        try {
            isotopeJSON = new JSONArray(StringUtil.getResourceAsString("isotopes.json"));
        } catch (IOException e) {
            throw new JSONException("Failed to load isotope file", e);
        }
    }

    public void load() {

        decayProductMap.clear();

        for (int i = 0; i < isotopeJSON.length(); i++) {
            JSONObject jsonObject = isotopeJSON.getJSONObject(i);
            int mass = jsonObject.getInt("mass");
            String abbr = jsonObject.getString("element");
            DecayType decayType;
            try {
                decayType = DecayType.getByRepresentation(
                    jsonObject.getString("decay")
                );
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(
                    "Failed to load decay type for isotope " + SuperNum.fromInt(mass) + abbr, e);
            }
            Isotope isotope = Isotope.addIsotope(mass, abbr, decayType);
            if (decayType != DecayType.STABLE) {
                decayProductMap.put(isotope, new NumberAndString(
                    jsonObject.getInt("pmass"),
                    jsonObject.getString("pelement")
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
