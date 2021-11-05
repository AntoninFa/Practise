package edu.kit.informatik;

/**
 * Diese Klasse ruft die für die Logik zuständigen Klassen auf.
 * Die Logik ist in die drei spezialisierten Klassen TrackMaterialSimulation, RollingMaterialSimulation
 * und DriveSimulation orientiert an dem Aufbau des Aufgabenblattes.
 *
 * @author Antonin Fahning
 * @version 1.0
 */

public class RailwaySimulation {


    /**
     * Klasse die für die Logik des Rollmaterials zuständig ist
     */
    private RollingMaterialSimulation rollingMSim = new RollingMaterialSimulation();

    /**
     * Klasse die für die Logik der Gleise zuständig ist
     */
    private TrackMaterialSimulation trackMSim = new TrackMaterialSimulation(rollingMSim);

    /**
     * Klasse die für die Simulation des Fahrbetriebes zuständig ist
     */
    private final DriveSimulation driveSim = new DriveSimulation(trackMSim, rollingMSim);


    // Track Material Methoden

    /**
     * Ruft die AddTrack-Methode in der für die Logik der Gleise zuständigen Klasse auf
     * @param startPoint Anfangspunkt des Gleises
     * @param endPoint Endpunkt des Gleises
     */
    public void callAddTrack(Point startPoint, Point endPoint) {
        trackMSim.addTrack(startPoint, endPoint);
    }

    /**
     * Ruft die AddSwitch-Methode in der für die Logik der Gleise zuständigen Klasse auf
     *
     * @param startPoint Startpunkt
     * @param endPoint der erste Endpunkt
     * @param endTwoPoint der zweite Endpunkt der Weiche
     */
    public void callAddSwitch(Point startPoint, Point endPoint, Point endTwoPoint) {
        trackMSim.addSwitch(startPoint, endPoint, endTwoPoint);
    }

    /**
     * Ruft die DeleteTrack-Methode in der für die Logik der Gleise zuständigen Klasse auf
     * @param trackID Id des zu löschenden Gleises
     */
    public void callDeleteTrack(int trackID) {
        trackMSim.deleteTrack(trackID);
    }

    /**
     * Ruft die ListTracks-Methode in der für die Logik der Gleise zuständigen Klasse auf
     */
    public void callListTracks() {
        trackMSim.listTracks();
    }

    /**
     * Ruft die SetSwitch-Methode in der für die Logik der Gleise zuständigen Klasse auf
     *
     * @param tID Id der Weiche
     * @param point Punkt der Weichenstellung
     */
    public void callSetSwitch(int tID, Point point) {
        trackMSim.setSwitch(tID, point);
    }

    // Rolling Stock Methoden


    /**
     * Ruft die CreateCoach-Methode in der für die Logik des Rollmaterials zuständigen Klasse auf
     *
     * @param coachType Typ des Wagens
     * @param length Länge als 32-Bit-Ganzzahl
     * @param cFront true Kupplung wenn es vorne eine Kupplung hat
     * @param cBack true Kupplung wenn es hinten eine Kupplung hat
     */
    public void callCreateCoach(String coachType, int length, boolean cFront, boolean cBack) {
        rollingMSim.createCoach(coachType, length, cFront, cBack);
    }

    /**
     * Ruft die ListCoaches-Methode in der für die Logik des Rollmaterials zuständigen Klasse auf
     *
     */
    public void callListCoaches() {
        rollingMSim.listCoaches();
    }


    /**
     * Ruft die CreatePowTrain-Methode in der für die Logik des Rollmaterials zuständigen Klasse auf
     *
     * @param engineType beschreibt den Typ
     * @param powTClass Baureihe
     * @param powTName Name des Antriebszugs
     * @param length  Länge des Antriebszugs
     * @param cFront true Kupplung wenn es vorne eine Kupplung hat
     * @param cBack true Kupplung wenn es hinten eine Kupplung hat
     */
    public void callCreatePowTrain(String engineType, String powTClass, String powTName
            , int length, boolean cFront, boolean cBack) {
        rollingMSim.createPowTrain(engineType, powTClass, powTName, length, cFront, cBack);
    }

    /**
     * Ruft die ListEngines-Methode in der für die Logik des Rollmaterials zuständigen Klasse auf
     */
    public void callListEngines() {
        rollingMSim.listEngines();
    }

    /**
     * Ruft die ListTrainSets-Methode in der für die Logik des Rollmaterials zuständigen Klasse auf
     */
    public void callListTrainSets() {
        rollingMSim.listTrainSets();
    }

    /**
     * Ruft die DeleteCoach-Methode in der für die Logik des Rollmaterials zuständigen Klasse auf
     *
     * @param id Wagen-ID
     */
    public void callDeleteCoach(int id) {
        rollingMSim.deleteCoach(id);
    }

    /**
     * Ruft die DeletePowT-Methode in der für die Logik des Rollmaterials zuständigen Klasse auf
     *
     * @param id Id des Antriebszugs
     */
    public void callDeletePowT(String id) {
        rollingMSim.deletePowT(id);
    }


    /**
     * Ruft die AddTrainCoach-Methode in der für die Logik des Rollmaterials zuständigen Klasse auf
     *
     * @param trainId ID des Zuges zu dem der Wagen hinzugefügt werden soll
     * @param rSId ID des Wagens
     */
    public void callAddTrainCoach(int trainId, int rSId) {
        rollingMSim.addTrainCoach(trainId, rSId);
    }

    /**
     * Ruft die AddTrainPow-Methode in der für die Logik des Rollmaterials zuständigen Klasse auf
     *
     * @param trainId ID des Zuges zu dem der Wagen hinzugefügt werden soll
     * @param rSId ID des Wagens
     */
    public void callAddTrainPow(int trainId, String rSId) {
        rollingMSim.addTrainPow(trainId, rSId);
    }

    /**
     * Ruft die callDeleteTrain-Methode in der für die Logik des Rollmaterials zuständigen Klasse auf
     *
     * @param id Id des zu entfernenden Zuges
     */
    public void callDeleteTrain(int id) {
        rollingMSim.deleteTrain(id);
    }

    /**
     * Ruft die ListTrains-Methode in der für die Logik des Rollmaterials zuständigen Klasse auf
     */
    public void callListTrains() {
        rollingMSim.listTrains();
    }

    /**
     * Ruft die ShowTrain-Methode in der für die Logik des Rollmaterials zuständigen Klasse auf
     *
     * @param tId Id des Zuges
     */
    public void callShowTrain(int tId) {
        rollingMSim.showTrain(tId);
    }



    // Drve Simulation Logik


    /**
     * Ruft die PutTrain-Methode in der für die Logik des Rollmaterials zuständigen Klasse auf
     *
     * @param trainId     ID des Zuges
     * @param stHeadPoint Kopfposition des Zuges
     * @param dirPoint    Richtungsvektor
     */
    public void callPutTrain(int trainId, Point stHeadPoint, Point dirPoint) {
        driveSim.putTrain(trainId, stHeadPoint, dirPoint);

    }

    /**
     * Ruft die PutTrain-Methode in der für die Logik des Rollmaterials zuständigen Klasse auf
     * @param speed 16-Bit-Ganzzahl gibt an um wieviele Einheiten die Züge fahren
     */
    public void callStep(short speed) {
        driveSim.step(speed);
    }

    /**
     * Getter der Klasse die für die Logik der Gleise zuständig ist
     * @return Klasse die für die Logik der Gleise zuständig ist
     */
    public TrackMaterialSimulation getTrackMSim() {
        return trackMSim;
    }

    /**
     * Getter der Klasse die für die Logik des Rollmaterials zuständig ist
     * @return Klasse die für die Logik des Rollmaterials zuständig ist
     */
    public RollingMaterialSimulation getrollingMSim() {
        return rollingMSim;
    }

    /**
     * Setter der Klasse die für die Logik der Gleise zuständig ist
     * @param trackMSim Klasse die für die Logik der Gleise zuständig ist
     */
    public void setTrackMSim(TrackMaterialSimulation trackMSim) {
        this.trackMSim = trackMSim;
    }

    /**
     * Setter der Klasse die für die Logik des Rollmaterials zuständig ist
     * @param rollingMSim Klasse die für die Logik des Rollmaterials zuständig ist
     */
    public void setRollingMSim(RollingMaterialSimulation rollingMSim) {
        this.rollingMSim = rollingMSim;
    }
}
