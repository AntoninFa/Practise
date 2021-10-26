/*
 * Copyright (c) 2019, IPD Koziolek. All rights reserved.
 */

package edu.kit.informatik;

import edu.kit.informatik.UI.InputException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * This class is for management and data storage of bus routes and booked tickets.
 *
 * @author Peter Oettig
 * @version 1.0
 */
public class BookingSystem {
    private Set<Route> routes;
    private Set<Customer> customers;
    private Map<Route, Set<Booking>> tickets;

    /**
     * The constructor for the booking system management class for data storage initialization.
     */
    public BookingSystem() {
        this.routes = new TreeSet<>();
        this.customers = new HashSet<>();
        this.tickets = new HashMap<>();
    }

    /**
     * Adds a new bus route to the booking system.
     * 
     * @param busId The ID of the new bus route. 
     * @param start The starting airport of the new flight.
     * @param destination The destination airport of the new flight.
     * @param price The price of a ticket for the new flight.
     * @param currency The currency that the price is in.
     * @throws InputException if a flight with this ID already exists.
     */
    public void addRoute(String busId, String start, String destination, double price, Currency currency)
            throws InputException {
        Route route = new Route(busId, start, destination, price, currency);
        if (routes.add(route)) {
            tickets.put(route, new HashSet<>());
        } else {
            throw new InputException("a bus route with this number already exists.");
        }
    }

    /**
     * Removes the bus route with a given busId and all booked tickets for it.
     * 
     * @param busId The ID of the bus route to remove.
     * @throws InputException if the route does not exist.
     */
    public void removeRoute(String busId) throws InputException {
        // Gets the route object to remove
        Route routeToRemove = getRouteById(busId);
        routes.remove(routeToRemove);
        tickets.remove(routeToRemove);
    }

    /**
     * Converts the list of existing bus routes to a string representation. One line per route.
     * 
     * @return The string representation.
     */
    public String routesToString() {
        return routes.stream().map(Route::toString).collect(Collectors.joining(System.lineSeparator()));
    }

    /**
     * Books a bus route for a specified customer.
     * Automatically creates a new customer entry if there is none already for this person.
     * 
     * @param busId The id of the bus route to book.
     * @param firstName The first name of the customer.
     * @param lastName The last name of the customer.
     * @return The finalized booking.
     * @throws InputException if the bus route does not exist.
     */
    public Booking bookRoute(String busId, String firstName, String lastName) throws InputException {
        // Check if a customer with this name combination already exists, sets it or otherwise creates a new one
        Customer customer = customers.stream()
                .filter(current -> current.equals(firstName, lastName))
                .findFirst()
                .orElse(null);
        
        if (customer == null) {
            customer = new Customer(firstName, lastName);
            customers.add(customer);
        }
        
        Route route = getRouteById(busId);
        Booking booking = new Booking(route, customer);
        tickets.get(route).add(booking);
        return booking;
    }

    /**
     * Converts the list of bookings into a string representation. One line per booking.
     * The string representation is sorted by customer number ascending, then invoice number descending.
     * 
     * @return The string representation.
     */
    public String bookingsToString() {
        // Create a list of all booking and return the string representation of it
        Set<Booking> allBookings = tickets.values().stream().collect(TreeSet::new, Set::addAll, Set::addAll);
        return  allBookings.stream().map(Booking::toStringWithBusId)
                .collect(Collectors.joining(System.lineSeparator()));
    }
    
    private Route getRouteById(String busId) throws InputException {
        return routes.stream()
                .filter(route -> route.getBusId().equals(busId))
                .findFirst()
                .orElseThrow(() -> new InputException("there is no bus route with this number."));
    }
}
