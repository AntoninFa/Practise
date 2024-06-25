package edu.kit.informatik;

/**
 * Klasse welche, AbstractRailwayTrack erweitert
 * Modelliert ein Gleis für die Modelleisenbahnsimulation
 *
 * @author Antonin Fahning
 * @version 1.0
 */

public class Track extends AbstractRailwayTrack {

    // Länge des Gleises
    private long length;

    /**
     * Erstellt eine neues Gleis und führt SetLenght aus
     *
     * @param id     Eindeutiger Identifikator beginnend bei 1
     * @param start  Punkt im Raum (Startpunkt eines Gleises)
     *               *
     * @param endOne Punkt im Raum (Endpunkt eines Gleises)
     */
    public Track(int id, Point start, Point endOne) {
        super(id, start, endOne, null, null);
        setLength();
    }

    /**
     * Methode berechnet und setzt die Länge des Gleises indem sie den
     * Abstand zwischen dem Start und Endpunkt des Gleises errechnet
     */
    public void setLength() {
        long dlength;
        if (firstIsHorz()) {
            dlength = ((getPos().getStartingPoint().getX()) - (getPos().getEndOnePoint().getX()));
        } else {
            dlength = ((getPos().getStartingPoint().getY()) - (getPos().getEndOnePoint().getY()));
        }
        this.length = Math.abs(dlength);


    }

    /**
     * Getter für die Länge
     *
     * @return length
     */
    public long getLength() {
        return this.length;
    }

    /**
     * Gibt zurück ob das Gleis horizontal Ausgerichtet ist
     *
     * @return true wenn Gleis horizontal Ausgerichtet
     */
    public boolean firstIsHorz() {
        return getPos().getStartingPoint().getY() == getPos().getEndOnePoint().getY();
    }


    /**
     * Wird bei tracks nicht gebraucht weil sie sowieso immer nur aus einem Gleis bestehen
     *
     * @return false
     */
    public boolean sndIsHorz() {
        return false;
    }

    /**
     * Wird bei tracks nicht gebraucht weil sie sowieso immer nur aus einem Gleis bestehen
     *
     * @return false
     */
    public boolean swSettingIsHorz() {
        return false;
    }

    /**
     * Gibt an ob das Gleis Vertical oder Horizontal orientiert ist
     *
     * @return true wenn das Gleis Vertical oder Horizontal orientiert ist false wenn nicht
     */
    public boolean trackIsVerticalOrHorz() {
        return getPos().getStartingPoint().getX() == getPos().getEndOnePoint().getX()
                || getPos().getStartingPoint().getY() == getPos().getEndOnePoint().getY();
    }


}

