package com.acme.song.graphql;

import com.acme.song.entity.Song;
import com.acme.song.service.ConstraintViolationsException;
import com.acme.song.service.SongWriteService;
import graphql.GraphQLError;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import org.springframework.graphql.data.method.annotation.Argument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.graphql.execution.ErrorType.BAD_REQUEST;

/**
 * Controller-Klasse für Schreibzugriffe über die GraphQL-Schnittstelle.
 */
@Controller
@RequiredArgsConstructor
@Slf4j
class SongMutationController {
    private final SongWriteService service;

    /**
     * Anlegen eines neuen Songs.
     *
     * @param input Eingabedaten für einen neuen Song
     * @return ID des erstellten Songs als Payload
     */
    @MutationMapping
    CreatePayload create(@Argument final SongInput input) {
        log.debug("create: input= {}", input);

        final var id = service.create(input.toSong()).getId();
        log.debug("create: id= {}", id);

        return new CreatePayload(id);
    }

    @GraphQlExceptionHandler
    GraphQLError handleDateTimeParseException(final DateTimeParseException ex) {
        return GraphQLError.newError()
            .errorType(BAD_REQUEST)
            .message("Das Datum " + ex.getParsedString() + " konnte nicht geparsed werden")
            .build();
    }

    @GraphQlExceptionHandler
    Collection<GraphQLError> handleConstraintViolations(final ConstraintViolationsException ex) {
        return ex.getViolations()
            .stream()
            .map(this::violationToGraphQLError)
            .collect(Collectors.toList());
    }

    private GraphQLError violationToGraphQLError(final ConstraintViolation<Song> violation) {
        final List<Object> path = new ArrayList<>(5);
        path.add("input");
        for (final Path.Node node: violation.getPropertyPath()) {
            path.add(node.toString());
        }
        return GraphQLError.newError()
            .errorType(BAD_REQUEST)
            .message(violation.getMessage())
            .path(path)
            .build();
    }
}
