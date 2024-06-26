package com.acme.song.graphql;

import com.acme.song.entity.Song;
import com.acme.song.service.SongReadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import static java.util.Collections.emptyMap;

/**
 * Controller-Klasse für lesende Zugriffe mit GraphQL.
 */
@Controller
@RequiredArgsConstructor
@Slf4j
class SongQueryController {
    private final SongReadService service;

    /**
     * Suche nach Song mit der passenden ID.
     *
     * @param id id des gesuchten Songs
     * @return Der gefundene Song
     */
    @QueryMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    Song song(@Argument final UUID id) {
        log.debug("song: id={}", id);

        final var song = service.findById(id);
        log.debug("song: {}", song);

        return song;
    }

    /**
     * Suche anhand von Suchkriterien.
     *
     * @param suchkriterienInput Suchkriterien
     * @return Die zu den Suchkriterien passenden Songs
     */
    @QueryMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    Collection<Song> songs(@Argument final Optional<Suchkriterien> suchkriterienInput) {
        log.debug("songs: suchkriterien={}", suchkriterienInput);

        final var suchkriterien = suchkriterienInput.map(Suchkriterien::toMap).orElse(emptyMap());
        final var songs = service.find(suchkriterien);
        log.debug("songs: songs={}", songs);

        return songs;
    }
}
