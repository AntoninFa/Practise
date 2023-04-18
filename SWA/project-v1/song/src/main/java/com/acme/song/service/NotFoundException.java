package com.acme.song.service;

import java.util.UUID;

/**
 * Exception die geworfen wird, falls kein Song gefunden wurde
 */
public class NotFoundException extends Exception {

    /**
     * UUID zu der kein Kunde gefunden wurde
     */
    private UUID id;
    private static final String NOTFOUNDERRORMESSAGE = "Kein Song gefunden mit ID: ";


    public NotFoundException(final UUID id){
        super(NOTFOUNDERRORMESSAGE + id);
        this.id = id;

    }

}
