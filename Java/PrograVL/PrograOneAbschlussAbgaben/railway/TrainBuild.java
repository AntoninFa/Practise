package edu.kit.informatik;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Modelliert einen Zug
 *
 * @author Antonin Fahning
 * @version 1.0
 */
public class TrainBuild {
    private int length = 0;
    private int powCounter = 0;
    private int keyCounter = 1;
    private boolean onTrack = false;
    private boolean containsTS = false;
    private String series = "";
    private Point headPos;
    private Point direction;
    private Point[] occupiedPoints;
    private final HashMap<Integer, Integer> occupiedTracks = new HashMap<>();
    private int occTracksKey = 1;
    private final TreeMap<Integer, RollingStock> trainContTreeMap = new TreeMap<>();

    /**
     * Erstellt einen neuen Zug
     * @param firstTrainPart Rollmaterial
     */
    public TrainBuild(RollingStock firstTrainPart) {
        trainContTreeMap.put(keyCounter, firstTrainPart);
        if (firstTrainPart.getSpecial().getcType() == 's') {
            containsTS = true;
            series = firstTrainPart.getSeries();
        }
        if (firstTrainPart.getSpecial().getcType() == 's' || firstTrainPart.getSpecial().getcType() == 'e') {
            powCounter++;
        }
    }

    /**
     * Fügt einem Zug Rollmaterial hinzu und prüft dabei auf die Vorgaben ( Kupplungen und ts-Spezifikationen)
     *
     * @param trainPart neues Rollmaterial
     * @return true wenn hinzufügen möglich
     */
    public boolean addTrainpart(RollingStock trainPart) {
        keyCounter++;

        if (trainContTreeMap.get(keyCounter - 1).getCouplingBack()
                && trainPart.getCouplingFront()) {

            if (!containsTS) {

                if (trainPart.getSpecial().getcType() == 's') {
                    keyCounter--;
                    return false;
                }
                trainContTreeMap.put(keyCounter, trainPart);
                return true;

            } else if (trainPart.getSeries() == null) {
                keyCounter--;
                return false;
            }
            if (trainPart.getSeries().equals(series)) {

                if (trainPart.getSpecial().getcType() == 's') {
                    containsTS = true;
                    series = trainPart.getSeries();
                    trainContTreeMap.put(keyCounter, trainPart);
                    return true;
                } else {
                    keyCounter--;
                    return false;
                }
            } else {
                keyCounter--;
                return false;
            }
        } else {
            keyCounter--;
            return false;
        }
    }

    /**
     * Getter für die Länge des Zuges
     * @return Länge des Zuges
     */
    public int getLength() {
        return this.length;
    }

    /**
     * Gibt zurück ob ein Zug nach den Vorschriften aufgebaut ist
     * @return true wenn Zug nach den Vorschriften aufgebaut
     */
    public boolean isValid() {
        if (powCounter > 0
                || trainContTreeMap.get(keyCounter).getSpecial().getcType() == 's'
                || trainContTreeMap.get(keyCounter).getSpecial().getcType() == 'e') {
            setLength();
            return true;
        }
        return false;
    }

    /**
     * Setter für das Attribut on Track
     * @param onTrack true wenn Zug auf Gleis gesetzt wurde
     */
    public void setOnTrack(boolean onTrack) {
        this.onTrack = onTrack;
    }

    /**
     * Gibt zurück ob sich der Zug auf dem Gleis befindet
     * @return true wenn Zug auf Gleis gesetzt wurde
     */
    public boolean isOnTrack() {
        return onTrack;
    }

    /**
     * Gibt an aus wievelen Rollmaterialien der Zug zusammengesetzt ist
     * @return Menge der Rollmaterialien
     */
    public int getAmountOfRS() {
        return trainContTreeMap.size();
    }

    /**
     * Gibt ein Rollmaterial an Stelle n aus
     * @param n Stelle im Zug
     * @return Rollmaterial an Stelle n
     */
    public RollingStock getRollingStockNr(int n) {
        return trainContTreeMap.get(n);
    }


    /**
     * Gibt die Kopfposition des Zuges an
     * @return Kopfposition des Zuges
     */
    public Point getHeadPos() {
        return headPos;
    }

    /**
     * Setter für die die Kopfposition des Zuges
     * @param headPos Kopfposition des Zuges
     */
    public void setHeadPos(Point headPos) {
        this.headPos = headPos;
    }

    /**
     * Setzt die Punkte die ein Zug belegt
     * @param occupiedPoints Punkte die ein Zug belegt
     */
    public void setOccupiedPoints(Point[] occupiedPoints) {
        this.occupiedPoints = occupiedPoints;
    }


    /**
     * Gibt die Punkte die ein Zug belegt zurück
     * @return Punkte die ein Zug belegt
     */
    public Point[] getOccupiedPoints() {
        return occupiedPoints;
    }

    /**
     * Gibt die Gleise die ein Zug belegt zurück
     * @param trackID Gleise die ein Zug belegt
     */
    public void addOccupiedtrack(int trackID) {
        if (!occupiedTracks.containsValue(trackID)) {
            occupiedTracks.put(occTracksKey, trackID);
            occTracksKey++;
        }
    }

    /**
     * Getter für die Hashmap die die ids der belegten Gleise speichert
     * @return Hashmap die die ids der belegten Gleise speichert
     */
    public HashMap<Integer, Integer> getOccupiedTracks() {
        return occupiedTracks;
    }

    /**
     * cleart die Hashmap occupiedTracks
     */
    public void clearOccupiedTracks() {
        occupiedTracks.clear();
    }

    /**
     * Berechnet die Länge eines Zuges
     */
    private void setLength() {
        Set<Map.Entry<Integer, RollingStock>> entries = trainContTreeMap.entrySet();
        for (Map.Entry<Integer, RollingStock> entry : entries) {
            this.length = this.length + entry.getValue().getLength();
        }
    }

    /**
     * Resetet die Länge eines Zuges
     */
    public void resetLength() {
        this.length = 0;
    }

    /**
     * Setter für den Richtungsvektor des Zuges
     * @param direction Richtungsvektor des Zuges
     */
    public void setDirection(Point direction) {
        this.direction = direction;
    }

    /**
     * Getter für den Richtungsvektor des Zuges
     * @return Richtungsvektor des Zuges
     */
    public Point getDirection() {
        return direction;
    }


    /**
     * Da die Zeilenanzahl der String-Repräsentrationen inkonsistent ist, der Praktomat aber keine
     * Leerzeilen will muss geschaut werden ob ein Zugteil aus 8 Zeilen besteht wenn ja sollen ( so weit man
     * das aus den nicht vorhandenen Vorgaben herausahnen kann) die Zugteile mit weniger Zeilen
     * Leerzeilen über sich haben sodass die Räder auf einer ebene liegen und wenn der Zug nur aus Einzelteilen
     * mit 6 Zeilen Stringrepräsentation besteht, dann besteht die Ausgabe nur aus 6 Zeilen.
     * Diese Methode schaut ob sich ein Zug-Teil mit höhe 8 teil des Zuges ist.
     * @return 8 wenn ja 6 wenn nicht
     */
    public int highestRep() {
        int rows = 6;
        Set<Map.Entry<Integer, RollingStock>> entries = trainContTreeMap.entrySet();
        for (Map.Entry<Integer, RollingStock> entry : entries) {
            if (entry.getValue().getSpecial().getcType() == 'e'
                    && entry.getValue().getSpecial().getrType() == 'e' ) {
                rows = 8;
                return rows;
            } else if (entry.getValue().getSpecial().getcType() == 's') {
                rows = 8;
                return rows;
            }
        }
        return rows;
    }
}
