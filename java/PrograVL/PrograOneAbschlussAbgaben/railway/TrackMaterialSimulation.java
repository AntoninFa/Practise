package edu.kit.informatik;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Klasse die für die Logik der Gleise zuständig ist
 *
 * @author Antonin Fahning
 * @version 1.0
 */
public class TrackMaterialSimulation {

    /**
     * Klasse die für die Logik des Rollmaterials zuständig ist
     */
    private final RollingMaterialSimulation rollingMSim;

    /**
     * Speichert das Gleismaterial, Integer als key und AbstractRailwayTrack als Value
     */
    private final HashMap<Integer, AbstractRailwayTrack> railwayTrackMap = new HashMap<>();

    // Speichert wieviele Gleise ex. ("Normale und Weichen als Gleismaterial zsm.-gefasst
    private int trackIdCounter = 1;
    // Speichert wieviel Rollmaterial ex.


    /**
     * Konstruktor für TrackMaterialSimulation, der Klasse die für die Logik der Gleise zuständig ist
     *
     * @param rollingMSim Klasse die für die Logik des Rollmaterials zuständig ist
     */
    public TrackMaterialSimulation(RollingMaterialSimulation rollingMSim) {
        this.rollingMSim = rollingMSim;
    }

    /**
     * Fügt der Simulation ein normales Gleis hinzu wobei
     *
     * @param startPoint der Anfangspunkt des Gleises und
     * @param endPoint   der Endpunkt des Gleises ist
     */
    public void addTrack(Point startPoint, Point endPoint) {
        int key = lookForFirstFreeTrackId();
        // Prüft ob ein Hinzufügen mölich ist
        if (trackIdCounter == 1
                || addingPossibleForTrack(startPoint, endPoint, null)) {
            // Zählt hoch wenn ein Gleis erstellt wird
            trackIdCounter++;
            Track track = new Track(key, startPoint, endPoint);
            if (track.trackIsVerticalOrHorz()) {
                railwayTrackMap.put(key, track);
                Terminal.printLine(key);
            } else {
                Terminal.printError("Gleis weder Horizontal noch Vertical");
            }
        } else {
            Terminal.printError("Gleis Startpunkt nicht mit einem (freien) GleisEnde/Anfang verbunden");
        }
    }

    /**
     * Fügt der Simulation eine Weiche hinzu wobei
     *
     * @param startPoint  der Startpunkt,
     * @param endOnePoint der erste Endpunkt und
     * @param endTwoPoint der zweite Endpunkt der Weiche ist
     */
    public void addSwitch(Point startPoint, Point endOnePoint, Point endTwoPoint) {
        int key = lookForFirstFreeTrackId();
        // Prüft ob ein Hinzufügen mölich ist
        if (trackIdCounter == 1 || addingPossibleForTrack(startPoint, endOnePoint, endTwoPoint)) {
            // Zählt hoch wenn eine Weiche erstellt wird
            trackIdCounter++;
            Switch railSwitch = new Switch(key, startPoint, endOnePoint, endTwoPoint);
            if (railSwitch.switchIsVerticalOrHorz()) {
                railwayTrackMap.put(key, railSwitch);
                Terminal.printLine(key);
            } else {
                Terminal.printError("Weiche weder Horizontal noch Verical");
            }
        } else {
            Terminal.printError("Weichen Startpunkt nicht mit einem GleisEnde/Anfang verbunden");
        }
    }


    /**
     * Sucht nach freier Id für das Gleismaterial
     *
     * @return Die erste ungenutzte Ganzzahl beginnend bei 1 die
     * für die Id genutzt werden kann
     */

    private int lookForFirstFreeTrackId() {
        if (railwayTrackMap.isEmpty()) {
            return 1;
        } else {
            for (int n = 1; n <= trackIdCounter; n++) {
                if (railwayTrackMap.get(n) == null) {
                    return n;
                }
            }
            return trackIdCounter;
        }
    }

    /**
     * Prüft ob einer der beiden Punkte
     * auf einem Start- oder Endpunkt eines anderen Gleis liegen --> Prüft ob ein Hinzufügen mölich ist
     *
     * @param ttStart  Startpunkt des Gleises das hinzugefügt werden soll
     * @param ttEndOne Endpunkt eines (normalen)Gleises/ 1. Endpunkt einer Weiche
     * @param ttEndTwo 2. Endpunkt einer Weiche
     * @return true wenn ja false wenn nicht
     */

    private boolean addingPossibleForTrack(Point ttStart, Point ttEndOne, Point ttEndTwo) {

        Point helper = null;
        int n;
        int m = 3;
        if (ttEndTwo == null) {
            m = 2;
        }
        Iterator<Map.Entry<Integer, AbstractRailwayTrack>> entrySet = railwayTrackMap.entrySet().iterator();

        while (entrySet.hasNext()) {
            n = 0;
            Map.Entry<Integer, AbstractRailwayTrack> entry = entrySet.next();
            // Schaut ob startpunkt auf einem Gleisende liegt
            while (n < m) {
                n++;
                if (n == 1) {
                    helper = ttStart;
                } else if (n == 2) {
                    helper = ttEndOne;
                } else if (n == 3) {
                    helper = ttEndTwo;
                }
                if (helper != null) {
                    // Schaut ob helper auf einem Weichenende liegt
                    if (entry.getValue().getPos().getEndOnePoint().getX() == helper.getX()
                            && entry.getValue().getPos().getEndOnePoint().getY() == helper.getY()) {
                        return true;
                        // Schaut ob helper auf einem Weichenende liegt
                    } else if (entry.getValue().getPos().getEndTwoPoint() != null
                            && entry.getValue().getPos().getEndTwoPoint().getX() == helper.getX()
                            && entry.getValue().getPos().getEndTwoPoint().getY() == helper.getY()) {
                        return true;
                        // Schaut ob helper auf einem Gleisbeginn (Gleis und Weiche) liegt
                    } else if (entry.getValue().getPos().getStartingPoint().getX() == helper.getX()
                            && entry.getValue().getPos().getStartingPoint().getY() == helper.getY()) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

    /**
     * Löscht ein Gleis falls möglich
     *
     * @param trackID Id des zu löschenden Gleises
     */

    public void deleteTrack(int trackID) {
        if (trackID > 0) {
            if ((railwayTrackMap.size() > 0) && railwayTrackMap.containsKey(trackID)) {
                if (noTrOnTrack(trackID)) {
                    if (trackDeletionPossible(trackID)) {
                        railwayTrackMap.remove(trackID);
                        trackIdCounter = trackIdCounter - 1;
                        Terminal.printLine("OK");
                    } else {
                        Terminal.printError("Gleis darf nach Vorgaben nicht entfernt werden");
                    }
                } else {
                    Terminal.printError("Es befindet sich leider ein Zug auf dem zu löschenden Gleis");
                }
            } else {
                Terminal.printError("ID ex. nicht");
            }
        } else {
            Terminal.printError("Ungültiger ID-Wert");
        }

    }

    /**
     * Prüft ob Gleis nur an einem Punkt verbunden ist
     *
     * @param trackID Id des zu löschenden Gleises
     * @return true wenn Gleis nur an einem Punkt verbunden ist
     */

    private boolean trackDeletionPossible(int trackID) {

        Point helper = null;
        int twice = 0;
        int n;
        int m = 3;
        if (railwayTrackMap.get(trackID).getPos().getEndTwoPoint() == null) {
            m = 2;
        }
        Iterator<Map.Entry<Integer, AbstractRailwayTrack>> entrySet = railwayTrackMap.entrySet().iterator();

        while (entrySet.hasNext()) {
            n = 0;
            Map.Entry<Integer, AbstractRailwayTrack> entry = entrySet.next();
            // Schaut ob startpunkt auf einem Gleisende liegt
            while (n < m) {
                n++;
                if (n == 1) {
                    helper = railwayTrackMap.get(trackID).getPos().getStartingPoint();
                } else if (n == 2) {
                    helper = railwayTrackMap.get(trackID).getPos().getEndOnePoint();
                } else if (n == 3) {
                    helper = railwayTrackMap.get(trackID).getPos().getEndTwoPoint();
                }

                if (helper != null) {
                    // Schaut ob helper auf einem Weichenende liegt
                    //L EOP der aus Map geholt wird
                    //R abhängig von n die (2-3), die von del Track reinkommen
                    if (entry.getValue().getPos().getEndOnePoint().getX() == helper.getX()
                            && entry.getValue().getPos().getEndOnePoint().getY() == helper.getY()) {
                        twice++;
                        // Schaut ob helper auf einem Weichenende liegt
                    } else if (entry.getValue().getPos().getEndTwoPoint() != null
                            && entry.getValue().getPos().getEndTwoPoint().getX() == helper.getX()
                            && entry.getValue().getPos().getEndTwoPoint().getY() == helper.getY()) {
                        twice++;
                        // Schaut ob helper auf einem Gleisbeginn (Gleis und Weiche) liegt
                    } else if (entry.getValue().getPos().getStartingPoint().getX() == helper.getX()
                            && entry.getValue().getPos().getStartingPoint().getY() == helper.getY()) {
                        twice++;
                    }
                }
            }
        }
        if (railwayTrackMap.get(trackID).getPos().getEndTwoPoint() == null) {
            // Weil sich das Gleis selber matcht
            twice = twice - 2;
        } else {
            // Weil sich die Weiche selber matcht
            twice = twice - 3;

        } return twice < 2; }

    /**
     * Gibt alle gespeicherten Gleise aus
     */
    public void listTracks() {
        if (!railwayTrackMap.isEmpty()) {
            Iterator<Map.Entry<Integer, AbstractRailwayTrack>> entrySet = railwayTrackMap.entrySet().iterator();
            while (entrySet.hasNext()) {
                Map.Entry<Integer, AbstractRailwayTrack> entry = entrySet.next();
                int startPointX = entry.getValue().getPos().getStartingPoint().getX();
                int startPointY = entry.getValue().getPos().getStartingPoint().getY();
                int endOnePointX = entry.getValue().getPos().getEndOnePoint().getX();
                int endOnePointY = entry.getValue().getPos().getEndOnePoint().getY();

                if (entry.getValue().getPos().getEndTwoPoint() != null) {
                    //Weiche
                    int endTwoPointX = entry.getValue().getPos().getEndTwoPoint().getX();
                    int endTwoPointY = entry.getValue().getPos().getEndTwoPoint().getY();
                    if (entry.getValue().getSwitchSetting() == null) {
                        // Ungesetzte Weiche
                        Terminal.printLine("s " + entry.getKey() + " (" + startPointX + "," + startPointY + ") -> ("
                                + endOnePointX + "," + endOnePointY + ")" + ",("
                                + endTwoPointX + "," + endTwoPointY + ") ");

                    } else {
                        //Gesetzte Weiche
                        Terminal.printLine("s " + entry.getKey() + " (" + startPointX + "," + startPointY + ") -> ("
                                + endOnePointX + "," + endOnePointY + ")" + ",("
                                + endTwoPointX + "," + endTwoPointY + ")" + entry.getValue().getLength());
                    }
                } else {
                    // Ausgabe für Normale Schienen
                    Terminal.printLine("t " + entry.getKey() + " (" + startPointX + "," + startPointY + ") -> ("
                            + endOnePointX + "," + endOnePointY + ") " + entry.getValue().getLength() + "");
                }
            }
        } else {
            Terminal.printLine("No track exists");
        }
    }

    /**
     * Setzt, wenn möglich, die Weichenstellung der Weiche
     *
     * @param tID   Id der Weiche
     * @param point Punkt der Weichenstellung
     */

    public void setSwitch(int tID, Point point) {
        if (railwayTrackMap.get(tID) != null) {
            if (railwayTrackMap.get(tID).getPos().getEndTwoPoint() != null) {
                Point s1 = railwayTrackMap.get(tID).getPos().getEndOnePoint();
                Point s2 = railwayTrackMap.get(tID).getPos().getEndTwoPoint();
                if (pointsEqual(point, s1) || pointsEqual(point, s2)) {
                    removeTrainIfOnSwitch(tID);
                    railwayTrackMap.get(tID).setSwitchSetting(point);
                    Terminal.printLine("OK");
                } else { Terminal.printError("Gegebener Punkt ist keine Mögliche Weichenstellung"); }
            } else { Terminal.printError("Gleis mit ID " + tID + " ist keine Weiche"); }
        } else { Terminal.printError("Gleis-ID nicht gefunden"); }
    }

    /**
     * Gibt true zurück wenn beide übergebenen Punkte gleich sind(gleicher X&Y Wert
     *
     * @param pointOne Punkt 1
     * @param pointTwo Punkt 2
     * @return true wenn beide übergebenen Punkte gleich sind(gleicher X&Y Wert)
     */
    public boolean pointsEqual(Point pointOne, Point pointTwo) {
        return pointOne.getX() == pointTwo.getX()
                && pointOne.getY() == pointTwo.getY();
    }

    /**
     * Getter für die HashMap die alle Gleise beinhaltet
     *
     * @return HashMap die alle Gleise beinhaltet
     */
    public HashMap<Integer, AbstractRailwayTrack> getAbstrRTrack() {
        return railwayTrackMap;
    }

    /**
     * Gibt zurück ob alle Switches gesetzt sind
     *
     * @return true wenn alle Switches gesetzt sind
     */
    public boolean allSwitchesSet() {
        Set<Map.Entry<Integer, AbstractRailwayTrack>> entries = railwayTrackMap.entrySet();
        for (Map.Entry<Integer, AbstractRailwayTrack> entry : entries) {
            if (entry.getValue().getPos().getEndTwoPoint() != null) {
                if (entry.getValue().getSwitchSetting() == null) {
                    return false;
                }
            }
        } return true; }

    /**
     * Schaut ob sich auf dem Gleis mit der ID trackId ein Zug befindet
     *
     * @param trackId ID eines Gleises
     * @return true wenn sich auf dem Gleis mit der ID trackId ein Zug befindet
     */
    private boolean noTrOnTrack(int trackId) {
        HashMap<Integer, TrainBuild> trainHM = rollingMSim.getTrainHashMap();
        Set<Map.Entry<Integer, TrainBuild>> entries = trainHM.entrySet();
        for (Map.Entry<Integer, TrainBuild> entry : entries) {
            Set<Map.Entry<Integer, Integer>> ocTrackSet = entry.getValue().getOccupiedTracks().entrySet();
            if (entry.getValue().isOnTrack()) {
                for (Map.Entry<Integer, Integer> track : ocTrackSet) {
                    if (track.getValue().equals(trackId)) {
                        return false;
                    }
                }
            }
        } return true; }

    /**
     * Wenn sich ein Zug auf einer Switch befindet die gesetzt wird wird der Zug nach Forum ohne Nachricht vom
     * Gleis genommen
     *
     * @param trackId ID der Weiche
     */
    private void removeTrainIfOnSwitch(int trackId) {
        HashMap<Integer, TrainBuild> trainHM = rollingMSim.getTrainHashMap();
        Set<Map.Entry<Integer, TrainBuild>> entries = trainHM.entrySet();
        for (Map.Entry<Integer, TrainBuild> entry : entries) {
            Set<Map.Entry<Integer, Integer>> ocTrackSet = entry.getValue().getOccupiedTracks().entrySet();
            if (entry.getValue().isOnTrack()) {
                for (Map.Entry<Integer, Integer> track : ocTrackSet) {
                    if (track.getValue().equals(trackId)) {
                        entry.getValue().setOnTrack(false);
                    }
                }
            }
        }
    }

}
