package com.acme.song.graphql;

import java.util.UUID;

/**
 * Value-Klasse für das Resultat von create in der GraphQL-Schnittstelle
 *
 * @param id ID des neu angelegten Songs
 */
record CreatePayload(UUID id) {
}
