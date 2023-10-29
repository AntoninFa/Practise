package com.acme.auto.graphql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Eine Value-Klasse für Eingabedaten passend zu Suchkriterien aus dem GraphQL-Schema.
 *
 * @author <a href="mailto:A@h-ka.de">A A</a>
 *
 * @param hersteller Hersteller
 * @param modellbezeichnung Modellbezeichnung
 */
record Suchkriterien(
    String hersteller,
    String modellbezeichnung
) {
    /**
     * Konvertierung in eine Map.
     *
     * @return Das konvertierte Map-Objekt
     */
    Map<String, List<String>> toMap() {
        final Map<String, List<String>> map = new HashMap<>(2, 1);
        if (hersteller != null) {
            map.put("hersteller", List.of(hersteller));
        }
        if (modellbezeichnung != null) {
            map.put("modellbezeichnung", List.of(modellbezeichnung));
        }
        return map;
    }
}
