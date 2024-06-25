package edu.kit.informatik;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Diese Klasse ist für die Speicherung der vom Spieler gebauten Gegenstände zuständig
 *
 * @author Antonin Fahning
 * @version 1.0
 */
class GameBuildObject {
    private TreeMap<Integer, Objects> buildObjects = new TreeMap<>();
    private int bObjKeyCounter = 0;

    /**
     * Fügt der Hahmap buildObjects einen Gegenstand hinzu
     *
     * @param object Gegenstand
     */
    void addObj(final Objects object) {
        buildObjects.put(bObjKeyCounter, object);
        bObjKeyCounter++;
    }

    /**
     * Gibt den value von buildObjects an Index n aus
     *
     * @param n Index
     * @return Gegenstand (value von buildObjects an Index n)
     */
    Objects getObjAt(final int n) {
        return buildObjects.get(n);
    }

    /**
     * Gibt die größe der Hashmap buildObjects zurück
     *
     * @return größe der Hashmap buildObjects
     */
    int getBObjSize() {
        return buildObjects.size();
    }

    /**
     * Gibt aus ob die Hashmap buildObjects leer ist
     *
     * @return true wenn leer
     */
    boolean bObjIsEmpty() {
        return buildObjects.isEmpty();
    }

    /**
     * Gibt zurück ob die Hashmap buildObjects den Gegenstand enthält
     *
     * @param object Gegenstand
     * @return true wenn buildObjects den Gegenstand enthält
     */
    boolean bObjContainsV(final Objects object) {
        return buildObjects.containsValue(object);
    }

    /**
     * Nach Spielvorgaben bekommt der Spieler im Kampf (wprfeln) gegen ein Tier, einen Punkte-Bonus von 1 bei
     * einer Keule und einen Bonus von 2 bei einer Axt. D.h. der gewürfelten Augenzahl wird entsprechend
     * 1 oder zu hinzugefügt
     *
     * @return bonus als byte
     */
    byte getFightBonus() {
        byte bonus = 0;
        if (buildObjects.containsValue(Objects.AXE)) {
            bonus = 2;
        } else if (buildObjects.containsValue(Objects.CLUB)) {
            bonus = 1;
        }
        return bonus;
    }

    /**
     * resetet die Hashmap buildObjects und setzt ihren keyCounter wieder auf 0
     */
    void clearBObj() {
        buildObjects.clear();
        bObjKeyCounter = 0;
    }

    /**
     * Gibt zurück ob der Spieler im Besitz einer Feuerstelle
     * @return true wenn Feuerstelle vorhanden
     */
    protected boolean hasFireplace() {
        return buildObjects.containsValue(Objects.FIREPLACE);
    }

    /**
     * Hilfsmethode die dafür sorgt, dass die TreeMap lückenlos mit Aufsteigenden Integerwerten als keys
     * durchnummeriert ist
     *
     * @param treeMap TreeMap die korrigiert werden soll
     * @return korrigierte TreeMap
     */
    private TreeMap<Integer, Objects> rearangeMapkeys(final TreeMap<Integer, Objects> treeMap) {
        int keyCounter = 0;
        final TreeMap<Integer, Objects> helper = new TreeMap<>();
        final Set<Map.Entry<Integer, Objects>> entries = treeMap.entrySet();
        for (final Map.Entry<Integer, Objects> entry : entries) {
            helper.put(keyCounter, entry.getValue());
            keyCounter++;
        }
        bObjKeyCounter = helper.size();
        return helper;
    }

    /**
     * Entfernt den übergebenen Gegenstand
     *
     * @param object übergebener Gegenstand
     */
    void removeValue(final Objects object) {
        if (buildObjects.containsValue(object)) {
            for (int n = 0; n < buildObjects.size(); n++) {
                if (buildObjects.get(n).equals(object)) {
                    buildObjects.remove(n);
                }
            }
            buildObjects = rearangeMapkeys(buildObjects);
        }
    }


}
