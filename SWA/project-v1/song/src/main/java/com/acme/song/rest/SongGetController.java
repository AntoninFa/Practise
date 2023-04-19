package com.acme.song.rest;

import com.acme.song.entity.Song;
import com.acme.song.service.NotFoundException;
import com.acme.song.service.SongReadService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Not;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.acme.song.rest.SongGetController.REST_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.UUID;
import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequestMapping(REST_PATH)
//TODO ?? Kann ziemlich save weg
//@OpenAPIDefinition(info = @Info(title = "Kunde API", version = "v1"))
@RequiredArgsConstructor
@Slf4j
public class SongGetController {


    private final SongReadService service;

    /**
     * Basispfad für die REST-Schnittstelle.
     */
    public static final String REST_PATH = "/rest";

    /**
     * Regex für eine gültige UUID.
     */
    public static final String ID_PATTERN =
        "[\\dA-Fa-f]{8}-[\\dA-Fa-f]{4}-[\\dA-Fa-f]{4}-[\\dA-Fa-f]{4}-[\\dA-Fa-f]{12}";


    @ApiResponse(responseCode = "200", description = "Kunde gefunden")
    @GetMapping(path = "{id:" + ID_PATTERN + "}", produces = APPLICATION_JSON_VALUE)
    Song findById(@PathVariable final UUID id) {
        log.debug("findById: id={}", id);
        final var song = service.findById(id);
        return song;
    }
}
