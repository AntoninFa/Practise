package main;

import input.Scanning;
import input.UserInput;
import gameLogic.WordContLog;
import projectExceptions.InputException;

import java.util.Scanner;

public class Main {
    /**
     * Main-Methode des Programms, führt es aus und liest die engegebenen Zeilen
     *
     * @param args Die der Mainmethode übergebenen Argumente (Werden für dieses Programm nicht gebraucht)
     */
    public static void main(final String[] args) {
        UserInput input = null;
        Scanner scr = new Scanner(System.in);


        final WordContLog wordCont = new WordContLog();


        // START
        // Hier mby irwan die wahl zwischen Spielmodis
        System.out.println("\"start\" um das Spiel zu starten");

        do try {
            input = UserInput.runInputMatching(Scanning.readTLine(scr), wordCont);
        } catch (final InputException e) {
            System.out.println(e.getMessage());
        }
        while (input == null || !input.getGameOver());
    }
}
