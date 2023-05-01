package com.acme.song.service;

import com.acme.song.entity.Song;
import com.acme.song.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Anwendungslogik fürs Schreiben der Song-DB.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SongWriteService {
    private final SongRepository repo;

    /**
     * Einen neuen Song anlegen.
     *
     * @param song Objekt des Songs der neu angelegt werden soll.
     * @return der neu angelegte Song mit generierter ID.
     */
    public Song create(final Song song) {
        log.debug("create: {}", song);

        final var songDB = repo.create(song);
        log.debug("create: {}", songDB);
        return songDB;
    }

    public void update(final Song song, final UUID id) {
        log.debug("update: {}", song);
        log.debug("update: id={}", id);

        //TODO Validation
        final var songOptional = repo.findById(id);
        if (songOptional.isEmpty()) {
            //TODO Exception Handling
            //throw new NotFoundException(id);

            song.setId(id);
            repo.update(song);
        }

    }

}
