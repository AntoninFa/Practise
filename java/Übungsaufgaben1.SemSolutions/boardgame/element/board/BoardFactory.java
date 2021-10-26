/*
 * Copyright (c) 2020, IPD Koziolek. All rights reserved.
 */

package edu.kit.informatik.boardgame.element.board;

import edu.kit.informatik.boardgame.exception.SemanticsException;
import edu.kit.informatik.boardgame.exception.SyntaxException;
import edu.kit.informatik.boardgame.element.Bag;
import edu.kit.informatik.boardgame.element.Player;

/**
 * This class represents a board factory for creating a {@link Board}.
 * 
 * @author Martin Löper
 * @author Jonathan Schenkenberger
 * @version 1.0
 */
public final class BoardFactory {
    /*
     * private Utility class constructor in order to avoid instatiation. 
     */
    private BoardFactory() {
    }

    /**
     * Creates a new board with the given parameters.
     * 
     * @param pitchType the pitch type, i.e. standard or torus
     * @param players the players
     * @param bag the bag of tokens
     * @param rows the number of rows
     * @param columns the number of columns
     * @return the board
     * @throws SemanticsException if a semantic exception occurs during board creation
     * @throws SyntaxException if an invalid pitch type is used
     */
    public static Board create(final String pitchType, final Player[] players, final Bag bag, final int rows,
            final int columns) throws SemanticsException, SyntaxException {
        switch (pitchType) {
            case Board.TYPE_STD:
                return new Board(rows, columns, players, bag);
            case Board.TYPE_TORUS:
                return new TorusBoard(rows, columns, players, bag);
            default:
                throw new SyntaxException("the type of pitch is invalid.");
        }
    }
}
