/*
 * Copyright (c) 2019, IPD Koziolek. All rights reserved.
 */

package edu.kit.informatik.container;

/**
 * A crane of a {@link ContainerTerminal}. A crane is positioned above one {@code ContainerStack} and is able to
 * lift exactly one container from the top of it. It can be moved to another stack and it can drop the container
 * it's lifting on the stack it's over.
 * <p>
 * Plus, a crane has a maximum lifting height. The maximum lifting height is the theoretical maximum lifting height
 * (160ft) minus the height of the lifted container. It's only possible to drop containers on stacks that are lower
 * than or as high as the maximum lifting height.
 * <p>
 * A crane has a maximum load: 3000kg. Any containers heavier than that will not be lifted.
 * 
 * @author Joshua Gleitze
 * @version 1.0
 *
 */
public class Crane {
    private static final int MAX_LIFTING_HEIGHT = 160 * 12;
    private static final int MAX_LOAD = 30000;
    private ContainerStack here;
    private Container lifted;

    /**
     * Moves the cran to the provided stack.
     * 
     * @param containerStack
     *            The stack this crane shall be over.
     */
    public void moveTo(ContainerStack containerStack) {
        this.here = containerStack;
    }

    /**
     * Lifts the top container (if there's any) off the stack this crane is over.
     * 
     * @return {@code true} only if the top container of the stack this crane is over was lifted. I.e. false if
     *         said container was to heavy.
     */
    public boolean lift() {
        if (this.here != null && this.here.topContainer().getWeight() <= MAX_LOAD) {
            this.lifted = this.here.topContainer();
            this.here.removeTopContainer();
            return true;
        }
        return false;
    }

    /**
     * @return The maximum lifting height that can be achieved with the momentary lifted container (if there's
     *         any).
     */
    public int maximumLiftingHeight() {
        return MAX_LIFTING_HEIGHT - ((this.lifted == null) ? 0 : this.lifted.getHeight());
    }

    /**
     * Drops the momentary lifted container (if there is any) on the stack the crane is over.
     * 
     * @return {@code true} if a container was dropped on the stack this crane is over.
     */
    public boolean drop() {
        if (this.lifted != null && this.here.getHeight() <= maximumLiftingHeight()) {
            this.here.addOnTop(this.lifted);
            this.lifted = null;
            return true;
        }
        return false;
    }
}
