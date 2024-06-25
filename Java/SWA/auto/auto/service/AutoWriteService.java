package com.acme.auto.service;

import com.acme.auto.entity.Auto;
import com.acme.auto.repository.AutoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import jakarta.validation.Validator;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

/**
 * Anwendungslogik für Autos.
 * Klassendiagramm
 *
 * @author <a href="mailto:A@h-ka.de">A A</a>
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AutoWriteService {
    private final AutoRepository repo;

    // https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#validation-beanvalidation
    private final Validator validator;

    /**
     * Ein neues Auto anlegen.
     *
     * @param auto Das Objekt des neu anzulegenden Autos.
     * @return Das neu angelegte Auto mit generierter ID
     * @throws ConstraintViolationsException Falls mindestens ein Constraint verletzt ist.
     * @throws FinExistsException Es gibt bereits ein Auto mit der FIN.
     */
    @Transactional
    @SuppressWarnings("TrailingComment")
    public Auto create(final Auto auto) {
        log.debug("create: {}", auto);

        final var violations = validator.validate(auto);
        if (!violations.isEmpty()) {
            log.debug("create: violations={}", violations);
            throw new ConstraintViolationsException(violations);
        }

        if (repo.existsByFin(auto.getFin())) {
            throw new FinExistsException(auto.getFin());
        }

        final var autoDB = repo.save(auto);
        log.debug("create: {}", autoDB);
        return autoDB;
    }

    /**
     * Ein vorhandenes Auto aktualisieren.
     *
     * @param auto Das Objekt mit den neuen Daten (ohne ID)
     * @param id ID des zu aktualisierenden Autos
     * @param version Die Version die erfordert wird
     * @return Aktualisiertes Auto mit erhöhter Versionsnummer
     * @throws ConstraintViolationsException Falls mindestens ein Constraint verletzt ist.
     * @throws NotFoundException Kein Auto zur ID vorhanden.
     * @throws FinExistsException Es gibt bereits ein Auto mit der FIN.
     */
    public Auto update(final Auto auto, final UUID id, final int version) {
        log.debug("update: {}", auto);
        log.debug("update: id={}", id);

        final var violations = validator.validate(auto);
        if (!violations.isEmpty()) {
            log.debug("update: violations={}", violations);
            throw new ConstraintViolationsException(violations);
        }

        final var autoDbOptional = repo.findById(id);
        if (autoDbOptional.isEmpty()) {
            throw new NotFoundException(id);
        }

        var autoDb = autoDbOptional.get();
        log.trace("update: version={}, kundeDb={}", version, autoDb);
        if (version != autoDb.getVersion()) {
            throw new VersionOutdatedException(version);
        }

        final var fin = auto.getFin();
        // Ist die neue FIN bei einem *ANDEREN* Auto vorhanden?
        if (!Objects.equals(fin, autoDb.getFin()) && repo.existsByFin(fin)) {
            log.debug("update: fin {} existiert", fin);
            throw new FinExistsException(fin);
        }
        autoDb.set(auto);
        autoDb = repo.save(auto);
        log.debug("update: {}", autoDb);
        return autoDb;
    }
}
