package com.acme.auto.graphql;

import com.acme.auto.entity.Auto;
import com.acme.auto.service.AutoWriteService;
import com.acme.auto.service.ConstraintViolationsException;
import com.acme.auto.service.FinExistsException;
import graphql.GraphQLError;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import static org.springframework.graphql.execution.ErrorType.BAD_REQUEST;

/**
 * Eine Controller-Klasse für das Schreiben mit der GraphQL-Schnittstelle und den Typen aus dem GraphQL-Schema.
 *
 * @author <a href="mailto:A@h-ka.de">A A</a>
 */
@Controller
@RequiredArgsConstructor
@Slf4j
class AutoMutationController {
    private final AutoWriteService service;

    /**
     * Ein neues Auto anlegen.
     *
     * @param input Die Eingabedaten für ein neues Auto
     * @return Die generierte ID für das neue Auto als Payload
     */
    @MutationMapping
    CreatePayload create(@Argument final AutoInput input) {
        log.debug("create: input={}", input);
        final var id = service.create(input.toAuto()).getId();
        log.debug("create: id={}", id);
        return new CreatePayload(id);
    }

    @GraphQlExceptionHandler
    GraphQLError handleFinExists(final FinExistsException ex) {
        return GraphQLError.newError()
            .errorType(BAD_REQUEST)
            .message("Die FIN " + ex.getFin() + " existiert bereits.")
            .build();
    }

    @GraphQlExceptionHandler
    Collection<GraphQLError> handleConstraintViolations(final ConstraintViolationsException ex) {
        return ex.getViolations()
            .stream()
            .map(this::violationToGraphQLError)
            .collect(Collectors.toList());
    }

    private GraphQLError violationToGraphQLError(final ConstraintViolation<Auto> violation) {
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
