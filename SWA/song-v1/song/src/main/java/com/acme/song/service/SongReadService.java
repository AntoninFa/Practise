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
 * Anwendungslogik fürs Lesen der Song-DB.
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
     * Sucht alle Songs.
     *
     * @return Alle Songs
     */
    public @NonNull Collection<Song> findAll() {
        final var songs = repo.findAll();
        log.debug("findAll: {}", songs);
        return songs;
    }


    public @NonNull Collection<Song> find(@NonNull final Map<String, List<String>> suchkriterien) {
        log.debug("find: suchkriterien={}", suchkriterien);

        if (suchkriterien.isEmpty()) {
            // Nicht Empfohlen bei sehr großen Datenbanken
            return repo.findAll();
        }
        /**
         * private @NonNull Collection<Kunde> findByInteresse(final Collection<String> interessenStr) {
         *         log.debug("findByInteressen: interessenStr={}", interessenStr);
         *         final var interessen = interessenStr
         *             .stream()
         *             .map(interesse -> InteresseType.of(interesse).orElse(null))
         *             .toList();
         *         if (interessen.contains(null)) {
         *             return emptyList();
         *         }
         *         final var kunden = KUNDEN.stream()
         *             .filter(kunde -> {
         *                 @SuppressWarnings("SetReplaceableByEnumSet")
         *                 final Collection<InteresseType> kundeInteressen = new HashSet<>(kunde.getInteressen());
         *                 return kundeInteressen.containsAll(interessen);
         *             })
         *             .toList();
         *         log.debug("findByNachname: kunden={}", kunden);
         *         return kunden;
         *     }
         */


        final var songs = repo.find(suchkriterien);
        if (songs.isEmpty()) {
            throw new NotFoundException(suchkriterien);
        }
        log.debug("find: {}", songs);
        return songs;


    }

}
