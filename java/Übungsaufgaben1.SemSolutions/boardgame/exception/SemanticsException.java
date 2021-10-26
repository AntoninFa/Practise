/*
 * Copyright (c) 2020, IPD Koziolek. All rights reserved.
 */

package edu.kit.informatik.boardgame.exception;

/**
 * This exception is used to indicate a semantics fault.
 * 
 * @author Martin Löper
 * @version 1.0
 *
 */
public class SemanticsException extends Exception {
    
    /**
     * Auto generated UID for serializing. Actually not needed according to the task, but absence produces annoying
     * warnings.
     */
    private static final long serialVersionUID = 2902883905608249312L;
    
    /**
     * Creates a new SemanticsException with the given detailed message.
     * @param pMessage some detailed error message (null is not allowed)
     */
    public SemanticsException(final String pMessage) {
        super(pMessage);
    }
}
