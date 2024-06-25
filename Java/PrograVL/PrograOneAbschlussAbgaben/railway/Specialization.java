package edu.kit.informatik;

/**
 * Klasse die ähnlich wie ein "Fahrzeugschein" die genauen Spezifikationen speichert
 *
 * @author Antonin Fahning
 * @version 1.0
 */

public class Specialization {
    private final String iD;
    private final char cType;
    private final char rType;
    private int trainID = -1;

    /**
     * Konstruktor für Specialization
     *
     * @param iD    id des Rollmaterials
     * @param cType Char der mit jeweils c,e,s für die drei Typen Coach, Engine und Train-Set steht
     * @param rType Steht für die unter-Typen des Rollmaterials bennung ist selbsterklärend aus Anfangsbuchstabe
     *              z.B. f für freight oder p für passenger
     */
    public Specialization(String iD, char cType, char rType) {
        this.iD = iD;
        this.cType = cType;
        this.rType = rType;
    }

    /**
     * Getter für die ID des Zuges
     * @return ID des Zuges
     */
    public String getiD() {
        return iD;
    }

    /**
     * Getter für den RS Typ als Char
     * @return RS-Typ als Char
     */
    public char getcType() {
        return cType;
    }

    /**
     * Getter für den RS-unter-Typ als Char
     * @return RS Typ als Char
     */
    public char getrType() {
        return rType;
    }

    /**
     * Getter für die ID des Zuges zu dem der RollingStock gehört
     *
     * @return int trainID
     */
    public int getTrainID() {
        return trainID;
    }

    /**
     * Setter für die ID des Zuges zu dem der RollingStock gehört
     *
     * @param trainID ID des Zuges zu dem der RollingStock gehört
     */
    public void setTrainID(int trainID) {
        this.trainID = trainID;
    }
}
