package com.acme.auto.graphql;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import com.acme.auto.entity.Auto;
import com.acme.auto.service.AutoReadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import static java.util.Collections.emptyMap;

/**
 * Eine Controller-Klasse für das Lesen mit der GraphQL-Schnittstelle und den Typen aus dem GraphQL-Schema.
 *
 * @author <a href="mailto:A@h-ka.de">A A</a>
 */
@Controller
@RequiredArgsConstructor
@Slf4j
class AutoQueryController {
    private final AutoReadService service;

    /**
     * Suche anhand der Auto-ID.
     *
     * @param id ID des zu suchenden Autos
     * @return Das gefundene Auto
     */
    @QueryMapping
    Auto auto(@Argument final UUID id) {
        log.debug("auto: id={}", id);
        final var auto = service.findById(id);
        log.debug("auto: {}", auto);
        return auto;
    }

    /**
     * Suche mit diversen Suchkriterien.
     *
     * @param input Suchkriterien und ihre Werte, z.B. `Hersteller` und `Modellbezeichnung (911)`
     * @return Die gefundenen Autos als Collection
     */
    @QueryMapping
    Collection<Auto> autos(@Argument final Optional<Suchkriterien> input) {
        log.debug("autos: suchkriterien={}", input);
        final var suchkriterien = input.map(Suchkriterien::toMap).orElse(emptyMap());
        final var autos = service.find(suchkriterien);
        log.debug("autos: {}", autos);
        return autos;
    }
}
