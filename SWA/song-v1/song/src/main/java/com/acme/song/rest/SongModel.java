package com.acme.song.rest;

import com.acme.song.entity.SongDuration;
import com.acme.song.entity.GenreType;
import com.acme.song.entity.Song;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import java.time.LocalDate;
import java.util.List;

/**
 * Model-Klasse für Spring HATEOAS.
 */
@JsonPropertyOrder({
    "titel", "erscheinungsDatum", "genre", "musikLabel", "duration"
})
@Relation(collectionRelation = "songs", itemRelation = "song")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Getter
@Setter
@ToString(callSuper = true)
class SongModel extends RepresentationModel<SongModel> {
    private final String titel;
    private final LocalDate erscheinungsDatum;
    private final List<GenreType> genre;
    private final String musikLabel;
    private final SongDuration duration;

    /**
     * Kopierkonstruktor von einem Song Objekt.
     *
     * @param song Song der Kopiert werden soll
     */
    SongModel(final Song song) {
        titel = song.getTitel();
        erscheinungsDatum = song.getErscheinungsDatum();
        genre = song.getGenre();
        musikLabel = song.getMusikLabel();
        duration = song.getDuration();
    }
}
