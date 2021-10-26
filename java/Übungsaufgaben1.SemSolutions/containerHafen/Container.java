/*
 * Copyright (c) 2019, IPD Koziolek. All rights reserved.
 */

package edu.kit.informatik.container;

/**
 * A simple container. A container has a (fix) height, length and identification number, as well as a (variable)
 * weight.
 * 
 * @author Joshua Gleitze
 * @version 1.0
 *
 */
public abstract class Container {

    private final int height;
    private final int id;
    private int weight;
    private final int length;

    /**
     * Creates a new container with the given, fixed height.
     * 
     * @param id
     *            This container's unique identification number.
     * @param height
     *            This container's height (in inches).
     * @param length
     *            This container's length (in inches).
     */
    protected Container(int id, int height, int length) {
        this.height = height;
        this.id = id;
        this.length = length;
    }

    /**
     * 
     * @return This container's length.F
     */
    public int getLength() {
        return this.length;
    }

    /**
     * @return This container's momentary weight, including both the weight of itself and its content.
     */
    public int getWeight() {
        return this.weight;
    }

    /**
     * Sets this container's weight.
     * 
     * @param weight
     *            This container's weight, including both the weight of itself and its content.
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * 
     * @return This container's height.
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * 
     * @return This container's unique identification number.
     */
    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return String.format("%d;%dkg", this.getId(), this.getWeight());
    }
}
