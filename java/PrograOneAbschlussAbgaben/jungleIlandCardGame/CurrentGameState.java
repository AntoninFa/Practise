package edu.kit.informatik;

/**
 * Diese Klasse ist für die Speicherung und änderung des Spielzustands zuständig ist
 *
 * @author Antonin Fahning
 * @version 1.0
 */
class CurrentGameState {
    /**
     * enum der den Spielzustand enthält
     */
    private GamePhase gamePhase;
    private EncounterType encounterType;
    private boolean lastCardDrawn = false;

    /**
     * Konstruktor der Klasse, es wird zu beginn die anfangs-Spielphase übergeben
     *
     * @param gamePhase Spielphase
     */
    CurrentGameState(final GamePhase gamePhase) {
        this.gamePhase = gamePhase;
    }

    /**
     * Getter für die Spielphase
     *
     * @return Spielphase
     */
    GamePhase getGamePhase() {
        return gamePhase;
    }

    /**
     * Gibt zurück ob die gegebene GamePhase der momentanen gleich ist
     *
     * @param gamePhase gegebene GamePhase
     * @return true wenn gleich
     */
    boolean gamePhaseEql(final GamePhase gamePhase) {
        return this.gamePhase.equals(gamePhase);
    }

    /**
     * Setter für die Spielphase
     *
     * @param gamePhase Spielphase
     */
    void setGamePhase(final GamePhase gamePhase) {
        this.gamePhase = gamePhase;
    }

    /**
     * Getter für die verschiedenen Tierarten des Spieles
     *
     * @return Tierart
     */
    EncounterType getEncounterType() {
        return encounterType;
    }

    /**
     * Diese Methode erlaubt es GamePhase und EncounterType mit nur einer Methode zu setzten, existiert nur
     * um Code übersichtlicher zu machen
     *
     * @param gamePhase Spielphase
     * @param encounterType Tierart
     */
    void setGamePAndEncountTy(final GamePhase gamePhase, final EncounterType encounterType) {
        this.gamePhase = gamePhase;
        this.encounterType = encounterType;
    }

    /**
     * Gibt zurück ob die letzte Karte vom Stapel gezogen wurde
     *
     * @return true wenn letzte Karte gezogen
     */
    boolean isLastCardDrawn() {
        return lastCardDrawn;
    }

    /**
     * Setter für lastCardDrawn
     *
     * @param lastCardDrawn Bool-Wert auf den this.lastCardDrawn gesetzt werden soll
     */
    void setLastCardDrawn(final boolean lastCardDrawn) {
        this.lastCardDrawn = lastCardDrawn;
    }
}
