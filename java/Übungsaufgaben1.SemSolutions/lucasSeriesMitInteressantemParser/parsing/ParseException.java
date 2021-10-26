/*
 * Copyright (c) 2019, IPD Koziolek. All rights reserved.
 */

package edu.kit.informatik.parsing;

/*-
 * Sample Solution Note:
 * This parsing system exceeds this tasks's requirements by far. It is certainly not necessary to put this much effort 
 * in parsing. Nevertheless, the system is used for a number of reasons:
 * 
 *      - It is meant to show techniques useful for parsing
 *      - It aims to minimize the amount of code needed to actually parse input and to allow easy readable
 *          code for parsing.
 *      - It will be used throughout the practice to maximize reusage of code.
 *      
 * Please do not consider this parsing system as the way parsing must be done, but rather as an inspiration to 
 * write your own, reusable, parsing API. It might be quite handy to already have that for the finals ;)    
 */
/**
 * Thrown if a string is to be parsed but is malformed, such that it cannot be interpreted the way it was expected to
 * be.
 * 
 * @author Joshua Gleitze
 * @version 1.0
 *
 */
public class ParseException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Creates a parse exception with the given description.
     * 
     * @param errorDescription
     *            A description of what went wrong.
     */
    public ParseException(String errorDescription) {
        super(errorDescription);
    }
}
