package edu.kit.informatik;

/**
 * enum der die Spielphasen des Spieles enthält
 *
 * @author Antonin Fahning
 * @version 1.0
 */
public enum GamePhase {

    /**
     * Beginn eines neuen Spiels erlaubt (nur) den start Befehl
     * Die toString Methode gibt die Stringrepräsentation der Spielphase zurück
     */
    START {
        @Override
        public String toString() {
            return START_REP;
        }
    },
    /**
     * In dieser Spielphase hat der Spieler die Möglichkeit Karten zu ziehen und unter den gegebenen
     * Bedingungen Gegenstände zu bauen
     * Die toString Methode gibt die Stringrepräsentation der Spielphase zurück
     */
    SCAVENGE {
        @Override
        public String toString() {
            return SCAVENGE_REP;
        }
    },
    /**
     * Wenn ein Tier gezogen wurde, muss der Spieler gegen "ihn"(das Tier) kämpfen
     * Die toString Methode gibt die Stringrepräsentation der Spielphase zurück
     */
    ENCOUNTER {
        @Override
        public String toString() {
            return ENCOUNTER_REP;
        }
    },
    /**
     * Spielzustand falls ein ein Gegenstand in der Kategorie Rettungen
     * gebaut worden ist
     * Die toString Methode gibt die Stringrepräsentation der Spielphase zurück
     */
    ENDEAVOR {
        @Override
        public String toString() {
            return ENDEAVOR_REP;
        }
    },
    /**
     * Das Ende des Spiels (End) ist durch eine garantierte Rettung gegeben oder wenn nach dem Zug
     * der letzten Karte vom Stapel keine weitere Aktion möglich ist
     * Die toString Methode gibt die Stringrepräsentation der Spielphase zurück
     */
    END {
        @Override
        public String toString() {
            return END_REP;
        }
    };
    // Stringrepräsentation der Spielphasen
    private static final String START_REP = "start";
    private static final String SCAVENGE_REP = "scavenge";
    private static final String ENCOUNTER_REP = "encounter";
    private static final String ENDEAVOR_REP = "endeavor";
    private static final String END_REP = "end";
}
