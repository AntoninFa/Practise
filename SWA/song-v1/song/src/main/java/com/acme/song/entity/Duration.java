package com.acme.song.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Klasse Speichert eine Zeitdauer.
 * In Stunden:Minuten:Sekunden.
 */
@Builder
@Getter
@Setter
@ToString
public class Duration {

    private int hours;
    private int minutes;
    private int seconds;

    /**
     * Der zugehörige Song.
     */
    @JsonIgnore
    @ToString.Exclude
    // Darf nicht @NotNull sein,
    // weil beim Anlegen ueber REST der Rueckwaertsverweis noch nicht existiert
    private Song song;
}
