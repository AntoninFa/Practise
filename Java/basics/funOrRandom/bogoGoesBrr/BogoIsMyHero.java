package bogoGoesBrr;

import java.text.DecimalFormat;
import java.util.Random;

public class BogoIsMyHero {
    public static int[] unsortedA = new int[]{5, 1, 2, 4, 10};
    private static DecimalFormat df = new DecimalFormat("#.##");

    public static void doOneBogoWithStringOutput() {
        int bogoCounter = 0;
        long timeUsedByTheSB = 0;
        long timeUsedByTheShuffler = 0;
        long startTime = System.nanoTime();

        while (!isArraySorted()) {
            long pST = System.nanoTime();
            printArray();
            //printArrayWithSK();
            long pET = System.nanoTime();
            timeUsedByTheSB += (pET - pST);
            pST = System.nanoTime();
            randomize();
            pET = System.nanoTime();
            timeUsedByTheShuffler += (pET - pST);
            System.out.println("\n\n");
            bogoCounter++;
        }
        long endTime = System.nanoTime();
        long timeTaken = endTime - startTime;
        printArray();
        System.out.println("BogoSort you are the best... Only " + bogoCounter + " tries");
        System.out.println(unsortedA.length + " Items to sort and you did it in only " + timeTaken + "ns (" +
                timeTaken / 1000000 + "ms)");
        double sbRatio = (double) timeUsedByTheSB / timeTaken;
        double shRatio = (double) timeUsedByTheShuffler / timeTaken;

        System.out.println("Time taken by the SB: " + timeUsedByTheSB + "(" + df.format(sbRatio) + ")" +
                "\nTime taken by the ArrayRandomizer: "
                + timeUsedByTheShuffler + "(" + df.format(shRatio) + ")");
    }

    public static long doOneBogoAndRetT(int[] newUnsortedA) {
        unsortedA = newUnsortedA;
        long bogoCounter = 0;
        while (!isArraySorted()) {
            randomize();
            bogoCounter++;
        }
        return bogoCounter;
    }

    static void randomize() {
        var rng = new Random();
        for (int i = 0; i < unsortedA.length; i++) {
            int k = rng.nextInt(unsortedA.length);
            int temp = unsortedA[k];
            unsortedA[k] = unsortedA[i];
            unsortedA[i] = temp;
        }
    }

    static boolean isArraySorted() {
        for (int n = 0; n < unsortedA.length - 1; n++) {
            if (unsortedA[n] >= unsortedA[n + 1]) {
                return false;
            }
        }
        return true;
    }

    static void printArray() {
        for (int i : unsortedA) {
            System.out.println("-".repeat(Math.max(0, i)));
        }
    }

    static void printArrayWithSK() {
        for (int i : unsortedA) {
            String temp = "";
            for (int n = 0; n < i; n++) {
                temp += "-";
            }
            System.out.println(temp);
        }
    }


}
