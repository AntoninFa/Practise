package com.acme.auto.rest;

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
 * ValueObject für das Neuanlegen und Ändern eines neuen Autos. Beim Lesen wird die Klasse AutoModel für die Ausgabe
 * verwendet.
 *
 * @author <a href="mailto:A@h-ka.de">A A</a>
 * @param hersteller Hersteller eines Autos
 * @param modellbezeichnung Modellbezeichnung eines Autos
 * @param fin Fahrzeugidentifikationsnummer eines Autos.
 * @param kilometerstand Kilometerstand eines Autos.
 * @param baujahr Baujahr eines Autos.
 * @param hubraum Hubraum eines Autos.
 * @param leistung Die Leistung eines Autos.
 * @param getriebeart Die Getriebeart eines Autos.
 * @param treibstoffart Die Treibstoffart eines Autos.
 * @param grundpreis Der Grundpreis eines Autos.
 * @param waehrung Die Währung des Grundpreises eines Autos.
 * @param ausstattung Die Ausstattung eines Autos
 */
@SuppressWarnings("RecordComponentNumber")
record AutoDTO(
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
    List<AusstattungsoptionDTO> ausstattung
) {
    /**
     * Konvertierung in ein Objekt des Anwendungskerns.
     *
     * @return Autoobjekt für den Anwendungskern
     */
    Auto toAuto() {
        final List<Ausstattungsoption> ausstattungEntity;
        if (ausstattung == null) {
            ausstattungEntity = new ArrayList<>(1);
        } else {
            ausstattungEntity = ausstattung.stream()
                .map(AusstattungsoptionDTO::toAusstattungsoption)
                .collect(Collectors.toList());
        }

        final var auto = Auto
            .builder()
            .id(null)
            .version(0)
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
            .erzeugt(null)
            .aktualisiert(null)
            .build();

        // Rueckwaertsverweise
        auto.getAusstattung()
            .forEach(ausstattungsoption -> ausstattungsoption.setAuto(auto));
        return auto;
    }
}
