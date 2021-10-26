public class Recursion {

    /**
     * Berechnet a^b
     * @param a Basis
     * @param b Exponent
     * @return Wenn b = 0 wird 1 zurückgegeben(Trivial-Fall), falls b gerade ist ruft sich die Methode
     * power selber wieder auf und übergibt dabei für a: a*a und für b: b/2,
     * falls b ungerade ist ruft sich die Methode power selber wieder auf und übergibt dabei für a: a * a und für
     * b: (b/2) * a
     *
     */
        public static int power(int a, int b) {
            if (b == 0) {
                return 1;
            }
            if (b % 2 == 0) {
                return power(a * a, b / 2);

            }
            return power(a * a, b / 2) * a;
        }

    /**
     *
     * @param a Erster Faktor
     * @param b Zweiter Faktor
     * @return Das Ergebnis aus a*b
     */

    public static int recursiveA( int a, int b) {
            if (b == 0) {
                // return von 0 auf 1 geändert
                return 0;
            }
            if (b % 2 == 0) {
                // * zu + verändert
                return power(a + a, b / 2);

            }
            // Beide * zu + verändert
            return power(a + a, b / 2) + a;
        }
    }

