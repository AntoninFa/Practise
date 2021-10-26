/*
 * Copyright (c) 2020, IPD Koziolek. All rights reserved.
 */

package edu.kit.informatik.boardgame.element;

/**
 * This class represents a property which has exactly two different states/values.
 * e.g. schwarz=0 (false) and weiss=1 (true)
 * 
 * @author Martin Löper
 * @version 1.0
 *
 */
public class BooleanProperty implements IProperty<Boolean> {
    private final boolean mValue;
    
    /**
     * Creates a new boolean property with the given value.
     * 
     * @param pValue the boolean property value
     */
    public BooleanProperty(final boolean pValue) {
        this.mValue = pValue;
    }
    
    @Override
    public Boolean getValue() {
        return this.mValue;
    }
    
    @Override
    public String toString() {
        return String.valueOf(getValue());
    }
}
