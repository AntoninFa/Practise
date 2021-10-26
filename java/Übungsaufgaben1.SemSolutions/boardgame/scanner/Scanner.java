/*
 * Copyright (c) 2020, IPD Koziolek. All rights reserved.
 */

package edu.kit.informatik.boardgame.scanner;

import edu.kit.informatik.boardgame.element.board.Board;

/**
 * This class represents an interface for possible implementations of Scanners.
 * A Scanner basically consists of a method {@link #getWinner()}, which traverses the board and returns the winner.
 * The internal functionality (e.g. traversal algorithms) is determined by this method.
 * 
 * @author Martin Löper
 * @version 1.0
 */
public abstract class Scanner {
    private final Board board;
    
    /**
     * Creates an new scanner for the given board.
     * @param board the board which will be scanned
     */
    public Scanner(final Board board) {
        this.board = board;
    }
    
    /**
     * Returns whether there is a winning constellation.
     * @return if there are four token's with the same property connected 
     * (either horizontally, vertically or diagonally)
     */
    public abstract boolean isWinningConstellation();
    
    /**
     * Returns the board on which this scanner operates.
     * @return the board
     */
    public Board getBoard() {
        return this.board;
    }
}
