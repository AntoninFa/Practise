package com.acme.song.rest;

import com.acme.song.service.SongWriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

import static com.acme.song.rest.SongGetController.ID_PATTERN;
import static com.acme.song.rest.SongGetController.REST_PATH;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.created;

/**
 * REST-Schnittstelle fürs Schreiben.
 */
@Controller
@RequestMapping(REST_PATH)
@RequiredArgsConstructor
@Slf4j
class SongWriteController {
    private final SongWriteService writeService;
    private final UriHelper uriHelper;


    /**
     * Einen neuen Song-Datensatz anlegen.
     *
     * @param songDTO Das Song DataTransferObject aus dem eingegangenen Request-Body.
     * @param request Das Request-Objekt, um `Location` im Response-Header zu erstellen.
     * @return Response mit dem Satuscode 201, einschließlich Location-Header.
     */
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Einen neuen Song anlegen", tags = "Neuanlegen")
    @ApiResponse(responseCode = "201", description = "Song neu angelegt")
    ResponseEntity<Void> create(@RequestBody final SongDTO songDTO, final HttpServletRequest request) {
        log.debug("create: {}", songDTO);
        final var song = writeService.create(songDTO.toSong());
        final var baseUri = uriHelper.getBaseUri(request).toString();
        final var location = URI.create(baseUri + '/' + song.getId());
        return created(location).build();
    }

    /**
     * Einen vorhandenen Song-Datensatz überschreiben.
     *
     * @param id       ID des Songs, welcher Überschrieben werden soll.
     * @param songDTO as Song DataTransferObject aus dem eingegangenen Request-Body.
     */
    @PutMapping(path = "{id:" + ID_PATTERN + "}", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Einen Song mit neuen Werten aktualisieren", tags = "Aktualisieren")
    @ApiResponse(responseCode = "204", description = "Aktualisiert")
    @ApiResponse(responseCode = "400", description = "Syntaktische Fehler im Request-Body")
    @ApiResponse(responseCode = "404", description = "Song nicht in der Datenbank")
    @ApiResponse(responseCode = "422", description = "Ungültige Werte")
    void update(@PathVariable final UUID id, @RequestBody final SongDTO songDTO
    ) {
        log.debug("update: id={}, {}", id, songDTO);
        final var song = songDTO.toSong();
        //TODO konnte hier nicht direkt songDTO.toSong() in der Klammer machen, why?
        writeService.update(song, id);
    }
}
