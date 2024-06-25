package edu.kit.informatik;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Diese Klasse ist für die Simulation des Fahrbetriebes zuständig
 *
 * @author Antonin Fahning
 * @version 1.0
 */

public class DriveSimulation {

    /**
     * Klasse die für die Logik der Gleise zuständig ist
     */
    private final TrackMaterialSimulation trackMSim;
    /**
     * Klasse die für die Logik des Rollmaterials zuständig ist
     */
    private final RollingMaterialSimulation rollingMSim;

    private final LinkedHashMap<Point, AbstractRailwayTrack> railwayNetwork = new LinkedHashMap<>();
    private Point direction;

    /**
     * Konstruktor der Logikklasse für den Fahrbetrieb
     *
     * @param trackMSim   Klasse die für die Logik der Gleise zuständig ist
     * @param rollingMSim Klasse die für die Logik des Rollmaterials zuständig ist
     */
    public DriveSimulation(TrackMaterialSimulation trackMSim, RollingMaterialSimulation rollingMSim) {
        this.trackMSim = trackMSim;
        this.rollingMSim = rollingMSim;
    }


    /**
     * Stellt einen validen (siehe Komposition von Rollmaterial) Zug auf ein Gleis an der Position <point>
     *
     * @param trainId     ID des Zuges
     * @param stHeadPoint Kopfposition des Zuges
     * @param dirPoint    Richtungsvektor
     */
    public void putTrain(int trainId, Point stHeadPoint, Point dirPoint) {
        HashMap<Integer, AbstractRailwayTrack> railwayTMHM = trackMSim.getAbstrRTrack();
        HashMap<Integer, TrainBuild> trainHM = rollingMSim.getTrainHashMap();
        AbstractRailwayTrack[] connTracks;

        if (trainHM.containsKey(trainId) && railwayTMHM.size() > 0) {
            if (trackMSim.allSwitchesSet()) {
                trainHM.get(trainId).resetLength();
                if (trainHM.get(trainId).isValid() && !trainHM.get(trainId).isOnTrack() ) {
                    railwayNetwork.clear();
                    generateNetwork();
                    connTracks = pointIsOnTracks(stHeadPoint);
                    Point[] occupied = new Point[trainHM.get(trainId).getLength()];
                    int ocKey = 0;

                    if (connTracks[0] != null) {
                        if (pointInDir(stHeadPoint, dirPoint) || pointInDir(stHeadPoint, dirPoint.negatePoint())) {
                            direction = dirPoint.negatePoint();
                            Point trainEnd = crawlInDir(stHeadPoint, direction);
                            occupied[ocKey] = trainEnd;
                            ocKey++;
                            if (trainHM.get(trainId).getLength() == 1
                                    && !pointInDir(stHeadPoint, dirPoint.negatePoint())) {
                                trainHM.get(trainId).resetLength();
                                Terminal.printError("Zug ist zu lang");
                                return;
                            }
                            Point trainOneBeforeEnd = stHeadPoint;
                            for (int n = 1; n < trainHM.get(trainId).getLength(); n++) {
                                trainEnd = crawlInDir(trainEnd, direction);
                                if (!trainEnd.equals(trainOneBeforeEnd) && !trainEnd.equals(stHeadPoint)
                                        && !trainEnd.equals(stHeadPoint.vectorAddition(dirPoint))) {
                                    occupied[ocKey] = trainEnd;
                                    ocKey++;
                                    trainOneBeforeEnd = trainEnd;
                                } else {
                                    trainHM.get(trainId).resetLength();
                                    Terminal.printError("Zug ist zu lang");
                                    return;
                                }
                            }
                            trainHM.get(trainId).setOccupiedPoints(occupied);
                            trainHM.get(trainId).setOnTrack(true);
                            trainHM.get(trainId).setHeadPos(stHeadPoint);
                            trainHM.get(trainId).setOccupiedPoints(occupied);
                            trainHM.get(trainId).setDirection(dirPoint);
                            HashMap<Integer, Integer> collision = collisionDetection();
                            Set<Map.Entry<Integer, Integer>> ids = collision.entrySet();
                            if (!(ids.size() > 1)) {
                                Terminal.printLine("OK");
                            } else {
                                trainHM.get(trainId).setOnTrack(false);
                                Terminal.printError("Zug kollidiert mit den bereits gesetzten Zügen");
                            }
                        } else {
                            trainHM.get(trainId).resetLength();
                            Terminal.printError("Richtungsvektor nicht parallel zu dem des Gleises");
                        }
                    } else {
                        trainHM.get(trainId).resetLength();
                        Terminal.printError("Punkt an den der Zug gesetzt werden soll ex. nicht auf den Gleisen");
                    }
                } else {
                    Terminal.printError("Zug nicht nach vorgaben aufgebaut oder bereits auf den Gleisen");
                }
            } else {
                Terminal.printError("Bevor ein Zug gesetzt werden kann müssen alle Weichen gesetzt sein");
            }
        } else {
            Terminal.printError("Es ex. noch kein Zug oder noch keine Gleise");
        }
    }

    /*
     * Erstellt ein "Netzwerk" das alle Punkte die auf einem Gleis liegen in die HashMap railwayNetwork schreibt
     * hierzu wird einfach jeder Punkt zwischen Start und Endpunkt zur Hashmap hinzugefügt
     */
    private void generateNetwork() {
        HashMap<Integer, AbstractRailwayTrack> railwayTMHM = trackMSim.getAbstrRTrack();
        Set<Map.Entry<Integer, AbstractRailwayTrack>> entries = railwayTMHM.entrySet();
        for (Map.Entry<Integer, AbstractRailwayTrack> entry : entries) {
            if (entry.getValue().getPos().getEndTwoPoint() == null) {
                if (entry.getValue().firstIsHorz()) {
                    //Wenn gleis Horizontal
                    int startingPX = entry.getValue().getPos().getStartingPoint().getX();
                    int endingPX = entry.getValue().getPos().getEndOnePoint().getX();
                    if (startingPX < endingPX) {
                        for (int n = startingPX; n <= endingPX; n++) {
                            Point point = new Point(n, entry.getValue().getPos().getStartingPoint().getY());
                            railwayNetwork.put(point, entry.getValue());
                        }
                    } else {
                        for (int n = endingPX; n <= startingPX; n++) {
                            Point point = new Point(n, entry.getValue().getPos().getStartingPoint().getY());
                            railwayNetwork.put(point, entry.getValue());
                        }
                    }
                } else {
                    // Wenn Gleis Vertical
                    int startingPY = entry.getValue().getPos().getStartingPoint().getY();
                    int endingPY = entry.getValue().getPos().getEndOnePoint().getY();
                    if (startingPY < endingPY) {
                        for (int n = startingPY; n <= endingPY; n++) {
                            Point point = new Point(entry.getValue().getPos().getStartingPoint().getX(), n);
                            railwayNetwork.put(point, entry.getValue());
                        }
                    } else {
                        for (int n = endingPY; n <= startingPY; n++) {
                            Point point = new Point(entry.getValue().getPos().getStartingPoint().getX(), n);
                            railwayNetwork.put(point, entry.getValue());
                        }
                    }
                }
            } else {
                //Weiche
                if (entry.getValue().getSwitchSetting() != null) {
                    if (entry.getValue().firstIsHorz()) {
                        int startingPX = entry.getValue().getPos().getStartingPoint().getX();
                        int endingPX = entry.getValue().getPos().getEndOnePoint().getX();
                        if (startingPX < endingPX) {
                            for (int n = startingPX; n <= endingPX; n++) {
                                Point point = new Point(n, entry.getValue().getPos().getStartingPoint().getY());
                                railwayNetwork.put(point, entry.getValue());
                            }
                        } else {
                            // Wenn sP > ist
                            for (int n = endingPX; n <= startingPX; n++) {
                                Point point = new Point(n, entry.getValue().getPos().getStartingPoint().getY());
                                railwayNetwork.put(point, entry.getValue());
                            }
                        }
                    } else {
                        int startingPY = entry.getValue().getPos().getStartingPoint().getY();
                        int endingPY = entry.getValue().getPos().getEndOnePoint().getY();
                        if (startingPY < endingPY) {
                            // Wenn sP < eP
                            for (int n = startingPY; n <= endingPY; n++) {
                                Point point = new Point(entry.getValue().getPos().getStartingPoint().getX(), n);
                                railwayNetwork.put(point, entry.getValue());
                            }
                        } else {
                            // sp > eP
                            for (int n = endingPY; n <= startingPY; n++) {
                                Point point = new Point(entry.getValue().getPos().getStartingPoint().getX(), n);
                                railwayNetwork.put(point, entry.getValue());
                            }
                        }
                    }
                } else {
                    Terminal.printLine("OK");
                }
            }
        }
    }

    /**
     * Gibt zurück ob ein Punkt auf einem Gleis liegt
     *
     * @param point Punkt der überprüft werden soll
     * @return Alle Strecken auf denen der Punkt liegt (0-2)
     */
    private AbstractRailwayTrack[] pointIsOnTracks(Point point) {
        AbstractRailwayTrack[] tracks = new AbstractRailwayTrack[2];
        int arraykey = 0;
        Set<Map.Entry<Point, AbstractRailwayTrack>> entries = railwayNetwork.entrySet();
        for (Map.Entry<Point, AbstractRailwayTrack> entry : entries) {
            if (arraykey > 1) {
                return tracks;
            } else if (entry.getKey().pointEquals(point)) {
                tracks[arraykey] = entry.getValue();
                arraykey++;
            }
        } return tracks; }

    /**
     * Schaut ob Punkt in die Richtung dir noch auf dem Gleisnetzwerk liegt
     *
     * @param from gegebener Startpunkt der sich auf dem Gleisnetzwerk befindet
     * @param dir  Richtungsvektor
     * @return true wenn Punkt in die Richtung dir noch auf dem Gleisnetzwerk liegt
     */
    private boolean pointInDir(Point from, Point dir) {
        Point to = from.vectorAddition(dir);
        Set<Map.Entry<Point, AbstractRailwayTrack>> entries = railwayNetwork.entrySet();
        for (Map.Entry<Point, AbstractRailwayTrack> entry : entries) {
            if (entry.getKey().pointEquals(to)) {
                return true;
            }
        } return false; }

    /**
     * Bewegt sich, von Punkt from auf dem GlNetzwerk, zu beginn in Richtung dir (Ist in der lage dem Schienenverlauf
     * zu folgen
     *
     * @param from Anfangspunkt
     * @param dir  Richtungsvektor
     * @return Ausgangspunkt
     */
    private Point crawlInDir(Point from, Point dir) {
        if (pointInDir(from, dir)) {
            return from.vectorAddition(dir);
        }
        if (Math.abs(dir.getX()) == 1 && Math.abs(dir.getY()) == 0) {
            // Anschluss-Gleis ist vertical
            Point north = new Point(0, 1);
            Point south = new Point(0, -1);
            if (pointInDir(from, north)) {
                direction = north;
                return from.vectorAddition(north);
            } else if (pointInDir(from, south)) {
                direction = south;
                return from.vectorAddition(south);
            }
        } else if (Math.abs(dir.getX()) == 0 && Math.abs(dir.getY()) == 1) {
            // Anschluss-Gleis ist horizontal
            Point east = new Point(1, 0);
            Point west = new Point(-1, 0);
            if (pointInDir(from, east)) {
                direction = east;
                return from.vectorAddition(east);
            } else if (pointInDir(from, west)) {
                direction = west;
                return from.vectorAddition(west);
            } else {
                //Sackgasse
                return from;
            }
        }
        //Sackgasse
        return from;
    }


    /**
     * Lässt alle Züge um speed -Einheiten fahren und gibt Crashes aus
     *
     * @param speed 16-Bit-Ganzzahl gibt an um wieviele Einheiten die Züge fahren
     */
    public void step(short speed) {
        HashMap<Integer, TrainBuild> trainHM = rollingMSim.getTrainHashMap();
        HashMap<Integer, String> stTransporter = new HashMap<>();
        // Speichert die IDs gecrashter Züge
        HashMap<Integer, Integer> alreadyCrashed = new HashMap<>();
        int stTPcounter = 0;
        if (trackMSim.allSwitchesSet()) {
            if (!trainHM.isEmpty() && trainsOnTrack()) {
                Set<Map.Entry<Integer, TrainBuild>> entries = trainHM.entrySet();
                if (speed != 0) {
                    for (int m = 0; m < Math.abs(speed); m++) {
                        // Schritte (speed) die die Züge machen
                        for (Map.Entry<Integer, TrainBuild> entry : entries) {
                            if (entry.getValue().isOnTrack()) {
                                Point newHP;

                                boolean newDirection = false;
                                if (!pointInDir(entry.getValue().getHeadPos(), entry.getValue().getDirection())) {
                                    newDirection = true;
                                }
                                if (speed > 0) {
                                    newHP = crawlInDir(entry.getValue().getHeadPos(), entry.getValue().getDirection());
                                } else {
                                    newHP = crawlInDir(entry.getValue().getHeadPos(),
                                            entry.getValue().getDirection().negatePoint());
                                }
                                if (!newHP.equals(entry.getValue().getHeadPos())) {
                                    newOccPoints(entry.getKey(), entry.getValue().getHeadPos());
                                    entry.getValue().setHeadPos(newHP);
                                    // neue pos vom Zug
                                    if (newDirection) {
                                        entry.getValue().setDirection(direction);
                                    }
                                } else {
                                    //Zug ist entgleist (Sackgasse)
                                    stTransporter.put(stTPcounter, "Crash of train " + entry.getKey());
                                    alreadyCrashed.put(stTPcounter, entry.getKey());
                                    stTPcounter++;
                                    entry.getValue().setOnTrack(false);
                                }
                            }
                        }
                    }
                    HashMap<Integer, Integer> collidedTrains = collisionDetection();
                    Set<Map.Entry<Integer, Integer>> ids = collidedTrains.entrySet();
                    String crashesOut = "";
                    if ((ids.size() > 1)) {
                        int helper = 0;
                        for (Map.Entry<Integer, Integer> entry : ids) {
                            if (notAlreadyCrashed(alreadyCrashed, entry.getValue())) {
                                if (helper > 0) {
                                    crashesOut = crashesOut + "," + entry.getValue();
                                } else {
                                    crashesOut = crashesOut + entry.getValue();
                                    helper++;
                                }
                                trainHM.get(entry.getValue()).setOnTrack(false);
                            }
                        }
                        stTransporter.put(stTPcounter, "Crash of train " + crashesOut);
                        stTPcounter++;
                    }
                }
                Set<Map.Entry<Integer, String>> strings = stTransporter.entrySet();
                for (Map.Entry<Integer, String> stEntry : strings) {
                    Terminal.printLine(stEntry.getValue());
                } // Ausgabe
                for (Map.Entry<Integer, TrainBuild> entry : entries) {
                    if (entry.getValue().isOnTrack()) {
                        Terminal.printLine("Train " + entry.getKey() + " at "
                                + entry.getValue().getHeadPos().toString());
                    }
                }
            } else {
                //Falls noch keine Züge gesetzt wurden
                Terminal.printLine("OK");
            }
        } else { Terminal.printError(", Weichen sind nicht gestellt"); }
    }

    /**
     * Updatet die Punkte die ein Zug besetzt
     *
     * @param trainID Id des zu überprüfenden Zuges
     * @param oldHP Alte Kopfposition
     */
    private void newOccPoints(int trainID, Point oldHP) {
        HashMap<Integer, TrainBuild> trainHM = rollingMSim.getTrainHashMap();
        Point[] oldOccu = trainHM.get(trainID).getOccupiedPoints();
        Point[] newOccu = new Point[trainHM.get(trainID).getLength()];
        for (int n = newOccu.length - 1; n > 0; n--) {
            newOccu[n] = oldOccu[n - 1];
        }
        newOccu[0] = oldHP;
        trainHM.get(trainID).setOccupiedPoints(newOccu);
    }

    /**
     * Eine Kollision ist nach Aufgabenblatt die Situation in der zwei Züge das gleiche Gleis belegen, hierauf testet
     * die Methode da es nach neuesten Regeländerungen unterschiede bei Step und allen anderen Befehlen gibt wird
     * hier unterschieden ob die Kollision für den Step Befehl überprüft werden soll oder nicht
     *
     * @return Kollidierende Züge
     */
    private HashMap<Integer, Integer> collisionDetection() {

        HashMap<Integer, TrainBuild> trainHM = rollingMSim.getTrainHashMap();
        Set<Map.Entry<Integer, TrainBuild>> entries = trainHM.entrySet();
        HashMap<Integer, Integer> colidedTrains = new HashMap<>();
        int colTKey = 0;
        //Für jeden Zug überprüfen
        for (Map.Entry<Integer, TrainBuild> entry : entries) {
            if (entry.getValue().isOnTrack()) {
                entry.getValue().clearOccupiedTracks();
                // Hier werden für jeden Zug die belegten Gleise berechnet
                Point[] occu = entry.getValue().getOccupiedPoints();
                for (Point point : occu) {
                    AbstractRailwayTrack[] tracksOccByOneP = pointIsOnTracks(point);
                    // ein Punkt kann auf einer oder 2 Strecken liegen (Knoten = 2)
                    int pointtimes = 1;
                    if (tracksOccByOneP[1] != null) {
                        pointtimes = 2;
                    }
                    for (int m = 0; m < pointtimes; m++) {
                        entry.getValue().addOccupiedtrack(tracksOccByOneP[m].getId());
                    }
                }
            }
        }
        for (Map.Entry<Integer, TrainBuild> entry : entries) {
            // Jeder Train
            if (entry.getValue().isOnTrack()) {
                //der gesetzt ist
                int times = 0;
                Set<Map.Entry<Integer, Integer>> ocTrackSet = entry.getValue().getOccupiedTracks().entrySet();
                for (Map.Entry<Integer, Integer> trackID : ocTrackSet) {
                    for (Map.Entry<Integer, TrainBuild> track : entries) {
                        if (entry.getValue().isOnTrack()) {
                            Set<Map.Entry<Integer, Integer>> sndOcTrackSet
                                    = track.getValue().getOccupiedTracks().entrySet();
                            for (Map.Entry<Integer, Integer> sndTrackID : sndOcTrackSet) {
                                if (trackID.getValue().equals(sndTrackID.getValue())) {
                                    times++;
                                }
                            }
                        }
                    }
                }
                /*Wenn times öfters hochgezählt wird als der Zug von sich aus GLeise belegt gibt es
                // eine Überschneidung (zwei Züge belegen das gleiche Gleis) mit einem anderen Zug */
                if (times - entry.getValue().getOccupiedTracks().size() > 0) {
                    colidedTrains.put(colTKey, entry.getKey());
                    colTKey++;
                }
            }
        } return colidedTrains; }


    /**
     * True wenn mindestens ein Zug auf den GLeisen steht
     * @return True wenn mindestens ein Zug auf den GLeisen steht
     */
    private boolean trainsOnTrack() {
        HashMap<Integer, TrainBuild> trainHM = rollingMSim.getTrainHashMap();
        Set<Map.Entry<Integer, TrainBuild>> entries = trainHM.entrySet();

        for (Map.Entry<Integer, TrainBuild> entry : entries) {
            if (entry.getValue().isOnTrack()) {
                return true;
            }
        } return false; }

    /**
     * Schaut ob der gegebene Zug nicht bereits gecrasht ist
     * @param alreadyCrashed Hasmap die die bereits gecrashten Züge beinhaltet
     * @param n Zug ID
     * @return true wenn noch nicht gecrasht
     */
    private boolean notAlreadyCrashed(HashMap<Integer, Integer> alreadyCrashed, int n) {
        Set<Map.Entry<Integer, Integer>> entries = alreadyCrashed.entrySet();
        for (Map.Entry<Integer, Integer> entry : entries) {
            if (entry.getValue() == n) {
                return false;
            }
        }
        return true;

    }


}
