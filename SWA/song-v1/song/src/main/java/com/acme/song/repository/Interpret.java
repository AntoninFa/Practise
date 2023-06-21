package com.acme.song.repository;

/**
 * Entity-Klasse für den REST-Client.
 *
 * @param name Name des Interpreten
 * @param genre Genre
 */
public record Interpret(String name, String genre) {
}
