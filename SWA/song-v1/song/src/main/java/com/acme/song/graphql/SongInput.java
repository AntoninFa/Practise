package com.acme.song.graphql;

import com.acme.song.entity.GenreType;
import com.acme.song.entity.Song;
import com.acme.song.entity.SongDuration;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Eine Value-Klasse für Eingabedaten passend zu SongInput aus dem GraphQL-Schema.
 *
 * @param titel Titel eines Songs.
 * @param erscheinungsDatum Datum an dem ein Song erschienen ist.
 * @param songGenre Genre eines Songs.
 * @param musikLabel Label unter welchem der Song erschienen ist.
 * @param duration Dauer eines Songs.
 * @param interpretId Id des Interpreten.
 */
record SongInput(
    String titel,
    String erscheinungsDatum,
    List<GenreType> songGenre,
    String musikLabel,
    DurationInput duration,
    UUID interpretId

) {
    /**
     * Methode die SongDTO zu einem Song macht.
     *
     * @return Songobject für den Anwendungskern
     */
    Song toSong() {
        final var durationEntity = SongDuration
            .builder()
            .hours(duration().hours())
            .minutes(duration().minutes())
            .seconds(duration().seconds())
            .build();
        final var song = Song
            .builder()
            .id(null)
            .titel(titel)
            .erscheinungsDatum(LocalDate.parse(erscheinungsDatum))
            .songGenre(songGenre)
            .musikLabel(musikLabel)
            .duration(durationEntity)
            .interpretId(interpretId)
            .build();
        song.getDuration().setSong(song);
        return song;
    }
}
