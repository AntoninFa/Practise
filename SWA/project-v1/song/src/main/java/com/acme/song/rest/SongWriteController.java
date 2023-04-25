package com.acme.song.rest;

import com.acme.song.service.SongWriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

import static com.acme.song.rest.SongGetController.REST_PATH;
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
}
