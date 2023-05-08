package com.acme.song.rest;
import com.acme.song.entity.SongDuration;
import com.acme.song.entity.GenreType;
import com.acme.song.entity.Song;
import java.time.LocalDate;
import java.util.List;

/**
 * ValueObject benötigt zum Neuanlegen und ändern eines Songs.
 *
 * @param titel Titel des Songs.
 * @param erscheinungsDatum Erscheinungsdatum des Songs im Format YYYY-MM-DD
 * @param genre Genre des des Song.
 * @param musikLabel Musiklabel, bei dem der Song produziert wurde.
 * @param duration Dauer des Songs.
 */
record SongDTO(

    String titel,
    LocalDate erscheinungsDatum,
    List<GenreType> genre,
    String musikLabel,
    DurationDTO duration

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
            .titel(titel)
            .erscheinungsDatum(erscheinungsDatum)
            .genre(genre)
            .musikLabel(musikLabel)
            .duration(durationEntity)
            .build();
        song.getDuration().setSong(song);
        return song;
    }
}
