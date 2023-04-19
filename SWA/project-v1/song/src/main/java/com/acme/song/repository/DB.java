package com.acme.song.repository;

import com.acme.song.entity.GenreType;
import com.acme.song.entity.Song;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DB {

    /**
     * Liste der Songs
     */
    @SuppressWarnings("StaticCollection")
    static final List<Song> SONGS = getSongs();

    static final LocalDate WATCHMEDATE = LocalDate.of(2015, 5, 5);
    static final LocalDate GIMMEDATE = LocalDate.of(1979, 10, 12);

    private DB() {
    }

    /**
     * Liste der Inhalte unserer Datenbank
     * @return diese Liste der Songs
     */
    private static List<Song> getSongs() {
        //TODO nochn paar mehr Einträge in die DB
        final List<Song> songs = Stream.of(
            Song.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000000"))
                .titel("Watch Me")
                .erscheinungsDatum(WATCHMEDATE)
                .genre(List.of(GenreType.RAP))
                .musikLabel("Capitol Records").build(),
            Song.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                .titel("Gimme! Gimme! Gimme!")
                .erscheinungsDatum(GIMMEDATE)
                .genre(List.of(GenreType.POP, GenreType.DANCEPOP))
                .musikLabel("Polar Music").build()
        )
            // erstellen einer Liste
            .toList();
            return songs;
    }
}
