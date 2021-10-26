/*
 * Copyright (c) 2019, IPD Koziolek. All rights reserved.
 */

package edu.kit.informatik;

/**
 * This class models a booking of a bus route by a customer. It also contains a invoice number for uniqueness.
 * 
 * @author Peter Oettig
 * @version 1.0
 */
public class Booking implements Comparable<Booking> {
    private static int invoiceCounter = 1;
    
    private final int invoiceNumber;
    // Route is in here to be able to create independent bookings without using the map in BookingSystem.
    // This increases flexibility and extendability.
    private Route route;
    private Customer customer;

    /**
     * Creates a new booking for a bus route by a customer.
     * 
     * @param route The booked bus route.
     * @param customer The booking customer.
     */
    public Booking(Route route, Customer customer) {
        this.route = route;
        this.customer = customer;
        this.invoiceNumber = invoiceCounter++;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        
        Booking booking = (Booking) other;
        return invoiceNumber == booking.invoiceNumber;
    }

    @Override
    public int hashCode() {
        return invoiceNumber;
    }

    @Override
    public String toString() {
        return invoiceNumber + ";" + customer.getCustomerNumber();
    }

    /**
     * Converts a booking to a string representation but with the bus id included.
     * 
     * @return The string representation with the bus id.
     */
    public String toStringWithBusId() {
        return customer.getCustomerNumber() + "," + route.getBusId() + "," + invoiceNumber;
    }
    
    @Override
    public int compareTo(Booking other) {
        int resultCustomer = customer.compareTo(other.customer); 
        if (resultCustomer == 0) {
            // Reverse sorting
            return other.route.compareTo(route);
        }
        
        return resultCustomer;
    }
}
