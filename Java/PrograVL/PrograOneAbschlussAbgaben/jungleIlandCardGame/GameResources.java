package edu.kit.informatik;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Diese Klasse ist für die Speicherung der Ressourcen zuständig
 *
 * @author Antonin Fahning
 * @version 1.0
 */
class GameResources {
    private int resKeyCounter = 0;
    private TreeMap<Integer, Cards> resources = new TreeMap<>();

    /**
     * Fügt der Hahmap resources eine Karte also Ressource hinzu
     * @param cards Ressource die hinzugefügt werden soll
     */
    void addResc(final Cards cards) {
        resources.put(resKeyCounter, cards);
        resKeyCounter++;
    }

    /**
     * Gibt den value von resources an Index n aus
     * @param n Index
     * @return Cards (value von resources an Index n)
     */
    Cards getRescAt(final int n) {
        return resources.get(n);
    }

    /**
     * Gibt aus ob die Hashmap resources leer ist
     * @return true wenn leer
     */
    boolean resIsEmpty() {
        return resources.isEmpty();
    }

    /**
     * Gibt die größe der Hashmap resources zurück
     * @return größe der Hashmap resources
     */
    int getResSize() {
        return resources.size();
    }

    /**
     * Diese Methode überprüft ob sich die benätigten Ressourcen in Spielerbesitz befinden
     *
     * @param neededRessc die benätigten Ressourcen
     * @return true wenn bauen möglich
     */
    boolean onlyCheckForRessc(final Cards[] neededRessc) {
        int foundResscCounter = 0;
        TreeMap<Integer, Cards> resourcesClone = new TreeMap<>(resources);
        for (final Cards neededCard : neededRessc) {
            // Überprüfen ob Ressc vorhanden
            for (int n = resourcesClone.size() - 1; n >= 0; n--) {
                if (resourcesClone.get(n).equals(neededCard)) {
                    resourcesClone.remove(n);
                    resourcesClone = rearangeMapkeys(resourcesClone, true);
                    foundResscCounter++;
                    break;
                }
            }
        }
        return foundResscCounter == neededRessc.length;
    }


    /**
     * Diese Methode überprüft mit onlyCheckForRessc ob der Gegenstand gebaut werden kann und entfernt die Ressourcen,
     * falls bauen möglich ist [Ressc wird als Abkürzung für Ressourcen verwendet]
     *
     * @param neededRessc die benätigten Ressourcen
     * @return true wenn Ressourcen vorhanden
     */
    boolean checkForResscAndRemove(final Cards[] neededRessc) {
        if (onlyCheckForRessc(neededRessc)) {
            // entfernen der Ressourcen
            for (final Cards neededCard : neededRessc) {
                for (int m = resources.size() - 1; m >= 0; m--) {
                    if (resources.get(m).equals(neededCard)) {
                        resources.remove(m);
                        resources = rearangeMapkeys(resources, false);
                        break;
                    }
                }
            }
            return true;
        } else { return false; }
    }


    /**
     * Hilfsmethode die dafür sorgt, dass die TreeMap lückenlos mit Aufsteigenden Integerwerten als keys
     * durchnummeriert ist
     *
     * @param treeMap TreeMap die korrigiert werden soll
     * @param clone   Wenn die Kopie von resources verändert wird muss der keyCounter nicht 0 werden-> true
     * @return korrigierte TreeMap
     */
    private TreeMap<Integer, Cards> rearangeMapkeys(final TreeMap<Integer, Cards> treeMap, final boolean clone) {
        int keyCounter = 0;
        final TreeMap<Integer, Cards> helper = new TreeMap<>();
        final Set<Map.Entry<Integer, Cards>> entries = treeMap.entrySet();
        for (final Map.Entry<Integer, Cards> entry : entries) {
            helper.put(keyCounter, entry.getValue());
            keyCounter++;
        }
        if (!clone) {
            resKeyCounter = helper.size();
        }
        return helper;
    }

    /**
     * Löscht alle Ressourcen die nicht in der Hütte gespeichert sind
     */
    void delExcShack() {
        for (int n = resources.size() - 6; n >= 0; n--) {
            resources.remove(n);
        }
        resources = rearangeMapkeys(resources, false);
    }

    /**
     * resetet die Hashmap resources und setzt ihren keyCounter wieder auf 0
     */
    void clearResc() {
        resources.clear();
        resKeyCounter = 0;
    }
}
