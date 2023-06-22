package com.acme.song.repository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import static org.springframework.http.HttpHeaders.IF_NONE_MATCH;

/**
 * "HTTP Interface" für den REST-Client für Daten über Interprete
 */
@HttpExchange("/rest")
public interface InterpretRestRepository {
    /**
     * Einen Interpretndatensatz vom Microservice "interpret" mit "Basic Authentication" anfordern.
     *
     * @param id ID des angeforderten Interpretn
     * @param version Version des angeforderten Datensatzes
     * @return Gefundener Interpret
     */
    @GetExchange("/{id}")
    @SuppressWarnings("unused")
    ResponseEntity<Interpret> getInterpretMitVersion(
        @PathVariable String id,
        @RequestHeader(IF_NONE_MATCH) String version
    );

    /**
     * Einen Interpretndatensatz vom Microservice "interpret" mit "Basic Authentication" anfordern.
     *
     * @param id ID des angeforderten Interpretn
     * @return Gefundener Interpret
     */
    @GetExchange("/{id}")
    ResponseEntity<Interpret> getInterpret(
        @PathVariable String id
    );
}
