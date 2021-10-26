/*
 * Copyright (c) 2019, IPD Koziolek. All rights reserved.
 */

package edu.kit.informatik.parsing;

import java.util.Scanner;
import java.util.regex.Pattern;

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
 * Generic parser for numerical tokens. The token may be specified by
 * <ul>
 * <li>setting its maximum value
 * <li>setting its minimum value
 * <li>setting a suffix
 * </ul>
 * 
 * @author Joshua Gleitze
 * @version 1.0
 *
 * @param <N>
 *            The number token's type.
 */
public abstract class NumberParser<N extends Number & Comparable<N>> extends TokenParser<N> {

    private String suffix = "";
    private N min;
    private N max;

    /**
     * Creates a number parser.
     * 
     * @param source
     *            The source scanner.
     * @param min
     *            The default minimum value. Usually the minimal value that can possible be specified by {@code N}.
     * @param max
     *            The default maximum value. Usually the maximum value that can possible be specified by {@code N}.
     */
    protected NumberParser(Scanner source, N min, N max) {
        super(source);
        this.min = min;
        this.max = max;
    }

    @Override
    protected N saveNext() throws ParseException {
        Pattern oldDelimiter = sourceScanner.delimiter();
        if (!suffix.equals("")) {
            if (!this.sourceScanner.hasNext(".+" + Pattern.quote(suffix))) {
                throw parseException(expected("%s with suffix '%s'", description(), suffix), false);
            }
            this.sourceScanner.useDelimiter(Pattern.quote(suffix));
        }
        N number = saveNumber();
        if (number.compareTo(max) > 0) {
            throw parseException(expected("%s no higher than %s.", description(), max), true);
        }
        if (number.compareTo(min) < 0) {
            throw parseException(expected("%s no lower than %s.", description(), min), true);
        }
        sourceScanner.useDelimiter(oldDelimiter);
        return number;
    }

    /**
     * The actual number parsing. Subclasses should implement this method instead of {@link #saveNext()}. They can be
     * sure that the next token of the source scanner will be the number token, without the suffix. Maximum and minimum
     * checks will be performed by this class after this method has run.
     * 
     * @return The parsed number token.
     * @throws ParseException
     *             If the token cannot be parsed for any reason.
     */
    protected abstract N saveNumber() throws ParseException;

    /**
     * Set the token's minimum value. Only (but not necessarily all) tokens higher than or equal to {@code min} will be
     * accepted.
     * 
     * @param min
     *            The token's minimum value.
     * @return {@code this}.
     */
    public NumberParser<N> minimum(N min) {
        this.min = min;
        return this;
    }

    /**
     * Set the token's maximum value. Only (but not necessarily all) tokens lower than or equal to {@code max} will be
     * accepted.
     * 
     * @param max
     *            The token's maximum value.
     * @return {@code this}.
     */
    public NumberParser<N> maximum(N max) {
        this.max = max;
        return this;
    }

    /**
     * Set the suffix of the numerical token. Only (but not necessarily all) tokens directly followed by {@code suffix}
     * will be accepted. Meanwhile, {@code suffix} will not be taken into account when parsing the numerical value.
     * 
     * @param suffix
     *            The token's suffix.
     * @return {@code this}.
     */
    public NumberParser<N> suffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

}
