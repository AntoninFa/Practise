package com.acme.song.graphql;

/**
 * ValueObject für eine Zeitdauer.
 *
 * @param hours Anzahl Stunden
 * @param minutes Anzahl Minuten
 * @param seconds Anzahl Sekunden
 */
record DurationInput(int hours, int minutes, int seconds) {
}
