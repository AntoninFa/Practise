package com.acme.song.repository;

import com.acme.song.entity.Song;
import com.querydsl.core.types.Predicate;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.List;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import static com.acme.song.entity.Song.DURATION_GRAPH;

/**
 * Repository für den DB-Zugriff.
 */
@Repository
public interface SongRepository extends JpaRepository<Song, UUID>, QuerydslPredicateExecutor<Song> {

    /**
     * Einen Song anhand seiner ID suchen.
     *
     * @param id Die Id des gesuchten Songs.
     * @return Optional leer oder mit dem gefundenen Song.
     */
    @EntityGraph(DURATION_GRAPH)
    @Override
    Optional<Song> findById(UUID id);

    /**
     * Alle Songs als Liste aus DB.
     *
     * @return Alle Songs.
     */
    @EntityGraph(DURATION_GRAPH)
    @Override
    List<Song> findAll();

    /**
     * Alle Songs anhand von Prädikat suchen.
     *
     * @return Alle Songs die das Prädikat erfüllen.
     */
    @EntityGraph(DURATION_GRAPH)
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
    @EntityGraph(DURATION_GRAPH)
    Collection<Song> findByTitel(String titel);
}
