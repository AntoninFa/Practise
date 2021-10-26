package edu.kit.informatik;

/**
 * Abstrakte Klasse die einen Antriebszug modelliert und an zwei Subklassen
 * NormalTrack und Switch vererbt
 *
 * @author Antonin Fahning
 * @version 1.0
 */

public abstract class PowerTrain extends RollingStock {

    private final String name;
    private final int length;
    private int trainID = -1;


    /**
     *  Erstellt einen neuen Antriebszug
     * @param series        Baureihe
     * @param name          Name
     * @param length        Länge der Lokomotive
     * @param couplingFront boolean, true wenn es vorne eine Kupplung gibt
     * @param couplingBack  boolean, true wenn es hinten eine Kupplung gibt
     * @param spec          Object das specifikationen wie die ID und Typen speichert
     */
    protected PowerTrain(String series,
                         String name, int length, boolean couplingFront, boolean couplingBack,
                         Specialization spec) {
        super(couplingFront, couplingBack, series, spec);


        this.name = name;
        this.length = length;
    }


    /**
     * Getter für den Namen des Antriebszuges
     * @return Name des Antriebszuges
     */
    public String getName() {
        return name;
    }

    /**
     * Getter für die Länge des Antriebszuges
     * @return Länge des Antriebszuges
     */
    public int getLength() {
        return length;
    }

    /**
     * Setter für die ID des Zuges zu dem der Powertrain gehört
     * @param trainID ID des Zuges zu dem der Powertrain gehört
     */
    public void setTrainID(int trainID) { this.trainID = trainID; }

    /**
     * Getter für die ID des Zuges zu dem der Powertrain gehört
     * @return int trainID
     */
    public int getTrainID() { return this.trainID; }


    /**
     * Get den Lokomotiven-Typ zurück
     * @return Lokomotiven-Typ
     */
    abstract EngineType getEngineType();

    /**
     * Gibt den Train-Set-Typ zurück
     * @return Train-Set-Typ
     */
    abstract TrainSetType getTrainSetType();

    /**
     * Gibt den Wagen-Typ zurück
     * @return  Wagen-Typ
     */
    abstract CoachType getCoachType();

}
