package com.acme.song.repository;

import com.acme.song.entity.Duration;
import com.acme.song.entity.GenreType;
import com.acme.song.entity.Song;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class DB {

    /**
     * Liste der Songs.
     */
    @SuppressWarnings("StaticCollection")
    static final List<Song> SONGS = getSongs();

    static final LocalDate WATCHMEDATE = LocalDate.of(2015, 5, 5);
    static final LocalDate GIMMEDATE = LocalDate.of(1979, 10, 12);

    static final LocalDate SNLIEBEDATE = LocalDate.of(1993, 9, 10);

    static final LocalDate LAMOURDATE = LocalDate.of(1962,11, 1);

    private DB() {

    }

    /**
     * Liste der Inhalte unserer Datenbank.
     *
     * @return diese Liste der Songs.
     */
    private static List<Song> getSongs() {final List<Song> songs = Stream.of(
                Song.builder()
                    .id(UUID.fromString("00000000-0000-0000-0000-000000000000"))
                    .titel("Watch Me")
                    .erscheinungsDatum(WATCHMEDATE)
                    .genre(List.of(GenreType.RAP))
                    .musikLabel("Capitol Records")
                    .duration(Duration.builder().hours(0).minutes(3).second(5).build()).build(),
                Song.builder()
                    .id(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                    .titel("Gimme! Gimme! Gimme!")
                    .erscheinungsDatum(GIMMEDATE)
                    .genre(List.of(GenreType.POP, GenreType.DANCEPOP))
                    .musikLabel("Polar Music")
                    .duration(Duration.builder().hours(0).minutes(4).second(53).build()).build(),
                Song.builder()
                    .id(UUID.fromString("00000000-0000-0000-0000-000000000002"))
                    .titel("Schrei nach Liebe")
                    .erscheinungsDatum(SNLIEBEDATE)
                    .genre(List.of(GenreType.PUNKROCK))
                    .musikLabel("Columbia Records")
                    .duration(Duration.builder().hours(0).minutes(4).second(13).build()).build(),
            Song.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000003"))
                .titel("Le Temps De L'Amour")
                .erscheinungsDatum(LAMOURDATE)
                .genre(List.of(GenreType.FOLK))
                .musikLabel("Northern Music Group")
                .duration(Duration.builder().hours(0).minutes(2).second(25).build()).build()
            )
            // erstellen einer Liste
            .toList();
        return songs;
    }
}
