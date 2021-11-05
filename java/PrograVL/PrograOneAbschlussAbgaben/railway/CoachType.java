package edu.kit.informatik;

/**
 * enum der die WagenTypen speichert
 *
 * @author Antonin Fahning [Die Grafische Repräsentation ist von Toyoda Masashi]
 * @version 1.0
 */

/*Copyright 1993,1998,2014 Toyoda Masashi (mtoyoda@acm.org)
        Everyone is permitted to do anything on this program including copying,
        modifying, and improving, unless you try to pretend that you wrote it
        i.e., the above copyright notice has to appear in all copies.
        THE AUTHOR DISCLAIMS ANY RESPONSIBILITY WITH REGARD TO THIS SOFTWARE.

        Modified by SDQ 2020 */

public enum CoachType {
    /**
     * Bezeichner für den Wagentyp Personenwagen
     */
    PASSENGER("passenger") {


    },
    /**
     * Bezeichner für den Wagentyp Güterwagen
     */
    FREIGHT("freight") {


    },
    /**
     * Bezeichner für den Wagentyp Spezialwagen
     */
    SPECIAL("special") {

    };

    private final String coachType;

    /**
     * Kontruktor des enums
     * @param coachType Wagentyp
     */
    CoachType(final String coachType) {
        this.coachType = coachType;
    }

    /**
     * Getter für den Wagentyp
     * @return Wagentyp
     */
    public String getCoachType() {
        return this.coachType;
    }


}
