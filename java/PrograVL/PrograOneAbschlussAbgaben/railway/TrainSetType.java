package edu.kit.informatik;

/**
 * enum der den Triebzug-Typen speichert, existiert damit die modellierung des Rolling-Stocks einheitlich
 * aufgebaut ist
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

public enum  TrainSetType {


    /**
     * Bezeichner für den  Triebzugtyp
     */
    TRAINSET("train-set") {
    };

    private final String trainSetType;

    /**
     * Kontruktor des enums
     * @param trainSetType Triebzugtyp
     */
    TrainSetType(final String trainSetType) {
        this.trainSetType = trainSetType;
    }

    /**
     * Getter für den Triebzugtyp
     * @return Triebzugtyp
     */
    public String getEngineType() {
        return this.trainSetType;
    }
}

