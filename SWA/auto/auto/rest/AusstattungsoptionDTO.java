package com.acme.auto.rest;

import com.acme.auto.entity.Ausstattungsoption;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * ValueObject für das Neuanlegen und Ändern einer Ausstattungsoption.
 *
 * @author <a href="mailto:A@h-ka.de">A A</a>
 * @param name Name
 * @param preis Preis
 * @param waehrung Währung
 */
record AusstattungsoptionDTO(
    String name,
    BigDecimal preis,
    Currency waehrung) {
    Ausstattungsoption toAusstattungsoption() {
        return Ausstattungsoption.builder().name(name).preis(preis).waehrung(waehrung).build();
    }
}
