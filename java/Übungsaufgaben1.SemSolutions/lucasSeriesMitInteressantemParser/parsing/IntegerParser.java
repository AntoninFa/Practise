/*
 * Copyright (c) 2019, IPD Koziolek. All rights reserved.
 */

package edu.kit.informatik.parsing;

import java.util.Scanner;

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
 * Parses an integer value. Unlike Java's parsing methods, this parser only accepts tokens matching the regular
 * expression {@code -?[0-9]+}.
 * 
 * @author Joshua Gleitze
 * @version 1.0
 *
 */
public class IntegerParser extends NumberParser<Integer> {

    /**
     * Creates an integer parser without a source scanner set. The source scanner must be set through
     * {@link #setSource(Scanner)} before {@link #next()} is called.
     */
    public IntegerParser() {
        this(null);
    }

    /**
     * Creates an integer parser using the specified {@code source} scanner.
     * 
     * @param source
     *            The scanner to retrieve the token from. The token should be at the scanner's momentary position.
     */
    public IntegerParser(Scanner source) {
        super(source, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    @Override
    protected Integer saveNumber() throws ParseException {
        if (!sourceScanner.hasNextInt() || !sourceScanner.hasNext("\\-?[0-9]+")) {
            throw parseException(expected(description()), false);
        }
        return sourceScanner.nextInt();
    }

    @Override
    protected String description() {
        return String.format("an Integer");
    }

}
