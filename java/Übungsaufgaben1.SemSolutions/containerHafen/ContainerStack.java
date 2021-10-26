/*
 * Copyright (c) 2019, IPD Koziolek. All rights reserved.
 */

package edu.kit.informatik.container;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * A stack of {@link Containers}. A container stack has a number. It is possible to put containers on top of the
 * stack ({@link #addOnTop(Container)} and to remove to top container {@link #removeTopContainer()}. A stack has a
 * height, which is the sum of all containers on the stack.
 * 
 * @author Joshua Gleitze
 * @version 1.0
 *
 */
public class ContainerStack {
    private final Deque<Container> stack;
    private int height;
    private final int number;

    /**
     * Creates a container stack with the given {@code number}.
     * 
     * @param number
     *            This stack's number.F
     */
    public ContainerStack(int number) {
        this.stack = new LinkedList<>();
        this.height = 0;
        this.number = number;
    }

    /**
     * Puts {@code container} on top of this container stack.
     * 
     * @param container
     *            The container to put on this stack.
     */
    public void addOnTop(Container container) {
        this.height += container.getHeight();
        this.stack.push(container);
    }

    /**
     * 
     * @return {@code true} only if this stack has no containers on it.
     */
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    /**
     * 
     * @return The top container of this stack, or {@code null} if this stack is empty.
     */
    public Container topContainer() {
        return (isEmpty()) ? null : this.stack.peekFirst();
    }

    /**
     * Removes the top container from this stack if it's not empty. Does nothing otherwise.
     */
    public void removeTopContainer() {
        if (!isEmpty()) {
            Container container = this.stack.removeFirst();
            this.height -= container.getHeight();
        }
    }

    /**
     * @return This stack's height. (The sum of the heights of all containers on this stack).
     */
    public int getHeight() {
        return this.height;
    }

    @Override
    public String toString() {
        StringBuilder resultBuilder = new StringBuilder();
        Iterator<Container> it = this.stack.descendingIterator();
        while (it.hasNext()) {
            if (resultBuilder.length() > 0) {
                resultBuilder.append(System.lineSeparator());
            }
            resultBuilder.append(String.format("%s;%d", it.next(), this.number));
        }
        return resultBuilder.toString();
    }
}
