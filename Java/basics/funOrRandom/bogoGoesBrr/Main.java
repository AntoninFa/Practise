package bogoGoesBrr;

public class Main {
    public static void main(String[] args) {
        final int loops = 500;
        int[] theUnsortedArray = new int[]{5, 1, 2, 4, 10};
        long numOfTries = 0;



        for(int n = 0; n < loops; n++) {
            numOfTries += BogoIsMyHero.doOneBogoAndRetT(theUnsortedArray.clone());
            if(n % 5 == 0) {
                System.out.println(numOfTries);
            }
        }
        System.out.println("\n\nThe average num of Tries was: " + numOfTries / loops
                + "\nWith an arrayLength of: " + theUnsortedArray.length);

    }


}
