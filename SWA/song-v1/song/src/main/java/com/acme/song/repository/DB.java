package com.acme.song.repository;

import com.acme.song.entity.SongDuration;
import com.acme.song.entity.GenreType;
import com.acme.song.entity.Song;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Emulation der Datenbank in denen die Songs gespeichert sind.
 */
public final class DB {

    /**
     * Liste der Songs.
     */
    @SuppressWarnings("StaticCollection")
    static final List<Song> SONGS = getSongs();

    private static final int WATCHMERY = 2015;
    private static final int WATCHMERD = 5;
    private static final int WATCHMEDURH = 0;
    private static final int WATCHMEDURM = 3;
    private static final int WATCHMEDURS = 5;


    private static final int GIMMERY = 1979;
    private static final int GIMMERD = 12;
    private static final int GIMMEDURH = 0;
    private static final int GIMMEDURM = 4;
    private static final int GIMMEDURS = 53;


    private static final int SNLIEBERY = 1993;
    private static final int SNLIEBERD = 10;
    private static final int SNLIEBEDURH = 0;
    private static final int SNLIEBEDURM = 4;
    private static final int SNLIEBEDURS = 13;


    private static final int LAMOURDURRY = 1962;
    private static final int LAMOURDUURD = 1;
    private static final int LAMOURDURH = 0;
    private static final int LAMOURDURM = 2;
    private static final int LAMOURDURS = 25;


    private static final int GOLDRRY = 1981;
    private static final int GOLDRRD = 1;
    private static final int GOLDRDURH = 0;
    private static final int GOLDRDURM = 4;
    private static final int GOLDRDURS = 30;

    private DB() {
    }

    /**
     * Liste der Inhalte unserer Datenbank.
     *
     * @return diese Liste der Songs.
     */
    private static List<Song> getSongs() {
        final List<Song> songs = Stream.of(
            Song.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000000"))
                .titel("Watch Me")
                .erscheinungsDatum(LocalDate.of(WATCHMERY, Month.MAY, WATCHMERD))
                .genre(List.of(GenreType.RAP))
                .musikLabel("Capitol Records")
                .duration(SongDuration.builder().hours(WATCHMEDURH)
                    .minutes(WATCHMEDURM).seconds(WATCHMEDURS).build()).build(),
            Song.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                .titel("Gimme! Gimme! Gimme!")
                .erscheinungsDatum(LocalDate.of(GIMMERY, Month.OCTOBER, GIMMERD))
                .genre(List.of(GenreType.POP, GenreType.DANCEPOP))
                .musikLabel("Polar Music")
                .duration(SongDuration.builder().hours(GIMMEDURH)
                    .minutes(GIMMEDURM).seconds(GIMMEDURS).build()).build(),
            Song.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000002"))
                .titel("Schrei nach Liebe")
                .erscheinungsDatum(LocalDate.of(SNLIEBERY, Month.SEPTEMBER, SNLIEBERD))
                .genre(List.of(GenreType.PUNKROCK))
                .musikLabel("Columbia Records")
                .duration(SongDuration.builder().hours(SNLIEBEDURH)
                    .minutes(SNLIEBEDURM).seconds(SNLIEBEDURS).build()).build(),
            Song.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000003"))
                .titel("Le Temps De L'Amour")
                .erscheinungsDatum(LocalDate.of(LAMOURDURRY, Month.NOVEMBER, LAMOURDUURD))
                .genre(List.of(GenreType.FOLK))
                .musikLabel("Northern Music Group")
                .duration(SongDuration.builder().hours(LAMOURDURH)
                    .minutes(LAMOURDURM).seconds(LAMOURDURS).build()).build(),
            Song.builder()
                .id(UUID.fromString("00000000-0000-0000-0000-000000000004"))
                .titel("Goldener Reiter")
                .erscheinungsDatum(LocalDate.of(GOLDRRY, Month.MAY, GOLDRRD))
                .genre(List.of(GenreType.NEUEDEUTSCHEWELLE, GenreType.POP))
                .musikLabel("WEA Records")
                .duration(SongDuration.builder().hours(GOLDRDURH)
                    .minutes(GOLDRDURM).seconds(GOLDRDURS).build()).build()
        ).collect(Collectors.toList());
        return songs;
    }
}
