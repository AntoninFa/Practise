/*
 * Copyright (c) 2020, IPD Koziolek. All rights reserved.
 */

package edu.kit.informatik.boardgame.element;

/**
 * This class represents a player.
 * @author Martin Löper
 * @version 1.0
 */
public class Player {
    private final int id;

    /**
     * Creates a new player with given id.
     * @param id the identifier
     */
    public Player(final int id) {
        this.id = id;
    }
    
    /**
     * Returns the identifier for this player.
     * @return the id
     */
    public int getId() {
        return this.id;
    }
    
    @Override
    public String toString() {
        return "P" + String.valueOf(this.id);
    }
}
