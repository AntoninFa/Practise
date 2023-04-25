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
import static java.util.UUID.randomUUID;

/**
 * Repository für den DB-Zugriff.
 */
@Repository
@Slf4j
public class SongRepository {

    /**
     * Einen Song anhand seiner ID suchen.
     *
     * @param id Die Id des gesuchten Songs.
     * @return Optional leer oder mit dem gefundenen Song.
     */
    public Optional<Song> findById(final UUID id) {
        log.debug("findById: id={}", id);
        final var result = SONGS.stream()
            .filter(song -> Objects.equals(song.getId(), id))
            .findFirst();
        log.debug("findById: {}", result);
        return result;
    }

    /**
     * Alle Songs als Collection aus DB.
     *
     * @return Alle Songs.
     */
    public @NonNull Collection<Song> findAll() {
        return SONGS;
    }


    /**
     * Einen neuen Song anlegen.
     *
     * @param song Der anzulegende Song.
     * @return Den Song.
     */
    public @NonNull Song create(final @NonNull Song song) {
        log.debug("create: {}", song);
        song.setId(randomUUID());
        SONGS.add(song);
        log.debug("create: {}", song);
        return song;
    }
}
