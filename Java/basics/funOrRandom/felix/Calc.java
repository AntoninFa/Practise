package felix;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Calc {

    public static void main(String[] args) {
       Calc c = new Calc();
       c.run();
    }

    private void run() {
        double n1, n2;
        Scanner scr = new Scanner(System.in);

        try {
            System.out.println("Enter first number");
            n1 = scr.nextDouble();

            System.out.println("Enter second number");
            n2 = scr.nextDouble();
            System.out.printf("Your answer is: %s ", (n1 + n2));
        } catch (InputMismatchException e) {
            System.out.println(" Ob du dumm bist?");
        }


        System.out.println("hey");
        scr.close();
    }
}
