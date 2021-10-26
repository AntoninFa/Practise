/*
 * Copyright (c) 2019, IPD Koziolek. All rights reserved.
 */

package edu.kit.informatik;

import edu.kit.informatik.parsing.IntegerParser;
import edu.kit.informatik.parsing.cli.CommandLineArgument;
import edu.kit.informatik.parsing.cli.CommandLineArgumentException;

/**
 * A simple calculator for the lucas series.
 * 
 * @author Joshua Gleitze
 * @version 1.0
 */
public class LucasSeriesCalculator {

    /**
     * Calculates elements of a lucas series with the parameters {@code p} and {@code q} up to index {@code n}. Prints
     * the elements on the console, one per line. Additionally prints how many elements have been calculated. The lucas
     * series is defined recursively as {@code f_i = p * f_(i-1) - q * f_(i-2)}.
     * 
     * @param args
     *            The following parameters are required (in the order given):
     *            <ul>
     *            <li> {@code n} the highest index of the members that shall be printed.
     *            <li> {@code p} the lucas series' {@code p} parameter.
     *            <li> {@code q} the lucas series' {@code q} parameter.
     *            </ul>
     */
    public static void main(String[] args) {
        Long[] members = null;

        /*-
         * Sample Solution Note:
         * We're using a parsing system here that exceeds this task's requirements by far. It is certainly not necessary
         * to put this much effort in parsing. Nevertheless, the system is used for a number of reasons:
         * 
         *      - It is meant to show techniques useful for parsing
         *      - It aims to minimize the amount of code needed to actually parse input and to allow easy readable
         *          code for parsing.
         *      - It will be used throughout the practice to maximize reusage of code.
         *      
         * Please do not consider this parsing system as the way parsing must be done, but rather as an inspiration to 
         * write your own, reusable, parsing API. It might be quite handy to already have that for the finals ;)    
         */
        final int argsLength = 3;
        if (args.length == argsLength) {
            CommandLineArgument<Integer> maxI = new CommandLineArgument<>(0, new IntegerParser().minimum(0), args);
            maxI.description("the maximum index up to which the lucas series shall be calculated").required();
            CommandLineArgument<Integer> p = new CommandLineArgument<>(1, new IntegerParser(), args);
            p.description("the lucas series' p parameter").required();
            CommandLineArgument<Integer> q = new CommandLineArgument<>(2, new IntegerParser(), args);
            q.description("the lucas series' q parameter").required();

            try {
                LucasSeries lucasSeries = new LucasSeries(p.get(), q.get());
                members = lucasSeries.calculateUpTo(maxI.get());
                q.assertLast();

                for (long j : members) {
                    Terminal.printLine("" + j);
                }
            } catch (CommandLineArgumentException e) {
                Terminal.printError(e.getMessage());
            }
        } else {
            Terminal.printError("program must be started with " + argsLength + " arguments: n, p and q");
        }
    }
}
