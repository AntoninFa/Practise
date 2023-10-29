package com.acme.auto.rest;

import com.acme.auto.entity.Ausstattungsoption;
import com.acme.auto.entity.Auto;
import com.acme.auto.entity.GetriebeType;
import com.acme.auto.entity.TreibstoffartType;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

/**
 * Model-Klasse für Spring HATEOAS. @lombok.Data fasst die Annotationsn @ToString, @EqualsAndHashCode, @Getter, @Setter
 * und @RequiredArgsConstructor zusammen.
 * <img src="../../../../../asciidoc/AutoModel.svg" alt="Klassendiagramm">
 *
 * @author <a href="mailto:A@h-ka.de">A A</a>
 */
@JsonPropertyOrder({
    "hersteller", "modellbezeichnung", "fin", "kilometerstand", "baujahr", "getriebeart", "treibstoffart",
    "grundpreis", "waehrung", "ausstattung"
})
@Relation(collectionRelation = "autos", itemRelation = "auto")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Getter
@Setter
@ToString(callSuper = true)
class AutoModel extends RepresentationModel<AutoModel> {
    private final String hersteller;
    private final String modellbezeichnung;
    private final String fin;
    private final int kilometerstand;
    private final int baujahr;
    private final int hubraum;
    private final int leistung;
    private final GetriebeType getriebeart;
    private final TreibstoffartType treibstoffart;
    private final BigDecimal grundpreis;
    private final Currency waehrung;
    private final List<Ausstattungsoption> ausstattung;

    AutoModel(final Auto auto) {
        hersteller = auto.getHersteller();
        modellbezeichnung = auto.getModellbezeichnung();
        fin = auto.getFin();
        kilometerstand = auto.getKilometerstand();
        baujahr = auto.getBaujahr();
        hubraum = auto.getHubraum();
        leistung = auto.getLeistung();
        getriebeart = auto.getGetriebeart();
        treibstoffart = auto.getTreibstoffart();
        grundpreis = auto.getGrundpreis();
        waehrung = auto.getWaehrung();
        ausstattung = auto.getAusstattung();
    }
}
