package com.acme.auto.repository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
/**
 * "HTTP Interface" für den REST-Client für Kundedaten.
 *
 * @author <a href="mailto:A@h-ka.de">A A</a>
 */
@HttpExchange("/rest")
public interface AutohausRestRepository {
    /**
     * Alle Autohausdatensätze vom Microservice "autohaus" mit "Basic Authentication" anfordern.
     *
     * @param authorization String für den HTTP-Header "Authorization"
     * @return Gefundenes Autohaus
     */
    @GetExchange("")
    ResponseEntity<AutohausResponse> getAutohaus(@RequestHeader(AUTHORIZATION) String authorization);
}
