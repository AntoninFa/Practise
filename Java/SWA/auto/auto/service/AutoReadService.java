package com.acme.auto.service;

import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.acme.auto.entity.Auto;
import com.acme.auto.repository.AutoRepository;
import com.acme.auto.repository.Autohaus;
import com.acme.auto.repository.AutohausResponse;
import com.acme.auto.repository.AutohausRestRepository;
import com.acme.auto.repository.PredicateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static org.springframework.http.HttpStatus.NOT_MODIFIED;

/**
 * Anwendungslogik für Autos.
 * <img src="../../../../../asciidoc/AutoReadService.svg" alt="Klassendiagramm">
 *
 * @author <a href="mailto:A@h-ka.de">A A</a>
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AutoReadService {
    private static final String ADMIN_CREDENTIALS = "admin:p";
    private static final String ADMIN_BASIC_AUTH = "Basic " +
        new String(Base64.getEncoder().encode(ADMIN_CREDENTIALS.getBytes(ISO_8859_1)), ISO_8859_1);

    private final AutoRepository repo;
    //private final AutohausRestRepository autohausRepository;
    private final PredicateBuilder predicateBuilder;
    /**
     * Ein Auto anhand seiner ID suchen.
     *
     * @param id Die Id des gesuchten Autos
     * @return Das gefundene Auto
     * @throws NotFoundException Falls kein Auto gefunden wurde
     * @throws AccessForbiddenException Falls die erforderlichen Rollen nicht gegeben sind
     */
    public @NonNull Auto findById(final UUID id) {
        final var autoOpt = repo.findById(id);
        final var auto = autoOpt.orElseThrow(() -> new NotFoundException(id));
        log.debug("findById: {}", auto);
        return auto;
    }

    /**
     * Autos anhand von Suchkriterien als Collection suchen.
     *
     * @param suchkriterien Die Suchkriterien
     * @return Die gefundenen Autos oder eine leere Liste
     * @throws NotFoundException Falls keine Autos gefunden wurden
     */
    @SuppressWarnings({"ReturnCount", "NestedIfDepth", "CyclomaticComplexity"})
    public @NonNull Collection<Auto> find(@NonNull final Map<String, List<String>> suchkriterien) {
        log.debug("find: suchkriterien={}", suchkriterien);

        if (suchkriterien.isEmpty()) {
            return repo.findAll();
        }

        if (suchkriterien.size() == 1) {
            final var hersteller = suchkriterien.get("hersteller");
            if (hersteller != null && hersteller.size() == 1) {
                final var auto = repo.findByHersteller(hersteller.get(0));
                if (auto.isEmpty()) {
                    throw new NotFoundException(suchkriterien);
                }
                final var autos = List.of(auto.get());
                log.debug("find (hersteller): {}", autos);
                return autos;
            }

            final var modellbezeichnung = suchkriterien.get("modellbezeichnung");
            if (modellbezeichnung != null && modellbezeichnung.size() == 1) {
                final var auto = repo.findByModellbezeichnung(modellbezeichnung.get(0));
                if (auto.isEmpty()) {
                    throw new NotFoundException(suchkriterien);
                }
                final var autos = List.of(auto.get());
                log.debug("find (modellbezeichnung): {}", autos);
                return autos;
            }
        }

        final var predicate = predicateBuilder
            .build(suchkriterien)
            .orElseThrow(() -> new NotFoundException(suchkriterien));
        final var autos = repo.findAll(predicate);
        if (autos.isEmpty()) {
            throw new NotFoundException(suchkriterien);
        }
        log.debug("find: {}", autos);
        return autos;
    }

    /**
     * Abfrage, welche FIN es zu einem Präfix gibt.
     *
     * @param prefix FIN-Präfix.
     * @return Die passenden FINs.
     * @throws NotFoundException Falls keine FINs gefunden wurden.
     */
    public Collection<String> findFinByPrefix(final String prefix) {
        log.debug("findFinByPrefix: {}", prefix);
        final var fins = repo.findFinByPrefix(prefix);
        if (fins.isEmpty()) {
            throw new NotFoundException();
        }
        log.debug("findFinByPrefix: {}", fins);
        return fins;
    }

    /**
     * Abfrage, aller Autohäuser.
     *
     * @return Alle Autohäuser.
     * @throws NotFoundException Falls keine Autohäuser gefunden wurden.
     */

    /*@SuppressWarnings("ReturnCount")
    public Collection<Autohaus> findAutohaeuser() {
        final ResponseEntity<AutohausResponse> response;
        try {
            response = autohausRepository.getAutohaus(ADMIN_BASIC_AUTH);
        } catch (final WebClientResponseException.NotFound ex) {
            log.error("findAutohaeuser: WebClientResponseException.NotFound");
            return List.of(new Autohaus("N/A", List.of("N/A")));
        } catch (final WebClientException ex) {
            log.error("findAutohaeuser: {}", ex.getClass().getSimpleName());
            return List.of(new Autohaus("Exception", List.of("Exception")));
        }

        final var statusCode = response.getStatusCode();
        log.debug("findAutohaeuser: statusCode={}", statusCode);
        if (statusCode == NOT_MODIFIED) {
            return List.of(new Autohaus("NOT MODIFIED", List.of("NOT MODIFIED")));
        }

        final var autohausResponse = response.getBody();
        final var autohaeuser = autohausResponse != null ? autohausResponse.getAutohaeuser() : null;
        log.debug("findAutohaeuser: {}", autohaeuser);

        return autohaeuser;
    }*/
}
