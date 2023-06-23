package com.acme.song.repository;
import com.acme.song.entity.GenreType;
import com.acme.song.entity.QSong;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import static java.util.Locale.GERMAN;

/**
 * Singleton-Klasse, um Prädikate durch QueryDSL für eine WHERE-Klausel zu bauen.
 */
@Component
@Slf4j
public class PredicateBuilder {
    /**
     * Prädikate durch QueryDSL für eine WHERE-Klausel bauen.
     *
     * @param queryParams als MultiValueMap
     * @return Predicate in QueryDSL für eine WHERE-Klausel
     */
    @SuppressWarnings("ReturnCount")
    public Optional<Predicate> build(final Map<String, ? extends List<String>> queryParams) {
        log.debug("build: queryParams={}", queryParams);

        final var qSong = QSong.song;
        final var booleanExprList = queryParams
            .entrySet()
            .stream()
            .map(entry -> toBooleanExpression(entry.getKey(), entry.getValue(), qSong))
            .toList();
        if (booleanExprList.isEmpty() || booleanExprList.contains(null)) {
            return Optional.empty();
        }
        final var result = booleanExprList
            .stream()
            .reduce(booleanExprList.get(0), BooleanExpression::and);
        return Optional.of(result);
    }

    @SuppressWarnings({"CyclomaticComplexity", "UnnecessaryParentheses"})
    private BooleanExpression toBooleanExpression(
        final String paramName,
        final List<String> paramValues,
        final QSong qSong
    ) {
        log.trace("toSpec: paramName={}, paramValues={}", paramName, paramValues);

        if (paramValues == null || (!Objects.equals(paramName, "songGenre") && paramValues.size() != 1)) {
            return null;
        }
        final var value = paramValues.get(0);
        return switch (paramName) {
            case "titel" -> titel(value, qSong);
            case "musikLabel" -> musikLabel(value, qSong);
            case "songGenre" -> genre(paramValues, qSong);
            default -> null;
        };
    }

    private BooleanExpression titel(final String teil, final QSong qSong) {
        return qSong.titel.toLowerCase().matches("%" + teil.toLowerCase(GERMAN) + '%');
    }

    private BooleanExpression musikLabel(final String teil, final QSong qSong) {
        return qSong.titel.toLowerCase().matches("%" + teil.toLowerCase(GERMAN) + '%');
    }

    private BooleanExpression genre(final Collection<String> genre, final QSong qSong) {
        if (genre == null || genre.isEmpty()) {
            return null;
        }
        final var expressionList = genre
            .stream()
            .map(genreStr -> GenreType.of(genreStr).orElse(null))
            .filter(Objects::nonNull)
            .map(g -> qSong.genreStr.matches("%" + g.name() + '%'))
            .toList();
        if (expressionList.isEmpty()) {
            return null;
        }
        return expressionList
            .stream()
            .reduce(expressionList.get(0), BooleanExpression::and);
    }
}
