/*
 * Copyright (c) 2020, IPD Koziolek. All rights reserved.
 */

package edu.kit.informatik.boardgame;

import edu.kit.informatik.boardgame.element.Bag;
import edu.kit.informatik.boardgame.element.Player;


/**
 * This class initializes the game and finally starts the interactive
 * command-line sequence.
 * 
 * @author Martin Löper
 * @version 1.0
 */
public final class MainClass {

    /**
     * This class does not need to be instantiated. The only significant element
     * is the main method.
     */
    private MainClass() { }

    /**
     * The program's main entry point. It initializes the game.
     * 
     * @param args the command-line arguments, consisting of:
     *            <ul>
     *            <li>(mandatory) the type of pitch to use</li>
     *            </ul>
     */
    public static void main(String[] args) {
        /* setup the players */
        final Player[] players = new Player[] {
            new Player(1),
            new Player(2)
        };
        
        /* setup the bag with tokens containing properties from TokenPropertyType */
        final Bag bag = new Bag();

        /* listen for user commands until user enters quit */
        CommandLine.startInteractiveSequence(players, bag);
    }
}
