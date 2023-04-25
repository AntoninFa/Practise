package com.acme.song.rest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import static com.acme.song.rest.SongGetController.REST_PATH;

/**
 * Hilfsklasse um URIs zu ermitteln.
 */
@Component
@Slf4j
class UriHelper {

    private static final String X_FORWARDED_PROTO = "X-Forwarded-Proto";
    private static final String X_FORWARDED_HOST = "x-forwarded-host";
    private static final String X_FORWARDED_PREFIX = "x-forwarded-prefix";
    private static final String SONGS_PREFIX = "/songs";

    /**
     * Basis-URI ermitteln.
     *
     * @param request Servlet-Request.
     * @return Die Basis-URI als String.
     */
    URI getBaseUri(final HttpServletRequest request) {
        final var forwardedHost = request.getHeader(X_FORWARDED_HOST);
        if (forwardedHost != null) {
            return getBaseUriForwarded(request, forwardedHost);
        }
        final var uriComponents = ServletUriComponentsBuilder.fromRequestUri(request).build();
        final var baseUri = uriComponents.getScheme() + "://" + uriComponents.getHost() + ':' +
            uriComponents.getPort() + REST_PATH;
        log.debug("getBaseUri (ohne Forwarding): baseUri={}", baseUri);
        return URI.create(baseUri);
    }

    private URI getBaseUriForwarded(final HttpServletRequest request, final String forwardedHost) {
        final var forwardedProto = request.getHeader(X_FORWARDED_PROTO);
        if (forwardedProto == null) {
            throw new IllegalStateException("Kein \"" + X_FORWARDED_PROTO + "\" im Header");
        }
        var forwardedPrefix = request.getHeader(X_FORWARDED_PREFIX);
        if (forwardedPrefix == null) {
            log.trace("getBaseUriForwarded: Kein \"" + X_FORWARDED_PREFIX + "\" im Header");
            forwardedPrefix = SONGS_PREFIX;
        }
        final var baseUri = forwardedProto + "://" + forwardedHost + forwardedPrefix + REST_PATH;
        log.debug("getBaseUriForwarded: baseUri={}", baseUri);
        return URI.create(baseUri);
    }
}
