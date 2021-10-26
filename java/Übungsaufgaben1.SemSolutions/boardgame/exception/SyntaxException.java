/*
 * Copyright (c) 2020, IPD Koziolek. All rights reserved.
 */

package edu.kit.informatik.boardgame.exception;

/**
 * This exception is used to indicate a syntactic fault either during file input parsing or command line parsing.
 * 
 * @author Martin Löper
 * @version 1.0
 *
 */
public class SyntaxException extends Exception {
    
    /**
     * Auto generated UID for serializing. Actually not needed according to the task, but absence produces annoying
     * warnings.
     */
    private static final long serialVersionUID = -5224960056785587052L;

    /**
     * Creates a new SyntaxException with the given detailed message.
     * @param pMessage some detailed error message (null is not allowed)
     */
    public SyntaxException(final String pMessage) {
        super(pMessage);
    }
}
