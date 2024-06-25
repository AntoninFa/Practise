package com.acme.auto.graphql;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * Ausstattungsoptionsdaten.
 *
 * @author <a href="mailto:A@h-ka.de">A A</a>
 *
 * @param name Name der Ausstattungsoption
 * @param preis Preis der Ausstattungsoption
 * @param waehrung Währung des Preises
 */
record AusstattungsoptionInput(String name, BigDecimal preis, Currency waehrung) {
}
