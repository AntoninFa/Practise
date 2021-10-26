/*
 * Copyright (c) 2020, IPD Koziolek. All rights reserved.
 */

package edu.kit.informatik.boardgame.element;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class represents a token.
 * 
 * @author Martin Löper
 * @author Jonathan Schenkenberger
 * @version 1.0
 */
public class Token {
    private final Map<TokenPropertyType, BooleanProperty> mProperties;
    private int mIndex;

    /**
     * Creates a new token with index -1.
     */
    public Token() {
        this(-1);
    }

    /**
     * Creates a new token with the given index.
     * 
     * @param pIndex the given index
     */
    public Token(final int pIndex) {
        this.mProperties = new LinkedHashMap<>();
        this.mIndex = pIndex;
    }

    /**
     * Copies the given token (copy-constructor).
     * 
     * @param pToken the given token
     */
    public Token(final Token pToken) {
        this.mProperties = new LinkedHashMap<>(pToken.mProperties);
        this.mIndex = pToken.mIndex;
    }

    /**
     * Sets the index.
     * 
     * @param pIndex the index
     */
    public void setIndex(final int pIndex) {
        this.mIndex = pIndex;
    }

    /**
     * Adds the given property to this token's properties.
     * 
     * @param pType the property type
     * @param pProperty the boolean property to be added
     */
    public void addProperty(final TokenPropertyType pType, final BooleanProperty pProperty) {
        this.mProperties.put(pType, pProperty);
    }

    @Override
    public String toString() {
        return String.valueOf(this.mIndex);
    }

    /**
     * 
     * @return a copy of this token's properties map.
     */
    public Map<TokenPropertyType, BooleanProperty> getProperties() {
        return new LinkedHashMap<>(this.mProperties);
    }

    /**
     * 
     * @return the token's index
     */
    public int getIndex() {
        return this.mIndex;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.mIndex;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Token other = (Token) obj;
        if (this.mIndex != other.mIndex)
            return false;
        return true;
    }
}
