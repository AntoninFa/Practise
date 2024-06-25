package com.acme.auto.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.acme.auto.entity.Auto;
import com.querydsl.core.types.Predicate;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import static com.acme.auto.entity.Auto.AUSSTATTUNG_GRAPH;

/**
 * Repository für den DB-Zugriff (Autos).
 *
 * @author <a href="mailto:A@h-ka.de">A A</a>
 */
@Repository
public interface AutoRepository extends JpaRepository<Auto, UUID>, QuerydslPredicateExecutor<Auto> {
    @EntityGraph(AUSSTATTUNG_GRAPH)
    @Override
    List<Auto> findAll();

    @EntityGraph(AUSSTATTUNG_GRAPH)
    @Override
    List<Auto> findAll(Predicate predicate);

    @EntityGraph(AUSSTATTUNG_GRAPH)
    // @EntityGraph(ADRESSE_UMSAETZE_GRAPH) // NOSONAR
    @Override
    Optional<Auto> findById(UUID id);

    /**
     * Auto zu gegebenen Hersteller aus der DB ermitteln.
     *
     * @param hersteller Hersteller für die Suche
     * @return Optional mit dem gefundenen Auto oder leeres Optional
     */
    @Query("""
        SELECT a
        FROM   Auto a
        WHERE  lower(a.hersteller) LIKE concat(lower(:hersteller), '%')
        """)
    @EntityGraph(AUSSTATTUNG_GRAPH)
    Optional<Auto> findByHersteller(String hersteller);

    /**
     * Auto zu gegebener Modellbezeichnung aus der DB ermitteln.
     *
     * @param modellbezeichnung Modellbezeichnung für die Suche
     * @return Optional mit dem gefundenes Auto oder leeres Optional
     */
    @Query("""
        SELECT a
        FROM   Auto a
        WHERE  lower(a.modellbezeichnung) LIKE concat(lower(:modellbezeichnung), '%')
        """)
    @EntityGraph(AUSSTATTUNG_GRAPH)
    Optional<Auto> findByModellbezeichnung(String modellbezeichnung);

    /**
     * Abfrage, ob es ein Auto mit gegebener Fin gibt.
     *
     * @param fin Fin für die Suche
     * @return true, falls es ein solches Auto gibt, sonst false
     */
    @SuppressWarnings("BooleanMethodNameMustStartWithQuestion")
    boolean existsByFin(String fin);

    /**
     * Abfrage, welche FIN es zu einem Präfix gibt.
     *
     * @param prefix FIN-Präfix.
     * @return Die passenden FINs oder eine leere Collection.
     */
    @Query("""
        SELECT DISTINCT a.fin
        FROM     Auto a
        WHERE    lower(a.fin) LIKE concat(lower(:prefix), '%')
        ORDER BY a.fin
        """)
    Collection<String> findFinByPrefix(String prefix);
}
