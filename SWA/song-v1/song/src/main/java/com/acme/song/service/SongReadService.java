package com.acme.song.service;

import com.acme.song.entity.Song;
import com.acme.song.repository.Interpret;
import com.acme.song.repository.InterpretRestRepository;
import com.acme.song.repository.PredicateBuilder;
import com.acme.song.repository.SongRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Anwendungslogik für Songs.
 * <img src="../../../../../../../build/docs/asciidoc/SongReadService.svg" alt="Klassendiagramm">
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class SongReadService {
    /**
     * Repository für den DB-Zugriff.
     */
    private final SongRepository repo;
    private final InterpretRestRepository interpretRepository;
    private final PredicateBuilder predicateBuilder;

    /**
     * Einen Song anhand seiner ID suchen.
     *
     * @param id Die Id des gesuchten Songs.
     * @return Den gefundene Song.
     */
    public @NonNull Song findById(final UUID id) {
        log.debug("findById: id={}", id);
        final var song = repo.findById(id)
            .orElseThrow();
        log.debug("findById: {}", song);
        return song;
    }

    /**
     * Songs anhand einer Collection von Suchkriterien finden.
     *
     * @param suchkriterien Die Collection aus Suchkriterien.
     * @return Collection aus den gefundenen Songs, welche leer ist, falls keine Passenden gefunden wurden
     *              oder alle Songs enthält, falls keine suchkriterien vorgegeben sind.
     * @throws NotFoundException Falls keine Songs gefunden wurden.
     */
    @SuppressWarnings({"ReturnCount", "NestedIfDepth"})
    public @NonNull Collection<Song> find(@NonNull final Map<String, List<String>> suchkriterien) {
        log.debug("find: suchkriterien={}", suchkriterien);

        if (suchkriterien.isEmpty()) {
            // Nicht Empfohlen bei sehr großen Datenbanken
            return repo.findAll();
        }

        if (suchkriterien.size() == 1) {
            final var titel = suchkriterien.get("titel");
            if (titel != null && titel.size() == 1) {
                final var songs = repo.findByTitel(titel.get(0));
                if (songs.isEmpty()) {
                    throw new NotFoundException(suchkriterien);
                }
                log.debug("find (titel): {}", songs);

                return songs;
            }
        }
        final var interpretIds = suchkriterien.get("interpretId");
        if (interpretIds != null && interpretIds.size() == 1) {
            final var songs = findByInterpretId(UUID.fromString(interpretIds.get(0)));
            if(songs.isEmpty()) {
                throw new NotFoundException(suchkriterien);
            }
            log.debug("find (autoId): {}", songs);
            return songs;
        }

        log.debug("Anzahl Suchkriterien: {}", suchkriterien.size());

        final var predicate = predicateBuilder
            .build(suchkriterien)
            .orElseThrow(() -> new NotFoundException(suchkriterien));
        final var songs = repo.findAll(predicate);
        if (songs.isEmpty()) {
            throw new NotFoundException(suchkriterien);
        }
        log.debug("find: {}", songs);

        return songs;
    }

    /**
     * Songs zur Interpreten-ID suchen.
     *
     * @param interpretId Die Id des vorhandenen Interpreten.
     * @return die gefundenen Songs.
     * @throws NotFoundException Falls keine Songs gefunden wurden.
     */
    public Collection<Song> findByInterpretId(final UUID interpretId) {
        log.debug("findByInterpretId: interpretId={}", interpretId);

        final var songs = repo.findByInterpretId(interpretId);
        if (songs.isEmpty()) {
            throw new NotFoundException(interpretId);
        }
        final var interpret = findInterpretById(interpretId);
        final var name = interpret == null ? null : interpret.name();
        final var genre = interpret == null ? null : interpret.genre();
        log.trace("findByInterpretId: name={}, genre={}", name, genre);

        songs.forEach(song-> {
            song.setInterpretName(name);
            song.setInterpretGenre(genre);
        });
        log.trace("findByInterpretId: songs={}", songs);

        return songs;
    }

    private Interpret findInterpretById(final UUID interpretId) {
        log.debug("findInterpretById: interpretId={}", interpretId);

        final ResponseEntity<Interpret> response;
        try {
            response = interpretRepository.getInterpret(interpretId.toString());
        } catch (final WebClientResponseException.NotFound ex) {
            // Statuscode 404
            log.error("findInterpretById: WebClientResponseException.NotFound");

            return new Interpret("N/A", "not.found@acme.com");
        } catch (final WebClientException ex) {
            // sonstiger Statuscode 4xx oder 5xx
            // WebClientRequestException oder WebClientResponseException (z.B. ServiceUnavailable)
            log.error("findInterpretById: {}", ex.getClass().getSimpleName());

            return new Interpret("Exception", "exception@acme.com");

        }
        final var interpret = response.getBody();
        log.debug("findInterpretById: {}", interpret);

        return interpret;
    }
}
