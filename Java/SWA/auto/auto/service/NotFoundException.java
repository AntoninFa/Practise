package com.acme.auto.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.Getter;

/**
 * RuntimeException, falls kein Auto gefunden wurde.
 */
@Getter
public final class NotFoundException extends RuntimeException {
    private final UUID id;
    private final Map<String, List<String>> suchkriterien;

    NotFoundException(final UUID id) {
        super("Kein Auto mit der ID " + id + " gefunden.");
        this.id = id;
        suchkriterien = null;
    }

    NotFoundException(final Map<String, List<String>> suchkriterien) {
        super("Keine Autos gefunden.");
        id = null;
        this.suchkriterien = suchkriterien;
    }
    NotFoundException() {
        super("Keine Autos gefunden.");
        id = null;
        suchkriterien = null;
    }
}
