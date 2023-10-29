package com.acme.auto.graphql;

import java.util.UUID;

/**
 * Value-Klasse für das Resultat, wenn an der GraphQL-Schnittstelle ein neues Auto angelegt wurde.
 *
 * @author <a href="mailto:A@h-ka.de">A A</a>
 *
 * @param id ID des neu angelegten Autos
 */
record CreatePayload(UUID id) {
}
