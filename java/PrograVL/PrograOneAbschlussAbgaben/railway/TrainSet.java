package edu.kit.informatik;

/**
 * Klasse, welche PowerTrain erweitert
 * Modelliert einen Triebzug für die Modelleisenbahnsimulation
 *
 * @author Antonin Fahning
 * @version 1.0
 */

public class TrainSet extends PowerTrain {

    private final TrainSetType trainSetType = TrainSetType.TRAINSET;
    private final int trainID = -1;

    /**
     * Erstellt einen neuen Triebzug
     *
     * @param series        Baureihe
     * @param name          Name
     * @param length        Länge des Triebzuges
     * @param couplingFront boolean, true wenn es vorne eine Kupplung gibt
     * @param couplingBack  boolean, true wenn es hinten eine Kupplung gibt
     * @param spec Object das specifikationen wie die ID und Typen speichert
     */


    public TrainSet(String series, String name, int length
            , boolean couplingFront, boolean couplingBack, Specialization spec) {
        super(series, name, length, couplingFront, couplingBack, spec);
    }



    /**
     * Getter für die ID des Zuges zu dem der TriebZug gehört
     *
     * @return int trainID
     */
    public int getTrainID() {
        return this.trainID;
    }

    @Override
    TrainSetType getTrainSetType() {
        return this.trainSetType;
    }

    @Override
    EngineType getEngineType() {
        return null;
    }

    @Override
    public CoachType getCoachType() { return null; }
}
