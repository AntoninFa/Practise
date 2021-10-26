package edu.kit.informatik;

/**
 * enum der die Lokomotiven-Typen speichert
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

public enum  EngineType {
    /**
     * Bezeichner für den Lokomotiven-Typ Elektrolokomotive
     */
    ELECTRICAL ("electrical") {

    },
    /**
     * Bezeichner für den Lokomotiven-Typ Dampflokomotive
     */
    STEAM("steam") {

    },
    /**
     * Bezeichner für den Lokomotiven-Typ Diesellokomotive
     */
    DIESEL("diesel") {

    };

    private final String engineType;

    /**
     * Kontruktor des enums
     * @param engineType Lokomotiven-Typ
     */
    EngineType(final String engineType) {
        this.engineType = engineType;
    }

    /**
     * Getter für den Lokomotiven-Typ
     * @return Lokomotiven-Typ
     */
    public String getEngineType() {
        return this.engineType;
    }
}
