/*
 * Copyright (c) 2020, IPD Koziolek. All rights reserved.
 */

package edu.kit.informatik.boardgame.element.board;

import edu.kit.informatik.boardgame.exception.CellAccessException;
import edu.kit.informatik.boardgame.exception.SemanticsException;
import edu.kit.informatik.boardgame.exception.SyntaxException;
import edu.kit.informatik.boardgame.scanner.IterativeScanner;
import edu.kit.informatik.boardgame.scanner.Scanner;
import edu.kit.informatik.boardgame.element.Bag;
import edu.kit.informatik.boardgame.element.Cell;
import edu.kit.informatik.boardgame.element.Player;
import edu.kit.informatik.boardgame.element.Token;

/**
 * This class represents the pitch of the game. The pitch consists of a
 * matrix-style two-dimensional array, which contains all the cells. Each row of
 * the pitch has the same amount of cells. Its behaviour is the standard one.
 * 
 * @author Martin Löper
 * @author Jonathan Schenkenberger
 * @version 1.0
 */
public class Board {
    
    /**
     * The board height.
     */
    public static final int BOARD_HEIGHT = 6;
    
    /**
     * The board width.
     */
    public static final int BOARD_WIDTH = 6;
    
    /**
     * The identifier for the special type of pitch "standard".
     */
    public static final String TYPE_STD = "standard";

    /**
     * The identifier for the special type of pitch "torus".
     */
    public static final String TYPE_TORUS = "torus";

    /**
     * The cells representing the pitch. They are arranged in a matrix-style using a
     * two-dimensional array. The first index is the row number, the second one the
     * column number.
     */
    private final Cell[][] mCells;

    /**
     * The number of columns of this pitch.
     */
    private final int mWidth;

    /**
     * The number of rows of this pitch.
     */
    private final int mHeight;

    private Player[] mPlayers;
    private int mCurrentActivePlayerIndex = 0;
    private boolean mFinished = false;
    private final Scanner mScanner;
    private final Bag mBag;
    private Token mCurrentSelectedToken;
    private Player mWinner;
    private int mMoveNumber = 0;

    /**
     * Creates a new empty pitch with the given number of columns and rows.
     * 
     * @param pRowNumber the number of rows to be created
     * @param pColumnNumber the number of columns to be created
     * @param pPlayers the players participating in the game
     * @param pBag the bag containing all accessible tokens for the players
     * @throws SemanticsException throw if the pitch to be created is semantically incorrect
     */
    public Board(final int pRowNumber, final int pColumnNumber, final Player[] pPlayers, final Bag pBag)
            throws SemanticsException {

        /* check if the pitch has at least 1 row and 1 column */
        if (pRowNumber <= 0 || pColumnNumber <= 0) {
            throw new SemanticsException(
                    "a pitch with size less or equal to 0 is actually not existent. " + "Please provide some cells.");
        }

        this.mWidth = pColumnNumber;
        this.mHeight = pRowNumber;
        this.mCells = new Cell[pRowNumber][pColumnNumber];
        this.mPlayers = pPlayers;
        this.mScanner = new IterativeScanner(this); // use the most simple scanning behaviour by default
        this.mBag = pBag;

        /* fill board with empty cells */
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                this.mCells[i][j] = new Cell(i, j);
            }
        }
    }

    /**
     * Returns the player who is currently active.
     * 
     * @return the active player
     */
    public Player getCurrentActivePlayer() {
        return this.mPlayers[this.mCurrentActivePlayerIndex];
    }

    /**
     * Changes the current active player. This method uses a circular modulo
     * algorithm, so that the players are active in ascending order of id.
     */
    public void changeCurrentActivePlayer() {
        this.mCurrentActivePlayerIndex = (this.mCurrentActivePlayerIndex + 1) % this.mPlayers.length;
    }

    /**
     * Returns the pitch's width.
     * 
     * @return the width (number of cells)
     */
    public int getWidth() {
        return this.mWidth;
    }

    /**
     * Returns the pitch's height.
     * 
     * @return the height (number of cells)
     */
    public int getHeight() {
        return this.mHeight;
    }

    /**
     * Adds a new cell to the pitch. Note that the space for this cell has to be
     * reserved during the pitch creation. Caution: A cell can only be added once,
     * otherwise an error message is shown.
     * 
     * @param pCell the cell to be added (null not allowed)
     * @throws SemanticsException if cell already exists
     */
    public void addCell(final Cell pCell) throws SemanticsException {
        /* check if enough space is reserved for the cell */
        if (pCell.getPositionX() < 0 || pCell.getPositionY() < 0 || this.mCells.length <= pCell.getPositionX()
                || this.mCells[pCell.getPositionX()].length <= pCell.getPositionY()) {

            throw new CellAccessException(getWidth(), getHeight(), pCell.getPositionX(), pCell.getPositionY());
        }

        /* check if cell is already existent */
        if (this.mCells[pCell.getPositionX()][pCell.getPositionY()] != null) {
            throw new SemanticsException("cannot overwrite the existing cell on index " + "[" + pCell.getPositionX()
                    + "][" + pCell.getPositionY() + "].");
        }

        /* add the cell */
        this.mCells[pCell.getPositionX()][pCell.getPositionY()] = pCell;
    }

    /**
     * Returns the cell which is on position with row index {@code pX} and column
     * index {@code pY}.
     * 
     * @param pX the row index
     * @param pY the column index
     * @return the cell at given coordinates
     * @throws CellAccessException thrown if the cell cannot be found
     */
    public Cell getCell(final int pX, final int pY) throws CellAccessException {
        if (pX < 0 || pY < 0 || this.mCells.length <= pX || this.mCells[pX].length <= pY) {
            throw new CellAccessException(getWidth(), getHeight(), pX, pY);
        }

        return this.mCells[pX][pY];
    }

    /**
     * This is basically a helper method which does the same like
     * {@link #getCell(int, int)}. The difference is the data type of both
     * coordinates. This method first checks if {@code pX} and {@code pY} are valid
     * Integer values.
     * 
     * @param pX the row index (null not allowed)
     * @param pY the column index (null not allowed)
     * @return the cell at given coordinates
     * @throws CellAccessException thrown if the cell cannot be found
     * @throws SyntaxException thrown if the given coordinates are not valid integers
     * @see #getCell(int, int)
     */
    public Cell getCellByStringCoordinates(final String pX, final String pY)
            throws CellAccessException, SyntaxException {
        try {
            final int x = Integer.valueOf(pX);
            final int y = Integer.valueOf(pY);
            return this.getCell(x, y);
        } catch (NumberFormatException e) {
            throw new SyntaxException(
                    "both cell coordinates have to be valid positive integers " + "(without spaces in between).");
        }
    }

    /**
     * Returns the boards's String representation.
     * 
     * @return each cell's string representation, whereas columns are not separated
     *         and rows are separated by "\n"
     */
    @Override
    public String toString() {
        String out = ""; // buffer for the output

        /* iterate through all rows */
        for (int i = 0; i < this.mCells.length; i++) {
            String line = ""; // buffer for the line content

            /* iterate through all cells inside the current row */
            for (int j = 0; j < this.mCells[i].length; j++) {

                /* add the current cell's String representation to the line buffer */
                line += this.mCells[i][j].toString();
            }

            /* place a new line character before each line except of the first one */
            out += ((i > 0) ? System.lineSeparator() : "") + line;
        }

        return out;
    }

    /**
     * Selects the token with the given index.
     * 
     * @param pIndex the given index
     * @throws SemanticsException user already selected a token
     */
    public void select(final int pIndex) throws SemanticsException {
        if (this.mCurrentSelectedToken != null) {
            throw new SemanticsException("the user has to place the selected token before selecting another one.");
        }

        this.mCurrentSelectedToken = this.mBag.getToken(pIndex);
        this.mBag.removeToken(pIndex);
        changeCurrentActivePlayer();
    }

    /**
     * 
     * @return gets the bag content as a {@code String}.
     */
    public String getBagContent() {
        return this.mBag.toString();
    }

    /**
     * Gets the row print string.
     * 
     * @param index the row index
     * @return the row print string
     * @throws SemanticsException if index is out of bounds
     */
    public String rowprint(final int index) throws SemanticsException {
        if (index >= getHeight() || index < 0) {
            throw new SemanticsException(
                    "the row index has to be between 0 and " + (getHeight() - 1) + " (both inclusive).");
        }

        String out = "";

        for (int i = 0; i < getWidth(); i++) {
            if (!out.isEmpty()) {
                out += " ";
            }

            try {
                out += getCell(index, i);
            } catch (CellAccessException e) {
                // will not happen, because we iterate over valid bounds
                assert false;
            }
        }

        return out;
    }

    /**
     * 
     * @return whether game is over
     */
    public boolean isGameOver() {
        return this.mFinished;
    }

    /**
     * Gets the column print string.
     * 
     * @param index the column index
     * @return the column print string
     * @throws SemanticsException if index is out of bounds
     */
    public String colprint(final int index) throws SemanticsException {
        if (index >= getWidth() || index < 0) {
            throw new SemanticsException(
                    "the column index has to be between 0 and " + (getWidth() - 1) + " (both inclusive).");
        }

        String out = "";

        for (int i = 0; i < getHeight(); i++) {
            if (!out.isEmpty()) {
                out += " ";
            }

            try {
                out += getCell(i, index);
            } catch (CellAccessException e) {
                // will not happen, because we iterate over valid bounds
                assert false;
            }
        }

        return out;
    }

    /**
     * Places the selected token on the given coordinates.
     * 
     * @param x the row (x) coordinate
     * @param y the column (y) coordinate
     * @throws SemanticsException if no token is selected
     */
    public void place(final int x, final int y) throws SemanticsException {
        if (this.mCurrentSelectedToken == null) {
            throw new SemanticsException("you need to select a token first.");
        }

        final Cell cCell = getCell(x, y);
        try {
            cCell.placeToken(this.mCurrentSelectedToken);
        } catch (SemanticsException e) {
            this.mBag.addToken(this.mCurrentSelectedToken);
            this.mCurrentSelectedToken = null;
            throw e;
        }
        this.mCurrentSelectedToken = null;

        /* check if game is over because current player wins */
        if (this.mScanner.isWinningConstellation()) {
            this.mFinished = true;
            this.mWinner = this.getCurrentActivePlayer();
        } else if (isBagEmpty()) { // or if bag is empty --> game is finished without winner
            this.mFinished = true;
        }

        this.mMoveNumber++;
    }

    /**
     * 
     * @return the current move number
     */
    public int getCurrentMoveNumber() {
        return this.mMoveNumber;
    }

    /**
     * 
     * @return whether bag of tokens is empty
     */
    public boolean isBagEmpty() {
        return this.mBag.isEmpty();
    }

    /**
     * 
     * @return the winner or null if no player has yet won or the game is draw
     */
    public Player getWinner() {
        return this.mWinner;
    }
}