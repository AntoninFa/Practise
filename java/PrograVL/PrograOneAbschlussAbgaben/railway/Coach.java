package edu.kit.informatik;

/**
 * Klasse, welche einen Wagen modelliert
 *
 * @author Antonin Fahning
 * @version 1.0
 */
public class Coach extends RollingStock {

    /**
     * Wagentypen gespeichert im enum CoachType
     */
    final CoachType coachType;
    private final int length;

    /**
     * Erstellt einen neuen Wagen für die Modelleisenbahnsimulation
     *
     * @param coachType     Wagentyp
     * @param length        int Wagenlänge
     * @param couplingFront boolean, true wenn es vorne eine Kupplung gibt
     * @param couplingBack  boolean, true wenn es hinten eine Kupplung gibt
     * @param spec          Object das Spezifische Informationen über den Wagen speichert
     */
    public Coach(CoachType coachType, int length, boolean couplingFront, boolean couplingBack
            , Specialization spec) {
        super(couplingFront, couplingBack, null, spec);
        this.coachType = coachType;
        this.length = length;

    }

    /**
     * Getter für den Typ des Wagens
     *
     * @return CoachType
     */
    public CoachType getCoachType() {
        return this.coachType;
    }

    /**
     * Getter für die Länge des Wagens
     *
     * @return int (Länge des Wagens)
     */

    public int getLength() {
        return this.length;
    }

    @Override
    EngineType getEngineType() {
        return null;
    }

    @Override
    TrainSetType getTrainSetType() {
        return null;
    }

}
