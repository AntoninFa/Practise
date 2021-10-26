/*
 * Copyright (c) 2019, IPD Koziolek. All rights reserved.
 */

package edu.kit.informatik.container;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple container terminal. It is possible to store new containers on specific stacks an to transfer them
 * between this.stacks.
 * 
 * @author Joshua Gleitze
 * @version 1.0
 *
 */
public class ContainerTerminal {
    private List<ContainerStack> stacks;
    private final Crane crane;

    /**
     * Intatiates a container terminal with no container stacks and a crane.
     */
    public ContainerTerminal() {
        this.stacks = new ArrayList<>();
        this.crane = new Crane();
    }
    
    /**
     * Stores {@code container} at stack number {@code stackNumber}.
     * 
     * @param container
     *            The container to store.
     * @param stackNumber
     *            The number of the stack to put {@code container} on.
     */
    public void storeContainer(Container container, int stackNumber) {
        for (int s = this.stacks.size(); s <= stackNumber; s++) {
            this.stacks.add(new ContainerStack(s));
        }
        this.stacks.get(stackNumber).addOnTop(container);
    }

    /**
     * Puts the top container of stack number {@code fromStackNumber} on top of stack number {@code toStackNumber}
     * if all of the following conditions are fulfilled:
     * <ul>
     * <li>stack number {@code fromStackNunber} is not empty
     * <li>stack number {@code toStackNumber} is not to high for the crane to operate.
     * </ul>
     * Otherwise, calling this method has no effect.
     * 
     * @param fromStackNumber
     *            The stack to get the container from.
     * @param toStackNumber
     *            The stack to transfer the container to.
     */
    public void transfer(int fromStackNumber, int toStackNumber) {
        this.crane.moveTo(this.stacks.get(fromStackNumber));
        if (this.crane.lift()) {
            this.crane.moveTo(this.stacks.get(toStackNumber));
            if (!this.crane.drop()) {
                this.crane.moveTo(this.stacks.get(fromStackNumber));
                this.crane.drop();
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder resultBuilder = new StringBuilder();
        for (ContainerStack stack : this.stacks) {
            if (!stack.isEmpty()) {
                if (resultBuilder.length() > 0) {
                    resultBuilder.append(System.lineSeparator());
                }
                resultBuilder.append(stack);
            }
        }
        return resultBuilder.toString();
    }
}
