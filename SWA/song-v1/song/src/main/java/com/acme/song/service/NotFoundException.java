package com.acme.song.service;

import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Exception die geworfen wird, falls kein Song gefunden wurde.
 */
@Getter
public class NotFoundException extends RuntimeException {

    private static final String NOTFOUNDERRORMESSAGE = "Kein Song gefunden mit ID: ";
    /**
     * UUID zu der kein Song gefunden wurde.
     */
    private final UUID id;
    /**
     * Suchkriterien zu welchen keine Songs gefunden wurden.
     */
    private final Map<String, List<String>> suchkriterien;

    NotFoundException(final UUID id) {
        super(NOTFOUNDERRORMESSAGE + id);
        this.id = id;
        this.suchkriterien = null;
    }

    NotFoundException(final Map<String, List<String>> suchkriterien) {
        super("Keine Kunden gefunden.");
        id = null;
        this.suchkriterien = suchkriterien;
    }
}
