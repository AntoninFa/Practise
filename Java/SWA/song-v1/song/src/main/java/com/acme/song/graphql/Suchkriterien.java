package com.acme.song.graphql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Eine Value-Klasse für Eingabedaten.
 *
 * @param titel Titel des Songs
 * @param musikLabel Label eines Songs
 */
record Suchkriterien(
    String titel,
    String musikLabel
) {

    /**
     * Konvertierung in eine Map.
     *
     * @return Das zu einer Map konvertierte Objekt
     */
    Map<String, List<String>> toMap() {
        final Map<String, List<String>> map = new HashMap<>(2, 1);
        if (titel != null) {
            map.put("titel", List.of(titel));
        }
        if (musikLabel != null) {
            map.put("musikLabel", List.of(musikLabel));
        }
        return map;
    }
}
