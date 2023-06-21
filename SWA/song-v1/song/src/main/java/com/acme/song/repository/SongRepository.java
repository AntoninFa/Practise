package com.acme.song.repository;

import com.acme.song.entity.Song;
import com.acme.song.entity.GenreType;
import com.querydsl.core.types.Predicate;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalInt;

import static java.util.Collections.emptyList;
import static java.util.UUID.randomUUID;
import static com.acme.song.repository.DB.SONGS;

/**
 * Repository für den DB-Zugriff.
 */
@Repository
public interface SongRepository extends JpaRepository<Song, UUID>, QueryPredicateExecutor<Song> {

    /**
     * Einen Song anhand seiner ID suchen.
     *
     * @param id Die Id des gesuchten Songs.
     * @return Optional leer oder mit dem gefundenen Song.
     */
    @Override
    Optional<Song> findById(UUID id);

    /**
     * Alle Songs als Liste aus DB.
     *
     * @return Alle Songs.
     */
    @Override
    List<Song> findAll();

    /**
     * Alle Songs anhand von Prädikat suchen.
     *
     * @return Alle Songs die das Prädikat erfüllen.
     */
    @Override
    List<Song> findAll(Predicate predicate);


    /**
     * Songs anhand seines Titels finden.
     *
     * @param titel Titel des Songs der gefunden werden soll als String
     * @return den gesuchten Song oder eine leere Collection, falls kein passender
     *          Song gefunden wurde
     */
    @Query("""
        SELECT   s
        FROM     Song s
        WHERE    lower(s.titel) LIKE concat('%', lower(:titel), '%')
        ORDER BY s.id
        """)
    Collection<Song> findByTitel(String titel);

}
