package input;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Scanning {

    public static String readTLine(Scanner scr) {

        String sysInString = "";
        try {
            sysInString = scr.toString();
            return sysInString;

        } catch (InputMismatchException e) {
            return sysInString;
        }

    }

}
