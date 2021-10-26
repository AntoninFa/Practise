/*
 * Copyright (c) 2020, IPD Koziolek. All rights reserved.
 */

package edu.kit.informatik.boardgame.element.board;

import edu.kit.informatik.boardgame.exception.CellAccessException;
import edu.kit.informatik.boardgame.exception.SemanticsException;
import edu.kit.informatik.boardgame.element.Bag;
import edu.kit.informatik.boardgame.element.Cell;
import edu.kit.informatik.boardgame.element.Player;


/**
 * The torus version of a pitch has a slightly different indexing behavior.
 * 
 * @author Martin Löper
 * @version 1.0
 */
public class TorusBoard extends Board {

    /**
     * Creates a torus pitch with given initial values.
     * 
     * @param pRowNumber the number of rows
     * @param pColumnNumber the number of columns
     * @param pPlayers the players participating in the game
     * @param pBag the bag containing all accessible tokens for the players
     * @throws SemanticsException thrown if a semantic fault occurs during board creation
     */
    public TorusBoard(final int pRowNumber, final int pColumnNumber, 
            final Player[] pPlayers, final Bag pBag) throws SemanticsException {
        super(pRowNumber, pColumnNumber, pPlayers, pBag);
    }

    @Override
    public Cell getCell(final int pX, final int pY) {
        final int x = (pX >= 0) ? (pX % getHeight()) : (((pX % getHeight()) + getHeight()) % getHeight());
        final int y = (pY >= 0) ? (pY % getWidth()) : (((pY % getWidth()) + getWidth()) % getWidth());

        try {
            return super.getCell(x, y);
        } catch (CellAccessException e) {
            /* this will not happen because we use modulo */
            throw new IllegalStateException();
        }
    }

}
