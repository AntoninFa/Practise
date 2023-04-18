package com.acme.song.entity;


import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Getter
@Setter
@ToString
@SuppressWarnings({"ClassFanOutComplexity", "JavadocDeclaration", "RequireEmptyLineBeforeBlockTagGroup"})
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
     * @return Den Titel
     */
    private String titel;

    /**
     * Datum an dem der Song offiziell erschienen ist.
     * @param erscheinungsDatum Das ErscheinungsDatum.
     * @return Das ErscheinungsDatum.
     */
    @Past
    private LocalDate erscheinungsDatum;


    private GenreType genre;


    private String musikLabel;
    //TODO Vielleicht Noch ErscheinungsOrt als Klasse?







}
