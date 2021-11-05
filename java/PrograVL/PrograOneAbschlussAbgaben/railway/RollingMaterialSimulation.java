package edu.kit.informatik;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Modelliert das Rollmaterial der Modelleisenbahnsimulatione
 *
 * @author Antonin Fahning
 * @version 1.0
 */
public class RollingMaterialSimulation {
    private final HashMap<Integer, Coach> coachHashMap = new HashMap<>();
    private final HashMap<String, PowerTrain> powTrainHashMap = new HashMap<>();
    private int coachIdCounter = 1;
    private final HashMap<Integer, TrainBuild> trainHashMap = new HashMap<>();
    private int trainIdCounter = 1;

    /**
     * Erstellt einen Wagen
     *
     * @param coachType Typ des Wagens
     * @param length    Länge als 32-Bit-Ganzzahl
     * @param cFront    true Kupplung wenn es vorne eine Kupplung hat
     * @param cBack     true Kupplung wenn es hinten eine Kupplung hat
     */
    public void createCoach(String coachType, int length, boolean cFront, boolean cBack) {
        if (cFront || cBack) {
            int key = lookForFirstFreeCoachId();
            coachIdCounter++;
            CoachType convCoachType = null;
            char representation = 0;
            switch (coachType) {
                case "passenger":
                    convCoachType = CoachType.PASSENGER;
                    representation = 'p';
                    break;
                case "freight":
                    convCoachType = CoachType.FREIGHT;
                    representation = 'f';
                    break;
                case "special":
                    convCoachType = CoachType.SPECIAL;
                    representation = 's';
                    break;
                default:
                    Terminal.printError("Coach Typ ungültig");
                    break;
            }
            if (convCoachType != null) {
                Specialization spec = new Specialization(String.valueOf(key), 'c', representation);
                Coach coach = new Coach(convCoachType, length, cFront, cBack, spec);
                coachHashMap.put(key, coach);
                Terminal.printLine(key);
            } else { Terminal.printError("Coach Typ ungültig"); }
        } else { Terminal.printError("Ein Rollingstock muss mindestens eine Kupplung besitzen"); }
    }

    private int lookForFirstFreeCoachId() {
        if (coachHashMap.isEmpty()) {
            return 1;
        } else {
            for (int n = 1; n <= coachIdCounter; n++) {
                if (coachHashMap.get(n) == null) {
                    return n;
                }
            } return coachIdCounter; }
    }

    /**
     * Gibt zeilenweise eine Liste mit allen Wagen aus
     * Die Liste ist aufsteigend nach der Wagen-ID
     * sortiert.
     */
    public void listCoaches() {
        if (!coachHashMap.isEmpty()) {
            TreeMap<Integer, Coach> sorted = new TreeMap<>(coachHashMap);
            Set<Map.Entry<Integer, Coach>> mappings = sorted.entrySet();
            String coachType = null;
            for (Map.Entry<Integer, Coach> mapping : mappings) {
                switch (mapping.getValue().getCoachType().getCoachType()) {
                    case "passenger":
                        coachType = "p";
                        break;
                    case "freight":
                        coachType = "f";
                        break;
                    case "special":
                        coachType = "s";
                        break;
                    default: Terminal.printError("Coach Typ ungültig");
                        break;
                }
                if (mapping.getValue().getSpecial().getTrainID() != -1) {
                    Terminal.printLine(mapping.getKey() + " "
                            + mapping.getValue().getSpecial().getTrainID() + " "
                            + coachType + " " + mapping.getValue().getLength() + " "
                            + mapping.getValue().getCouplingFront() + " " + mapping.getValue().getCouplingBack());

                } else {
                    Terminal.printLine(mapping.getKey() + " " + "none" + " "
                            + coachType + " " + mapping.getValue().getLength() + " "
                            + mapping.getValue().getCouplingFront() + " " + mapping.getValue().getCouplingBack());
                }
            }
        } else { Terminal.printLine("No coach exists"); }
    }

    /**
     * Erstellt einen Antriebszug
     *
     * @param engineType beschreibt den Typ
     * @param powTClass  Baureihe
     * @param powTName   Name des Antriebszugs
     * @param length     Länge des Antriebszugs
     * @param cFront     true Kupplung wenn es vorne eine Kupplung hat
     * @param cBack      true Kupplung wenn es hinten eine Kupplung hat
     */
    public void createPowTrain(String engineType, String powTClass, String powTName
            , int length, boolean cFront, boolean cBack) {
        if (cFront || cBack) {
            String key = powTClass + "-" + powTName;
            char representation = 0;
            if (!powTrainHashMap.containsKey(key)) {
                if (engineType != null) {
                    EngineType convEnType = null;
                    switch (engineType) {
                        case "electrical":
                            convEnType = EngineType.ELECTRICAL;
                            representation = 'e';
                            break;
                        case "steam":
                            convEnType = EngineType.STEAM;
                            representation = 's';
                            break;
                        case "diesel":
                            convEnType = EngineType.DIESEL;
                            representation = 'd';
                            break;
                        default:
                            Terminal.printError("Engine Typ ungültig");
                            break;
                    }
                    if (convEnType != null) {
                        Specialization spec = new Specialization(key, 'e', representation);
                        Engine engine = new Engine(convEnType, powTClass, powTName, length, cFront, cBack, spec);

                        powTrainHashMap.put(key, engine);
                        Terminal.printLine(key);
                    } else {
                        Terminal.printError("Coach Typ ungültig");
                    }
                } else {
                    Specialization spec = new Specialization(key, 's', 't');
                    TrainSet trainSet = new TrainSet(powTClass, powTName, length, cFront, cBack, spec);
                    powTrainHashMap.put(key, trainSet);
                    Terminal.printLine(key);
                }
            } else {
                Terminal.printError("Die gegebene ID + (" + key + ") wird bereits genutzt");
            }
        } else { Terminal.printError("Ein Rollingstock muss mindestens eine Kupplung besitzen"); }
    }

    /**
     * Gibt zeilenweise eine Liste mit allen Lokomotiven aus
     * Die Liste ist aufsteigend nach der Lok-ID
     * sortiert.
     */
    public void listEngines() {
        if (!powTrainHashMap.isEmpty()) {
            TreeMap<String, PowerTrain> sorted = new TreeMap<>(powTrainHashMap);
            Set<Map.Entry<String, PowerTrain>> mappings = sorted.entrySet();
            String engineType = null;
            for (Map.Entry<String, PowerTrain> mapping : mappings) {
                if (mapping.getValue().getEngineType() != null) {
                    //engine
                    switch (mapping.getValue().getEngineType().getEngineType()) {
                        case "electrical":
                            engineType = "e";
                            break;
                        case "steam":
                            engineType = "s";
                            break;
                        case "diesel":
                            engineType = "d";
                            break;
                        default: Terminal.printError("Engine Typ ungültig");
                            break;
                    }
                    if (mapping.getValue().getSpecial().getTrainID() != -1) {
                        Terminal.printLine(mapping.getValue().getSpecial().getTrainID() + " " + engineType + " "
                                + mapping.getValue().getSeries() + " "
                                + mapping.getValue().getName() + " " + mapping.getValue().getLength() + " "
                                + mapping.getValue().getCouplingFront() + " " + mapping.getValue().getCouplingBack());
                    } else {
                        Terminal.printLine("none " + engineType + " " + mapping.getValue().getSeries() + " "
                                + mapping.getValue().getName() + " " + mapping.getValue().getLength() + " "
                                + mapping.getValue().getCouplingFront() + " " + mapping.getValue().getCouplingBack());
                    }
                }
            }
        } else {
            Terminal.printLine("No engine exists");
        }
    }

    /**
     * Gibt zeilenweise eine Liste mit allen Triebzügen aus. Die Liste ist lexikografisch nach der Triebzug
     * ID geordnet.
     */
    public void listTrainSets() {
        if (!powTrainHashMap.isEmpty()) {
            TreeMap<String, PowerTrain> sorted = new TreeMap<>(powTrainHashMap);
            Set<Map.Entry<String, PowerTrain>> mappings = sorted.entrySet();
            for (Map.Entry<String, PowerTrain> mapping : mappings) {
                if (mapping.getValue().getEngineType() == null) {
                    if (mapping.getValue().getTrainID() != -1) {
                        Terminal.printLine(mapping.getValue().getTrainID() + " "
                                + mapping.getValue().getSeries() + " "
                                + mapping.getValue().getName() + " " + mapping.getValue().getLength() + " "
                                + mapping.getValue().getCouplingFront() + " " + mapping.getValue().getCouplingBack());
                    } else {
                        Terminal.printLine("none " + mapping.getValue().getSeries() + " "
                                + mapping.getValue().getName() + " " + mapping.getValue().getLength() + " "
                                + mapping.getValue().getCouplingFront() + " " + mapping.getValue().getCouplingBack());
                    }
                }
            }
        } else { Terminal.printLine("No train-set exists"); }
    }

    /**
     * Löscht einen Wagen
     *
     * @param id Wagen-ID
     */
    public void deleteCoach(int id) {
        if (coachHashMap.containsKey(id)) {
            if (coachHashMap.get(id).getSpecial().getTrainID() == -1) {
                coachHashMap.remove(id);
                coachIdCounter = coachIdCounter - 1;
                Terminal.printLine("OK");
            } else { Terminal.printError("Wagen wird in einem Zug verwendet"); }
        } else { Terminal.printError("Rolling Stock mit dieser ID ex. nicht"); }
    }

    /**
     * Löscht einen Antriebszug
     *
     * @param id Id des Antriebszugs
     */
    public void deletePowT(String id) {
        if (powTrainHashMap.containsKey(id)) {
            if (powTrainHashMap.get(id).getTrainID() == -1) {
                powTrainHashMap.remove(id);
                Terminal.printLine("OK");
            } else { Terminal.printError("Wagen wird in einem Zug verwendet"); }
        } else { Terminal.printError("Rolling Stock mit dieser ID ex. nicht"); }
    }

    /**
     * Fügt einem Zug einen Coach-Wagen hinzu "Falls der Zug mit der ID
     * noch nicht existiert wird er automatisch angelegt. Die
     * ID muss der nächsten freien ID entsprechen" habe ich nach Beitrag im Forum so ausgelegt,
     * dass wenn die geg. Id nicht der nächsten freien entspricht eine Fehlermeldung ausgegeben wird
     *
     * @param trainId ID des Zuges zu dem der Wagen hinzugefügt werden soll
     * @param rSId    ID des Wagens
     */
    public void addTrainCoach(int trainId, int rSId) {
        if (coachHashMap.containsKey(rSId) && !coachHashMap.get(rSId).isAddedToTrain()) {
            if (!trainHashMap.containsKey(trainId)) {
                int key = lookForFirstFreeTrainId();
                trainIdCounter++;
                if (key == trainId) {
                    TrainBuild train = new TrainBuild(coachHashMap.get(rSId));
                    coachHashMap.get(rSId).getSpecial().setTrainID(trainId);
                    coachHashMap.get(rSId).setAddedToTrain(true);
                    trainHashMap.put(key, train);
                    Terminal.printLine(coachHashMap.get(rSId).getCoachType().getCoachType() + " coach W"
                            + rSId + " added to train " + key);
                } else {
                    trainIdCounter--;
                    Terminal.printError(trainId + " entspricht nicht der nächsten freien ID");
                }
            } else {
                if (!trainHashMap.get(trainId).isOnTrack()) {
                    if (trainHashMap.get(trainId).addTrainpart(coachHashMap.get(rSId))) {
                        coachHashMap.get(rSId).getSpecial().setTrainID(trainId);
                        coachHashMap.get(rSId).setAddedToTrain(true);
                        Terminal.printLine(coachHashMap.get(rSId).getCoachType().getCoachType() + " coach W"
                                + rSId + " added to train " + trainId);
                    } else { Terminal.printError("Zug lässt sich nach Vorgaben nicht zum Zug hinzufügen"); }
                } else { Terminal.printError("Zug steht bereits auf einem Gleis"); }
            }
        } else { Terminal.printError(rSId + " ex. nicht oder wird bereits in einem Zug verwendet"); }
    }

    private int lookForFirstFreeTrainId() {
        if (trainHashMap.isEmpty()) {
            return 1;
        } else {
            for (int n = 1; n <= trainIdCounter; n++) {
                if (trainHashMap.get(n) == null) {
                    return n;
                }
            } return trainIdCounter; }
    }

    /**
     * "Falls der Zug mit der ID noch nicht existiert wird er automatisch angelegt. Die
     * ID muss der nächsten freien ID entsprechen" habe ich nach Beitrag im Forum so ausgelegt,
     * dass wenn die geg. Id nicht der nächsten freien entspricht eine Fehlermeldung ausgegeben wird
     *
     * @param trainId ID des Zuges zu dem der Wagen hinzugefügt werden soll
     * @param rSId    ID des Wagens
     */
    public void addTrainPow(int trainId, String rSId) {
        if (powTrainHashMap.containsKey(rSId) && !powTrainHashMap.get(rSId).isAddedToTrain()) {
            if (!trainHashMap.containsKey(trainId)) {
                int key = lookForFirstFreeTrainId();
                trainIdCounter++;
                if (key == trainId) {
                    TrainBuild train = new TrainBuild(powTrainHashMap.get(rSId));
                    powTrainHashMap.get(rSId).getSpecial().setTrainID(key);
                    powTrainHashMap.get(rSId).setAddedToTrain(true);
                    trainHashMap.put(key, train);
                } else {
                    trainIdCounter--;
                    Terminal.printError(trainId + " entspricht nicht der nächsten freien ID");
                    return;
                }
                if (powTrainHashMap.get(rSId).getEngineType() != null) {
                    Terminal.printLine(powTrainHashMap.get(rSId).getEngineType().getEngineType() + " engine "
                            + rSId + " added to train " + key);
                } else {
                    Terminal.printLine("train-set "
                            + rSId + " added to train " + key);
                }
            } else {
                if (!trainHashMap.get(trainId).isOnTrack()) {
                    if (trainHashMap.get(trainId).addTrainpart(powTrainHashMap.get(rSId))) {
                        if (powTrainHashMap.get(rSId).getEngineType() != null) {
                            powTrainHashMap.get(rSId).getSpecial().setTrainID(trainId);
                            powTrainHashMap.get(rSId).setAddedToTrain(true);
                            Terminal.printLine(powTrainHashMap.get(rSId).getEngineType().getEngineType()
                                    + " engine " + rSId + " added to train " + trainId);
                        } else {
                            Terminal.printLine("train-set "
                                    + rSId + " added to train " + trainId);
                        }
                    } else { Terminal.printError("Zug lässt sich nach Vorgaben nicht zum Zug hinzufügen"); }
                } else { Terminal.printError("Zug steht bereits auf einem Gleis"); }
            }
        } else { Terminal.printError(rSId + " ex. nicht oder wird bereits in einem Zug verwendet"); }
    }

    /**
     * Löscht den Zug mit der <ID> . Falls der Zug sich auf der Strecke befindet, wird er auch von der
     * Strecke entfernt. Nach Forum wird Rolling Stock nicht gelöscht sondern wieder freigegeben
     *
     * @param id Id des zu entfernenden Zuges
     */
    public void deleteTrain(int id) {
        if (trainHashMap.containsKey(id)) {
            for (int n = 1; n <= trainHashMap.get(id).getAmountOfRS(); n++) {
                trainHashMap.get(id).getRollingStockNr(n).setAddedToTrain(false);
                trainHashMap.get(id).getRollingStockNr(n).getSpecial().setTrainID(-1);
            } trainHashMap.remove(id);
            trainIdCounter--;
            Terminal.printLine("OK");
        } else { Terminal.printError("Train mit dieser ID ex. nicht"); }
    }

    /**
     * Gibt zeilenweise eine Liste mit allen Zügen aus
     */
    public void listTrains() {
        if (!trainHashMap.isEmpty()) {
            TreeMap<Integer, TrainBuild> sorted = new TreeMap<>(trainHashMap);
            Set<Map.Entry<Integer, TrainBuild>> mappings = sorted.entrySet();
            for (Map.Entry<Integer, TrainBuild> mapping : mappings) {
                StringBuilder members = new StringBuilder();
                for (int n = 1; n <= mapping.getValue().getAmountOfRS(); n++) {
                    String id = mapping.getValue().getRollingStockNr(n).getSpecial().getiD();
                    if (mapping.getValue().getRollingStockNr(n).getSpecial().getcType() == 'c') {
                        members.append(" W").append(id);
                    } else { members.append(" ").append(id); }
                } Terminal.printLine(mapping.getKey() + members.toString()); }
        } else { Terminal.printLine("No train exists"); }
    }

    /**
     * gibt die grafische Repräsentation des Zuges aus
     *
     * @param tId Id des Zuges
     */
    public void showTrain(int tId) {
        if (trainHashMap.containsKey(tId)) {
            String[] added = new String[]{"", "", "", "", "", ""};
            String space = "";
            int lilCounter = 0;
            int rows = trainHashMap.get(tId).highestRep();
            if ( rows == 8) {
                added = new String[]{"", "", "", "", "", "", "", ""};
            }
            for (int n = 1; n <= trainHashMap.get(tId).getAmountOfRS(); n++) {
                boolean eightRows = false;
                String[] addition = new String[]{"", "", "", "", "", ""};
                String[] addition8 = new String[]{"                    ", "                    "
                        , "", "", "", "", "", ""};
                // Coach:
                if (trainHashMap.get(tId).getRollingStockNr(n).getSpecial().getcType() == 'c') {
                    switch (trainHashMap.get(tId).getRollingStockNr(n).getSpecial().getrType()) {
                        case 'p':
                            addition = trainHashMap.get(tId).getRollingStockNr(n).getPassRep();
                            break;
                        case 'f':
                            addition = trainHashMap.get(tId).getRollingStockNr(n).getFreiRep();
                            break;
                        case 's':
                            addition = trainHashMap.get(tId).getRollingStockNr(n).getSpecialRep();
                            break;
                        default: Terminal.printError("Object ist nicht nach Spezifikationen aufgebaut");
                    } // Engine:
                } else if (trainHashMap.get(tId).getRollingStockNr(n).getSpecial().getcType() == 'e') {
                    switch (trainHashMap.get(tId).getRollingStockNr(n).getSpecial().getrType()) {
                        case 'e':
                            addition8 = trainHashMap.get(tId).getRollingStockNr(n).getElectricalRep();
                            eightRows = true;
                            break;
                        case 's':
                            addition = trainHashMap.get(tId).getRollingStockNr(n).getSteamRep();
                            break;
                        case 'd':
                            addition = trainHashMap.get(tId).getRollingStockNr(n).getDieselRep();
                            break;
                        default: Terminal.printError("Object ist nicht nach Spezifikationen aufgebaut");
                    } // Train-Set:
                } else if (trainHashMap.get(tId).getRollingStockNr(n).getSpecial().getcType() == 's') {
                    eightRows = true;
                    addition8 = trainHashMap.get(tId).getRollingStockNr(n).getTrainsetRep();
                }
                if (lilCounter > 0) {
                    space = " ";
                }
                lilCounter++;
                if (rows == 8) {
                    if (!eightRows) {
                        for (int z = 2; z < 8; z++) {
                            addition8[z] = addition[z - 2];
                        }
                    }
                    for (int m = 0; m < added.length; m++) {
                        added[m] = added[m] + space + addition8[m];
                    }
                } else {
                    for (int m = 0; m < added.length; m++) {
                        added[m] = added[m] + space + addition[m];
                    }
                }
            }
            for (String s : added) {
                Terminal.printLine(s);
            }
        } else { Terminal.printError("Es ex. noch kein Zug mit dieser ID"); }
    }

    /**
     * HashMap die die Wägen speichert, Integer als key und Coach als Value
     *
     * @return HashMap die die Wägen speichert
     */
    public HashMap<Integer, Coach> getCoachHashMap() {
        return coachHashMap;
    }

    /**
     * HashMap die die Antriebszüge speichert, Integer als key und PowerTrain als Value
     *
     * @return HashMap die die Antriebszüge speichert
     */
    public HashMap<String, PowerTrain> getPowTrainHashMap() {
        return powTrainHashMap;
    }

    /**
     * HashMap die die Züge speichert, Integer als key und TrainBuild als Value
     *
     * @return HashMap die die Züge speichert
     */
    public HashMap<Integer, TrainBuild> getTrainHashMap() {
        return trainHashMap;
    }
}