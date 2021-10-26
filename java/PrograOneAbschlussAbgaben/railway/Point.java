package edu.kit.informatik;

/**
 * Modelliert einen Punkt im 2-D Raum
 *
 * @author Antonin Fahning
 * @version 1.0
 */

public class Point {
    // X-Koordinate
    private final Integer x;
    // Y-Koordinate
    private final Integer y;

    /**
     * Erstellt einen neuen Punkt
     *
     * @param x X-Koordinate des Punktes
     * @param y Y-Koordinate des Punktes
     */
    public Point(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter für die X-Koordinate
     *
     * @return X-Koordinate
     */
    public int getX() {
        return this.x;
    }

    /**
     * Getter für die Y-Koordinate
     *
     * @return Y-Koordinate
     */
    public int getY() {
        return this.y;
    }

    /**
     * Vergleicht ob zwei Punkte die gleichen X und Y Kooardinaten haben
     *
     * @param point Punkt im 2D Raum
     * @return true wenn gleich
     */
    public boolean pointEquals(Point point) {
        return point.getY() == this.y && point.getX() == this.x;
    }

    /**
     * toString Methode für einen Punkt wie in Aufgabenstellung gefordert
     *
     * @return Stringrepräsentation
     */
    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }

    /**
     * Addiert zwei Vektoren(Punktschreibweise)
     *
     * @param vector Vektor
     * @return den resultierenden Vektor
     */
    public Point vectorAddition(Point vector) {
        return new Point((this.x + vector.getX()), (this.y + vector.getY()));
    }

    /**
     * Gibt den Punkt mit Positiven Koordinaten wieder
     *
     * @return Punkt mit Positiven Koordinaten
     */
    public Point pAbsoluteValue() {
        return new Point(Math.abs(this.x), Math.abs(this.y));
    }

    /**
     * Negiert die X und Y Koordinate
     *
     * @return negierten Punkt
     */
    public Point negatePoint() {
        return new Point(-this.x, -this.y);
    }

    /**
     * Normalisiert den Richtungsvektor der nach Aufgabenstellung immer gegeben ist
     *
     * @return neuen Punkt der ein normalisierter Richtungsvektor des Ursprünglichen Punktes ist
     */
    public Point toDir() {
        int newX = 0;
        int newY = 0;
        if (this.x < 0) {
            newX = -1;
        } else if (this.x > 0) {
            newX = 1;
        }
        if (this.y < 0) {
            newY = -1;
        } else if (this.y > 0) {
            newY = 1;
        }
        return new Point(newX, newY);
    }


}
