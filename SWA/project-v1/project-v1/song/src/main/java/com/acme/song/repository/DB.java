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

    static final LocalDate WATCHMEDATE = LocalDate.of(2015, Month.MAY, 5);
    static final int WATCHMEDURH = 0;
    static final int WATCHMEDURM = 3;
    static final int WATCHMEDURS = 5;


    static final LocalDate GIMMEDATE = LocalDate.of(1979, Month.OCTOBER, 12);
    static final int GIMMEDURH = 0;
    static final int GIMMEDURM = 4;
    static final int GIMMEDURS = 53;


    static final LocalDate SNLIEBEDATE = LocalDate.of(1993, Month.SEPTEMBER, 10);
    static final int SNLIEBEDURH = 0;
    static final int SNLIEBEDURM = 4;
    static final int SNLIEBEDURS = 13;


    static final LocalDate LAMOURDATE = LocalDate.of(1962, Month.NOVEMBER, 1);
    static final int LAMOURDURH = 0;
    static final int LAMOURDURM = 2;
    static final int LAMOURDURS = 25;


    //TODO
    static final LocalDate GOLDRDATE = LocalDate.of(1962, Month.NOVEMBER, 1);
    static final int GOLDRDURH = 0;
    static final int GOLDRDURM = 0;
    static final int GOLDRDURS = 0;



    static final LocalDate SCHNAPPIDATE = LocalDate.of(2004, Month.DECEMBER, 6);
    static final int SCHNAPPIDURH = 0;
    static final int SCHNAPPIDURM = 2;
    static final int SCHNAPPIDURS = 0;

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
                    .erscheinungsDatum(WATCHMEDATE)
                    .genre(List.of(GenreType.RAP))
                    .musikLabel("Capitol Records")
                    .duration(Duration.builder().hours(WATCHMEDURH)
                        .minutes(WATCHMEDURM).second(WATCHMEDURS).build()).build(),
                Song.builder()
                    .id(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                    .titel("Gimme! Gimme! Gimme!")
                    .erscheinungsDatum(GIMMEDATE)
                    .genre(List.of(GenreType.POP, GenreType.DANCEPOP))
                    .musikLabel("Polar Music")
                    .duration(Duration.builder().hours(GIMMEDURH)
                        .minutes(GIMMEDURM).second(GIMMEDURS).build()).build(),
                Song.builder()
                    .id(UUID.fromString("00000000-0000-0000-0000-000000000002"))
                    .titel("Schrei nach Liebe")
                    .erscheinungsDatum(SNLIEBEDATE)
                    .genre(List.of(GenreType.PUNKROCK))
                    .musikLabel("Columbia Records")
                    .duration(Duration.builder().hours(SNLIEBEDURH)
                        .minutes(SNLIEBEDURM).second(SNLIEBEDURS).build()).build(),
                Song.builder()
                    .id(UUID.fromString("00000000-0000-0000-0000-000000000003"))
                    .titel("Le Temps De L'Amour")
                    .erscheinungsDatum(LAMOURDATE)
                    .genre(List.of(GenreType.FOLK))
                    .musikLabel("Northern Music Group")
                    .duration(Duration.builder().hours(LAMOURDURH)
                        .minutes(LAMOURDURM).second(LAMOURDURS).build()).build(),
                Song.builder()
                    //TODO reit
                    .id(UUID.fromString("00000000-0000-0000-0000-000000000004"))
                    .titel("Le Temps De L'Amour")
                    .erscheinungsDatum(LAMOURDATE)
                    .genre(List.of(GenreType.FOLK))
                    .musikLabel("Northern Music Group")
                    .duration(Duration.builder().hours(LAMOURDURH)
                        .minutes(LAMOURDURM).second(LAMOURDURS).build()).build(),
                Song.builder()
                    //TODO schn
                    .id(UUID.fromString("00000000-0000-0000-0000-000000000005"))
                    .titel("Schnappi, das kleine Krokodil")
                    .erscheinungsDatum(SCHNAPPIDATE)
                    .genre(List.of(GenreType.KINDERMUSIK))
                    .musikLabel("Polydor Records")
                    .duration(Duration.builder().hours(LAMOURDURH)
                        .minutes(LAMOURDURM).second(LAMOURDURS).build()).build()
            )
            // erstellt Liste der Songs
            .toList();
        return songs;
    }
}
