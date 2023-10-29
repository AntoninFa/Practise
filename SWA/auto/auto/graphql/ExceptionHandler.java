package com.acme.auto.graphql;

import com.acme.auto.service.NotFoundException;
import graphql.GraphQLError;
import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import static org.springframework.graphql.execution.ErrorType.NOT_FOUND;

/**
 * Abbildung von Exceptions auf GraphQLError.
 *
 * @author <a href="mailto:A@h-ka.de">A A</a>
 */
@ControllerAdvice
final class ExceptionHandler {
    @GraphQlExceptionHandler
    GraphQLError handleNotFound(final NotFoundException ex) {
        final var id = ex.getId();
        final var message = id == null
            ? "Kein Auto gefunden: suchkriterien=" + ex.getSuchkriterien()
            : "Kein Auto mit der ID " + id + " gefunden";
        return GraphQLError.newError()
            .errorType(NOT_FOUND)
            .message(message)
            .build();
    }
}
