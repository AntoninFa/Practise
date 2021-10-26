package edu.kit.informatik;

/**
 * enum der die verschiedenen Kartentypen des Spiels enthält
 *
 * @author Antonin Fahning
 * @version 1.0
 */
public enum Cards {
    /**
     * Ist eine Spielkarte der Kategorie Ressourcen (Holz) mit denen der Spieler
     * Gegenstände bauen kann
     * Die toString Methode gibt die Stringrepräsentation des Kartentyp zurück
     * Die getAmountOfCards Methode gibt die vom Aufgabenblatt vorgegebene Anzahl an Karten zurück
     */
    WOOD {
        @Override
        public String toString() {
            return WOOD_STRING_REP;
        }

        @Override
        protected byte getAmountOfCards() {
            return AMOUNT_OF_WOOD_CARDS;
        }
    },
    /**
     * Ist eine Spielkarte der Kategorie Ressourcen (Metall)  mit denen der Spieler
     * Gegenstände bauen kann
     * Die toString Methode gibt die Stringrepräsentation des Kartentyp zurück
     * Die getAmountOfCards Methode gibt die vom Aufgabenblatt vorgegebene Anzahl an Karten zurück
     */
    METAL {
        @Override
        public String toString() {
            return METAL_STRING_REP;
        }

        @Override
        protected byte getAmountOfCards() {
            return AMOUNT_OF_METAL_CARDS;
        }
    },
    /**
     * Ist eine Spielkarte der Kategorie Ressourcen (Plastic) mit denen der Spieler
     * Gegenstände bauen kann
     * Die toString Methode gibt die Stringrepräsentation des Kartentyp zurück
     * Die getAmountOfCards Methode gibt die vom Aufgabenblatt vorgegebene Anzahl an Karten zurück
     */
    PLASTIC {
        @Override
        public String toString() {
            return PLASTIC_STRING_REP;
        }

        @Override
        protected byte getAmountOfCards() {
            return AMOUNT_OF_PLASTIC_CARDS;
        }
    },
    /**
     * Ist eine Spielkarte der Kategorie feindliche Tiere hier muss der Spieler würfeln und
     * verliert gegebenenfalls alle seine Ressourcen
     * Die getAmountOfCards Methode gibt die vom Aufgabenblatt vorgegebene Anzahl an Karten zurück
     */
    SPIDER {
        @Override
        protected byte getAmountOfCards() {
            return AMOUNT_OF_SPIDER_CARDS;
        }
    },
    /**
     * Ist eine Spielkarte der Kategorie feindliche Tiere hier muss der Spieler würfeln und
     * verliert gegebenenfalls alle seine Ressourcen
     * Die getAmountOfCards Methode gibt die vom Aufgabenblatt vorgegebene Anzahl an Karten zurück
     */
    SNAKE {
        @Override
        protected byte getAmountOfCards() {
            return AMOUNT_OF_SNAKE_CARDS;
        }
    },
    /**
     * Ist eine Spielkarte der Kategorie feindliche Tiere hier muss der Spieler würfeln und
     * verliert gegebenenfalls alle seine Ressourcen
     * Die getAmountOfCards Methode gibt die vom Aufgabenblatt vorgegebene Anzahl an Karten zurück
     */
    TIGER {
        @Override
        protected byte getAmountOfCards() {
            return AMOUNT_OF_TIGER_CARDS;
        }
    },
    /**
     * Ist eine Spielkarte der Kategorie Katastrophe hier verliert der Spieler seine Feuerstelle und alle
     * seine gesammelten und bereits gezogenen Karten (Ausnahme: Die Hütte)
     * Die getAmountOfCards Methode gibt die vom Aufgabenblatt vorgegebene Anzahl an Karten zurück
     */
    THUNDERSTORM {
        @Override
        protected byte getAmountOfCards() {
            return AMOUNT_OF_THUNDERSTORM_CARDS;
        }
    },
    /**
     * default Typ falls Eingabe ungültig war
     */
    DEFAULT {
        @Override
        protected byte getAmountOfCards() {
            return -1;
        }
    };

    //Die vom Aufgabenblatt vorgegebene Anzahl an Karten wird hier oben festgelegt um zukünftige Spiel
    //veränderungen/erweiterungen zu erleichtern
    private static final byte AMOUNT_OF_WOOD_CARDS = 16;
    private static final byte AMOUNT_OF_METAL_CARDS = 16;
    private static final byte AMOUNT_OF_PLASTIC_CARDS = 16;
    private static final byte AMOUNT_OF_SPIDER_CARDS = 5;
    private static final byte AMOUNT_OF_SNAKE_CARDS = 5;
    private static final byte AMOUNT_OF_TIGER_CARDS = 5;
    private static final byte AMOUNT_OF_THUNDERSTORM_CARDS = 1;

    // Auflistung der Stringrepräsentationen
    private static final String WOOD_STRING_REP = "wood";
    private static final String METAL_STRING_REP = "metal";
    private static final String PLASTIC_STRING_REP = "plastic";
    private static final String SPIDER_STRING_REP = "spider";
    private static final String SNAKE_STRING_REP = "snake";
    private static final String TIGER_STRING_REP = "tiger";
    private static final String THUNDERSTORM_STRING_REP = "thunderstorm";

    /**
     * Faktory-Methode die den Strings den entsprechenden enum zuordnet
     * @param string String Eingabe des Spielers die eine Karte repräsentiert
     * @return den Kartentyp oder DEFAULT falls Eingabe ungültig
     */
    static Cards fromStringtoEnum(final String string) {
        switch(string) {
            case WOOD_STRING_REP:
                return WOOD;
            case METAL_STRING_REP:
                return METAL;
            case PLASTIC_STRING_REP:
                return PLASTIC;
            case SPIDER_STRING_REP:
                return SPIDER;
            case SNAKE_STRING_REP:
                return SNAKE;
            case TIGER_STRING_REP:
                return TIGER;
            case THUNDERSTORM_STRING_REP:
                return THUNDERSTORM;
            default:
                return DEFAULT;
        }
    }

    /**
     * Gibt die vom Aufgabenblatt vorgegebene Anzahl an Karten zurück
     * @return Anzahl an Karten in byte
     */
    protected abstract byte getAmountOfCards();

}
