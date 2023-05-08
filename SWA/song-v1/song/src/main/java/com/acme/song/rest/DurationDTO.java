package com.acme.song.rest;

/**
 * ValueObject benötigt zum Neuanlegen und ändern eines Songs.
 *
 * @param hours   Anzahl Stunden
 * @param minutes Anzahl Minuten
 * @param seconds Anzahl Sekunden
 */
record DurationDTO(

    int hours,
    int minutes,
    int seconds) {
}
