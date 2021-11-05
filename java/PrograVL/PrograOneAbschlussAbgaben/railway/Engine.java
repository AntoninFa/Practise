package edu.kit.informatik;

/**
 * Klasse, welche PowerTrain erweitert
 * Modelliert eine Lokomotive für die Modelleisenbahnsimulation
 *
 * @author Antonin Fahning
 * @version 1.0
 */

public class Engine extends PowerTrain {

    private final EngineType type;

    /**
     * Erstellt eine neue Lokomotive
     *
     * @param type          enum der den Typ der Lokomotive angibt
     * @param series        Baureihe
     * @param name          Name
     * @param length        Länge der Lokomotive
     * @param couplingFront boolean, true wenn es vorne eine Kupplung gibt
     * @param couplingBack  boolean, true wenn es hinten eine Kupplung gibt
     * @param spec          Object das specifikationen wie die ID und Typen speichert
     */
    public Engine(EngineType type, String series, String name, int length
            , boolean couplingFront, boolean couplingBack, Specialization spec) {
        super(series, name, length, couplingFront, couplingBack, spec);
        this.type = type;
    }

    @Override
    EngineType getEngineType() {
        return this.type;
    }

    @Override
    TrainSetType getTrainSetType() {
        return null;
    }

    @Override
    public CoachType getCoachType() {
        return null;
    }
}
