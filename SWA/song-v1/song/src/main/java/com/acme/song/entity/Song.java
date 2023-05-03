package com.acme.song.entity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.UniqueElements;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Daten von einem Song.
 */
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Getter
@Setter
@ToString
public class Song {

    /**
     * ID die einen Song eindeutig identifiziert.
     */
    private UUID id;

    /**
     * Titel des Songs.
     */
    @NotNull

    private String titel;

    /**
     * Datum, an dem der Song offiziell erschienen ist.
     */
    @Past
    private LocalDate erscheinungsDatum;

    /**
     * Liste an Genres des Songs.
     */
    @UniqueElements
    @ToString.Exclude
    private List<GenreType> genre;

    /**
     * Musik-Label, unter welchem der SOng erschienen ist.
     */
    private String musikLabel;

    /**
     * Dauer des Songs in Stunden:Minute:Sekunden.
     */
    @Valid
    @ToString.Exclude
    private Duration duration;
}
