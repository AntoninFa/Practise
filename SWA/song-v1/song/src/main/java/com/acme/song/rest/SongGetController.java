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
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import static com.acme.song.rest.SongGetController.REST_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import java.util.Collection;
import java.util.UUID;

/**
 * REST-Schnittstelle fürs Lesen.
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

    /**
     * Suche nach Song mit der passenden ID.
     *
     * @param id id des gesuchten Songs
     * @return den Song
     */
    @ApiResponse(responseCode = "200", description = "Song gefunden")
    @GetMapping(path = "{id:" + ID_PATTERN + "}", produces = APPLICATION_JSON_VALUE)
    Song findById(@PathVariable final UUID id) {
        log.debug("findById: id={}", id);
        return service.findById(id);
    }

    /**
     * Sucht alle Songs.
     *
     * @return Response mit allen Songs und Statuscode 200
     */
    @ApiResponse(responseCode = "200", description = "Song gefunden")
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    Collection<Song> findAll() {
        final var songs = service.findAll();
        log.debug("findAll: {}", songs);
        return songs;
    }


    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Suche mit Suchkriterien", tags = "Suchen")
    @ApiResponse(responseCode = "200", description = "CollectionModel mid den Songs")
    @ApiResponse(responseCode = "404", description = "Keine Songs gefunden")
    Collection<Song> find(
        @RequestParam @NonNull final MultiValueMap<String, String> suchkriterien) {
        log.debug("find: suchkriterien={}", suchkriterien);
        return service.find(suchkriterien);
    }
}
