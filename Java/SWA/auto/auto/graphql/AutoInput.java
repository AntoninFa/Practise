package com.acme.auto.graphql;

import com.acme.auto.entity.Ausstattungsoption;
import com.acme.auto.entity.Auto;
import com.acme.auto.entity.GetriebeType;
import com.acme.auto.entity.TreibstoffartType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Eine Value-Klasse für Eingabedaten passend zu AutoInput aus dem GraphQL-Schema.
 *
 * @author <a href="mailto:A@h-ka.de">A A</a>
 * @param hersteller Hersteller
 * @param modellbezeichnung Modellbezeichnung
 * @param fin FIN
 * @param kilometerstand Kilometerstand
 * @param baujahr Baujahr
 * @param hubraum Hubraum
 * @param leistung Leistung
 * @param getriebeart Getriebeart
 * @param treibstoffart Treibstoffart
 * @param grundpreis Grundpreis
 * @param waehrung Währung
 * @param ausstattung Ausstattung als Liste
 */
record AutoInput(
    String hersteller,
    String modellbezeichnung,
    String fin,
    int kilometerstand,
    int baujahr,
    int hubraum,
    int leistung,
    GetriebeType getriebeart,
    TreibstoffartType treibstoffart,
    BigDecimal grundpreis,
    Currency waehrung,
    List<AusstattungsoptionInput> ausstattung
) {
    /**
     * Konvertierung in ein Objekt der Entity-Klasse Auto.
     *
     * @return Das konvertierte Auto-Objekt
     */
    Auto toAuto() {
        final List<Ausstattungsoption> ausstattungEntity;
        if (ausstattung == null) {
            ausstattungEntity = new ArrayList<>(1);
        } else {
            ausstattungEntity = ausstattung.stream()
                .map(ausstattungsoption -> Ausstattungsoption.builder()
                    .name(ausstattungsoption.name())
                    .preis(ausstattungsoption.preis())
                    .waehrung(ausstattungsoption.waehrung())
                    .build()
                )
                .collect(Collectors.toList());
        }

        final var auto = Auto
            .builder()
            .id(null)
            .hersteller(hersteller)
            .modellbezeichnung(modellbezeichnung)
            .fin(fin)
            .kilometerstand(kilometerstand)
            .baujahr(baujahr)
            .hubraum(hubraum)
            .leistung(leistung)
            .getriebeart(getriebeart)
            .treibstoffart(treibstoffart)
            .grundpreis(grundpreis)
            .waehrung(waehrung)
            .ausstattung(ausstattungEntity)
            .build();

        // Rueckwaertsverweise
        auto.getAusstattung()
            .forEach(ausstattungsoption -> ausstattungsoption.setAuto(auto));
        return auto;
    }
}
