package edu.kit.informatik;

import java.util.Stack;

/**
 * Diese Klasse ist für den Kartenstapel zuständig
 *
 * @author Antonin Fahning
 * @version 1.0
 */
class CardLogik {


    private final Stack<String> cards;
    /**
     * Klasse die den Spielzustand enthält
     */
    private final CurrentGameState currentGameState;
    /**
     * Diese Klasse ist für die Speicherung der Ressourcen zuständig
     */
    private final GameResources gameResources;

    /**
     * Konstruktor für die Klasse die für den Kartenstapel zuständig ist
     * @param cards der Kartenstapel
     * @param gameResources Diese Klasse ist für die Speicherung der Ressourcen zuständig
     * @param currentGameState Klasse die den Spielzustand enthält
     */
    CardLogik(final Stack<String> cards, final GameResources gameResources
            , final CurrentGameState currentGameState) {
        this.cards = cards;
        this.gameResources = gameResources;
        this.currentGameState = currentGameState;
    }

    /**
     * Gibt eine Kopie des Kartenstapels zurück
     *
     * @return Kopie des Kartenstapels
     */
    Stack<String> cloneStack() {
        final Stack<String> cardsClone = new Stack<>();
        for (final String card : cards) {
            cardsClone.push(card);
        }
        return cardsClone;
    }

    /**
     * Überprüft den Kartenstapel auf Gültigkeit (Ob der Stapel die passende Kartentyp-Anzahl hat)
     *
     * @return true wenn Kartenstapel gültig
     */
    boolean validateDeck() {
        //Counter die hochzählen wenn im Stapel gefunden
        int wood = 0;
        int metal = 0;
        int plastic = 0;
        int spider = 0;
        int snake = 0;
        int tiger = 0;
        int thunderstorm = 0;
        for (final String card : cards) {
            final Cards oneCard = Cards.fromStringtoEnum(card);
            switch (oneCard) {
                case WOOD:
                    wood++;
                    break;
                case METAL:
                    metal++;
                    break;
                case PLASTIC:
                    plastic++;
                    break;
                case SPIDER:
                    spider++;
                    break;
                case SNAKE:
                    snake++;
                    break;
                case TIGER:
                    tiger++;
                    break;
                case THUNDERSTORM:
                    thunderstorm++;
                    break;
                default:
                    break;
            }
        }
        // vergleicht Vorkommen mit vorgegebener Anzahl
        return (wood == Cards.WOOD.getAmountOfCards())
                && (metal == Cards.METAL.getAmountOfCards())
                && (plastic == Cards.PLASTIC.getAmountOfCards())
                && (spider == Cards.SPIDER.getAmountOfCards())
                && (snake == Cards.SNAKE.getAmountOfCards())
                && (tiger == Cards.TIGER.getAmountOfCards())
                && (thunderstorm == Cards.THUNDERSTORM.getAmountOfCards());
    }

    /**
     * Die Methode führt die durch das Ziehen einer Karte gegebenen Spielverönderungen durch und gibt zurück
     * was für Terminal ausgaben gebraucht werden.
     *
     * @param card die gezogene Karte
     * @return String[] der an index 0 spezifiert ob die Karte gebaut werden muss, die Katastrophenkarte gezogen
     * wurde oder der default case erreicht wurde. Index 1 enthält dann den auszugebenen String (falls nötig)
     */
    String[] drawCard(final String card) {
        final String[] result;
        final Cards oneCard = Cards.fromStringtoEnum(card);
        switch (oneCard) {
            case WOOD:
                gameResources.addResc(Cards.WOOD);
                result = new String[]{"build", "wood"};
                break;
            case METAL:
                gameResources.addResc(Cards.METAL);
                result = new String[]{"build", "metal"};
                break;
            case PLASTIC:
                gameResources.addResc(Cards.PLASTIC);
                result = new String[]{"build", "plastic"};
                break;
            case SPIDER:
                result = new String[]{"build", "spider"};
                currentGameState.setGamePAndEncountTy(GamePhase.ENCOUNTER, EncounterType.SPIDER);
                break;
            case SNAKE:
                result = new String[]{"build", "snake"};
                currentGameState.setGamePAndEncountTy(GamePhase.ENCOUNTER, EncounterType.SNAKE);
                break;
            case TIGER:
                result = new String[]{"build", "tiger"};
                currentGameState.setGamePAndEncountTy(GamePhase.ENCOUNTER, EncounterType.TIGER);
                break;
            case THUNDERSTORM:
                result = new String[]{"", "thunderstorm"};
                break;
            default: result = new String[]{"Err", ""};
        }
        return result;
    }

}
