package edu.kit.informatik;

import java.util.Stack;
import java.util.regex.Pattern;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;

/**
 * Enum der die regex-Pattern für die Verschiedenen Benutzer Inputs vorgibt
 * und die dem Befehl zugehörige Methode ausführt
 *
 * @author Antonin Fahning
 * @version 1.0
 */
public enum UserInput {

    /**
     * Gibt das pattern des addtrack Befehls für den matcher vor
     * Und übergibt die matcherGruppen
     */
    START("start (" + UserInput.CARDS_PATTERN + "),(" + UserInput.CARDS_PATTERN + "),("
            + UserInput.CARDS_PATTERN + "),(" + UserInput.CARDS_PATTERN + "),(" + UserInput.CARDS_PATTERN
            + "),(" + UserInput.CARDS_PATTERN + "),(" + UserInput.CARDS_PATTERN + "),("
            + UserInput.CARDS_PATTERN + "),(" + UserInput.CARDS_PATTERN + "),(" + UserInput.CARDS_PATTERN + "),("
            + UserInput.CARDS_PATTERN + "),(" + UserInput.CARDS_PATTERN + "),(" + UserInput.CARDS_PATTERN
            + "),(" + UserInput.CARDS_PATTERN + "),(" + UserInput.CARDS_PATTERN + "),("
            + UserInput.CARDS_PATTERN + "),(" + UserInput.CARDS_PATTERN + "),(" + UserInput.CARDS_PATTERN + "),("
            + UserInput.CARDS_PATTERN + "),(" + UserInput.CARDS_PATTERN + "),(" + UserInput.CARDS_PATTERN
            + "),(" + UserInput.CARDS_PATTERN + "),(" + UserInput.CARDS_PATTERN + "),("
            + UserInput.CARDS_PATTERN + "),(" + UserInput.CARDS_PATTERN + "),(" + UserInput.CARDS_PATTERN + "),("
            + UserInput.CARDS_PATTERN + "),(" + UserInput.CARDS_PATTERN + "),(" + UserInput.CARDS_PATTERN
            + "),(" + UserInput.CARDS_PATTERN + "),(" + UserInput.CARDS_PATTERN + "),("
            + UserInput.CARDS_PATTERN + "),(" + UserInput.CARDS_PATTERN + "),(" + UserInput.CARDS_PATTERN + "),("
            + UserInput.CARDS_PATTERN + "),(" + UserInput.CARDS_PATTERN + "),(" + UserInput.CARDS_PATTERN
            + "),(" + UserInput.CARDS_PATTERN + "),(" + UserInput.CARDS_PATTERN + "),("
            + UserInput.CARDS_PATTERN + "),(" + UserInput.CARDS_PATTERN + "),(" + UserInput.CARDS_PATTERN + "),("
            + UserInput.CARDS_PATTERN + "),(" + UserInput.CARDS_PATTERN + "),(" + UserInput.CARDS_PATTERN
            + "),(" + UserInput.CARDS_PATTERN + "),(" + UserInput.CARDS_PATTERN + "),("
            + UserInput.CARDS_PATTERN + "),(" + UserInput.CARDS_PATTERN + "),(" + UserInput.CARDS_PATTERN + "),("
            + UserInput.CARDS_PATTERN + "),(" + UserInput.CARDS_PATTERN + "),(" + UserInput.CARDS_PATTERN
            + "),(" + UserInput.CARDS_PATTERN + "),(" + UserInput.CARDS_PATTERN + "),("
            + UserInput.CARDS_PATTERN + "),(" + UserInput.CARDS_PATTERN + "),(" + UserInput.CARDS_PATTERN + "),("
            + UserInput.CARDS_PATTERN + "),(" + UserInput.CARDS_PATTERN + "),(" + UserInput.CARDS_PATTERN
            + "),(" + UserInput.CARDS_PATTERN + "),(" + UserInput.CARDS_PATTERN + "),("
            + UserInput.CARDS_PATTERN + ")") {
        @Override
        protected void runCommand(final MatchResult matcher, final DangerIsland dangerIsland) {
            final Stack<String> cards = new Stack<>();

            for (int z = 64; z > 0; z--) {
                cards.push(matcher.group(z));
            }
            dangerIsland.start(cards);
        }
    },

    /**
     * Gibt das pattern des draw Befehls für den matcher vor und setzt GameOver auf true
     */
    DRAW("draw") {
        @Override
        protected void runCommand(final MatchResult matcher, final DangerIsland dangerIsland) {
            dangerIsland.draw();
        }
    },

    /**
     * Gibt das pattern des list-resources Befehls für den matcher vor und setzt GameOver auf true
     */
    LIST_RESOURCES("list-resources") {
        @Override
        protected void runCommand(final MatchResult matcher, final DangerIsland dangerIsland) {
            dangerIsland.listResc();
        }
    },
    /**
     * Gibt das pattern des build Befehls für den matcher vor und setzt GameOver auf true
     */
    BUILD("build (" + UserInput.CRAFTABLES_PATTERN + ")") {
        @Override
        protected void runCommand(final MatchResult matcher, final DangerIsland dangerIsland) {
            final String buildableObject = matcher.group(1);
            dangerIsland.build(buildableObject);
        }
    },
    /**
     * Gibt das pattern des build? Befehls für den matcher vor und setzt GameOver auf true
     */
    BUILD_QUESTIONMARK("build\\?") {
        @Override
        protected void runCommand(final MatchResult matcher, final DangerIsland dangerIsland) {
            dangerIsland.buildable(true);
        }
    },


    /**
     * Gibt das pattern des rollD Befehls für den matcher vor und setzt GameOver auf true
     *
     * Mir wurde aus der Aufgabenstellung nicht ganz klar ob die Eingaben für y auch den MAX-Integer Wert
     * überschreiten können, es soll wohl im Forum gesagt worden sein, ich konnte den passenden Forum Eintrag
     * aber leider nicht finden weswegen ich eine Number-Format Exeption fange und nur eine Error Nachricht ausgebe
     */
    ROLLD("rollD(" + UserInput.POSITIVE_INTEGER_PATTERN + ") ("
            + UserInput.POSITIVE_INTEGER_PARSE_PATTERN + ")") {
        @Override
        protected void runCommand(final MatchResult matcher, final DangerIsland dangerIsland) {
            final String diceType = matcher.group(1);
            final String result = matcher.group(2);
            try {
                dangerIsland.roll(Integer.parseInt(diceType), Integer.parseInt(result));
            } catch (final NumberFormatException e) {
                Terminal.printError(e.getMessage());
            }

        }
    },

    /**
     * Gibt das pattern des list-buildings Befehls für den matcher vor und setzt GameOver auf true
     */
    LIST_BUILDINGS("list-buildings") {
        @Override
        protected void runCommand(final MatchResult matcher, final DangerIsland dangerIsland) {
            dangerIsland.listBuildings();
        }
    },

    /**
     * Gibt das pattern des list-buildings Befehls für den matcher vor und setzt GameOver auf true
     */
    RESET("reset") {
        @Override
        protected void runCommand(final MatchResult matcher, final DangerIsland dangerIsland) {
            dangerIsland.reset(true);
        }
    },
    /**
     * Gibt das pattern des quit Befehls für den matcher vor und setzt GameOver auf true
     */
    QUIT("quit") {
        @Override
        protected void runCommand(final MatchResult matcher, final DangerIsland dangerIsland) {
            setGameOver(true);
        }
    };

    private static final String INCORRECT_SYNTAX_MESSAGE = "Inkorrekte Syntax bei Befehlseingabe";
    private static final String CARDS_PATTERN = "wood|metal|plastic|spider|snake|tiger|thunderstorm";
    private static final String CRAFTABLES_PATTERN = "axe|club|shack|fireplace|sailingraft|hangglider|steamboat|ballon";
    private static final String POSITIVE_INTEGER_PATTERN = "[0-9]+";
    private static final String POSITIVE_INTEGER_PARSE_PATTERN = "\\+?[0-9]+";
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
    boolean getGameOver() {
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

    /**
     * Führt die für den gegebenen Befehl zuständige Methode in RailwaySimulation aus
     *
     * @param matcher   Matcher welcher die die die Matcher-Gruppen beinhaltet
     * @param dangerIsland Klasse die für die Logik des Spieles zuständig ist
     */
    protected abstract void runCommand(MatchResult matcher, DangerIsland dangerIsland);

    /**
     * Matcht den Userinput mit den gegebenen regex-Pattern und führt die dem Befehl zugehörige Methode aus
     *
     * @param consoleIn UserInput (String)
     * @param dangerIsland Klasse die das Kartenspiel Modelliert/Realisiert
     * @return den UserInput falls dieser nach Syntaxforschriften aufgebaut war
     * @throws InputException Input Exeption für falsche Syntax bei Usereingaben
     */
    static UserInput runInputMatching(final String consoleIn, final DangerIsland dangerIsland)
            throws InputException {
        for (final UserInput input : UserInput.values()) {

            final Matcher matcher = input.pattern.matcher(consoleIn);
            if (matcher.matches()) {
                input.runCommand(matcher, dangerIsland);
                return input;
            }
        }
        throw new InputException(INCORRECT_SYNTAX_MESSAGE);
    }

}
