/*
 * Copyright (c) 2019, IPD Koziolek. All rights reserved.
 */

package edu.kit.informatik.parsing.cli;

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
 * Thrown if a {@link CommandLineArgument} cannot be parsed properly.
 * 
 * @author Joshua Gleitze
 * @version 1.0
 *
 */
public class CommandLineArgumentException extends Exception {
    private static final long serialVersionUID = 1L;
    private CommandLineArgument<?> argument;
    private String causeDescription;
    private boolean generate;

    /**
     * Creates an exception for the provided {@code argument} after a parsing error described in
     * {@code causeDescription} occurred.
     * 
     * @param argument
     *            The command line argument that could not be parsed properly.
     * @param causeDescription
     *            A description of what went wrong when trying to parse {@code argument}.
     */
    public CommandLineArgumentException(CommandLineArgument<?> argument, String causeDescription) {
        super();
        this.argument = argument;
        this.causeDescription = causeDescription;
        generate = true;
    }

    /**
     * Creates an exception containing only the provided message. Use this constructor if there was a general error with
     * the command line arguments that cannot be related to a specific one.s
     * 
     * @param causeDescription
     *            A description of what went wrong when trying to parse the command line arguments.
     */
    public CommandLineArgumentException(String causeDescription) {
        this.causeDescription = causeDescription;
        generate = false;
    }

    @Override
    public String getMessage() {
        if (generate) {
            return String.format("%d. argument, %s, was invalid. %s", argument.getIndex(), argument.getDescription(),
                causeDescription);
        }
        return causeDescription;
    }
}
