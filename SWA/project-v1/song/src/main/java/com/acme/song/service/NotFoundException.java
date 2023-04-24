package com.acme.song.service;

import lombok.Getter;

import java.util.UUID;

/**
 * Exception die geworfen wird, falls kein Song gefunden wurde.
 */
@Getter
public class NotFoundException extends Exception {

    private static final String NOTFOUNDERRORMESSAGE = "Kein Song gefunden mit ID: ";
    /**
     * UUID zu der kein Song gefunden wurde.
     */
    private final UUID id;

    NotFoundException(final UUID id) {
        super(NOTFOUNDERRORMESSAGE + id);
        this.id = id;
    }

}
