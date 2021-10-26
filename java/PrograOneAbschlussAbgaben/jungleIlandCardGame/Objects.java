package edu.kit.informatik;

/**
 * enum der die verschiedenen Gegenstände des Spiels enthälte
 *
 * @author Antonin Fahning
 * @version 1.0
 */
public enum Objects {

    /**
     * Ist ein Spielgegenstand der dem Spieler beim Würfeln im Kampf gegen ein Tier einen Bonus von 2 gibt
     * Die toString Methode gibt die Stringrepräsentation der Axt zurück
     * Die returnReceip Methode gibt die Ressourcen zurück welche für den Bau einer Axt gebraucht werden
     * Die needsFireplace Methode gibt an ob für den Bau des Gegenstands eine Feuerstelle benötigt wird ist nur
     * für Ballon und Steamboat true
     */
    AXE {
        @Override
        protected Cards[] returnRecipe() {
            return AXE_RECIPE;
        }

        @Override
        public String toString() {
            return AXE_STRING_REP;
        }

        @Override
        protected boolean needsFireplace() {
            return AXE_NEEDS_FIREPLACE;
        }

    },
    /**
     * Ist ein Spielgegenstand der dem Spieler beim Würfeln im Kampf gegen ein Tier einen Bonus von 1 gibt
     * Die toString Methode gibt die Stringrepräsentation der Keule zurück
     * Die returnReceip Methode gibt die Ressourcen zurück welche für den Bau einer Keule gebraucht werden
     * Die needsFireplace Methode gibt an ob für den Bau des Gegenstands eine Feuerstelle benötigt wird ist nur
     * für Ballon und Steamboat true
     */
    CLUB {
        @Override
        protected Cards[] returnRecipe() {
            return CLUB_RECIPE;
        }

        @Override
        public String toString() {
            return CLUB_STRING_REP;
        }

        @Override
        protected boolean needsFireplace() {
            return CLUB_NEEDS_FIREPLACE;
        }

    },
    /**
     * Hütte in der 5 Ressourcen gespeichert werden können
     * Die toString Methode gibt die Stringrepräsentation der Hütte zurück
     * Die returnReceip Methode gibt die Ressourcen zurück welche für den Bau einer Hütte gebraucht werden
     * Die needsFireplace Methode gibt an ob für den Bau des Gegenstands eine Feuerstelle benötigt wird ist nur
     * für Ballon und Steamboat true
     */
    SHACK {
        @Override
        protected Cards[] returnRecipe() {
            return SHACK_RECIPE;
        }

        @Override
        public String toString() {
            return SHACK_STRING_REP;
        }

        @Override
        protected boolean needsFireplace() {
            return SHACK_NEEDS_FIREPLACE;
        }

    },
    /**
     * Ist ein Spielgegenstand der dem Spieler das bauen der garantierten Rettungen ermöglicht
     * Die toString Methode gibt die Stringrepräsentation der Feuerstelle zurück
     * Die returnReceip Methode gibt die Ressourcen zurück welche für den Bau einer Feuerstelle gebraucht werden
     * Die needsFireplace Methode gibt an ob für den Bau des Gegenstands eine Feuerstelle benötigt wird ist nur
     * für Ballon und Steamboat true
     */
    FIREPLACE {
        @Override
        protected Cards[] returnRecipe() {
            return FIREPLACE_RECIPE;
        }

        @Override
        public String toString() {
            return FIREPLACE_STRING_REP;
        }

        @Override
        protected boolean needsFireplace() {
            return FIREPLACE_NEEDS_FIREPLACE;
        }

    },
    /**
     * Ist ein Spielgegenstand der Kategorie Rettung
     * Die toString Methode gibt die Stringrepräsentation des Segelboots zurück
     * Die returnReceip Methode gibt die Ressourcen zurück welche für den Bau des Segelboots gebraucht werden
     * Die needsFireplace Methode gibt an ob für den Bau des Gegenstands eine Feuerstelle benötigt wird ist nur
     * für Ballon und Steamboat true
     */
    SAILINGRAFT {
        @Override
        protected Cards[] returnRecipe() {
            return SAILINGRAFT_RECIPE;
        }

        @Override
        public String toString() {
            return SAILINGRAFT_STRING_REP;
        }

        @Override
        protected boolean needsFireplace() {
            return SAILINGRAFT_NEEDS_FIREPLACE;
        }

    },
    /**
     * Ist ein Spielgegenstand der Kategorie Rettung
     * Die toString Methode gibt die Stringrepräsentation des Gleiters zurück
     * Die returnReceip Methode gibt die Ressourcen zurück welche für den Bau des Gleiters gebraucht werden
     * Die needsFireplace Methode gibt an ob für den Bau des Gegenstands eine Feuerstelle benötigt wird ist nur
     * für Ballon und Steamboat true
     */
    HANGGLIDER {
        @Override
        protected Cards[] returnRecipe() {

            return HANGGLIDER_RECIPE;
        }

        @Override
        public String toString() {
            return HANGGLIDER_STRING_REP;
        }

        @Override
        protected boolean needsFireplace() {
            return HANGGLIDER_NEEDS_FIREPLACE;
        }

    },
    /**
     * Ist ein Spielgegenstand der Kategorie Garantierte-Rettung
     * Die toString Methode gibt die Stringrepräsentation des Dampfschiffes zurück
     * Die returnReceip Methode gibt die Ressourcen zurück welche für den Bau eines Dampfschiffes gebraucht werden
     * Die needsFireplace Methode gibt an ob für den Bau des Gegenstands eine Feuerstelle benötigt wird ist nur
     * für Ballon und Steamboat true
     */

    STEAMBOAT {
        @Override
        protected Cards[] returnRecipe() {
            return STEAMBOAT_RECIPE;
        }

        @Override
        public String toString() {
            return STEAMBOAT_STRING_REP;
        }

        @Override
        protected boolean needsFireplace() {
            return STEAMBOAT_NEEDS_FIREPLACE;
        }

    },
    /**
     * Ist ein Spielgegenstand der Kategorie Garantierte-Rettung
     * Die toString Methode gibt die Stringrepräsentation eines Ballons zurück
     * Die returnReceip Methode gibt die Ressourcen zurück welche für den Bau eines Ballons gebraucht werden
     * Die needsFireplace Methode gibt an ob für den Bau des Gegenstands eine Feuerstelle benötigt wird ist nur
     * für Ballon und Steamboat true
     */
    BALLON {
        @Override
        protected Cards[] returnRecipe() {
            return BALLON_RECIPE;
        }

        @Override
        public String toString() {
            return BALLON_STRING_REP;
        }

        @Override
        protected boolean needsFireplace() {
            return BALLON_NEEDS_FIREPLACE;
        }
    },
    /**
     * default Typ falls eingabe ungültig war
     */
    DEFAULT {
        @Override
        protected Cards[] returnRecipe() {
            return null;
        }

        @Override
        public String toString() {
            return "";
        }

        @Override
        protected boolean needsFireplace() {
            return false;
        }
    };

    // Ressourcen-Kombinationen der baubaren Gegenstände
    private static final Cards[] AXE_RECIPE = new Cards[]{Cards.METAL, Cards.METAL, Cards.METAL};

    private static final Cards[] CLUB_RECIPE = new Cards[]{Cards.WOOD, Cards.WOOD, Cards.WOOD};

    private static final Cards[] SHACK_RECIPE = new Cards[]{Cards.WOOD, Cards.WOOD
            , Cards.METAL
            , Cards.PLASTIC, Cards.PLASTIC};

    private static final Cards[] FIREPLACE_RECIPE = new Cards[]{Cards.WOOD, Cards.WOOD, Cards.WOOD
            , Cards.METAL};

    private static final Cards[] SAILINGRAFT_RECIPE = new Cards[]{Cards.WOOD, Cards.WOOD, Cards.WOOD, Cards.WOOD
            , Cards.METAL, Cards.METAL
            , Cards.PLASTIC, Cards.PLASTIC};

    private static final Cards[] HANGGLIDER_RECIPE = new Cards[]{Cards.WOOD, Cards.WOOD
            , Cards.METAL, Cards.METAL
            , Cards.PLASTIC, Cards.PLASTIC, Cards.PLASTIC, Cards.PLASTIC};

    private static final Cards[] STEAMBOAT_RECIPE = new Cards[]{Cards.METAL, Cards.METAL, Cards.METAL
            , Cards.METAL, Cards.METAL, Cards.METAL
            , Cards.PLASTIC};

    private static final Cards[] BALLON_RECIPE = new Cards[]{Cards.WOOD
            , Cards.PLASTIC, Cards.PLASTIC, Cards.PLASTIC, Cards.PLASTIC, Cards.PLASTIC, Cards.PLASTIC};

    // true wenn zum Bau eine Feuerstelle benötigt wird
    private static final boolean AXE_NEEDS_FIREPLACE = false;
    private static final boolean CLUB_NEEDS_FIREPLACE = false;
    private static final boolean SHACK_NEEDS_FIREPLACE = false;
    private static final boolean FIREPLACE_NEEDS_FIREPLACE = false;
    private static final boolean SAILINGRAFT_NEEDS_FIREPLACE = false;
    private static final boolean HANGGLIDER_NEEDS_FIREPLACE = false;
    private static final boolean STEAMBOAT_NEEDS_FIREPLACE = true;
    private static final boolean BALLON_NEEDS_FIREPLACE = true;

    // Auflistung der Stringrepräsentationen
    private static final String AXE_STRING_REP = "axe";
    private static final String CLUB_STRING_REP = "club";
    private static final String SHACK_STRING_REP = "shack";
    private static final String FIREPLACE_STRING_REP = "fireplace";
    private static final String SAILINGRAFT_STRING_REP = "sailingraft";
    private static final String HANGGLIDER_STRING_REP = "hangglider";
    private static final String STEAMBOAT_STRING_REP = "steamboat";
    private static final String BALLON_STRING_REP = "ballon";


    /**
     * Faktory-Methode die den Strings den entsprechenden enum zuordnet
     * @param string String Eingabe des Spielers die einn baubaren Gegenstand repräsentiert
     * @return der Objekt-Typ oder DEFAULT falls Eingabe ungültig
     */
    static Objects fromStringtoEnum(final String string) {
        switch(string) {
            case AXE_STRING_REP:
                return AXE;
            case CLUB_STRING_REP:
                return CLUB;
            case SHACK_STRING_REP:
                return SHACK;
            case FIREPLACE_STRING_REP:
                return FIREPLACE;
            case SAILINGRAFT_STRING_REP:
                return SAILINGRAFT;
            case HANGGLIDER_STRING_REP:
                return HANGGLIDER;
            case STEAMBOAT_STRING_REP:
                return STEAMBOAT;
            case BALLON_STRING_REP:
                return BALLON;
            default:
                return DEFAULT;
        }
    }



    /**
     * Die returnReceip Methode gibt die Ressourcen zurück welche für den Bau eines Gegenstands gebraucht werden
     * Da der Array nicht verändert wird habe ich mich hier aus performance-Gründen, dazu entschieden die direkte
     * Objekt referenz zurückzugeben.
     *
     * @return Ressourcen welche für den Bau eines Gegenstands gebraucht werden
     */
    protected abstract Cards[] returnRecipe();

    /**
     * Die needsFireplace Methode gibt an ob für den Bau des Gegenstands eine Feuerstelle benötigt wird
     *
     * @return true wenn benötigt false wenn nicht
     */
    protected abstract boolean needsFireplace();
}
