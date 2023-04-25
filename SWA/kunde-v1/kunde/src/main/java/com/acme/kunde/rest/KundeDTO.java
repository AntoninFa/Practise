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
package com.acme.kunde.rest;

import com.acme.kunde.entity.Adresse;
import com.acme.kunde.entity.FamilienstandType;
import com.acme.kunde.entity.GeschlechtType;
import com.acme.kunde.entity.InteresseType;
import com.acme.kunde.entity.Kunde;
import com.acme.kunde.entity.Umsatz;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ValueObject für das Neuanlegen und Ändern eines neuen Kunden. Beim Lesen wird die Klasse KundeModel für die Ausgabe
 * verwendet.
 *
 * @author <a href="mailto:Juergen.Zimmermann@h-ka.de">Jürgen Zimmermann</a>
 * @param nachname Gültiger Nachname eines Kunden, d.h. mit einem geeigneten Muster.
 * @param email Email eines Kunden.
 * @param kategorie Kategorie eines Kunden mit eingeschränkten Werten.
 * @param hasNewsletter Flag, ob es ein Newsletter-Abo gibt.
 * @param geburtsdatum Das Geburtsdatum eines Kunden.
 * @param homepage Die Homepage eines Kunden.
 * @param geschlecht Das Geschlecht eines Kunden.
 * @param familienstand Der Familienstand eines Kunden.
 * @param adresse Die Adresse eines Kunden.
 * @param umsaetze Die Umsaetze eines Kunden.
 * @param interessen Die Interessen eines Kunden.
 */
@SuppressWarnings("RecordComponentNumber")
record KundeDTO(
    String nachname,
    String email,
    int kategorie,
    boolean hasNewsletter,
    LocalDate geburtsdatum,
    URL homepage,
    GeschlechtType geschlecht,
    FamilienstandType familienstand,
    AdresseDTO adresse,
    List<UmsatzDTO> umsaetze,
    List<InteresseType> interessen
) {
    /**
     * Konvertierung in ein Objekt des Anwendungskerns.
     *
     * @return Kundeobjekt für den Anwendungskern
     */
    Kunde toKunde() {
        final var adresseEntity = adresse() == null
            ? null
            : Adresse
            .builder()
            .plz(adresse().plz())
            .ort(adresse().ort())
            .build();

        final List<Umsatz> umsaetzeEntity;
        if (umsaetze == null) {
            umsaetzeEntity = new ArrayList<>(1);
        } else {
            umsaetzeEntity = umsaetze.stream()
                .map(umsatz -> Umsatz.builder()
                    .betrag(umsatz.betrag())
                    .waehrung(umsatz.waehrung())
                    .build()
                )
                .collect(Collectors.toList());
        }

        final var kunde = Kunde
            .builder()
            .id(null)
            .nachname(nachname)
            .email(email)
            .kategorie(kategorie)
            .hasNewsletter(hasNewsletter)
            .geburtsdatum(geburtsdatum)
            .homepage(homepage)
            .geschlecht(geschlecht)
            .familienstand(familienstand)
            .adresse(adresseEntity)
            .umsaetze(umsaetzeEntity)
            .interessen(interessen)
            .build();
        // Rueckwaertsverweise
        kunde.getAdresse()
            .setKunde(kunde);
        kunde.getUmsaetze()
            .forEach(umsatz -> umsatz.setKunde(kunde));
        return kunde;
    }
}
