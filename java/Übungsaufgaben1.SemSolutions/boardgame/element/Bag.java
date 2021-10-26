/*
 * Copyright (c) 2020, IPD Koziolek. All rights reserved.
 */

package edu.kit.informatik.boardgame.element;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.kit.informatik.boardgame.exception.SemanticsException;

/**
 * This class represents the bag of tokens.
 * 
 * @author Martin Löper
 * @author Jonathan Schenkenberger
 * @version 1.0
 */
public class Bag {
    private static final int PROPERTY_COUNT = TokenPropertyType.values().length;
    
    private final List<Token> mTokens;

    /**
     * Creates the bag dynamically with power set building techniques.
     */
    public Bag() {
        mTokens = new LinkedList<>(); // usually containing initially Math.pow(PROPERTY_COUNT, 2) elements
        mTokens.addAll(getAllTokensRecursivly(0, null));

        /*
         * set the tokens' identifiers based on the position (i.e. index) inside the
         * list
         */
        for (int i = 0; i < mTokens.size(); i++) {
            mTokens.get(i).setIndex(i);
        }
    }

    /**
     * Adds all possible property combinations to result list.
     * 
     * @param pOffset the index of the currently processed property from type {@link TokenPropertyType}
     * @param pToken the currently built token or null if at the start of the recursion
     * @return list containing tokens with all possible property value combinations (i.e. power set)
     */
    private Collection<? extends Token> getAllTokensRecursivly(final int pOffset, final Token pToken) {
        List<Token> tokens = new LinkedList<>();
        Token cToken = (pToken != null) ? pToken : new Token();

        /* at the end of the recursion, add the recently built token to list */
        if (pOffset == PROPERTY_COUNT) {
            tokens.add(cToken);
            return tokens;
        }

        /* clone the current token once, because we effectively branch */
        Token t1 = new Token(cToken);
        Token t2 = cToken;

        /* get property type for offset */
        TokenPropertyType cType = TokenPropertyType.values()[pOffset];

        /* add new property to current token */
        t1.addProperty(cType, new BooleanProperty(false));
        t2.addProperty(cType, new BooleanProperty(true));

        /* start recursion */
        tokens.addAll(getAllTokensRecursivly(pOffset + 1, t1));
        tokens.addAll(getAllTokensRecursivly(pOffset + 1, t2));

        return tokens;
    }

    /**
     * 
     * @param pIndex the given index
     * @return the token with the given index
     * @throws SemanticsException if the token is already in use or index is negative
     */
    public Token getToken(final int pIndex) throws SemanticsException {
        if (pIndex < 0) {
            throw new SemanticsException("a token with negative index does not exist inside the bag.");
        }

        Token cToken = getTokenByIndex(pIndex);

        if (cToken == null) {
            throw new SemanticsException("the token with the given index " + pIndex + " is already in use.");
        }

        return cToken;
    }

    private Token getTokenByIndex(final int pIndex) {
        for (Token cToken : this.mTokens) {
            if (cToken.getIndex() == pIndex) {
                return cToken;
            }
        }

        return null;
    }

    /**
     * Removes the token with the given index
     * 
     * @param pIndex the given index
     */
    public void removeToken(final int pIndex) {
        final Iterator<Token> it = this.mTokens.iterator();

        while (it.hasNext()) {
            final Token cToken = it.next();
            if (cToken.getIndex() == pIndex) {
                it.remove();
                return;
            }
        }
    }
    
    /**
     * Adds the given token to this bag. 
     * This method is used in order to revert selection if place does not succeed.
     * @param pToken the token to be added
     */
    public void addToken(final Token pToken) {
        /* 
         * getting correct list index by traversing list, 
         * finding the correct list index for adding the given token
         */
        final int pTokenIndex = pToken.getIndex();
        int listIndex = this.mTokens.size();
        final Iterator<Token> it = this.mTokens.iterator();
        for (int i = 0; it.hasNext(); i++) {
            final Token cToken = it.next();
            final int cTokenIndex = cToken.getIndex();
            if (cTokenIndex > pTokenIndex) {
                listIndex = i;
                break;
            }
        }
        
        this.mTokens.add(listIndex, pToken);
    }

    /**
     * 
     * @return whether the bag is empty
     */
    public boolean isEmpty() {
        return this.mTokens.isEmpty();
    }

    @Override
    public String toString() {
        String out = "";

        for (int i = 0; i < mTokens.size(); i++) {
            if (!out.isEmpty()) {
                out += " ";
            }

            out += this.mTokens.get(i).toString();
        }

        return out;
    }
}
