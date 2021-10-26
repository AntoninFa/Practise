/*
 * Copyright (c) 2020, IPD Koziolek. All rights reserved.
 */

package edu.kit.informatik.boardgame.element;

/**
 * Enumeration representing all different types of token properties. This
 * approach provides a flexible way to add or remove token properties.
 * Personally, I think it is better to show students how to keep functionality
 * extendible. Thus I follow this principle in the following places:
 * <ul>
 * <li>variable number of players</li>
 * <li>variable board size</li>
 * <li>variable number of properties</li>
 * <li>scanning behavior for detection of winning positions is changeable during runtime 
 * (for more information see StrategyPattern)</li>
 * </ul>
 * 
 * @author Martin Löper
 * @version 1.0
 */
public enum TokenPropertyType {
    /**
     * The color type.
     */
    COLOR("schwarz", "wei\u00DF"), 
    
    /**
     * The shape type.
     */
    SHAPE("eckig", "zylinderf\u00F6rmig"),
    
    /**
     * The size type.
     */
    SIZE("klein", "gro\u00DF"),
    
    /**
     * The consistency type.
     */
    CONSISTENCY("hohl", "massiv");

    private final String[] mNames;

    // note: by default we have binary properties, all other types should use the
    // other constructor
    private TokenPropertyType() {
        this.mNames = new String[] {"false", "true"};
    }

    private TokenPropertyType(final String... pNames) {
        this.mNames = pNames;
    }

    /**
     * 
     * @param pIndex the index of the property value name
     * @return the property value name
     */
    public String getPropertyValueName(final int pIndex) {
        return this.mNames[pIndex];
    }
}
