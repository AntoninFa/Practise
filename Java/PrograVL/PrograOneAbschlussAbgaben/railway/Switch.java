package edu.kit.informatik;

/**
 * Klasse, welche AbstractRailwayTrack erweitert
 * Modelliert eine Weiche für die Modelleisenbahnsimulation
 *
 * @author Antonin Fahning
 * @version 1.0
 */

public class Switch extends AbstractRailwayTrack {
    // Länge des Gleises
    private long length;
    private Point switchSetting;

    /**
     * Erstellt eine neue Weiche
     *
     * @param id     Eindeutiger Identifikator beginnend bei 1
     * @param start  Punkt im Raum (Startpunkt einer Weiche)
     * @param endOne Punkt im Raum (1. Endpunkt der Weiche)
     * @param endTwo Punkt im Raum (2. Endpunkt der Weiche)
     */
    public Switch(int id, Point start, Point endOne, Point endTwo) {
        super(id, start, endOne, endTwo, null);
    }

    /**
     * Überprüft ob die Weiche nach Vorgabe Horizontal oder Vertical ist
     *
     * @return true wenn Vertical oder HorZ
     */
    public boolean switchIsVerticalOrHorz() {
        return (getPos().getStartingPoint().getX() == getPos().getEndOnePoint().getX()
                || getPos().getStartingPoint().getY() == getPos().getEndOnePoint().getY())
                && (getPos().getStartingPoint().getX() == getPos().getEndTwoPoint().getX()
                || getPos().getStartingPoint().getY() == getPos().getEndTwoPoint().getY());
    }

    /**
     * Wenn Weiche gesetzt ist kann hier die länge der Weiche gesetzt werden
     *
     * @return die länget der Weiche (double)
     */
    public long getLength() {
        if (switchSetting != null) {
            long dlength;
            if (firstIsHorz()) {
                dlength = ((getPos().getStartingPoint().getX()) - switchSetting.getX());
            } else {
                dlength = ((getPos().getStartingPoint().getY()) - switchSetting.getY());
                return dlength;
            }
            length = Math.abs(dlength);
            return dlength;
        } else {
            Terminal.printError("Gleis wurde noch nicht gesetzt");
        }
        return -1;
    }


    /**
     * Gibt zurück ob die 1. Weichenstrecke horizontal Ausgerichtet ist
     *
     * @return true wenn Gleis(Abschnitt) horizontal Ausgerichtet
     */
    public boolean firstIsHorz() {
        return getPos().getStartingPoint().getY() == getPos().getEndOnePoint().getY();
    }


    /**
     * Gibt zurück ob die 2. Weichenstrecke horizontal Ausgerichtet ist
     *
     * @return true wenn Gleis(Abschnitt) horizontal Ausgerichtet
     */
    public boolean sndIsHorz() {
        return getPos().getStartingPoint().getY() == getPos().getEndOnePoint().getY();
    }

    /**
     * Gibt zurück ob die gesetzte Weichenstrecke horizontal Ausgerichtet ist
     *
     * @return true wenn Gleis(Abschnitt) horizontal Ausgerichtet
     */
    public boolean swSettingIsHorz() {
        return getPos().getStartingPoint().getY() == switchSetting.getY();
    }
}
