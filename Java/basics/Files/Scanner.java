/*
 * Copyright (c) 2019, IPD Koziolek. All rights reserved.
 */

package edu.kit.informatik.container;

import edu.kit.informatik.Terminal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a Scanner for scanning and splitting an input file.
 * 
 * @author Thomas Weber
 * @author Jonathan Schenkenberger
 * @version 1.0
 */
public class Scanner {

    private String[] input;
    private int index;

    /**
     * Creates a new Scanner for the given input file.
     * The input is initially split as by the {@code Terminal.readFile} method.
     * 
     * @param filePath - the path of the given input file
     */
    public Scanner(final String filePath) {
        input = Terminal.readFile(filePath);
        index = 0;
    }

    /**
     * Splits the input by the given regex pattern and resets the seek-index to 0.
     * 
     * @param pattern - the given regex pattern
     */
    public void splitInput(final String pattern) {
        List<String> splittedInput = new ArrayList<>();
        for (String line : input) {
            splittedInput.addAll(Arrays.asList(line.split(pattern)));
        }
        input = splittedInput.toArray(new String[splittedInput.size()]);
        index = 0;
    }

    /**
     * Determines whether the next element matches the given pattern.
     * 
     * @param pattern - the given pattern
     * @return whether the next element matches the given pattern
     */
    public boolean hasNext(final String pattern) {
        return pattern.matches(input[index]);
    }

    /**
     * Increments the seek-index and returns the next element
     * 
     * @return the next element
     */
    public String next() {
        return input[index++];
    }

    /**
     * Returns the next element as an int. 
     * If the next element is not an int a {@code NumberFormatException} is thrown.
     * 
     * @return the next element as an int
     */
    public int nextInt() {
        final String next = next();
        return Integer.parseInt(next);
    }

    /**
     * Determines whether the scanner has a next element, 
     * i.e. the seek-index has not reached the end of the input.
     * 
     * @return whether the scanner reached its end
     */
    public boolean hasNext() {
        return index < input.length;
    }
}
