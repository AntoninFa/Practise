package com.acme.song.repository;

import com.acme.song.entity.Song;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.acme.song.repository.DB.SONGS;

@Repository
@Slf4j
public class SongRepository {

    /**
     * Einen Song anhand seiner ID suchen.
     *
     * @param id Die Id des gesuchten Songs.
     * @return Optional leer oder mit dem gefundenen Kunden.
     */
    public Optional<Song> findById(final UUID id) {
        log.debug("findById: id={}", id);
        final var result = SONGS.stream()
            .filter(kunde -> Objects.equals(kunde.getId(), id))
            .findFirst();
        log.debug("findById: {}", result);
        return result;
    }

    /**
     * Alle Songs als Collection aus DB.
     *
     * @return Alle Songs
     */
    public @NonNull Collection<Song> findAll() {
        return SONGS;
    }
}
