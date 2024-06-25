// Währung.cs (zu Zulassungsaufgabe 22W)

using System;

namespace EasyBankingBilanz.Datenhaltung.Transfer
{
    /// <summary>
    /// Struktur für einen Geldbetrag
    /// </summary>
    public struct Währung
    {
        /// <summary>
        /// Geldbetrag
        /// </summary>
        /// <remarks>bezüglich des Datentyps <c>decimal</c> siehe https://docs.microsoft.com/de-de/dotnet/csharp/language-reference/keywords/decimal </remarks>
        public decimal Betrag { get; }

        /// <summary>
        /// Konstruktor für die Klasse 'Währung'
        /// </summary>
        /// <param name="betrag">Geldbetrag</param>
        /// <remarks>
        /// Geldbetrag wird kaufmännisch auf zwei Nachkommastellen gerundet
        /// (bezüglich der kaufmännischen Rundung siehe https://docs.microsoft.com/de-de/dotnet/api/system.midpointrounding?view=netframework-4.8)
        /// </remarks>
        /// <example>10.009M ergeben 10,01€</example>
        public Währung(decimal betrag) => Betrag = decimal.Round(betrag, 2, MidpointRounding.AwayFromZero);

        /// <summary>
        /// implizite Konvertierung von 'Währung' nach 'decimal'
        /// </summary>
        /// <param name="op">zu konvertierender Geldbetrag</param>
        /// <returns>
        /// Ergebnis der Konvertierung ist der Wert der Eigenschaft 'Betrag' der Klasse 'Währung'
        /// (zur impliziten Konvertierung siehe https://docs.microsoft.com/de-de/dotnet/csharp/language-reference/keywords/implicit)
        /// </returns>
        public static implicit operator decimal(Währung op) => op.Betrag;

        /// <summary>
        /// implizite Konvertierung von 'decimal' nach 'Währung'
        /// </summary>
        /// <param name="op">zu konvertierender 'decimal'-Wert</param>
        /// <returns>Ergebnis der Konvertierung ist ein Objekt der Klasse 'Währung' mit dem 'decimal'-Wert als Konstruktorparameter</returns>
        public static implicit operator Währung(decimal op) => new Währung(op);

        /// <summary>
        /// druckbare Darstellung des Geldbetrags
        /// </summary>
        /// <returns>druckbare Darstellung</returns>
        /// <example>Betrag von 40000000.0M ergibt "40.000.000,00 €"</example>
        /// <remarks>zu standardmäßigen Zahlenformatzeichenfolgen siehe https://docs.microsoft.com/de-de/dotnet/standard/base-types/standard-numeric-format-strings </remarks>
        public override string ToString() => String.Format("{0:C}", Betrag);

        /// <summary>
        /// druckbare Darstellung des Geldbetrags in Tausend€ (kaufmännische Rundung)
        /// </summary>
        /// <returns>druckbare Darstellung</returns>
        /// <example>Betrag von 40000000.0M ergibt "40.000 T€"</example>
        public string ToTausenderString() => String.Format("{0:n0} T€", decimal.Round(Betrag / 1000.0M, 0, MidpointRounding.AwayFromZero));

        /// <summary>
        /// druckbare Darstellung des Geldbetrags in Millionen€ (kaufmännische Rundung)
        /// </summary>
        /// <returns>druckbare Darstellung</returns>
        /// <example>Betrag von 40000000.0M ergibt "40 Mio€"</example>
        public string ToMillionenString() => String.Format("{0:n0} Mio€", decimal.Round(Betrag / 1000000.0M, 0, MidpointRounding.AwayFromZero));

        /// <summary>
        /// Hashwert für einen Geldbetrag
        /// </summary>
        /// <returns>Hashwert</returns>
        public override int GetHashCode() => Betrag.GetHashCode();

        /// <summary>
        /// Wertvergleich zweier Geldbeträge
        /// </summary>
        /// <param name="obj">zu vergleichender Geldbetrag</param>
        /// <returns>true bei Übereinstimmung, sonst false</returns>
        public override bool Equals(object obj) => (obj is Währung w) && Betrag.Equals(w.Betrag);

        /// <summary>
        /// Vergleich zweier Geldbeträge auf Wertgleichheit
        /// </summary>
        /// <param name="wert1">erster Wert</param>
        /// <param name="wert2">zweiter Wert</param>
        /// <returns>true bei Übereinstimmung der Werte, false sonst</returns>
        /// <remarks>
        ///   <list type="bullet">
        ///     <item>Der Operator == liefert immer dasselbe Ergebnis wie die Methode Equals.</item>
        ///     <item>Bei Werttypen sollten daher mit Equals auch immer die Operatoren == und != definiert werden.</item>
        ///   </list>
        /// </remarks>
        public static bool operator ==(Währung wert1, Währung wert2) => wert1.Equals(wert2);

        /// <summary>
        /// Vergleich zweier Geldbeträge auf Wertungleichheit
        /// </summary>
        /// <param name="wert1">erster Wert</param>
        /// <param name="wert2">zweiter wert</param>
        /// <returns>true bei Nicht-Übereinstimmung der Werte, false sonst</returns>
        /// <remarks>Negierung des Operators ==</remarks>
        public static bool operator !=(Währung wert1, Währung wert2) => !(wert1 == wert2);
    }
}
