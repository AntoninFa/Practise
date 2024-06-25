#include "parseint.h"

/*
 * Returns the value of c or -1 on error
 */
int parseDecimalChar(char c)
{
    (void)c;
// Check if char is a single decimal digit 
if ((c >= '0') && (c <= '9')) {
    int n = (c - '0');
    return n;
}

    return -1;
}

/*
 * Returns 1 if the char c is equal to zero(c == '0')
 *
*/
int isZero(char c) {
    (void)c;
    if(c == '0') {
        return 1;
    } else {
        return 0;
    }

}

/**
 * Returns the Length of a given String 
*/
int lengthOfString(char *string) {
    int n;
    for (n = 0; string[n] != '\0'; ++n);
    return n;
}

/**
 * Calculates x raised to the power y 
 * x^y 
 */
int power(int x, int y) {
    if (y == 0) {
        return 1;
    }
    else if (y % 2 == 0) {
        return power(x, y / 2) * power(x, y / 2); 
    }
    else {
        return x * power(x, y / 2) * power(x, y / 2); 
    }
}

/**
 * returns the non-negative integer of a given string, if it is provided
 * with the base of the input string
 */
int convertStringToIntWithBase(char *string, int base) {

    int decRes = 0;
    int loops = (lengthOfString(string) - 1);
        int i;
        for (i = loops; i >= 0; i--) {
            int x = parseDecimalChar(string[i]);
        
            if (x == -1) {
                return -1;
            } else {
                int r = power(base, loops - i);
                decRes += x * r;
            }
        } 
        return decRes;
}

/*
 * Parses a non-negative integer, interpreted as octal when starting with 0,
 * decimal otherwise. Returns -1 on error.
 */
int parseInt(char *string) {
    (void)string;
    
    if(isZero(string[0])) {
        // Octal:
        int base = 8;

        return convertStringToIntWithBase(string, base);
    } else {
        //Decimal:
        int base = 10;

        return convertStringToIntWithBase(string, base);
    }
    return -1;
}