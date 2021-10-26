package input;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import projectExceptions.InputException;
import gameLogic.WordContLog;

public enum UserInput {
    START("start") {
        @Override
        protected void runCommand(final MatchResult matcher, final WordContLog wordContLog) {
            wordContLog.start();
        }
    },
    SPNUM("sp POSITIVE_INTEGER_PATTERN") {
        @Override
        protected void runCommand(final MatchResult matcher, final WordContLog wordContLog) {
            final String num = matcher.group(1);
            try {
                wordContLog.setNumOPlayers(Integer.parseInt(num));
            } catch (final NumberFormatException e) {
                System.out.println("ERROR 101");
                //TODO anständige Error Messages
            }

        }
    },

    GO("y") {
        @Override
        protected void runCommand(final MatchResult matcher, final WordContLog wordContLog) {
            wordContLog.go();
        }
    },

    QUIT("Beenden") {
        @Override
        protected void runCommand(final MatchResult matcher, final WordContLog wordContLog) {
            setGameOver(true);
        }
    };

    private static final String INCORRECT_SYNTAX_MESSAGE = "Inkorrekte Syntax bei Befehlseingabe";
    private static final String POSITIVE_INTEGER_PATTERN = "\\+?[0-9]+";

    private final Pattern pattern;
    private boolean gameOver = false;

    /**
     * Konstruktor für den UserInput enum
     *
     * @param pattern Übergebenes String Pattern für die Syntaxüberprüfung
     */
    UserInput(final String pattern) {
        this.pattern = Pattern.compile(pattern);
        setGameOver(false);
    }

    /**
     * Getter für die Escape-Variable
     *
     * @return Escape-Variable
     */
    public boolean getGameOver() {
        return this.gameOver;
    }

    /**
     * Setter für die Escape-Variable
     *
     * @param gameOver Escape-Variable
     */
    void setGameOver(final boolean gameOver) {
        this.gameOver = gameOver;
    }


    protected abstract void runCommand(MatchResult matcher, WordContLog wordContLog);


    public static UserInput runInputMatching(final String consoleIn, final WordContLog wordContLog)
            throws InputException {
        for (final UserInput input : UserInput.values()) {

            final Matcher matcher = input.pattern.matcher(consoleIn);
            if (matcher.matches()) {
                input.runCommand(matcher, wordContLog);
                return input;
            }
        }
        throw new InputException(INCORRECT_SYNTAX_MESSAGE);
    }


}
