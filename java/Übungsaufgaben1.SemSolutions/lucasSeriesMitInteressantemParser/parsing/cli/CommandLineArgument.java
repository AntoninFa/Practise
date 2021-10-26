/*
 * Copyright (c) 2019, IPD Koziolek. All rights reserved.
 */

package edu.kit.informatik.parsing.cli;

import java.util.Scanner;

import edu.kit.informatik.parsing.ParseException;
import edu.kit.informatik.parsing.TokenParser;

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
 * Class for handling command line arguments. Instances of this class handle the complete command line argument checking
 * and return the parsed value of a command line argument. They rely on {@link TokenParser}s for parsing.
 * 
 * @author Joshua Gleitze
 * @version 1.0
 *
 * @param <T>
 *            This command line argument's parsed type.
 */
public class CommandLineArgument<T> {
    private boolean required;
    private String description;
    private int index;
    private String[] args0;
    private TokenParser<T> parser;

    /**
     * Creates a command line argument.
     * 
     * @param index
     *            The argument's index when provided on the command line.
     * @param parser
     *            The parser to process the argument.
     * @param args0
     *            The command line argument array to get this argument from.
     */
    public CommandLineArgument(int index, TokenParser<T> parser, String... args0) {
        this.index = index;
        this.args0 = args0;
        this.parser = parser;
    }

    /**
     * @return {@code true} only if this arguments is required.
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * @return This argument's name.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return This argument's index in the command line arguments.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Set this argument as required.
     * 
     * @return {@code this}.
     */
    public CommandLineArgument<T> required() {
        this.required = true;
        return this;
    }

    /**
     * Set this argument's description.
     * 
     * @param description
     *            A String describing this argment's function.
     * @return {@code this}.
     */
    public CommandLineArgument<T> description(String description) {
        this.description = description;
        return this;
    }

    /**
     * Extracts this argument out of the arguments array provided to a program and returns its parsed value.
     * 
     * @return This argument's parsed value.
     * @throws CommandLineArgumentException
     *             <ul>
     *             <li>If this command line argument is required but was not given.
     *             <li>Parsing of the command line argument failes.
     *             </ul>
     */
    public T get() throws CommandLineArgumentException {
        if (args0.length > index) {
            parser.setSource(new Scanner(args0[index]));
            try {
                return parser.next();
            } catch (ParseException e) {
                throw new CommandLineArgumentException(this, e.getMessage());
            }
        } else if (required) {
            throw new CommandLineArgumentException(this, "The argument was not provided while it is required.");
        } else {
            return null;
        }
    }

    /**
     * Asserts that this command line argument is the last one provided.
     * 
     * @throws CommandLineArgumentException
     *             If this command line argument was not the last one.
     */
    public void assertLast() throws CommandLineArgumentException {
        if (args0.length > index + 1) {
            throw new CommandLineArgumentException(String.format("Found more than %d command line arguments.",
                index + 1));
        }
    }
}
