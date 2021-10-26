/*
 * Copyright (c) 2020, IPD Koziolek. All rights reserved.
 */

package edu.kit.informatik.boardgame.scanner;

import edu.kit.informatik.boardgame.element.board.Board;
import edu.kit.informatik.boardgame.exception.CellAccessException;
import edu.kit.informatik.boardgame.element.Cell;
import edu.kit.informatik.boardgame.element.TokenPropertyType;

/**
 * This class represents an implementation of scanner, which traverses the board
 * iteratively. It iterates through each cell and meanwhile checks every
 * direction (vertical, horizontal, diagUp, diagDown). This is one of the
 * slowest possible implementations, but definitely semantically correct and
 * easy to understand.
 * 
 * @author Martin Löper
 * @version 1.0
 */
public class IterativeScanner extends Scanner {

    /**
     * Creates an IterativeScanner for the given board.
     * 
     * @param board the board which will be scanned.
     */
    public IterativeScanner(final Board board) {
        super(board);
    }

    @Override
    public boolean isWinningConstellation() {
        for (int i = 0; i < getBoard().getHeight(); i++) {
            for (int j = 0; j < getBoard().getWidth(); j++) {
                if (checkForConnectedFour(getRight(i, j)) 
                        || checkForConnectedFour(getBottom(i, j))
                        || checkForConnectedFour(getDiagonalDown(i, j)) 
                        || checkForConnectedFour(getDiagonalUp(i, j))) {
                    return true;
                }
            }
        }

        /* the game is not over */
        return false;
    }

    private boolean checkForConnectedFour(final Cell[] cells) {
        // check for each property
        outer: 
            for (TokenPropertyType cType : TokenPropertyType.values()) {
            Boolean expectedPropertyValue = null;
            for (int i = 0; i < cells.length; i++) {
                if (cells[i] == null) {
                    return false;
                }

                if (!cells[i].hasToken() || (expectedPropertyValue != null
                        && !cells[i].getToken().getProperties().get(cType).getValue().equals(expectedPropertyValue))) {
                    continue outer;
                }
                expectedPropertyValue = cells[i].getToken().getProperties().get(cType).getValue();
            }

            return true;
        }

        return false;
    }

    // alias bottom right!!!
    private Cell[] getDiagonalDown(final int x, final int y) {
        final Cell[] cells = new Cell[4];
        for (int i = 0; i < 4; i++) {
            try {
                cells[i] = getBoard().getCell(x + i, y + i);
            } catch (CellAccessException e) {
                cells[i] = null;
            }
        }

        return cells;
    }

    private Cell[] getDiagonalUp(final int x, final int y) {
        final Cell[] cells = new Cell[4];
        for (int i = 0; i < 4; i++) {
            try {
                cells[i] = getBoard().getCell(x - i, y + i);
            } catch (CellAccessException e) {
                cells[i] = null;
            }
        }

        return cells;
    }

    private Cell[] getBottom(final int x, final int y) {
        final Cell[] cells = new Cell[4];
        for (int i = 0; i < 4; i++) {
            try {
                cells[i] = getBoard().getCell(x + i, y);
            } catch (CellAccessException e) {
                cells[i] = null;
            }
        }

        return cells;
    }

    private Cell[] getRight(final int x, final int y) {
        final Cell[] cells = new Cell[4];
        for (int i = 0; i < 4; i++) {
            try {
                cells[i] = getBoard().getCell(x, y + i);
            } catch (CellAccessException e) {
                cells[i] = null;
            }
        }

        return cells;
    }
}
