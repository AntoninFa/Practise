package firstTask;

public class TaskOne {

    final char[] alphabet = new char []{'a','b','c','d','e','f','g','h','i','j','k','l'
            ,'m','n','o','p','q','r','s','t','u','v','w','x','y','z'};

    boolean checkPalindrome(char[] stringArray) {
        if (stringArray != null) {
            //TODO Case length = 1 GBI Def. true, Wiki false
            // !!! Falls du willst, das a k nicht Akzeptiert wird muss du hier ne Fallunterscheidung machen
            int counter = stringArray.length - 1;
            for (int i = 0; i < stringArray.length / 2; i++) {
                if (stringArray[i] != stringArray[counter]) {
                    return false;
                }
                counter--;
            } return true;
        } return false;
    }

    void removeValue(char[] charArray, int[] ind) {
        int lgth = charArray.length - 1;
        int counter = 0;
        int n = 0;
        int z = 0;
        while(n <= lgth - counter) {
            System.out.println("While n:" + n);
            System.out.println("While z:" + z);
            if (n == ind[z] && counter < ind.length) {
                counter++;
                System.out.println("match");
                System.out.println(charArray[n]);

                if (ind.length - 1 > z) {
                    z++;
                }
            } else {
                charArray[n] = charArray[n + counter];
                System.out.println(charArray[n]);
                n++;
            }
            System.out.println("n: "+n + "counter:" + counter);
        }
        for (int i = 0; i < counter; i++) {
            charArray[lgth - i] = 0;
        }
    }

    public boolean checkSent(String input) {
        if (input.length() < 26) {
            return false;
        }
        //TODO deine Fallunterscheidung für = 26
        char[] charArray = stringToArray(input.toLowerCase());
        for (char c : alphabet) {
            if (!charArrayContains(charArray, c)){
                return false;
            }
        } return true;
    }

    private boolean charArrayContains(char[] charArray, char c) {
        for (char cr: charArray) {
            if (cr == c) {
                return true;
            }
        } return false;
    }


    private char[] stringToArray(String input) {
        char[] output = new char[input.length()];
        for (int n = 0; n < input.length(); n++) {
            output[n] = input.charAt(n);
        }
        return output;
    }



}
