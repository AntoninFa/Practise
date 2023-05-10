package com.acme.song.graphql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Eine Value-Klasse für Eingabedaten.
 *
 * @param titel Titel des Songs
 * @param genres Genre eines Songs
 */
record Suchkriterien(
    String titel,
    List<String> genres
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
        if (genres != null) {
            map.put("genre", genres);
        }
        return map;
    }
}
