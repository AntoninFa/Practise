package com.acme.auto.repository;

import java.util.List;

/**
 * Entity-Klasse für den REST-Client.
 *
 * @author <a href="mailto:A@h-ka.de">A A</a>
 * @param name Name
 * @param marken marken
 */
public record Autohaus(
    String name,
    List<String> marken
) {
}
