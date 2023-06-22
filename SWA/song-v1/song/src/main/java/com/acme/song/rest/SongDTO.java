package com.acme.song.rest;
import com.acme.song.entity.SongDuration;
import com.acme.song.entity.GenreType;
import com.acme.song.entity.Song;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * ValueObject benötigt zum Neuanlegen und ändern eines Songs.
 *
 * @param titel Titel des Songs.
 * @param erscheinungsDatum Erscheinungsdatum des Songs im Format YYYY-MM-DD
 * @param genre Genre des des Song.
 * @param musikLabel Musiklabel, bei dem der Song produziert wurde.
 * @param duration Dauer des Songs.
 * @param interpretId Id des Interpreten
 */
record SongDTO(

    String titel,
    LocalDate erscheinungsDatum,
    List<GenreType> genre,
    String musikLabel,
    DurationDTO duration,
    UUID interpretId

) {

    /**
     * Methode die SongDTO zu einem Song macht.
     *
     * @return Songobject für den Anwendungskern
     */
    Song toSong() {
        final var durationEntity = duration() == null
            ? null
            : SongDuration
            .builder()
            .hours(duration().hours())
            .minutes(duration().minutes())
            .seconds(duration().seconds())
            .build();

        final var song = Song
            .builder()
            .id(null)
            .version(0)
            .titel(titel)
            .erscheinungsDatum(erscheinungsDatum)
            .genre(genre)
            .musikLabel(musikLabel)
            .duration(durationEntity)
            .interpretId(interpretId)
            .build();
        song.getDuration().setSong(song);
        return song;
    }
}
