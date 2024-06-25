/*
 * Copyright (c) 2019, IPD Koziolek. All rights reserved.
 */

public class RecursionSol {

    /**
     * Calculates a^b recursively.
     *
     * @param a - the base
     * @param b - the exponent (must be >= 0)
     */
    public static int pow(int a, int b) {
        if (b == 0) {
            return 1;
        }
        if (b % 2 == 0) {
            return pow(a * a, b / 2);
        }
        return pow(a * a, b / 2) * a;
    }

    /**
     * Calculates a*b recursively.
     *
     * @param a - the first factor
     * @param b - the second factor
     */
    public static int recursiveA(int a, int b) {
        if (b == 0) {
            return 0;
        }
        if (b % 2 == 0) {
            // replaced * with +
            return recursiveA(a + a, b / 2);
        }
        // replaced * with +
        return recursiveA(a + a, b / 2) + a;
    }
}
