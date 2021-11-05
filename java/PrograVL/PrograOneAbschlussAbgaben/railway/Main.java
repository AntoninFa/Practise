package edu.kit.informatik;

/**
 * Benutzerschnittstellen Klasse welche, die main-Methode enthält welche den User-Input an
 * die für den User-Input zuständige Klasse übergibt
 *
 * @author Antonin Fahning
 * @version 1.0
 */

public class Main {

    /**
     * Main-Methode des Programms, führt es aus und liest die engegebenen Zeilen
     * @param args Die der Mainmethode übergebenen Argumente (Werden für dieses Programm nicht gebraucht)
     */
    public static void main(String[] args) {

        UserInput input = null;
        RailwaySimulation railwaySimulation = new RailwaySimulation();
        do try {
                input = UserInput.runInputMatching(Terminal.readLine(), railwaySimulation);
            } catch (InputException e) {
                Terminal.printError(e.getMessage());
            }
        while (input == null || !input.getProgramExit());
    }
}