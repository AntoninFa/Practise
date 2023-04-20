package com.acme.song.repository;
import com.acme.song.entity.Duration;
import com.acme.song.entity.GenreType;
import com.acme.song.entity.Song;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.UUID;
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


    private static final int SCHNAPPIRY = 2004;
    private static final int SCHNAPPIRD = 6;
    private static final int SCHNAPPIDURH = 0;
    private static final int SCHNAPPIDURM = 2;
    private static final int SCHNAPPIDURS = 0;

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
                    .duration(Duration.builder().hours(WATCHMEDURH)
                        .minutes(WATCHMEDURM).second(WATCHMEDURS).build()).build(),
                Song.builder()
                    .id(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                    .titel("Gimme! Gimme! Gimme!")
                    .erscheinungsDatum(LocalDate.of(GIMMERY, Month.OCTOBER, GIMMERD))
                    .genre(List.of(GenreType.POP, GenreType.DANCEPOP))
                    .musikLabel("Polar Music")
                    .duration(Duration.builder().hours(GIMMEDURH)
                        .minutes(GIMMEDURM).second(GIMMEDURS).build()).build(),
                Song.builder()
                    .id(UUID.fromString("00000000-0000-0000-0000-000000000002"))
                    .titel("Schrei nach Liebe")
                    .erscheinungsDatum(LocalDate.of(SNLIEBERY, Month.SEPTEMBER, SNLIEBERD))
                    .genre(List.of(GenreType.PUNKROCK))
                    .musikLabel("Columbia Records")
                    .duration(Duration.builder().hours(SNLIEBEDURH)
                        .minutes(SNLIEBEDURM).second(SNLIEBEDURS).build()).build(),
                Song.builder()
                    .id(UUID.fromString("00000000-0000-0000-0000-000000000003"))
                    .titel("Le Temps De L'Amour")
                    .erscheinungsDatum(LocalDate.of(LAMOURDURRY, Month.NOVEMBER, LAMOURDUURD))
                    .genre(List.of(GenreType.FOLK))
                    .musikLabel("Northern Music Group")
                    .duration(Duration.builder().hours(LAMOURDURH)
                        .minutes(LAMOURDURM).second(LAMOURDURS).build()).build(),
                Song.builder()
                    .id(UUID.fromString("00000000-0000-0000-0000-000000000004"))
                    .titel("Goldener Reiter")
                    .erscheinungsDatum(LocalDate.of(GOLDRRY, Month.MAY, GOLDRRD))
                    .genre(List.of(GenreType.NEUEDEUTSCHEWELLE, GenreType.POP))
                    .musikLabel("Northern Music Group")
                    .duration(Duration.builder().hours(GOLDRDURH)
                        .minutes(GOLDRDURM).second(GOLDRDURS).build()).build(),
                Song.builder()
                    .id(UUID.fromString("00000000-0000-0000-0000-000000000005"))
                    .titel("Schnappi, das kleine Krokodil")
                    .erscheinungsDatum(LocalDate.of(SCHNAPPIRY, Month.DECEMBER, SCHNAPPIRD))
                    .genre(List.of(GenreType.KINDERMUSIK))
                    .musikLabel("Polydor Records")
                    .duration(Duration.builder().hours(SCHNAPPIDURH)
                        .minutes(SCHNAPPIDURM).second(SCHNAPPIDURS).build()).build()
            )
            // erstellt Liste der Songs
            .toList();
        return songs;
    }
}
