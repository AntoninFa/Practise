package edu.kit.informatik;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Diese Klasse Modelliert/Realisiert das Insel-Kartenspiel
 * [Ressc wird als Abkürzung für Ressourcen verwendet]
 *
 * @author Antonin Fahning
 * @version 1.0
 */
class DangerIsland {

    // Enthält alle baubaren Gegenstände in Alphabetischer Reihenfolge
    private final Objects[] objectsAPlayerCouldBuild = new Objects[]{Objects.AXE, Objects.BALLON, Objects.CLUB
            , Objects.FIREPLACE, Objects.HANGGLIDER, Objects.SAILINGRAFT, Objects.SHACK, Objects.STEAMBOAT};
    /**
     * Enthält den Kartenstapel (Stack) einmal für den Spielbetrieb und ein 2tes mal für den reset Befehl
     */
    private ArrayList<Stack<String>> cardsStackArray;
    /**
     * Klasse die den Spielzustand enthält (zu beginn START)
     */
    private CurrentGameState currentGameState = new CurrentGameState(GamePhase.START);
    /**
     * Klasse die für den Kartenstapel zuständig ist
     */
    private CardLogik cardLog;
    /**
     * Diese Klasse ist für die Speicherung der Ressourcen zuständig
     */
    private final GameResources gameResources = new GameResources();
    /**
     * Diese Klasse ist für die Speicherung der vom Spieler gebauten Gegenstände zuständig
     */
    private final GameBuildObject gameBuildObj = new GameBuildObject();

    /**
     * Start des Spiels, empfängt den gegebenen Kartenstapel und speichert ihn in cardsStackArray
     *
     * @param cards gegebener Kartenstapel
     */
    void start(final Stack<String> cards) {
        if (currentGameState.gamePhaseEql(GamePhase.START) || currentGameState.gamePhaseEql(GamePhase.END)) {
            // Neues Spiel mit neuem Stapel
            if (currentGameState.gamePhaseEql(GamePhase.END)) {
                reset(false);
            }
            cardLog = new CardLogik(cards, gameResources, currentGameState);
            if (cardLog.validateDeck()) {
                cardsStackArray = new ArrayList<Stack<String>>(2);
                cardsStackArray.add(0, cards);
                cardsStackArray.add(1, cardLog.cloneStack());
                Terminal.printLine("OK");
                currentGameState.setGamePhase(GamePhase.SCAVENGE);
            } else { Terminal.printError("Der Kartenstapel ist leider nicht gültig, versuch es einfach nochmal :)"); }
        } else {
            Terminal.printError("In der Spielphase: \"" + currentGameState.getGamePhase().toString()
                    + "\" darf der start-Befehl leider nicht ausgeführt werden");
        }
    }

    /**
     * Zieht die Oberste Karte vom Stapel und führt mithilfe von cardLog die der Karte entsprechenden
     * Spielveränderungen durch
     */
    void draw() {
        if (currentGameState.gamePhaseEql(GamePhase.SCAVENGE)) {
            if (cardsStackArray.get(0).size() > 0) {
                final String[] res = cardLog.drawCard(cardsStackArray.get(0).peek());
                if (res[0].equals("build")) {
                    Terminal.printLine(res[1]);
                } else if (res[0].equals("Err")) {
                    Terminal.printError("Der Kartenstapel ist leer");
                } else {
                    deleteResExcShack();
                    gameBuildObj.removeValue(Objects.FIREPLACE);
                    Terminal.printLine(res[1]);
                }
                cardsStackArray.get(0).pop();
                if (cardsStackArray.get(0).size() == 0) {
                    if (currentGameState.gamePhaseEql(GamePhase.ENCOUNTER)) {
                        currentGameState.setLastCardDrawn(true);
                    } else {
                        currentGameState.setLastCardDrawn(true);
                        checkForGameEnd();
                    }
                }
            } else { Terminal.printError("Alle Karten gezogen"); }
        } else {
            Terminal.printError("In der Spielphase: \"" + currentGameState.getGamePhase().toString()
                    + "\" dürfen keine Karten gezogen werden");
        }
    }

    /**
     * listet die bereits gezogenen und nicht verbrauchten (also die zur Verfügung stehenden) Ressourcen auf
     */
    void listResc() {
        if (!currentGameState.gamePhaseEql(GamePhase.START)) {
            if (!gameResources.resIsEmpty()) {
                for (int n = 0; n < gameResources.getResSize(); n++) {
                    Terminal.printLine(gameResources.getRescAt(n).toString());
                }
            } else { Terminal.printLine("EMPTY"); }
        } else {
            Terminal.printError("In der Spielphase: \"" + currentGameState.getGamePhase().toString()
                    + "\" dürfen keine Karten gezogen werden");
        }
    }

    /**
     * Zuständig für den build Befehl wenn alle von der Aufgabenstellung geforderten bedingungen erfüllt sind
     * wird der Gegenstand gebaut / Gegebenenfalls Aktionen durchgeführt (siehe Rettung)
     *
     * @param buildableObject String der einen baubaren-Gegenstand repräsentiert
     */
    void build(final String buildableObject) {
        if (currentGameState.gamePhaseEql(GamePhase.SCAVENGE)) {
            if (!gameResources.resIsEmpty()) {
                boolean objectBuilt = false;
                boolean rescued = false;
                final Objects object = Objects.fromStringtoEnum(buildableObject);
                switch (object) {
                    case AXE:
                        objectBuilt = buildItem(Objects.AXE);
                        break;
                    case CLUB:
                        objectBuilt = buildItem(Objects.CLUB);
                        break;
                    case SHACK:
                        objectBuilt = buildItem(Objects.SHACK);
                        break;
                    case FIREPLACE:
                        objectBuilt = buildItem(Objects.FIREPLACE);
                        break;
                    case SAILINGRAFT:
                        objectBuilt = buildItem(Objects.SAILINGRAFT);
                        if (objectBuilt) { currentGameState.setGamePhase(GamePhase.ENDEAVOR); }
                        break;
                    case HANGGLIDER:
                        objectBuilt = buildItem(Objects.HANGGLIDER);
                        if (objectBuilt) { currentGameState.setGamePhase(GamePhase.ENDEAVOR); }
                        break;
                    case STEAMBOAT:
                        rescued = buildItem(Objects.STEAMBOAT);
                        if (rescued) { currentGameState.setGamePhase(GamePhase.END); }
                        break;
                    case BALLON:
                        rescued = buildItem(Objects.BALLON);
                        if (rescued) { currentGameState.setGamePhase(GamePhase.END); }
                        break;
                    default: Terminal.printError("Ungültiges Item ");
                        break;
                }
                if (objectBuilt) {
                    Terminal.printLine("OK");
                } else if (!rescued) {
                    Terminal.printError("Gegenstand konnte leider nicht gebaut werden");
                } else { Terminal.printLine("win"); }
            } else { Terminal.printError("Es sind leider keine Ressourcen vorhanden"); }
            if (currentGameState.isLastCardDrawn() && currentGameState.gamePhaseEql(GamePhase.SCAVENGE)) {
                checkForGameEnd();
            }
        } else {
            Terminal.printError("In der Spielphase: " + currentGameState.getGamePhase().toString()
                    + " darf leider nicht gebaut werden");
        }
    }

    /**
     * Baut den Gegenstand falls bauen möglich mit Vorhandenen Ressourcen und gegebenenfalls vorhandener Feuerstelle
     *
     * @param obj Gegenstand der Gebaut werden soll
     * @return true wenn gebaut
     */
    private boolean buildItem(final Objects obj) {
        if (!obj.needsFireplace()) {
            if (!gameBuildObj.bObjContainsV(obj)) {
                if (gameResources.checkForResscAndRemove(obj.returnRecipe())) {
                    gameBuildObj.addObj(obj);
                    return true;
                }
            }
        } else if (gameResources.checkForResscAndRemove(obj.returnRecipe())
                && gameBuildObj.bObjContainsV(Objects.FIREPLACE)) {
            gameBuildObj.addObj(obj);
            return true;
        } return false; }

    /**
     * Geht durch alle Gegenstände die theoretisch gebaut werden könnten und gibt ihre Stringrepräsentation
     * über Terminal.printLine aus wenn sie mit den vorhandenen Ressourcen gebaut
     * werden könnten (Alphabetisch geordnet). Falls Feuerstelle benötigt muss diese zur Ausgabe vorhanden sein
     * Wenn print false, wird nur die Anzahl an baubaren Gegenständen zurückgegeben
     *
     * @param print true wenn build? Befehl eingegeben wurde false wenn nur counter zurückgegeben werden soll
     * @return counter (Wie viele baubare Gegenstände existieren) [int]
     */
    int buildable(final boolean print) {
        int counter = 0;
        if (currentGameState.gamePhaseEql(GamePhase.SCAVENGE) || !print) {
            for (final Objects value : objectsAPlayerCouldBuild) {
                if (!gameBuildObj.bObjContainsV(value)) {
                    if (gameResources.onlyCheckForRessc(value.returnRecipe())) {
                        if (!value.needsFireplace()) {
                            if (print) {
                                Terminal.printLine(value.toString());
                            }
                            counter++;
                        } else if (gameBuildObj.bObjContainsV(Objects.FIREPLACE)) {
                            if (print) {
                                Terminal.printLine(value.toString());
                            } counter++; }
                    }
                }
            }
            if (!print) { return counter; }
            if (counter == 0) { Terminal.printLine("EMPTY"); }
        } else {
            Terminal.printError("In der Spielphase: " + currentGameState.getGamePhase().toString()
                    + " ist dieser Befehl leider nicht erlaubt");
        } return counter; }

    /**
     * Methode die für das Würfeln zuständig ist (Einfache if Abfragen ob Würfelergebnis
     * (mölgicherweise + bonus durch Waffe) größer als die Vorgegebene Schwelle ist
     *
     * Nach https://ilias.studium.kit.edu/goto.php?target=frm_1083287_138830_537254#537254 reicht es
     * nur genau x ∈ {4, 6, 8} zu erlauben da mir nicht direkt klar wurde ob dies für y genauso gelte, erlaube
     * ich für y sicherheitshalber
     * "Also alles was Integer.parseInt akzeptiert soll akzeptiert werden.
     * Alles was davon nicht akzeptiert wird, soll nicht akzeptiert werden"
     * Also auch eingaben wie > rollD4 +0000005
     *
     * @param diceTypeInput Zahl die der Spieler als Seitenanzahl eingibt
     * @param result        Zahl die der Spieler als Würfelergebnis eingibt
     */
    void roll(final int diceTypeInput, final int result) {
        if (currentGameState.gamePhaseEql(GamePhase.ENCOUNTER) || currentGameState.gamePhaseEql(GamePhase.ENDEAVOR)) {
            boolean invalidDiceType = true;
            boolean invalidRollRes = true;
            final DiceType currentRoll = DiceType.fromNumToEnum(diceTypeInput);
            switch (currentRoll) {
                case FOUR_SIDED_DICE:
                    if (currentGameState.getEncounterType().equals(EncounterType.SPIDER)) {
                        invalidDiceType = false;
                        if (0 < result && result <= diceTypeInput) {
                            invalidRollRes = false;
                            if ((result + gameBuildObj.getFightBonus()) > EncounterType.SPIDER.getThreshold()) {
                                Terminal.printLine("survived");
                            } else {
                                Terminal.printLine("lose");
                                deleteResExcShack();
                            }
                            currentGameState.setGamePAndEncountTy(GamePhase.SCAVENGE, EncounterType.NONE);
                        }
                    }
                    break;
                case SIX_SIDED_DICE:
                    if (currentGameState.gamePhaseEql(GamePhase.ENDEAVOR)) {
                        invalidDiceType = false;
                        if (0 < result && result <= diceTypeInput) {
                            invalidRollRes = false;
                            if (result > EndeavorType.NORMAL_ENDEAVOR.getThreshold()) {
                                Terminal.printLine("win");
                                currentGameState.setGamePhase(GamePhase.END);
                            } else {
                                Terminal.printLine("lose");
                                currentGameState.setGamePhase(GamePhase.SCAVENGE);
                            }
                        }
                    } else if (currentGameState.getEncounterType().equals(EncounterType.SNAKE)) {
                        invalidDiceType = false;
                        if (0 < result && result <= diceTypeInput) {
                            invalidRollRes = false;
                            if ((result + gameBuildObj.getFightBonus()) > EncounterType.SNAKE.getThreshold()) {
                                Terminal.printLine("survived");
                            } else {
                                Terminal.printLine("lose");
                                deleteResExcShack();
                            }
                            currentGameState.setGamePAndEncountTy(GamePhase.SCAVENGE, EncounterType.NONE);
                        }
                    }
                    break;
                case EIGHT_SIDED_DICE:
                    if (currentGameState.getEncounterType().equals(EncounterType.TIGER)) {
                        invalidDiceType = false;
                        if (0 < result && result <= diceTypeInput) {
                            invalidRollRes = false;
                            if ((result + gameBuildObj.getFightBonus()) > EncounterType.TIGER.getThreshold()) {
                                Terminal.printLine("survived");
                            } else {
                                Terminal.printLine("lose");
                                deleteResExcShack();
                            } currentGameState.setGamePAndEncountTy(GamePhase.SCAVENGE, EncounterType.NONE); }
                    }
                    break;
                default: invalidDiceType = true;
            }
            if (invalidDiceType) { Terminal.printError(diceTypeInput + " Ist eine ungültige Seitenanzahl"); }
            if (invalidRollRes && !invalidDiceType) {
                Terminal.printError(result + " Ist eine ungültiges Würfelergebnis");
            }
        } else {
            Terminal.printError("In der Spielphase: " + currentGameState.getGamePhase().toString()
                    + " kann leider nicht gewürfelt werden");
        }
        if (currentGameState.gamePhaseEql(GamePhase.SCAVENGE) && currentGameState.isLastCardDrawn()) {
            checkForGameEnd();
        }
    }

    /**
     * Löscht alle Ressourcen die nicht in der Hütte gespeichert sind
     */
    private void deleteResExcShack() {
        if (gameBuildObj.bObjContainsV(Objects.SHACK)) {
            gameResources.delExcShack();
        } else { gameResources.clearResc(); }
    }

    /**
     * listet die gebauten Gegenstände auf, die sich aktuell
     * noch im Besitz des Spielers befinden
     */
    void listBuildings() {
        if (!currentGameState.gamePhaseEql(GamePhase.START)) {
            if (!gameBuildObj.bObjIsEmpty()) {
                // Ausgabe Zeilenweise nach Bauzeitpunkt
                for (int n = gameBuildObj.getBObjSize() - 1; n >= 0; n--) {
                    Terminal.printLine(gameBuildObj.getObjAt(n).toString());
                }
            } else { Terminal.printLine("EMPTY"); }
        } else {
            Terminal.printError("In der Spielphase: \"" + currentGameState.getGamePhase().toString()
                    + "\" dürfen keine Karten gezogen werden");
        }
    }

    /**
     * Initialisiert das Kartenspiel neu
     *
     * @param withoutStart true wenn der Kartenstapel gelöscht werden soll false wenn der Kartenstapel
     *                     behalten werden soll
     */
    void reset(final boolean withoutStart) {
        if (!currentGameState.gamePhaseEql(GamePhase.START)) {
            gameResources.clearResc();
            gameBuildObj.clearBObj();
            if (withoutStart) {
                final CardLogik clogReset = new CardLogik(cardsStackArray.get(1)
                        , gameResources, currentGameState);
                cardsStackArray.set(0, clogReset.cloneStack());
                currentGameState.setGamePhase(GamePhase.SCAVENGE);
                Terminal.printLine("OK");
            } else {
                cardsStackArray.clear();
                currentGameState = new CurrentGameState(GamePhase.START);
            }
        } else {
            Terminal.printError("In der Spielphase: \"" + currentGameState.getGamePhase().toString()
                    + "\" ist der reset-Befehl leider nicht erlaubt");
        }
    }

    /**
     * Schaut mithilfe der buildable Methode (nachdem die letzte Karte vom Stapel gezogen wurde)
     * ob der Spieler mit den in seinem Besitz befindlichen Ressourcen noch
     * etwas bauen könnte, wenn das nicht der Fall ist (und er nicht noch eine Aktion ausführen muss(würfeln))
     * hat er das Spiel nach Vorgaben verloren
     */
    private void checkForGameEnd() {
        if (buildable(false) < 1) {
            Terminal.printLine("lost");
            currentGameState.setGamePhase(GamePhase.END);
        }
    }
}
