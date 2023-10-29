package com.acme.auto.repository;
import com.acme.auto.entity.GetriebeType;
import com.acme.auto.entity.TreibstoffartType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.Predicate;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Map;
import com.acme.auto.entity.QAuto;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import static java.util.Locale.GERMAN;

/**
 * Singleton-Klasse, um Prädikate durch QueryDSL für eine WHERE-Klausel zu bauen.
 *
 * @author <a href="mailto:A@h-ka.de">A A</a>
 */
@Component
@Slf4j
public class PredicateBuilder {
    /**
     * Prädikate durch QueryDSL für eine WHERE-Klausel zu bauen.
     *
     * @param queryParams als MultiValueMap
     * @return Predicate in QueryDSL für eine WHERE-Klausel
     */
    @SuppressWarnings("ReturnCount")
    public Optional<Predicate> build(final Map<String, ? extends List<String>> queryParams) {
        log.debug("build: queryParams={}", queryParams);

        final var qAuto = QAuto.auto;
        final var booleanExprList = queryParams
            .entrySet()
            .stream()
            .map(entry -> toBooleanExpression(entry.getKey(), entry.getValue(), qAuto))
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
        final QAuto qAuto
    ) {

        log.trace("toSpec: paramName={}, paramValues={}", paramName, paramValues);

        if (paramValues == null) {
            return null;
        }

        final var value = paramValues.get(0);
        return switch (paramName) {
            case "hersteller" -> hersteller(value, qAuto);
            case "modellbezeichnung" ->  modellbezeichnung(value, qAuto);
            case "fin" -> fin(value, qAuto);
            case "kilometerstand" -> kilometerstand(value, qAuto);
            case "baujahr" -> baujahr(value, qAuto);
            case "hubraum" -> hubraum(value, qAuto);
            case "leistung" -> leistung(value, qAuto);
            case "getriebeart" -> getriebeart(value, qAuto);
            case "treibstoffart" -> treibstoffart(value, qAuto);
            case "grundpreis" -> grundpreis(value, qAuto);
            case "waehrung" -> waehrung(value, qAuto);
            default -> null;
        };
    }
    private BooleanExpression hersteller(final String teil, final QAuto qAuto) {
        return qAuto.hersteller.toLowerCase().matches("%" + teil.toLowerCase(GERMAN) + '%');
    }
    private BooleanExpression modellbezeichnung(final String teil, final QAuto qAuto) {
        return qAuto.modellbezeichnung.toLowerCase().matches("%" + teil.toLowerCase(GERMAN) + '%');
    }
    private BooleanExpression fin(final String teil, final QAuto qAuto) {
        return qAuto.fin.toLowerCase().matches("%" + teil.toLowerCase(GERMAN) + '%');
    }
    private BooleanExpression kilometerstand(final String kilometerstand, final QAuto qAuto) {
        final int kilometerstandInt;
        try {
            kilometerstandInt = Integer.parseInt(kilometerstand);
        } catch (final NumberFormatException e) {
            //noinspection ReturnOfNull
            return null;
        }
        return qAuto.kilometerstand.eq(kilometerstandInt);
    }
    private BooleanExpression baujahr(final String baujahr, final QAuto qAuto) {
        final int baujahrInt;
        try {
            baujahrInt = Integer.parseInt(baujahr);
        } catch (final NumberFormatException e) {
            //noinspection ReturnOfNull
            return null;
        }
        return qAuto.baujahr.eq(baujahrInt);
    }
    private BooleanExpression hubraum(final String hubraum, final QAuto qAuto) {
        final int hubraumInt;
        try {
            hubraumInt = Integer.parseInt(hubraum);
        } catch (final NumberFormatException e) {
            //noinspection ReturnOfNull
            return null;
        }
        return qAuto.hubraum.eq(hubraumInt);
    }
    private BooleanExpression leistung(final String leistung, final QAuto qAuto) {
        final int leistungInt;
        try {
            leistungInt = Integer.parseInt(leistung);
        } catch (final NumberFormatException e) {
            //noinspection ReturnOfNull
            return null;
        }
        return qAuto.leistung.eq(leistungInt);
    }
    private BooleanExpression getriebeart(final String getriebeart, final QAuto qAuto) {
        return qAuto.getriebeart.eq(GetriebeType.of(getriebeart).orElse(null));
    }
    private BooleanExpression treibstoffart(final String treibstoffart, final QAuto qAuto) {
        return qAuto.treibstoffart.eq(TreibstoffartType.of(treibstoffart).orElse(null));
    }
    private BooleanExpression grundpreis(final String grundpreis, final QAuto qAuto) {
        final BigDecimal grundpreisDecimal;
        try {
            grundpreisDecimal = new BigDecimal(grundpreis);
        } catch (final NumberFormatException e) {
            //noinspection ReturnOfNull
            return null;
        }
        return qAuto.grundpreis.eq(grundpreisDecimal);
    }
    private BooleanExpression waehrung(final String waehrung, final QAuto qAuto) {
        final Currency waehrungCurrency;
        try {
            waehrungCurrency = Currency.getInstance(waehrung);
        } catch (final IllegalArgumentException e) {
            //noinspection ReturnOfNull
            return null;
        }
        return qAuto.waehrung.eq(waehrungCurrency);
    }
}
