package com.acme.song.entity;


import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Getter
@Setter
@ToString
public class Song {


    /**
     * ID die eine Song eindeutig identifiziert.
     * @param id Die id.
     * @return Die id.
     */
    private UUID id;


    /**
     * Titel des Songs.
     * @param titel Der Titel.
     * @return Den Titel.
     */
    private String titel;

    /**
     * Datum an dem der Song offiziell erschienen ist.
     * @param erscheinungsDatum Das ErscheinungsDatum.
     * @return Das ErscheinungsDatum.
     */
    @Past
    private LocalDate erscheinungsDatum;


    private List<GenreType> genre;


    private String musikLabel;

    private Duration duration;


}
