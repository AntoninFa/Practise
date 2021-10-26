/*
 * Copyright (c) 2020, IPD Koziolek. All rights reserved.
 */

package edu.kit.informatik.boardgame.element;

import edu.kit.informatik.boardgame.exception.SemanticsException;

/**
 * This class represents a single cell which is part of a board and thus possesses x and y coordinates.
 * @author Martin Löper
 * @version 1.0
 */
public class Cell {
    
    /**
     * The row index of the cell (beginning with zero).
     */
    private final int mPosX;
    
    /**
     * The column index of the cell (beginning with zero).
     */
    private final int mPosY;
    
    private Token mToken = null;

    
    /**
     * Creates a new cell with given coordinates.
     * @param pPosX the row index of the cell
     * @param pPosY the column index of the cell
     */
    public Cell(final int pPosX, final int pPosY) {
        this.mPosX = pPosX;
        this.mPosY = pPosY;
    }

    /**
     * Returns the row index of the cell
     * @return the row index
     */
    public int getPositionX() {
        return this.mPosX;
    }
    
    /**
     * Return the column index of the cell
     * @return the column index
     */
    public int getPositionY() {
        return this.mPosY;
    }
    
    /**
     * Returns the cell's String representation.
     * @return the token's string representation if token available, "#" if cell is empty
     */
    @Override
    public String toString() {
        if (this.hasToken()) {
            return this.mToken.toString();
        }
        else {
            return "#";
        }
    }
    
    /**
     * Places the given token
     * @param pToken the given token
     * @throws SemanticsException if cell already holds a token
     */
    public void placeToken(final Token pToken) throws SemanticsException {
        if (hasToken()) {
            throw new SemanticsException("cannot place token on a cell which already holds token " + mToken + ".");
        }
        else {
            this.mToken = pToken;
        }
    }
    
    /**
     * 
     * @return the token on this cell
     */
    public Token getToken() {
        return mToken;
    }

    /**
     * 
     * @return whether this cell holds a token
     */
    public boolean hasToken() {
        return mToken != null;
    }
}
