package com.acme.song.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.UUID;
import static jakarta.persistence.FetchType.LAZY;

/**
 * Klasse speichert die Zeitdauer eines Songs.
 * In Stunden:Minuten:Sekunden.
 * Dabei sollen Minuten und Sekunden jeweils <60 sein
 */
@Entity
@Table(name = "duration")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class SongDuration {

    /**
     * Minuten und Sekunden sollen jeweils <60 sein.
     */
    private static final int MAXHM = 59;
    @Id
    @GeneratedValue
    @JsonIgnore
    private UUID id;
    @NotNull
    @Min(0)
    private int hours;
    @NotNull
    @Min(0)
    @Max(MAXHM)
    private int minutes;
    @NotNull
    @Min(0)
    @Max(MAXHM)
    private int seconds;

    /**
     * Der zugehörige Song.
     */
    @OneToOne(optional =false, fetch = LAZY)
    @JoinColumn(updatable = false)
    @JsonIgnore
    @ToString.Exclude
    // Darf nicht @NotNull sein,
    // weil beim Anlegen über REST der Rückwärts verweis noch nicht existiert
    private Song song;
}
