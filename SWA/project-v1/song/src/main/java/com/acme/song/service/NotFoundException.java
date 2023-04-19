package com.acme.song.service;

import lombok.Getter;

import java.util.UUID;

/**
 * Exception die geworfen wird, falls kein Song gefunden wurde
 */
@Getter
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
