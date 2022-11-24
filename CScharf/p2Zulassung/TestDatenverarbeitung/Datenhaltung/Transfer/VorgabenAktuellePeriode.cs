// VorgabenAktuellePeriode.cs (zu Zulassungsaufgabe 22W)

using System;

namespace EasyBankingBilanz.Datenhaltung.Transfer
{
    /// <summary>
    /// Klasse mit festen Vorgaben für die Bilanzberechnung der aktuellen Periode (P1)
    /// </summary>
    /// <remarks>Diese Klasse wird vorgegeben und muss nicht programmiert werden.</remarks>
    public class VorgabenAktuellePeriode
    {
        /// <summary>
        /// prozentualer Anteil an den Forderungen an Kunden zur Risikovorsorge
        /// </summary>
        public Prozent Risikovorsorge => new Prozent(0.005);

        /// <summary>
        /// prozentuale Abschreibung auf die Filialen
        /// </summary>
        public Prozent AbschreibungFilialen => new Prozent(0.025);

        /// <summary>
        /// prozentuale Abschreibung auf die gesamte IT
        /// </summary>
        public Prozent AbschreibungIT => new Prozent(0.2);

        /// <summary>
        /// Zinssatz für ein Girokonto
        /// </summary>
        public Prozent ZinssatzGirokonto => new Prozent(0.001);

        /// <summary>
        /// Zinssatz für eine Spareinlage
        /// </summary>
        public Prozent ZinssatzSpareinlage => new Prozent(0.01);

        /// <summary>
        /// Zinssatz für Termingeld
        /// </summary>
        public Prozent ZinssatzTermingeld => new Prozent(0.015);

        /// <summary>
        /// Zinssatz für Termingeld der Vorperiode (P0)
        /// </summary>
        public Prozent ZinssatzTermingeldVorperiode => new Prozent(0.02);

        /// <summary>
        /// prozentualer Anteil an der Gewinnrücklage für die Dividendenausschüttung
        /// </summary>
        public Prozent Dividende => new Prozent(0.25);

        /// <summary>
        /// das gezeichnete Kapital
        /// </summary>
        public Währung GezeichnetesKapital => new Währung(150000000.0M);

        /// <summary>
        /// die Gewinnrücklage der Vorperiode (P0)
        /// </summary>
        public Währung GewinnrücklageVorperiode => new Währung(50000000.0M);

        /// <summary>
        /// der Periodenüberschuss
        /// </summary>
        public Währung Periodenüberschuss => new Währung(40000000.0M);

        /// <summary>
        /// der Periodenüberschuss der Vorperiode (P0)
        /// </summary>
        public Währung PeriodenüberschussVorperiode => new Währung(50000000.0M);

        /// <summary>
        /// der Verlustvortrag der Vorperiode (P0)
        /// </summary>
        public Währung VerlustvortragVorperiode => new Währung(0.0M);

        /// <summary>
        /// leerer Konstruktor
        /// </summary>
        public VorgabenAktuellePeriode()
        { }
    }
}
