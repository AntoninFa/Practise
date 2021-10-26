/*
 * Copyright (c) 2020, IPD Koziolek. All rights reserved.
 */

package edu.kit.informatik.boardgame.element;

/**
 * This interface represents a property which contains a value of type V.
 * 
 * @author Martin Löper
 * @author Jonathan Schenkenberger
 * @version 1.0
 * @param <V> the value generic type
 */
public interface IProperty<V> {
    
    /**
     * 
     * @return gets the value of this property
     */
    V getValue();
}
