package com.acme.song.graphql;

import com.acme.song.entity.GenreType;
import com.acme.song.entity.Song;
import com.acme.song.entity.SongDuration;

import java.time.LocalDate;
import java.util.List;

record SongInput (
    String titel,
    String erscheinungsDatum,
    List<GenreType> genre,
    String musikLabel,
    DurationInput duration

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
            .genre(genre)
            .musikLabel(musikLabel)
            .duration(durationEntity)
            .build();
        song.getDuration().setSong(song);
        return song;
    }
}
