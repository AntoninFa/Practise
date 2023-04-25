/*
 * Copyright (C) 2022 - present Juergen Zimmermann, Hochschule Karlsruhe
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.acme.kunde.graphql;

import com.acme.kunde.entity.Adresse;
import com.acme.kunde.entity.FamilienstandType;
import com.acme.kunde.entity.GeschlechtType;
import com.acme.kunde.entity.InteresseType;
import com.acme.kunde.entity.Kunde;
import com.acme.kunde.entity.Umsatz;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Eine Value-Klasse für Eingabedaten passend zu KundeInput aus dem GraphQL-Schema.
 *
 * @author <a href="mailto:Juergen.Zimmermann@h-ka.de">Jürgen Zimmermann</a>
 * @param nachname Nachname
 * @param email Emailadresse
 * @param kategorie Kategorie
 * @param hasNewsletter Newsletter-Abonnement
 * @param geburtsdatum Geburtsdatum
 * @param homepage URL der Homepage
 * @param geschlecht Geschlecht
 * @param familienstand Familienstand
 * @param adresse Adresse
 * @param umsaetze Umsätze
 * @param interessen Interessen als Liste
 */
@SuppressWarnings("RecordComponentNumber")
record KundeInput(
    String nachname,
    String email,
    int kategorie,
    boolean hasNewsletter,
    String geburtsdatum,
    URL homepage,
    GeschlechtType geschlecht,
    FamilienstandType familienstand,
    AdresseInput adresse,
    List<UmsatzInput> umsaetze,
    List<InteresseType> interessen
) {
    /**
     * Konvertierung in ein Objekt der Entity-Klasse Kunde.
     *
     * @return Das konvertierte Kunde-Objekt
     */
    Kunde toKunde() {
        final var geburtsdatumTmp = LocalDate.parse(geburtsdatum);
        final var adresseEntity = Adresse.builder().plz(adresse.plz()).ort(adresse.ort()).build();
        final List<Umsatz> umsaetzeEntity;
        if (umsaetze == null) {
            umsaetzeEntity = null;
        } else {
            umsaetzeEntity = umsaetze.stream()
                .map(umsatz -> Umsatz.builder().betrag(umsatz.betrag()).waehrung(umsatz.waehrung()).build())
                .collect(Collectors.toList());
        }

        final var kunde = Kunde
            .builder()
            .id(null)
            .nachname(nachname)
            .email(email)
            .kategorie(kategorie)
            .hasNewsletter(hasNewsletter)
            .geburtsdatum(geburtsdatumTmp)
            .homepage(homepage)
            .geschlecht(geschlecht)
            .familienstand(familienstand)
            .interessen(interessen)
            .umsaetze(umsaetzeEntity)
            .adresse(adresseEntity)
            .build();

        // Rueckwaertsverweise
        kunde.getAdresse()
            .setKunde(kunde);
        kunde.getUmsaetze()
            .forEach(umsatz -> umsatz.setKunde(kunde));

        return kunde;
    }
}
