package com.acme.auto.service;

import lombok.Getter;

/**
 * Exception, falls die FIN bereits existiert.
 *
 * @author <a href="mailto:A@h-ka.de">A A</a>
 */
@Getter
public class FinExistsException extends RuntimeException {
    /**
     * Bereits vorhandene FIN.
     */
    private final String fin;

    FinExistsException(@SuppressWarnings("ParameterHidesMemberVariable") final String fin) {
        super("Die FIN " + fin + " existiert bereits");
        this.fin = fin;
    }
}
