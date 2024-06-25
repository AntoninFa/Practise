package edu.kit.informatik;

/**
 * Input Exeption für falsche Syntax bei Usereingaben, erbt von Exception
 * und beinhalet eine passende Error Nachricht (String)
 *
 * @author Antonin Fahning
 * @version 1.0
 */

class InputException extends Exception {


    /**
     * Konstruktor für die Input-Exception
     * @param inputError Fehlernachricht
     */
    public InputException(final String inputError) {
        super(inputError);
    }
}
