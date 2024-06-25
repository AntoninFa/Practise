package com.acme.song.repository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * "HTTP Interface" für den REST-Client für Daten über Interpreten.
 */
@HttpExchange("/rest")
public interface InterpretRestRepository {

    /**
     * Einen Interpretndatensatz vom Microservice "interpret" mit "Basic Authentication" anfordern.
     *
     * @param id ID des angeforderten Interpreten
     * @param authorization String für den HTTP-Header "Authorization"
     * @return Gefundener Interpret
     */
    @GetExchange("/{id}")
    ResponseEntity<Interpret> getInterpret(
        @PathVariable String id,
        @RequestHeader(AUTHORIZATION) String authorization
    );
}
