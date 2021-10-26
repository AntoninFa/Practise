/*
 * Copyright (c) 2019, IPD Koziolek. All rights reserved.
 */

package edu.kit.informatik.container;

/**
 * A container that's 40 feet long and 9 feet and 6 inches long.
 * 
 * @author Joshua Gleitze
 * @version 1.0
 *
 */
public class FortyFtHighCubeContainer extends Container {
    /**
     * Creates a new Forty Feet High Cube Container.
     * 
     * @param id
     *            This container's unique identification number.
     */
    public FortyFtHighCubeContainer(int id) {
        super(id, 9 * 12 + 6, 40 * 12);
    }

    @Override
    public String toString() {
        return String.format("40ftHC;%s", super.toString());
    }
}
