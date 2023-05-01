package com.acme.song.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Klasse speichert eine Zeitdauer.
 * In Stunden:Minuten:Sekunden.
 */
@Builder
@Getter
@Setter
@ToString
public class Duration {

    @NotNull
    @Min(0)
    private int hours;
    @NotNull
    @Min(0)
    @Max(60)
    private int minutes;
    @NotNull
    @Min(0)
    @Max(60)
    private int seconds;

    /**
     * Der zugehörige Song.
     */
    @JsonIgnore
    @ToString.Exclude
    // Darf nicht @NotNull sein,
    // weil beim Anlegen über REST der Rückwärts verweis noch nicht existiert
    private Song song;
}
