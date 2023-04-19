package com.acme.song.service;

import com.acme.song.entity.Song;
import com.acme.song.repository.SongRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

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

}
