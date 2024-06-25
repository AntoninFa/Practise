package edu.kit.informatik;

import java.util.regex.Pattern;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;

/**
 * Enum der die regex-Pattern für die Verschiedenen Benutzer Inputs vorgibt und die,
 * dem Befehl zugehörige Methode ausführt
 *
 * @author Antonin Fahning
 * @version 1.0
 */
public enum UserInput {

    /**
     * Gibt das regex-Pattern des addtrack Befehls für den matcher vor
     * Und übergibt die matcherGruppen
     */

    ADDTRACK("add track \\(" + UserInput.INTEGER_PATTERN + "," + UserInput.INTEGER_PATTERN
            + "\\) -> \\(" + UserInput.INTEGER_PATTERN + "," + UserInput.INTEGER_PATTERN + "\\)") {
        @Override
        public void runCommand(MatchResult matcher, RailwaySimulation railwaySimulation) {
            String startPointX = matcher.group(1);
            String startPointY = matcher.group(2);
            String endPointX = matcher.group(3);
            String endPointY = matcher.group(4);

            Point startPoint = new Point(Integer.parseInt(startPointX), Integer.parseInt(startPointY));
            Point endPoint = new Point(Integer.parseInt(endPointX), Integer.parseInt(endPointY));
            railwaySimulation.callAddTrack(startPoint, endPoint);
        }
    },

    /**
     * Gibt das pattern des addswitch Befehls für den matcher vor
     * Und übergibt die matcherGruppen
     */

    ADD_SWITCH("add switch \\(" + UserInput.INTEGER_PATTERN + "," + UserInput.INTEGER_PATTERN
            + "\\) -> \\(" + UserInput.INTEGER_PATTERN + "," + UserInput.INTEGER_PATTERN + "\\),\\("
            + UserInput.INTEGER_PATTERN + "," + UserInput.INTEGER_PATTERN + "\\)") {
        @Override
        public void runCommand(MatchResult matcher, RailwaySimulation railwaySimulation) {
            String startPointX = matcher.group(1);
            String startPointY = matcher.group(2);
            String endOnePointX = matcher.group(3);
            String endOnePointY = matcher.group(4);
            String endTwoPointX = matcher.group(5);
            String endTwoPointY = matcher.group(6);

            Point startPoint = new Point(Integer.parseInt(startPointX), Integer.parseInt(startPointY));
            Point endOnePoint = new Point(Integer.parseInt(endOnePointX), Integer.parseInt(endOnePointY));
            Point endTwoPoint = new Point(Integer.parseInt(endTwoPointX), Integer.parseInt(endTwoPointY));
            railwaySimulation.callAddSwitch(startPoint, endOnePoint, endTwoPoint);

        }
    },
    /**
     * Gibt das pattern des delete track Befehls für den matcher vor
     * * Und übergibt die matcherGruppen
     */
    DELETETRACK("delete track (" + UserInput.POSITIVE_INTEGER_PATTERN + ")") {
        @Override
        public void runCommand(MatchResult matcher, RailwaySimulation railwaySimulation) {
            int trackID;
            trackID = Integer.parseInt(matcher.group(1));
            railwaySimulation.callDeleteTrack(trackID);
        }
    },
    /**
     * Gibt das pattern des list tracks Befehls für den matcher vor
     * * Und übergibt die matcherGruppen
     */
    LISTTRACK("list tracks") {
        @Override
        public void runCommand(MatchResult matcher, RailwaySimulation railwaySimulation) {
            railwaySimulation.callListTracks();
        }
    },
    /**
     * Gibt das pattern des set switch Befehls für den matcher vor
     * Und übergibt die matcherGruppen
     */
    SETSWITCH("set switch " + UserInput.INTEGER_PATTERN + " position \\("
            + UserInput.INTEGER_PATTERN + "," + UserInput.INTEGER_PATTERN + "\\)") {
        @Override
        public void runCommand(MatchResult matcher, RailwaySimulation railwaySimulation) {
            int trackID;
            trackID = Integer.parseInt(matcher.group(1));
            String pointX = matcher.group(2);
            String pointY = matcher.group(3);
            Point point = new Point(Integer.parseInt(pointX), Integer.parseInt(pointY));

            railwaySimulation.callSetSwitch(trackID, point);
        }
    },
    /**
     * Gibt das pattern des create coach Befehls für den matcher vor
     * Und übergibt die matcherGruppen
     * <p>
     * Im Aufgabenblatt wird die Länge als 32-Bit-Ganzzahl angegeben weswegen eine Negative-Länge eigentlich
     * erlaubt sein sollte(könnte) im Forum wurde aber angegeben, dass negative Werte nicht zulässig sind weswegen
     * ich bei values < 0 eine Fehlermeldung ausgebe
     */
    CREATE_COACH("create coach (" + UserInput.COACH_TYPE_PATTERN + ") ("
            + UserInput.POSITIVE_NONZEROINTEGER_PATTERN + ") ("
            + UserInput.COUPLING_PATTERN + ") (" + UserInput.COUPLING_PATTERN + ")") {
        @Override
        public void runCommand(MatchResult matcher, RailwaySimulation railwaySimulation) {
            String coachType = matcher.group(1);
            String length = matcher.group(5);
            String cFront = matcher.group(6);
            String cBack = matcher.group(9);
            railwaySimulation.callCreateCoach(coachType, Integer.parseInt(length), Boolean.parseBoolean(cFront),
                    Boolean.parseBoolean(cBack));
        }
    },
    /**
     * Gibt das pattern des list coaches Befehls für den matcher vor
     * Und übergibt die matcherGruppen
     */
    LISTCOACHES("list coaches") {
        @Override
        public void runCommand(MatchResult matcher, RailwaySimulation railwaySimulation) {
            railwaySimulation.callListCoaches();
        }
    },
    /**
     * Gibt das pattern des create engine Befehls für den matcher vor
     * Und übergibt die matcherGruppen
     * Auch hier habe ich mich aus oben angeführtem Grund dazu entschieden nur Positive Id und length Werte
     * zuzulassen
     */
    CREATE_ENGINE("create engine (" + UserInput.ENGINE_TYPE_PATTERN + ") (" + UserInput.POWTRAIN_CLASS_PATTERN
            + ") (" + UserInput.POWTRAIN_NAME_PATTERN + ") (" + UserInput.POSITIVE_NONZEROINTEGER_PATTERN + ") ("
            + UserInput.COUPLING_PATTERN + ") (" + UserInput.COUPLING_PATTERN + ")") {
        @Override
        public void runCommand(MatchResult matcher, RailwaySimulation railwaySimulation) {
            String engineType = matcher.group(1);
            String engineClass = matcher.group(6);

            String engineName = matcher.group(8);
            String length = matcher.group(9);
            String cFront = matcher.group(10);
            String cBack = matcher.group(14);
            railwaySimulation.callCreatePowTrain(engineType, engineClass, engineName,
                    Integer.parseInt(length), Boolean.parseBoolean(cFront), Boolean.parseBoolean(cBack));

        }
    },
    /**
     * Gibt das pattern des list engines Befehls für den matcher vor
     * Und übergibt die matcherGruppen
     */
    LISTENGINES("list engines") {
        @Override
        public void runCommand(MatchResult matcher, RailwaySimulation railwaySimulation) {
            railwaySimulation.callListEngines();
        }
    },
    /**
     * Gibt das pattern des create train-set Befehls für den matcher vor
     * Und übergibt die matcherGruppen
     * Auch hier habe ich mich aus oben angeführtem Grund dazu entschieden nur Positive Id und length Werte
     * zuzulassen
     */
    CREATE_TRAINSET("create train-set (" + UserInput.POWTRAIN_CLASS_PATTERN
            + ") (" + UserInput.POWTRAIN_NAME_PATTERN + ") (" + UserInput.POSITIVE_INTEGER_PATTERN + ") ("
            + UserInput.COUPLING_PATTERN + ") (" + UserInput.COUPLING_PATTERN + ")") {
        @Override
        public void runCommand(MatchResult matcher, RailwaySimulation railwaySimulation) {
            String engineClass = matcher.group(1);
            String engineName = matcher.group(3);
            String length = matcher.group(5);
            String cFront = matcher.group(6);
            String cBack = matcher.group(9);
            railwaySimulation.callCreatePowTrain(null, engineClass, engineName,
                    Integer.parseInt(length), Boolean.parseBoolean(cFront), Boolean.parseBoolean(cBack));

        }
    },
    /**
     * Gibt das pattern des list train-sets Befehls für den matcher vor
     * Und übergibt die matcherGruppen
     */
    LISTTRAINSETS("list train-sets") {
        @Override
        public void runCommand(MatchResult matcher, RailwaySimulation railwaySimulation) {
            railwaySimulation.callListTrainSets();
        }
    },
    /**
     * Gibt das pattern des delete rolling stock Befehls für Wägen für den matcher vor
     * Und übergibt die matcherGruppen
     */
    DELETE_COACH("delete rolling stock W(" + UserInput.POSITIVE_INTEGER_PATTERN + ")") {
        @Override
        public void runCommand(MatchResult matcher, RailwaySimulation railwaySimulation) {
            String id = matcher.group(1);
            railwaySimulation.callDeleteCoach(Integer.parseInt(id));
        }
    },
    /**
     * Gibt das pattern des delete rolling stock Befehls für Antriebszüge für den matcher vor
     * Und übergibt die matcherGruppen
     */
    DELETE_POWT("delete rolling stock (" + UserInput.POWTRAIN_CLASS_PATTERN + ")-("
            + UserInput.POWTRAIN_NAME_PATTERN + ")") {
        @Override
        public void runCommand(MatchResult matcher, RailwaySimulation railwaySimulation) {
            String id1 = matcher.group(1);
            String id2 = matcher.group(3);
            String id = id1 + "-" + id2;

            railwaySimulation.callDeletePowT(id);
        }
    },
    /**
     * Gibt das pattern des add train Befehls für Wägen für den matcher vor
     * Und übergibt die matcherGruppen
     */
    ADDTRAIN_COACH("add train (" + UserInput.POSITIVE_INTEGER_PATTERN + ") W("
            + UserInput.POSITIVE_INTEGER_PATTERN + ")") {
        @Override
        public void runCommand(MatchResult matcher, RailwaySimulation railwaySimulation) {
            String trainId = matcher.group(1);
            String rSId = matcher.group(2);

            railwaySimulation.callAddTrainCoach(Integer.parseInt(trainId), Integer.parseInt(rSId));

        }
    },
    /**
     * Gibt das pattern des add train Befehls für Antriebszüge für den matcher vor
     * Und übergibt die matcherGruppen
     */
    ADDTRAIN_POWT("add train (" + UserInput.POSITIVE_INTEGER_PATTERN + ") ("
            + UserInput.POWTRAIN_ID_PATTERN + ")") {
        @Override
        public void runCommand(MatchResult matcher, RailwaySimulation railwaySimulation) {
            String trainId = matcher.group(1);
            String rSId = matcher.group(2);
            railwaySimulation.callAddTrainPow(Integer.parseInt(trainId), rSId);

        }
    },
    /**
     * Gibt das pattern des delete train Befehls für den matcher vor
     * Und übergibt die matcherGruppen
     */
    DELETE_TRAIN("delete train (" + UserInput.INTEGER_PATTERN + ")") {
        @Override
        public void runCommand(MatchResult matcher, RailwaySimulation railwaySimulation) {
            String id = matcher.group(1);
            railwaySimulation.callDeleteTrain(Integer.parseInt(id));
        }
    },
    /**
     * Gibt das pattern des list trains Befehls für den matcher vor
     * Und übergibt die matcherGruppen
     */
    LIST_TRAINS("list trains") {
        @Override
        public void runCommand(MatchResult matcher, RailwaySimulation railwaySimulation) {
            railwaySimulation.callListTrains();
        }
    },
    /**
     * Gibt das pattern des show train Befehls für den matcher vor
     * Und übergibt die matcherGruppen
     */
    SHOW_TRAIN("show train (" + UserInput.POSITIVE_INTEGER_PATTERN + ")") {
        @Override
        public void runCommand(MatchResult matcher, RailwaySimulation railwaySimulation) {
            String id = matcher.group(1);
            railwaySimulation.callShowTrain(Integer.parseInt(id));
        }
    },
    /**
     * Gibt das pattern des put train Befehls für den matcher vor
     * Und übergibt die matcherGruppen
     */
    PUT_TRAIN("put train (" + UserInput.POSITIVE_INTEGER_PATTERN + ") at " + "\\("
            + UserInput.INTEGER_PATTERN + "," + UserInput.INTEGER_PATTERN + "\\) " + "in direction "
            + UserInput.INTEGER_PATTERN + "," + UserInput.INTEGER_PATTERN) {
        @Override
        public void runCommand(MatchResult matcher, RailwaySimulation railwaySimulation) {
            String traindID = matcher.group(1);
            String startPointX = matcher.group(2);
            String startPointY = matcher.group(3);
            String dirPointX = matcher.group(4);
            String dirPointY = matcher.group(5);

            Point startPoint = new Point(Integer.parseInt(startPointX), Integer.parseInt(startPointY));
            Point endPoint = new Point(Integer.parseInt(dirPointX), Integer.parseInt(dirPointY));
            railwaySimulation.callPutTrain(Integer.parseInt(traindID), startPoint, endPoint);
        }
    },
    /**
     * Gibt das pattern des step Befehls für den matcher vor
     * Und übergibt die matcherGruppen
     */
    STEP("step (" + UserInput.INTEGER_PATTERN + ")") {
        @Override
        public void runCommand(MatchResult matcher, RailwaySimulation railwaySimulation) {
            String id = matcher.group(1);
            railwaySimulation.callStep(Short.parseShort(id));
        }
    },
    /**
     * Gibt das pattern des exit Befehls für den matcher vor
     * Und übergibt die matcherGruppen
     */
    EXIT("exit") {
        @Override
        public void runCommand(MatchResult matcher, RailwaySimulation railwaySimulation) {
            setStopped(true);
        }
    };

    private static final String INTEGER_PATTERN = "([0-9]|-?[1-9][0-9]*)";
    private static final String POSITIVE_INTEGER_PATTERN = "[0-9]+";
    private static final String POSITIVE_NONZEROINTEGER_PATTERN = "[1-9][0-9]*";
    private static final String COACH_TYPE_PATTERN = "(passenger)|(freight)|(special)";
    private static final String COUPLING_PATTERN = "(true)|(false)";
    private static final String ENGINE_TYPE_PATTERN = "(electrical)|(steam)|(diesel)";
    private static final String POWTRAIN_CLASS_PATTERN = "(?!W)+([\\p{L}0-9]+)+";
    private static final String POWTRAIN_ID_PATTERN = "[\\p{L}0-9]+-[\\p{L}0-9]+";
    private static final String POWTRAIN_NAME_PATTERN = "([\\p{L}0-9]+)+";
    private static final String INCORRECT_SYNTAX_MESSAGE = "Inkorrekte Syntax bei Befehlseingabe";
    private final Pattern pattern;
    private boolean stopped;


    /**
     * Konstruktor für den UserInput enum
     *
     * @param pattern Übergebenes String Pattern für die Syntaxüberprüfung
     */
    UserInput(String pattern) {
        this.pattern = Pattern.compile(pattern);
        setStopped(false);
    }

    /**
     * Getter für die Escape-Variable
     *
     * @return Escape-Variable
     */
    public boolean getProgramExit() {
        return this.stopped;
    }

    /**
     * Setter für die Escape-Variable
     *
     * @param stopped Escape-Variable
     */
    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    /**
     * Führt die für den gegebenen Befehl zuständige Methode in RailwaySimulation aus
     *
     * @param matcher           Matcher welcher die die die Matcher-Gruppen beinhaltet
     * @param railwaySimulation Klasse die für die Logik der Modelleisenbahn zuständig ist
     */
    public abstract void runCommand(MatchResult matcher, RailwaySimulation railwaySimulation);


    /**
     * Matcht den Userinput mit den gegebenen regex-Pattern und führt die dem Befehl zugehörige Methode aus
     *
     * @param consoleIn         UserInput (String)
     * @param railwaySimulation Klasse die für die Logik der Modelleisenbahn zuständig ist
     * @return den UserInput falls dieser nach Syntaxforschriften aufgebaut war
     * @throws InputException Input Exeption für falsche Syntax bei Usereingaben
     */
    public static UserInput runInputMatching(String consoleIn, RailwaySimulation railwaySimulation)
            throws InputException {
        for (UserInput input : UserInput.values()) {

            Matcher matcher = input.pattern.matcher(consoleIn);
            if (matcher.matches()) {
                input.runCommand(matcher, railwaySimulation);
                return input;
            }
        }
        throw new InputException(INCORRECT_SYNTAX_MESSAGE);
    }


}
