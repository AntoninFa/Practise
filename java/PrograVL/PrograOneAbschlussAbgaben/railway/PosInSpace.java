package edu.kit.informatik;

/**
 * Klasse welche, die Position einer Schiene im Raum
 * modelliert und an zwei Subklassen PosOfTrackInSpace und
 * PosOfSwitchInSpace vererbt
 * @author Antonin Fahning
 * @version 1.0
 */

public class PosInSpace {

    private final Point startingPoint;
    private final Point endOnePoint;
    private final Point endTwoPoint;


    /**
     * Erstellt eine neue Position im Raum
     *
     * @param start
     *            Punkt im Raum (Startpunkt eines Gleises)
     * @param endOne
     *            Punkt im Raum (Endpunkt eines Gleises)
     * @param endTwo
     *            Punkt im Raum (Endpunkt eines Gleises)
     */
    protected PosInSpace(Point start, Point endOne, Point endTwo) {
        this.startingPoint = new Point(start.getX(), start.getY());
        this.endOnePoint = new Point(endOne.getX(), endOne.getY());
        if (endTwo != null) {
            this.endTwoPoint = new Point(endTwo.getX(), endTwo.getY());
        } else {
            this.endTwoPoint = null;
        }
    }

    /**
     * @return Getter für den Start-Punkt
     */
    public Point getStartingPoint() { return this.startingPoint; }

    /**
     * @return Getter für den EndOne-Punkt
     */
    public Point getEndOnePoint() { return this.endOnePoint; }

    /**
     * @return Getter für den EndTwo-Punkt
     */
    public Point getEndTwoPoint() { return this.endTwoPoint; }

}
