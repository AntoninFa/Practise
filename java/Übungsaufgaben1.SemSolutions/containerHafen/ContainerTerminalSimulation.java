/*
 * Copyright (c) 2019, IPD Koziolek. All rights reserved.
 */

package edu.kit.informatik.container;

import edu.kit.informatik.Terminal;

/**
 * A simple program that reads in an input file and simulates the movements in a container terminal based on it.
 * 
 * @author Joshua Gleitze
 * @version 1.0
 *
 */
public class ContainerTerminalSimulation {
    private static ContainerTerminal terminal = new ContainerTerminal();

    /**
     * Reads in the provided input file and performs the movements specified in it. Prints the result on
     * {@link System#out}.
     * 
     * @param args
     *            The path to the input file as only argument.
     */
    public static void main(String [] args) {
        terminal = new ContainerTerminal();
        Scanner inputFileScanner = new Scanner(args[0]);

        // First part: Container definition
        inputFileScanner.splitInput("(;)|(kg;)");
        while (!inputFileScanner.hasNext("--")) {
            Container container;
            if (inputFileScanner.next().equals("40ft")) {
                container = new FortyFtContainer(inputFileScanner.nextInt());
            } else {
                container = new FortyFtHighCubeContainer(inputFileScanner.nextInt());
            }
            container.setWeight(inputFileScanner.nextInt());
            terminal.storeContainer(container, inputFileScanner.nextInt());
        }
        // Jump over "--"
        inputFileScanner.next();

        // Second part: Container movement
        while (inputFileScanner.hasNext()) {
            terminal.transfer(inputFileScanner.nextInt(), inputFileScanner.nextInt());
        }
        Terminal.printLine(terminal);
    }
}
