package io.github.mooy1.slimechem.implementation.atomic.isotopes;

import io.github.mooy1.slimechem.utils.StringUtil;
import static io.github.mooy1.slimechem.implementation.atomic.isotopes.Isotope.DecayType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class IsotopeLoader {
    private final JSONArray isotopeJSON;

    public IsotopeLoader() {
        try {
            isotopeJSON = new JSONArray(StringUtil.getResourceAsString("isotopes.json"));
        } catch (IOException e) {
            throw new JSONException("Failed to load isotope file", e);
        }
    }

    public void load() {
        final Map<Isotope, StringUtil.NumberAndString> decayProductMap = new HashMap<>();

        for (int i = 0; i < isotopeJSON.length(); i++) {
            JSONObject jsonObject = isotopeJSON.getJSONObject(i);
            int mass = jsonObject.getInt("mass");
            String abbr = jsonObject.getString("element");
            DecayType decayType = DecayType.getByRepresentation(
                jsonObject.getString("decay")
            );
            Isotope isotope = Isotope.addIsotope(mass, abbr, decayType);
            if (decayType != DecayType.STABLE) {
                decayProductMap.put(isotope, new StringUtil.NumberAndString(
                    jsonObject.getInt("pmass"),
                    jsonObject.getString("pelement")
                ));
            }
        }
    }
}
