/*
 * Copyright (c) 2020, IPD Koziolek. All rights reserved.
 */

package edu.kit.informatik.boardgame.exception;

/**
 * This exception is used to indicate an access fault on a non-existent cell or a cell creation fault.
 * 
 * @author Martin Löper
 * @version 1.0
 *
 */
public class CellAccessException extends SemanticsException {
    
    /**
     * Auto generated UID for serializing. Actually not needed according to the task, but absence produces annoying
     * warnings.
     */
    private static final long serialVersionUID = 2902383905608249312L;
    
    /**
     * Creates a new CellAccessException with the given detailed message.
     * @param width the width of the board
     * @param height the height of the board
     * @param x the x-coordinate which references the non-existent cell
     * @param y the y-coordinate which references the non-existent cell
     */
    public CellAccessException(final int width, final int height, final int x, final int y) {
        super("the cell with coords (" + x + ", " + y + ") does not exist on a " + height + "x" + width + " board.");
    }
}
