package com.acme.song.rest;

import com.acme.song.entity.Song;
import com.acme.song.service.SongReadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.UUID;

import static com.acme.song.rest.SongGetController.REST_PATH;
import static org.springframework.hateoas.MediaTypes.HAL_JSON_VALUE;

/**
 * Eine @RestController-Klasse als REST-Schnittstelle für lesende Zugriffe.
 */
@Controller
@RequestMapping(REST_PATH)
@ResponseBody
@RequiredArgsConstructor
@Slf4j
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

    //TODO ist @Operation wichtig, irwie für Java-Doc oderso? Gleiche Frage bei find..

    /**
     * Suche nach Song mit der passenden ID.
     *
     * @param id id des gesuchten Songs
     * @return den Song
     */
    @ApiResponse(responseCode = "200", description = "Song gefunden")
    @ApiResponse(responseCode = "404", description = "Song konnte nicht gefunden werden")
    @GetMapping(path = "{id:" + ID_PATTERN + "}", produces = HAL_JSON_VALUE)
    SongModel findById(@PathVariable final UUID id, final HttpServletRequest sRequest) {
        log.debug("findById: id={}", id);

        final var song = service.findById(id);
        final var model = new SongModel(song);
        final var baseUri = uriHelper.getBaseUri(sRequest).toString();
        final var idUri = baseUri + '/' + song.getId();
        final var selfLink = Link.of(idUri);
        final var listLink = Link.of(baseUri, LinkRelation.of("list"));
        final var addLink = Link.of(baseUri, LinkRelation.of("add"));
        final var updateLink = Link.of(idUri, LinkRelation.of("update"));
        final var removeLink = Link.of(idUri, LinkRelation.of("remove"));
        model.add(selfLink, listLink, addLink, updateLink, removeLink);
        log.debug("findById: model= {}", model);

        return model;
    }

    /**
     * Suche mit Query Parametern.
     *
     * @param suchkriterien Query-Parameter als Map.
     * @return Die gefundenen Songs als Collection aus Songs
     */
    @Operation(summary = "Suche mit Suchkriterien", tags = "Suchen")
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
