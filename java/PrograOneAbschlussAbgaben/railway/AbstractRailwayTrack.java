package edu.kit.informatik;

/**
 * Abstrakte Klasse die eine Schiene modelliert und an zwei Subklassen
 * NormalTrack und Switch vererbt
 * @author Antonin Fahning
 * @version 1.0
 */
public abstract class AbstractRailwayTrack {
    // Eindeutiger Bezeichner (Zählt von 1 aus hoch)
    private final int id;
    // Position im Raum gibt speichert Start- und Endpunkt
    private final PosInSpace pos;
    private Point switchSetting;
    private final long length = -1;



    /**
     *  Contruktor der Klasse AbstractRailwayTrack
     * @param id Eindeutige ID des Tracks
     * @param start start Punkt des Tracks
     * @param endOne end Punkt des Tracks
     * @param endTwo zweiter end Punkt des Tracks
     * @param switchSetting Punkt der Weichenstellung
     */
    protected AbstractRailwayTrack(int id, Point start, Point endOne, Point endTwo, Point switchSetting) {
        this.id = id;
        this.pos = new PosInSpace(start, endOne, endTwo);
        this.switchSetting = switchSetting;


    }

    /**
     * Getter für die Position des Gleises im Raum aus
     * @return PosInSpace
     */
    public PosInSpace getPos() { return pos; }

    /**
     * Getter für die Weichenstellung
     * @return Point (Stellung der Weiche)
     */
    public Point getSwitchSetting() { return this.switchSetting; }

    /**
     * Setter für die Weichenstellung
     * @param point auf den die Weiche gestellt wird
     */
    public void setSwitchSetting(Point point) {
        this.switchSetting = point;
    }

    /**
     * Getter für die Länge eines Gleises
     * @return LENGTH (Die Länge eines Gleises
     */
    public long getLength() { return length; }

    /**
     * Getter für die Eindeutige ID
     * @return id (int) Eindeutige ID
     */
    public int getId() {
        return id;
    }

    /**
     * Gibt zurück ob das Gleis horizontal Ausgerichtet ist, wird in den Unterklassen implementiert
     * @return ob das Gleis horizontal Ausgerichtet ist
     */
    abstract boolean firstIsHorz();

    /**
     * Gibt zurück ob die 2. Weichenstrecke horizontal Ausgerichtet ist, wird in den Unterklassen implementiert
     * @return true wenn Gleis(Abschnitt) horizontal Ausgerichtet
     */
    abstract boolean sndIsHorz();

    /**
     * Gibt zurück ob die gesetzte Weichenstrecke horizontal Ausgerichtet ist, wird in den Unterklassen implementiert
     * @return true wenn Gleis(Abschnitt) horizontal Ausgerichtet
     */
    abstract boolean swSettingIsHorz();

}

