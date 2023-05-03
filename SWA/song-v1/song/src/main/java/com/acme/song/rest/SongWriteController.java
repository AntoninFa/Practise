package com.acme.song.rest;

import com.acme.song.service.ConstraintViolationsException;
import com.acme.song.service.SongWriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.net.URI;
import java.util.UUID;
import static org.springframework.http.ResponseEntity.created;
import static com.acme.song.rest.SongGetController.ID_PATTERN;
import static com.acme.song.rest.SongGetController.REST_PATH;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Eine @RestController-Klasse als REST-Schnittstelle für lesende Zugriffe.
 */
@Controller
@RequestMapping(REST_PATH)
@RequiredArgsConstructor
@Slf4j
class SongWriteController {
    private static final String PROBLEM_PATH = "/problem/";
    private final SongWriteService writeService;
    private final UriHelper uriHelper;

    /**
     * Einen neuen Song-Datensatz anlegen.
     *
     * @param songDTO Das Song DataTransferObject aus dem eingegangenen Request-Body.
     * @param request Das Request-Objekt, um `Location` im Response-Header zu erstellen.
     * @return Response mit dem Statuscode 201, einschließlich Location-Header.
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
        writeService.update(song, id);
    }

    @ExceptionHandler
    ProblemDetail onConstraintViolations(
        final ConstraintViolationsException ex,
        final HttpServletRequest request
    ) {
        log.debug("onConstraintViolations: {}", ex.getMessage());

        final var songViolations = ex.getViolations()
            .stream()
            .map(violation -> violation.getPropertyPath() + ": " +
                violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName() + " " +
                violation.getMessage())
            .toList();
        log.trace("onConstraintViolations: {}", songViolations);

        final String detail;
        if (songViolations.isEmpty()) {
            detail = "N/A";
        } else {
            final var violationsStr = songViolations.toString();
            detail = violationsStr.substring(1, violationsStr.length() - 2);
        }
        final var problemDetail = ProblemDetail.forStatusAndDetail(UNPROCESSABLE_ENTITY, detail);
        problemDetail.setType(URI.create(PROBLEM_PATH + ProblemType.CONSTRAINTS.getValue()));
        problemDetail.setInstance(URI.create(request.getRequestURL().toString()));
        return problemDetail;
    }
}
