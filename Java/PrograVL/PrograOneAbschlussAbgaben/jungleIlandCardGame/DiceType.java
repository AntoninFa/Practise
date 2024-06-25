package edu.kit.informatik;

/**
 * enum der die verschiedenen Würfeltypen enthält
 *
 * @author Antonin Fahning
 * @version 1.0
 */
public enum DiceType {

    /**
     * vier-seitiger Würfel der bei der Begegnung des Spielers mit einer Spinne benötigt wird
     */
    FOUR_SIDED_DICE,

    /**
     * sechs-seitiger Würfel der bei der Begegnung des Spielers mit einer Schlange und bei den
     * nicht garantierten Rettungen benötigt wird
     */
    SIX_SIDED_DICE,

    /**
     * acht-seitiger Würfel der bei der Begegnung des Spielers mit einem Tiger benötigt wird
     */
    EIGHT_SIDED_DICE,

    /**
     * default Typ falls eingabe ungültig war
     */
    DEFAULT;

    // Um mögliche zukünftige Änderungen/Erweiterungen zu erleichtern sind die 3 erlaubten Würfel-Arten hier
    // aufgelistet (bei Erweiterung muss natürlich auch um switch block erweitert werden)
    private static final int FOUR_SIDED_DICE_SIDES = 4;
    private static final int SIX_SIDED_DICE_SIDES = 6;
    private static final int EIGHT_SIDED_DICE_SIDES = 8;

    /**
     * Factory-Methode die den Nummern den entsprechenden enum zuordnet
     *
     * @param num Zahl die vom Spieler eingegeben wurde (Seitenanzahl eines Würfels)
     * @return den Würfeltyp oder DEFAULT falls Eingabe ungültig
     */
    static DiceType fromNumToEnum(final int num) {
        switch (num) {
            case FOUR_SIDED_DICE_SIDES:
                return FOUR_SIDED_DICE;
            case SIX_SIDED_DICE_SIDES:
                return SIX_SIDED_DICE;
            case EIGHT_SIDED_DICE_SIDES:
                return EIGHT_SIDED_DICE;
            default: return DEFAULT;
        }
    }
}