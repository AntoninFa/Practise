package gameLogic;

import gameStates.GameState;
import input.Scanning;

import java.util.Random;

public class WordContLog {
    private  GameState currentGameState = GameState.START;
    private int numOfPlayers = -1;
    private int[] scores;
    private int currentPlayer = -1;

    public void start() {
        System.out.println("Spieleranzahl wird durch \"sp INT\" gesetzt");
        currentGameState = GameState.ISPIELERANZAHL;
    }

    public void setNumOPlayers(int num) {
        if (currentGameState == GameState.ISPIELERANZAHL) {
            numOfPlayers = num;
            scores = new int[num];
            // Alle scores sind am Anfang 0
            for (int sc : scores) {
                sc = 0;
            }
            System.out.println(num + " Spieler sind dabei");
            System.out.println("Spieler 1 bereit? (y)");
            currentGameState = GameState.WAITINGFORPLAYER;

        } else {
            System.out.println("geht nur zu beginn");
        }
    }

    public void go() {
        if (currentGameState == GameState.WAITINGFORPLAYER) {
            System.out.println("Deine Zwei Buchstaben sind: " + twoRandomChars());
            currentGameState = GameState.PLAYING;

        }
        System.out.println("Das geht grade leider nicht");
    }

    private String twoRandomChars() {
        Random rnd = new Random();
        char one = (char) ('a' + rnd.nextInt(26));
        char two = (char) ('a' + rnd.nextInt(26));
        return one + "" + two;
    }




}
