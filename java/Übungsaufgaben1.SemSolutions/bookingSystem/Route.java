/*
 * Copyright (c) 2019, IPD Koziolek. All rights reserved.
 */

package edu.kit.informatik;

import java.util.Locale;
import java.util.Objects;

/**
 * This class models a bookable bus route that consists of an ID, a start and destination and a price.
 *
 * @author Peter Oettig
 * @version 1.0
 */
public class Route implements Comparable<Route> {
    private final String busId;
    private String startingBusStation;
    private String destinationBusStation;
    private Price price;

    /**
     * Creates a new bus route from all needed info directly.
     * 
     * @param busId The ID of the bus.
     * @param startingBusStation The starting bus station of the route.
     * @param destinationBusStation The destination bus station of the route.
     * @param price The price of one ticket of the bus route.
     */
    public Route(String busId, String startingBusStation, String destinationBusStation, Price price) {
        this.busId = busId;
        this.startingBusStation = startingBusStation;
        this.destinationBusStation = destinationBusStation;
        this.price = price;
    }
    
    /**
     * Creates a new route taking a price and the currency separated.
     *
     * @param busId The ID of the bus.
     * @param startingBusStation The starting bus station of the route.
     * @param destinationBusStation The destination bus station of the route.
     * @param price The price of one ticket of the bus route without currency information.
     * @param currency The currency information for the price.
     */
    public Route(String busId, String startingBusStation, String destinationBusStation, 
                double price, Currency currency) {
        this.busId = busId;
        this.startingBusStation = startingBusStation;
        this.destinationBusStation = destinationBusStation;
        this.price = new Price(price, currency);
    }

    /**
     * Gets the bus id of the bus route.
     * 
     * @return The bus id.
     */
    public String getBusId() {
        return busId;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        
        Route route = (Route) other;
        return Objects.equals(busId, route.busId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(busId);
    }

    @Override
    public String toString() {
        return busId + ";" + startingBusStation + ";" + destinationBusStation + ";"
                + String.format(Locale.ENGLISH, "%.2f", price.getAmount()) + ";" + price.getCurrency();
    }

    @Override
    public int compareTo(Route other) {
        return busId.compareTo(other.busId);
    }
}
