/*
 * Copyright (c) 2020, IPD Koziolek. All rights reserved.
 */

package edu.kit.informatik.boardgame;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.boardgame.element.Bag;
import edu.kit.informatik.boardgame.element.Player;
import edu.kit.informatik.boardgame.element.board.Board;
import edu.kit.informatik.boardgame.element.board.BoardFactory;
import edu.kit.informatik.boardgame.exception.SemanticsException;
import edu.kit.informatik.boardgame.exception.SyntaxException;

/**
 * This class handles the user's commands from command-line.
 * 
 * @author Martin Löper
 * @author Jonathan Schenkenberger
 * @version 1.0
 */
public final class CommandLine {

    /*
     * The quit command. It terminates the program without producing output. It does
     * not accept any arguments. No output is created unless an exception occurs.
     */
    private static final String QUIT = "quit";
    
    /*
     * The other commands. See assignment sheet for more information about the commands.
     */
    private static final String START = "start";
    private static final String SELECT = "select";
    private static final String PLACE = "place";
    private static final String BAG = "bag";
    private static final String ROWPRINT = "rowprint";
    private static final String COLPRINT = "colprint";
    
    private static final String OK = "OK";

    private static boolean isQuit = false;
    private static boolean isStarted = false;

    /**
     * Private constructor to avoid object generation.
     */
    private CommandLine() {
    }

    /**
     * Helper method which checks the number of required arguments against the
     * number of given one.
     * 
     * @param given
     *            the number of arguments actually provided
     * @param required
     *            the number of required arguments
     * @throws SyntaxException
     */
    private static void checkParameterNumber(final int given, final int required) throws SyntaxException {
        if (given != required && required == 2) {
            throw new SyntaxException("this command requires exactly 1 parameter to work, but you provided no one.");
        } else if (given != required && required == 1) {
            throw new SyntaxException("command does not accept any additional parameter, but you provided some.");
        }
    }

    /**
     * Reads commands from the command-line and executes them if they are valid.
     * This method runs infinitely until the quit command is called
     * 
     * @param pPlayers the players of the game
     * @param pBag the bag of tokens for the game
     */
    public static void startInteractiveSequence(final Player[] pPlayers, final Bag pBag) {
        // the board is initially null and gets initialized by the start command
        Board board = null;  
        while (!isQuit) {                                   // repeat until isQuit is set to true
            final String command = Terminal.readLine();     // read the command
            if (command == null) {                          // terminate program on end of stream
                quit();
                continue;
            }
            final String[] parts = command.split("\\s", 2); // split the command by first whitespace occurrence
                                       
            try {
                /*
                 * try to match with a valid command name and delegate to the corresponding
                 * method
                 */
                switch (parts[0]) {
                    case CommandLine.START:
                        checkParameterNumber(parts.length, 2);
                        final String pitchType = parts[1];
                        final int height = Board.BOARD_HEIGHT;
                        final int width = Board.BOARD_WIDTH;
                        board = BoardFactory.create(pitchType, pPlayers, pBag, height, width);
                        isStarted = true;
                        Terminal.printLine(OK);
                        break;
                    case CommandLine.SELECT:
                        checkParameterNumber(parts.length, 2);
                        CommandLine.select(board, parts[1]);
                        break;
                    case CommandLine.PLACE:
                        checkParameterNumber(parts.length, 2);
                        CommandLine.place(board, parts[1]);
                        break;
                    case CommandLine.BAG:
                        checkParameterNumber(parts.length, 1);
                        CommandLine.bag(board);
                        break;
                    case CommandLine.ROWPRINT:
                        checkParameterNumber(parts.length, 2);
                        CommandLine.rowprint(board, parts[1]);
                        break;
                    case CommandLine.COLPRINT:
                        checkParameterNumber(parts.length, 2);
                        CommandLine.colprint(board, parts[1]);
                        break;
                    case CommandLine.QUIT:
                        checkParameterNumber(parts.length, 1);
                        CommandLine.quit();
                        break;
                    default:
                        /* check if only enter was pressed */
                        if (command.trim().length() == 0) {
                            Terminal.printError("please enter a command.");
                        } else {
                            /* print error stating the requested command was not found */
                            Terminal.printError("command \"" + parts[0] + "\" not found. "
                                + "Only the following are valid: " + QUIT + ", " + SELECT + ", " + PLACE + ", " + BAG
                                + ", " + ROWPRINT + ", " + COLPRINT + ".");
                        }
                }
            } catch (SyntaxException | SemanticsException e) {
                Terminal.printError(e.getMessage());
            }
        }
    }

    private static void colprint(final Board pBoard, final String param) throws SemanticsException, SyntaxException {
        throwIfGameNotStarted();
        try {
            final int index = Integer.parseInt(param);
            Terminal.printLine(pBoard.colprint(index));
        } catch (NumberFormatException e) {
            throw new SyntaxException("the column's index has to be a valid Integer.");
        }
    }

    private static void rowprint(final Board pBoard, final String param) throws SemanticsException, SyntaxException {
        throwIfGameNotStarted();
        try {
            final int index = Integer.parseInt(param);
            Terminal.printLine(pBoard.rowprint(index));
        } catch (NumberFormatException e) {
            throw new SyntaxException("the row's index has to be a valid Integer.");
        }
    }

    private static void bag(final Board pBoard) throws SemanticsException {
        throwIfGameNotStarted();
        Terminal.printLine(pBoard.getBagContent());
    }

    private static void place(final Board pBoard, final String param) throws SyntaxException, SemanticsException {
        throwIfGameNotStarted();
        if (pBoard.isGameOver()) {
            throw new SemanticsException("cannot execute the place command after the game is finished.");
        }

        final String[] parts = param.split(";", 2);

        if (parts.length < 2) {
            throw new SyntaxException("at least two semi-colon separated values required for the place command.");
        }

        try {
            final int x = Integer.parseInt(parts[0]);
            final int y = Integer.parseInt(parts[1]);

            pBoard.place(x, y);

            if (pBoard.isGameOver()) {
                if (pBoard.isBagEmpty() && pBoard.getWinner() == null) {
                    Terminal.printLine("draw");
                } else {
                    Terminal.printLine(pBoard.getWinner().toString() + " wins");
                    Terminal.printLine(String.valueOf(pBoard.getCurrentMoveNumber() - 1));
                }
            } else {
                Terminal.printLine(OK);
            }
        } catch (NumberFormatException e) {
            throw new SyntaxException("the column and row index both must be valid Integers.");
        }
    }

    private static void select(final Board pBoard, final String param) throws SemanticsException, SyntaxException {
        throwIfGameNotStarted();
        if (pBoard.isGameOver()) {
            throw new SemanticsException("cannot execute the select command after the game is finished.");
        }

        try {
            final int index = Integer.parseInt(param);
            pBoard.select(index);
            Terminal.printLine(OK);
        } catch (NumberFormatException e) {
            throw new SyntaxException("the token's index has to be a valid Integer.");
        }
    }

    private static void throwIfGameNotStarted() throws SemanticsException {
        if (!isStarted) {
            throw new SemanticsException("the game must be started in order to execute other commands.");
        }
    }

    /**
     * The quit command implementation.
     * 
     * @see #QUIT
     */
    private static void quit() {
        isQuit = true;
    }
}
