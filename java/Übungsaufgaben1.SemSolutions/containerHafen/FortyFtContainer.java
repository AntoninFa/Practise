/*
 * Copyright (c) 2019, IPD Koziolek. All rights reserved.
 */

package edu.kit.informatik.container;

/**
 * A container that's 40 feet long and 8 feet and 6 inches long.
 * 
 * @author Joshua Gleitze
 * @version 1.0
 *
 */
public class FortyFtContainer extends Container {
    /**
     * Constructs a new Forty Feet Container.
     * 
     * @param id
     *            This container's unique identification number.
     */
    public FortyFtContainer(int id) {
        super(id, 8 * 12 + 6, 40 * 12);
    }

    @Override
    public String toString() {
        return String.format("40ft;%s", super.toString());
    }
}
