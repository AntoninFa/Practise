package edu.kit.informatik;

import java.util.Comparator;

/**
 * Klasse implemementiert Comparator und
 * vergleicht die Strings s1 und s2 lexikographisch
 *
 * @author Antonin Fahning
 * @version 1.0
 */
public class IDStringComperator implements Comparator<String> {
    /**
     * Vergleicht die Strings s1 und s2 lexikographisch
     * @param s1 String 1
     * @param s2 String 2
     * @return int < 0 wenn s1 kleiner als s2, also lexgrhisch "vor" s2 stehen soll
     */
    @Override
    public int compare(String s1, String s2) {
        return s1.compareToIgnoreCase(s2);
    }
}
