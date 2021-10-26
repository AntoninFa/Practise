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
 * Describes a token an allows to parse it from a {@link Scanner}.
 * 
 * @author Joshua Gleitze
 * @version 1.0
 *
 * @param <T>
 *            The type of the parse result.
 */
public abstract class TokenParser<T> {

    private static String lastRead = null;

    /**
     * The scanner provided by the user. The token to be parsed should be at the scanner's current position.
     */
    protected Scanner sourceScanner;

    private T last = null;

    /**
     * Creates a parser that retrieves the token from the {@code source} scanner.
     * 
     * @param source
     *            The scanner to retrieve the token from. The token should be at the scanner's momentary position.
     */
    public TokenParser(Scanner source) {
        this.sourceScanner = source;
    }

    /**
     * Tries to parse the token described by {@code this} from the source scanner.
     * 
     * @return The token described by {@code this}.
     * @throws ParseException
     *             If the token cannot be parsed for any reason.
     * @throws NullPointerException
     *             If the source scanner is {@code null}. This happens especially if the default constructor was used
     *             and no input scanner was set .
     */
    public T next() throws ParseException {
        if (!sourceScanner.hasNext()) {
            String message;
            if (lastRead != null) {
                message = "Unexpected end of input. " + expected("%s after '%s'", description(), lastRead);
            } else {
                message = "Did not find any input! " + expected(description());
            }
            throw new ParseException(message);
        }
        last = saveNext();
        lastRead = sourceScanner.match().group();
        return last;
    }

    /**
     * Runs {@link #next()}, and asserts that no more input can be found in the source scanner afterwards.
     * 
     * @return What {@link #next()} would return.
     * @throws ParseException
     *             If any input, including delimiters, is found after calling {@link #next()} or if {@link #next()}
     *             would throw a parse exception.
     */
    public T end() throws ParseException {
        T result = next();
        if (!"".equals(sourceScanner.findWithinHorizon(".*", 0))) {
            throw parseException(expected("end of input"), true);
        }
        return result;
    }

    /**
     * Handles the actual parsing of the token. When this method is called, implementing classes can be sure that
     * {@link #sourceScanner} has a next token.
     * 
     * @return The parse result.
     * @throws ParseException
     *             If the token cannot sbe parsed for any reason.
     */
    protected abstract T saveNext() throws ParseException;

    /**
     * @return A description of the token that is to be parsed by this token parser.
     */
    protected abstract String description();

    /**
     * @return What {@link #next()} returned the last time it was called. {@code null} if {@link #next()} has never been
     *         called yet.
     */
    public T last() {
        return last;
    }

    /**
     * Sets this parser's source scanner.
     * 
     * @param sourceScanner
     *            The scanner to retrieve the token from. The token should be at the scanner's momentary position.
     */
    public void setSource(Scanner sourceScanner) {
        this.sourceScanner = sourceScanner;
    }

    /**
     * Shortcut for generating a "Expected..." string, using {@link String#format(String, Object...)}.
     * 
     * @param s
     *            The string to pass to {@link String#format(String, Object...)} and to prepend with {@code "Excpected"}
     * @param arguments
     *            The arguments to pass to {@link String#format(String, Object...)}.
     * @return {@code "Expected " + String.format(s, arguments) + "."}.
     */
    protected String expected(String s, Object... arguments) {
        return "Expected " + String.format(s, arguments) + ".";
    }

    /**
     * Generates a Parse Exception and handles clean up. Any parse exception should only be thrown through this method.
     * 
     * @param reason
     *            Why parsing failed. {@link #description()} and {@link #expected(String, Object...)} may help creating
     *            it.
     * @param matched
     *            Whether any of the {@code Scanner#next...}-methods have already been called since the start of
     *            {@link #saveNext()}. Settings this flag incorrectly might result in runtime exceptions being thrown.
     * @return A prepared Parse Exception to throw for {@code reason}.
     */
    protected ParseException parseException(String reason, boolean matched) {
        if (!matched) {
            sourceScanner.next();
        }
        lastRead = sourceScanner.match().group();
        return new ParseException(String.format("Unexpected token '%s'. %s", lastRead, reason));
    }
}
