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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.PRECONDITION_FAILED;
import static org.springframework.http.HttpStatus.PRECONDITION_REQUIRED;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.created;
import static com.acme.song.rest.SongGetController.ID_PATTERN;
import static com.acme.song.rest.SongGetController.REST_PATH;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.noContent;

/**
 * Eine @RestController-Klasse als REST-Schnittstelle für lesende Zugriffe.
 * <img src="../../../../../../../build/docs/asciidoc/SongWriteController.svg" alt="Klassendiagramm">
 */
@Controller
@RequestMapping(REST_PATH)
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings("ClassFanOutComplexity")
class SongWriteController {
    private static final String VERSIONSNUMMER_FEHLT = "Versionsnummer fehlt";
    /**
     * Basispfad für "type" innerhalb von ProblemDetail.
     */
    public static final String PROBLEM_PATH = "/problem/";
    private final SongWriteService service;
    private final UriHelper uriHelper;

    /**
     * Einen neuen Song-Datensatz anlegen.
     *
     * @param songDTO Das Song DataTransferObject aus dem eingegangenen Request-Body.
     * @param request Das Request-Objekt, um `Location` im Response-Header zu erstellen.
     * @return Response mit dem Statuscode 201, einschließlich Location-Header.
     */
    @Operation(summary = "Einen neuen Song anlegen", tags = "Neuanlegen")
    @ApiResponse(responseCode = "201", description = "Song neu angelegt")
    @ApiResponse(responseCode = "400", description = "Syntaktische Fehler im Request-Body")
    @ApiResponse(responseCode = "422", description = "Fehlerhafte Werte")
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<Void> post(@RequestBody final SongDTO songDTO, final HttpServletRequest request) {
        log.debug("create: {}", songDTO);

        if (songDTO == null) {
            return badRequest().build();
        }
        final var song = service.create(songDTO.toSong());
        final var baseUri = uriHelper.getBaseUri(request).toString();
        final var location = URI.create(baseUri + '/' + song.getId());
        return created(location).build();
    }

    /**
     * Einen vorhandenen Song-Datensatz überschreiben.
     *
     * @param id      ID des Songs, welcher Überschrieben werden soll.
     * @param songDTO as Song DataTransferObject aus dem eingegangenen Request-Body.
     * @return Response mit Statuscode 204 oder Statuscode 400, falls der JSON-Datensatz syntaktisch nicht korrekt ist
     *          ,oder 422 falls Constraints verletzt sind
     *          ,oder 412 falls die Versionsnummer nicht ok ist
     *          ,oder 428 falls die Versionsnummer fehlt.
     */
    @PutMapping(path = "{id:" + ID_PATTERN + "}", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Einen Song mit neuen Werten aktualisieren", tags = "Aktualisieren")
    @ApiResponse(responseCode = "204", description = "Aktualisiert")
    @ApiResponse(responseCode = "400", description = "Syntaktische Fehler im Request-Body")
    @ApiResponse(responseCode = "404", description = "Song nicht in der Datenbank")
    @ApiResponse(responseCode = "422", description = "Fehlerhafte Werte")
    @ApiResponse(responseCode = "412", description = "Versionsnummer falsch")
    @ApiResponse(responseCode = "428", description = VERSIONSNUMMER_FEHLT)
    ResponseEntity<Void> put(@PathVariable final UUID id,
                               @RequestBody final SongDTO songDTO,
                               @RequestHeader("If-Match") final Optional<String> version,
                               final HttpServletRequest sRequest
    ) {
        log.debug("put: id={}, {}", id, songDTO);

        final int versionInt = getVersion(version, sRequest);
        final var song = service.update(songDTO.toSong(), id, versionInt);
        log.debug("put: {}", song);

        return noContent().eTag("\"" + song.getVersion() + '"').build();
    }

    @SuppressWarnings({"MagicNumber"})
    private int getVersion(final Optional<String> versionOpt, final HttpServletRequest request) {
        log.trace("getVersion: {}", versionOpt);
        if (versionOpt.isEmpty()) {
            throw new VersionInvalidException(
                PRECONDITION_REQUIRED,
                VERSIONSNUMMER_FEHLT,
                URI.create(request.getRequestURL().toString()));
        }


    final var versionStr = versionOpt.get();
        if (versionStr.length() < 3 ||
        versionStr.charAt(0) != '"' ||
        versionStr.charAt(versionStr.length() - 1) != '"') {
        throw new VersionInvalidException(
            PRECONDITION_FAILED,
            "Ungueltiges ETag " + versionStr,
            URI.create(request.getRequestURL().toString())
        );
    }

    final int version;
        try {
        version = Integer.parseInt(versionStr.substring(1, versionStr.length() - 1));
    } catch (final NumberFormatException ex) {
        throw new VersionInvalidException(
            PRECONDITION_FAILED,
            "Ungueltiges ETag " + versionStr,
            URI.create(request.getRequestURL().toString()),
            ex
        );
    }

        log.trace("getVersion: version={}", version);
        return version;
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

    @ExceptionHandler
    ProblemDetail onVersionOutdated(
        final VersionInvalidException ex,
        final HttpServletRequest request
    ) {
        log.debug("onVersionOutdated: {}", ex.getMessage());
        final var problemDetail = ProblemDetail.forStatusAndDetail(PRECONDITION_FAILED, ex.getMessage());
        problemDetail.setType(URI.create(PROBLEM_PATH + ProblemType.PRECONDITION.getValue()));
        problemDetail.setInstance(URI.create(request.getRequestURL().toString()));
        return problemDetail;
    }
}
