package edu.kit.informatik;

/**
 * enum der die Rettungstypen des Spieles enthält
 * (Existiert um mögliche Zukünftige Erweiterungen zu vereinfachen)
 *
 * @author Antonin Fahning
 * @version 1.0
 */
public enum EndeavorType {
    /**
     * Der Rettungstyp der bei den beiden nicht garantierten Rettungen verwendet wird
     * Die getThreshold Methode gibt den Schwellenwert an ab dem eine größere Augenzahl
     * den Spielsieg bedeutet
     */
    NORMAL_ENDEAVOR {
        @Override
        protected byte getThreshold() {
            return NORMAL_ENDEAVOR_THRESHOLD;
        }
    };

    // Schwellenwerte der Rettung ab denen eine größere Augenzahl den Spielsieg bedeutet
    // Normale Rettung, Augenzahl > 2
    private static final byte NORMAL_ENDEAVOR_THRESHOLD = 3;

    /**
     * Gibt den Schwellenwert an ab dem eine größere Augenzahl den Spielsieg bedeutet
     * @return diesen Schwellenwert
     */
    protected abstract byte getThreshold();
}
