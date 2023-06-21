package com.acme.song.rest;

import com.acme.song.entity.Song;
import com.acme.song.service.SongReadService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import static com.acme.song.rest.SongGetController.REST_PATH;
import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;
import static org.springframework.http.HttpStatus.NOT_MODIFIED;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

/**
 * Eine @RestController-Klasse als REST-Schnittstelle für lesende Zugriffe.
 * <img src="../../../../../../../build/docs/asciidoc/SongGetController.svg" alt="Klassendiagramm">
 */
@Controller
@RequestMapping(REST_PATH)
@ResponseBody
@OpenAPIDefinition(info = @Info(title = "Song API", version = "v1"))
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings("ClassFanOutComplexity")
public class SongGetController {
    /**
     * Basispfad für die REST-Schnittstelle.
     */
    public static final String REST_PATH = "/rest";
    /**
     * Regex für eine gültige UUID.
     */
    public static final String ID_PATTERN =
        "[\\dA-Fa-f]{8}-[\\dA-Fa-f]{4}-[\\dA-Fa-f]{4}-[\\dA-Fa-f]{4}-[\\dA-Fa-f]{12}";

    private final SongReadService service;
    private final UriHelper uriHelper;

    /**
     * Suche nach Song mit der passenden ID.
     *
     * @param id id des gesuchten Songs.
     * @param version Versionsnummer aus dem Header If-None-Match.
     * @param request Das Request-Objekt, um Links für HATEOAS zu erstellen.
     * @param authentication Authentication-Objekt für Security
     * @return Response mit dem gefundenen Song.
     */
    @Operation(summary = "Suche anhand der Song-ID", tags = "Suchen")
    @ApiResponse(responseCode = "200", description = "Song gefunden")
    @ApiResponse(responseCode = "404", description = "Song konnte nicht gefunden werden")
    @GetMapping(path = "{id:" + ID_PATTERN + "}", produces = HAL_JSON_VALUE)
    ResponseEntity<SongModel> getById(@PathVariable final UUID id,
                                       @RequestHeader("If-None-Match") final Optional<String> version,
                                       final HttpServletRequest request,
                                       final Authentication authentication) {
        log.debug("getById: id={}, version={}", id, version);

        final var song = service.findById(id);
        log.debug("getById: {}", song);

        final var currentVersion = "\"" + song.getVersion() + '"';
        if (Objects.equals(version.orElse(null), currentVersion)) {
            return status(NOT_MODIFIED).build();
        }
        final var model = songToModel(song, request);
        log.debug("getById: model={}", model);

        return ok().eTag(currentVersion).body(model);
    }

    private SongModel songToModel(final Song song, final HttpServletRequest request) {
        final var model = new SongModel(song);
        final var baseUri = uriHelper.getBaseUri(request).toString();
        final var idUri = baseUri + '/' + song.getId();
        final var selfLink = Link.of(idUri);
        final var listLink = Link.of(baseUri, LinkRelation.of("list"));
        final var addLink = Link.of(baseUri, LinkRelation.of("add"));
        final var updateLink = Link.of(idUri, LinkRelation.of("update"));
        final var removeLink = Link.of(idUri, LinkRelation.of("remove"));
        model.add(selfLink, listLink, addLink, updateLink, removeLink);
        return model;
    }

    /**
     * Suche mit Query Parametern.
     *
     * @param suchkriterien Query-Parameter als Map.
     * @param sRequest Das HttpServletRequest-Objekt, um Links für HATEOAS zu erstellen.
     * @return Die gefundenen Songs als Collection aus Songs
     */
    @Operation(summary = "Suche mit den Suchkriterien", tags = "Suchen")
    @ApiResponse(responseCode = "200", description = "CollectionModel mit den Songs")
    @ApiResponse(responseCode = "404", description = "Keine Songs gefunden")
    @GetMapping(produces = HAL_JSON_VALUE)
    CollectionModel<? extends SongModel> find(
        @RequestParam @NonNull final MultiValueMap<String, String> suchkriterien,
        final HttpServletRequest sRequest) {
        log.debug("find: suchkriterien={}", suchkriterien);

        final var baseUri = uriHelper.getBaseUri(sRequest).toString();
        final var songModels = service.find(suchkriterien)
            .stream()
            .map(song -> {
                final var songModel = new SongModel(song);
                songModel.add(Link.of(baseUri + '/' + song.getId()));
                return songModel;
            }).toList();
        log.debug("find: model= {}", songModels);

        return CollectionModel.of(songModels);
    }
}
