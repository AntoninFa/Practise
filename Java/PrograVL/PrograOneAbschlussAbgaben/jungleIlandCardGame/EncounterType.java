package edu.kit.informatik;

/**
 * enum der die verschiedenen Tierarten des Spieles enthält
 *
 * @author Antonin Fahning
 * @version 1.0
 */
public enum EncounterType {
    /**
     * Spinnen Karte gegen die der Spieler "kömpfen" also würfeln muss
     * 4-seitiger Würfel
     * Augenzahl > 2
     * Die getThreshold Methode gibt den Schwellenwert an ab dem eine größere Augenzahl
     * das Überleben des Spielers bedeutet
     */
    SPIDER {
        @Override
        protected byte getThreshold() {
            return SPIDER_THRESHOLD;
        }
    },
    /**
     * Schlangen Karte gegen die der Spieler "kömpfen" also würfeln muss
     * 6-seitiger Würfel
     * Augenzahl > 3
     * Die getThreshold Methode gibt den Schwellenwert an ab dem eine größere Augenzahl
     * das Überleben des Spielers bedeutet
     */
    SNAKE {
        @Override
        protected byte getThreshold() {
            return SNAKE_THRESHOLD;
        }
    },
    /**
     * Tiger Karte gegen die der Spieler "kömpfen" also würfeln muss
     * 8-seitiger Würfel
     * Augenzahl > 4
     * Die getThreshold Methode gibt den Schwellenwert an ab dem eine größere Augenzahl
     * das Überleben des Spielers bedeutet
     */
    TIGER {
        @Override
        protected byte getThreshold() {
            return TIGER_THRESHOLD;
        }
    },
    /**
     * Zustand falls nicht in Encounter
     * Die getThreshold Methode gibt den Schwellenwert an ab dem eine größere Augenzahl
     * das Überleben des Spielers bedeutet
     */
    NONE {
        @Override
        protected byte getThreshold() {
            return -1;
        }
    };

    // Schwellenwerte der Tierarten ab denen eine größere Augenzahl das Überleben des Spielers bedeutet
    //Spinne, Augenzahl > 2
    private static final byte SPIDER_THRESHOLD = 2;
    //Schlange, Augenzahl > 3
    private static final byte SNAKE_THRESHOLD = 3;
    //Tiger, Augenzahl > 4
    private static final byte TIGER_THRESHOLD = 4;

    /**
     * Gibt den Schwellenwert an ab dem eine größere Augenzahl das Überleben des Spielers bedeutet
     * @return diesen Schwellenwert
     */
    protected abstract byte getThreshold();
}
