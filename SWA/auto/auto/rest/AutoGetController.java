package com.acme.auto.rest;

import com.acme.auto.entity.Auto;
import com.acme.auto.service.AutoReadService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static com.acme.auto.rest.AutoGetController.REST_PATH;
import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;
import static org.springframework.http.HttpStatus.NOT_MODIFIED;

/**
 * Eine @RestController-Klasse bildet die REST-Schnittstelle, wobei die HTTP-Methoden, Pfade und MIME-Typen auf die
 * Methoden der Klasse abgebildet werden.
 *  <img src="../../../../../asciidoc/AutoGetController.svg" alt="Klassendiagramm">
 *
 * @author <a href="mailto:A@h-ka.de">A A</a>
 */
@RestController
@RequestMapping(REST_PATH)
@ResponseBody
@OpenAPIDefinition(info = @Info(title = "Auto API", version = "v1"))
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings({"TrailingComment", "ClassFanOutComplexity"})
public class AutoGetController {
    /**
     * Basispfad für die REST-Schnittstelle.
     */
    public static final String REST_PATH = "/rest"; //NOSONAR

    /**
     * Muster fuer eine UUID. [\dA-Fa-f]{8}{8}-([\dA-Fa-f]{8}{4}-){3}[\dA-Fa-f]{8}{12} enthält eine "capturing group"
     * und ist nicht zulässig.
     */
    static final String ID_PATTERN = "[\\dA-Fa-f]{8}-[\\dA-Fa-f]{4}-[\\dA-Fa-f]{4}-[\\dA-Fa-f]{4}-[\\dA-Fa-f]{12}";

    /**
     * Pfad, um FIN abzufragen.
     */
    @SuppressWarnings("TrailingComment")
    private static final String FIN_PATH = "/fin"; //NOSONAR

    private final AutoReadService service;
    private final UriHelper uriHelper;

    // https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html#webflux-ann-methods
    // https://localhost:8080/swagger-ui.html
    /**
     * Suche anhand der Auto-ID als Pfad-Parameter.
     *
     * @param id ID des zu suchenden Autos
     * @param request Das Request-Objekt, um Links für HATEOAS zu erstellen.
     * @param version Versionsnummer aus dem Header If-None-Match
     * @return Gefundenes Auto mit Atom-Links
     */
    @GetMapping(path = "{id:" + ID_PATTERN + "}", produces = HAL_JSON_VALUE)
    @Operation(summary = "Suche mit der Auto-ID", tags = "Suchen")
    @ApiResponse(responseCode = "200", description = "Auto gefunden")
    @ApiResponse(responseCode = "404", description = "Auto nicht gefunden")
    ResponseEntity<AutoModel> getById(
        @PathVariable final UUID id,
        @RequestHeader("If-None-Match") final Optional<String> version,
        final HttpServletRequest request
    ) {
        final var auto = service.findById(id);
        log.debug("getById: {}", auto);

        final var currentVersion = "\"" + auto.getVersion() + '"';
        if (Objects.equals(version.orElse(null), currentVersion)) {
            return status(NOT_MODIFIED).build();
        }

        /*
        final var autohaeuser = service.findAutohaeuser();
        log.debug("Response von autohaus-v2 - autohaeuser: {}", autohaeuser);
        */

        final var model = autoToModel(auto, request);
        log.debug("getById: model={}", model);
        return ok().eTag(currentVersion).body(model);
    }

    private AutoModel autoToModel(final Auto auto, final HttpServletRequest request) {
        // HATEOAS
        final var model = new AutoModel(auto);
        // evtl. Forwarding von einem API-Gateway
        final var baseUri = uriHelper.getBaseUri(request).toString();
        final var idUri = baseUri + '/' + auto.getId();
        final var selfLink = Link.of(idUri);
        final var listLink = Link.of(baseUri, LinkRelation.of("list"));
        final var addLink = Link.of(baseUri, LinkRelation.of("add"));
        final var updateLink = Link.of(idUri, LinkRelation.of("update"));
        final var removeLink = Link.of(idUri, LinkRelation.of("remove"));
        model.add(selfLink, listLink, addLink, updateLink, removeLink);
        return model;
    }

    /**
     * Suche mit diversen Suchkriterien als Query-Parameter.
     *
     * @param suchkriterien Query-Parameter als Map.
     * @param request Das Request-Objekt, um Links für HATEOAS zu erstellen.
     * @return Ein Response mit dem Statuscode 200 und den gefundenen Autos als CollectionModel oder Statuscode 404.
     */
    @GetMapping(produces = HAL_JSON_VALUE)
    @Operation(summary = "Suche mit Suchkriterien", tags = "Suchen")
    @ApiResponse(responseCode = "200", description = "List mit den Autos")
    @ApiResponse(responseCode = "404", description = "Keine Autos gefunden")
    CollectionModel<AutoModel> get(
        @RequestParam @NonNull final MultiValueMap<String, String> suchkriterien,
        final HttpServletRequest request
    ) {
        log.debug("get: suchkriterien={}", suchkriterien);

        final var baseUri = uriHelper.getBaseUri(request).toString();
        final var models = service.find(suchkriterien)
            .stream()
            .map(auto -> {
                final var model = new AutoModel(auto);
                model.add(Link.of(baseUri + '/' + auto.getId()));
                return model;
            })
            .toList();

        log.debug("get: {}", models);
        return CollectionModel.of(models);
    }

    /**
     * Abfrage, welche FIN es zu einem Präfix gibt.
     *
     * @param prefix FIN-Präfix als Pfadvariable.
     * @return Die passenden FINs oder Statuscode 404, falls es keine gibt.
     */
    @GetMapping(path = FIN_PATH + "/{prefix}", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Suche FIN mit Praefix", tags = "Suchen")
    String getFinByPrefix(@PathVariable final String prefix) {
        log.debug("getFinByPrefix: {}", prefix);
        final var fin = service.findFinByPrefix(prefix);
        log.debug("getFinByPrefix: {}", fin);
        return fin.toString();
    }
}
