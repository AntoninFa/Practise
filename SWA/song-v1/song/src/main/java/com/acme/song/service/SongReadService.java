package com.acme.song.service;

import com.acme.song.entity.Song;
import com.acme.song.repository.SongRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Anwendungslogik für Songs.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public final class SongReadService {


    /**
     * Repository für den DB-Zugriff.
     */
    private final SongRepository repo;


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
     * @return Collection aus den gefundenen Songs, die leer ist, falls keine Passenden gefunden worden sind
     *              oder alle, falls keine suchkriterien gegeben sind.
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
        final var songs = repo.find(suchkriterien);
        if (songs.isEmpty()) {
            throw new NotFoundException(suchkriterien);
        }
        log.debug("find: {}", songs);
        return songs;
    }
}
