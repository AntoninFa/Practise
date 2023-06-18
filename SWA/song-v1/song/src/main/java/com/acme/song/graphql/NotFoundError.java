package com.acme.song.graphql;

import graphql.GraphQLError;
import graphql.language.SourceLocation;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.execution.ErrorType;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import static org.springframework.graphql.execution.ErrorType.NOT_FOUND;

/**
 * Fehlerklasse für GraphQL, falls eine NotFoundException geworfen wurde.
 */
@RequiredArgsConstructor
class NotFoundError implements GraphQLError {
    private final UUID id;
    private final Map<String, List<String>> suchkriterien;

    /**
     * Der Typ des Errors wird auf NOT_FOUND gesetzt.
     *
     * @return NOT_FOUND als ErrorType
     */
    @Override
    public ErrorType getErrorType() {
        return NOT_FOUND;
    }

    /**
     * Message innerhalb von Errors beim Response für einen GraphQL-Request.
     *
     * @return Message für errors.
     */
    @Override
    public String getMessage() {
        return id == null
            ? "Kein Song gefunden: suchkriterien=" + suchkriterien
            : "Kein Song mit der ID " + id + " gefunden";
    }

    /**
     * Keine Angabe von Zeilen- und Spaltennummer der GraphQL-Query, falls kein Song gefunden wurde.
     *
     * @return null.
     */
    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }
}
