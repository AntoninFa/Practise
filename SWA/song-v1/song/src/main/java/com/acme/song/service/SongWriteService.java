package com.acme.song.service;

import com.acme.song.entity.Song;
import com.acme.song.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.UUID;
import jakarta.validation.Validator;
import org.springframework.transaction.annotation.Transactional;

/**
 * Anwendungslogik für Songs.
 * <img src="../../../../../../../build/docs/asciidoc/SongWriteService.svg" alt="Klassendiagramm">
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SongWriteService {
    private final SongRepository repo;
    private final Validator validator;

    /**
     * Einen neuen Song anlegen.
     *
     * @param song Objekt des Songs der neu angelegt werden soll.
     * @return der neu angelegte Song mit generierter ID.
     * @throws ConstraintViolationsException Falls mindestens ein Constraint verletzt ist.
     */
    public Song create(final Song song) {
        log.debug("create: {}", song);

        final var violations = validator.validate(song);
        if (!violations.isEmpty()) {
            log.debug("create: violations={}", violations);

            throw new ConstraintViolationsException(violations);
        }
        final var songDB = repo.save(song);
        log.debug("create: {}", songDB);
        return songDB;
    }


    /**
     * Aktualisieren eines bereits vorhandenen Songs.
     *
     * @param song das Songobjekt mit den neuen Daten
     * @param id ID des Songs der Aktualisiert werden soll
     * @throws NotFoundException Kein Song zur ID vorhanden.
     * @throws ConstraintViolationsException Falls mindestens ein Constraint verletzt ist.
     */
    public Song update(final Song song, final UUID id, final int version) {
        log.debug("update: song={}", song);
        log.debug("update: id={}", id);

        final var violations = validator.validate(song);
        if (!violations.isEmpty()) {
            log.debug("update: violations={}", violations);

            throw new ConstraintViolationsException(violations);
        }
        log.trace("update: Keine Constraints verletzt");

        final var songDbOptional = repo.findById(id);
        if (songDbOptional.isEmpty()) {
            throw new NotFoundException(id);
        }

        var songDb = songDbOptional.get();
        log.trace("update: version={}, songDb={}", version, songDb);
        if (version != songDb.getVersion()) {
            throw new VersionOutdatedException(version);
        }
        songDb.set(song);
        songDb = repo.save(songDb);
        return songDb;

    }
}
