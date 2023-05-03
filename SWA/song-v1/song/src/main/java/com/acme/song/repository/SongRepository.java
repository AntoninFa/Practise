package com.acme.song.repository;

import com.acme.song.entity.Song;
import com.acme.song.entity.GenreType;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
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
     * @param song Der anzulegende Song
     * @return Den Song.
     */
    public @NonNull Song create(final @NonNull Song song) {
        log.debug("create: {}", song);
        song.setId(randomUUID());
        SONGS.add(song);
        log.debug("create: {}", song);
        return song;
    }

    /**
     * Einen vorhandenen Song aktualisieren.
     *
     * @param song Das Song-Objekt mit den neuen Daten
     */
    public void update(final @NonNull Song song) {
        log.debug("update: {}", song);
        final OptionalInt index = IntStream
            .range(0, SONGS.size())
            .filter(i -> Objects.equals(SONGS.get(i).getId(), song.getId()))
            .findFirst();
        log.trace("update: index={}", index);
        if (index.isEmpty()) {
            return;
        }
        SONGS.set(index.getAsInt(), song);
        log.debug("update: {}", song);
    }

    /**
     * Kunden anhand einer Liste von Suchkriterien finden.
     *
     * @param suchkriterien Die Suchkriterien
     * @return Liste aus Songs die zu den Suchkriterien passen, oder eine Leere Liste, falls keine passen
     */
    @SuppressWarnings({"ReturnCount", "JavadocLinkAsPlainText"})
    public @NonNull Collection<Song> find(final Map<String, ? extends List<String>> suchkriterien) {
        log.debug("find: suchkriterien ={}", suchkriterien);

        if (suchkriterien.isEmpty()) {
            return findAll();
        }

        for (final var e : suchkriterien.entrySet()) {
            switch (e.getKey()) {
                case "titel" -> {
                    return findByTitel(e.getValue().get(0));
                }
                case "genre" -> {
                    return findByGenre(e.getValue());
                }
                default -> {
                    log.debug("find: Suchkriterium nicht erkannt: {}", e.getKey());
                    return emptyList();
                }
            }
        }
        return emptyList();
    }

    /**
     * Songs anhand seines Titels finden.
     *
     * @param titel Titel des Songs der gefunden werden soll als String
     * @return den gesuchten Song oder eine leere Collection, falls kein passender
     *          Song gefunden wurde
     */
    public @NonNull Collection<Song> findByTitel(final CharSequence titel) {
        log.debug("findByTitel: titel={}", titel);

        final var songs = SONGS.stream()
            .filter(song -> song.getTitel().contains(titel))
            .toList();
        log.debug("findByTitel: titel={}", titel);
        return songs;
    }

    @NonNull Collection<Song> findByGenre(final Collection<String> genreString) {
        log.debug("findByGenre: genreString={}", genreString);
        final var genres = genreString
            .stream()
            .map(genre -> GenreType.of(genre)
                .orElse(null))
            .toList();
        if (genres.contains(null)) {
            return emptyList();
        }
        final var songs = SONGS.stream()
            .filter(song -> {
                @SuppressWarnings("SetReplaceableByEnumSet")
                final Collection<GenreType> songGenre = new HashSet<>(song.getGenre());
                return songGenre.containsAll(genres);
            })
            .toList();
        log.debug("findByGenre: songs={}", songs);
        return songs;
    }

}
