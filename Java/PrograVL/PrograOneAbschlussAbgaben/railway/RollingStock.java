package edu.kit.informatik;

/*Copyright 1993,1998,2014 Toyoda Masashi (mtoyoda@acm.org)
        Everyone is permitted to do anything on this program including copying,
        modifying, and improving, unless you try to pretend that you wrote it
        i.e., the above copyright notice has to appear in all copies.
        THE AUTHOR DISCLAIMS ANY RESPONSIBILITY WITH REGARD TO THIS SOFTWARE.

        Modified by SDQ 2020 */

/**
 * Abstracte Klasse die gemeinsamme Methoden/ Werte des Rollmaterials beinhaltet
 *
 * @author Antonin Fahning [Die Grafische Repräsentation ist von Toyoda Masashi]
 * @version 1.0
 */
abstract class RollingStock {
    private final boolean couplingFront;
    private final boolean couplingBack;
    private int length;
    private final Specialization special;
    private final String series;
    private boolean addedToTrain = false;

    private final String[] passRep = new String[]{"____________________", "|  ___ ___ ___ ___ |"
            , "|  |_| |_| |_| |_| |", "|__________________|", "|__________________|", "   (O)        (O)   "};
    private final String[] freiRep = new String[]{"                      ", "|                  |  "
            , "|                  |  ", "|                  |  "
            , "|__________________|  ", "   (O)        (O)     "};
    private final String[] specialRep = new String[]{"                      ", "               ____   "
            , "/--------------|  |   ", "\\--------------|  |   ", "  | |          |  |   ", " _|_|__________|  |   "
            , "|_________________|   ", "   (O)       (O)      "};
    private final String[] steamRep = new String[]{"     ++      +------", "     ||      |+-+ | "
            , "   /---------|| | | ", "  + ========  +-+ | "
            , " _|--/~\\------/~\\-+ ", "//// \\_/      \\_/   "};
    private final String[] trainsetRep = new String[]{"         ++           ", "         ||           "
            , "_________||_________  ", "|  ___ ___ ___ ___ |  ", "|  |_| |_| |_| |_| |  ", "|__________________|  "
            , "|__________________|  ", "   (O)        (O)     "};
    private final String[] electricalRep = new String[]{"               ___    ", "                 \\    "
            , "  _______________/__  ", " /_| ____________ |_\\ ", "/   |____________|   \\", "\\                    /"
            , " \\__________________/ ", "  (O)(O)      (O)(O)  "};
    private final String[] dieselRep = new String[]{"                      ", "                      "
            , "  _____________|____  ", " /_| ____________ |_\\ ", "/   |____________|   \\"
            , "\\                    /", " \\__________________/ ", "  (O)(O)      (O)(O)  "};

    /**
     * Erstellt ein neues Rollmaterial
     *
     * @param couplingFront boolean, true wenn es vorne eine Kupplung gibt
     * @param couplingBack  boolean, true wenn es hinten eine Kupplung gibt
     * @param series        Baureihe
     * @param spec          Object das specifikationen wie die ID und Typen speichert
     */
    public RollingStock(boolean couplingFront, boolean couplingBack, String series, Specialization spec) {
        this.couplingFront = couplingFront;
        this.couplingBack = couplingBack;
        this.series = series;
        special = spec;
    }

    /**
     * Getter für die vordere Kupplung am Zug
     *
     * @return true Wenn vorhanden
     */
    public boolean getCouplingFront() {
        return this.couplingFront;
    }

    /**
     * Getter für die hintere Kupplung am Zug
     *
     * @return true Wenn vorhanden
     */
    public boolean getCouplingBack() {
        return this.couplingBack;
    }

    /**
     * Gibt zurück ob der Wagon in einem Zug verwendet wird
     *
     * @return true wenn Teil eines Zuges
     */
    public boolean isAddedToTrain() {
        return addedToTrain;
    }

    /**
     * Setzt ob sich das Rollmaterial an einem Zug befindet
     * @param addedToTrain true wenn sich das Rollmaterial an einem Zug befindet
     */
    public void setAddedToTrain(boolean addedToTrain) {
        this.addedToTrain = addedToTrain;
    }

    /**
     * Getter für die Länge des Rollmaterials
     * @return Länge des Rollmaterials
     */
    public int getLength() {
        return this.length;
    }

    /**
     * Getter für die Baureihe des Rollmaterials
     * @return Baureihe des Rollmaterials
     */
    public String getSeries() {
        return series;
    }

    /**
     * Getter für die Specifikationen
     * @return Specialization
     */
    public Specialization getSpecial() {
        return special;
    }


    /**
     * Getter für die Personenwagen Reprösentation
     * @return Personenwagen Reprösentation
     */
    public String[] getPassRep() {
        return passRep;
    }

    /**
     * Getter für die Güterwagen Reprösentation
     * @return Güterwagen Reprösentation
     */
    public String[] getFreiRep() {
        return freiRep;
    }

    /**
     * Getter für die Spezialwagen Reprösentation
     * @return Spezialwagen Reprösentation
     */
    public String[] getSpecialRep() {
        return specialRep;
    }



    /**
     * Getter für die Dampflokomotiven Reprösentation
     * @return Dampflokomotiven Reprösentation
     */
    public String[] getSteamRep() {
        return steamRep;
    }

    /**
     * Getter für die Triebzügen Reprösentation
     * @return Triebzügen Reprösentation
     */
    public String[] getTrainsetRep() {
        return trainsetRep;
    }

    /**
     * Getter für die Elektrolokomotiven Reprösentation
     * @return Elektrolokomotiven Reprösentation
     */
    public String[] getElectricalRep() {
        return electricalRep;
    }

    /**
     * Getter für die Diesellokomotiven Reprösentation
     * @return Diesellokomotiven Reprösentation
     */
    public String[] getDieselRep() {
        return dieselRep;
    }

    /**
     * Get den Lokomotiven-Typ zurück
     * @return Lokomotiven-Typ
     */
    abstract EngineType getEngineType();

    /**
     * Gibt den Train-Set-Typ zurück
     * @return Train-Set-Typ
     */
    abstract TrainSetType getTrainSetType();

    /**
     * Gibt den Wagen-Typ zurück
     * @return  Wagen-Typ
     */
    abstract CoachType getCoachType();


}
