// Prozent.cs (zu Zulassungsaufgabe 22W)

using System;

namespace EasyBankingBilanz.Datenhaltung.Transfer
{
    /// <summary>
    /// Struktur für einen Prozentwert
    /// </summary>
    public struct Prozent
    {
        /// <summary>
        /// Prozentwert
        /// </summary>
        /// <example>liefert 0.1 für 10%</example>
        public double Wert { get; }

        /// <summary>
        /// Konstruktor für einen Prozentwert
        /// </summary>
        /// <param name="wert">Prozentwert</param>
        /// <remarks>
        /// Wert wird kaufmännisch auf drei Stellen genau gerundet
        /// (bezüglich der kaufmännischen Rundung siehe https://docs.microsoft.com/de-de/dotnet/api/system.midpointrounding?view=netframework-4.8)
        /// </remarks>
        /// <example>
        ///   <list type="bullet">
        ///     <item>0.1 ergibt 10,0%</item>
        ///     <item>0.399 ergibt 39,9%</item>
        ///     <item>0.3999 ergibt 40,0%</item>
        ///   </list>
        /// </example>
        public Prozent(double wert) => Wert = Math.Round(wert, 3, MidpointRounding.AwayFromZero);

        /// <summary>
        /// implizite Konvertierung von 'double' nach 'Prozent'
        /// </summary>
        /// <param name="op">zu konvertierender 'double'-Wert</param>
        /// <returns>
        /// Ergebnis der Konvertierung ist ein neuer Prozentwert, gebildet mit dem 'double'-Wert als Konstruktorparameter
        /// (zur impliziten Konvertierung siehe https://docs.microsoft.com/de-de/dotnet/csharp/language-reference/keywords/implicit)
        /// </returns>
        public static implicit operator Prozent(double op) => new Prozent(op);

        /// <summary>
        /// implizite Konvertierung von 'Prozent' nach 'double'
        /// </summary>
        /// <param name="op">zu konvertierender Prozentwert</param>
        /// <returns>Ergebnis der Konvertierung ist der Wert der Eigenschaft 'Wert' des Prozentwerts</returns>
        /// <example>
        /// Aus
        /// Konsumkredit = volumenNeugeschäft.Konsumkredite * Convert.ToDecimal(1.0 + par30.Konsumkredite.Wert);
        /// wird so
        /// Konsumkredit = volumenNeugeschäft.Konsumkredite * Convert.ToDecimal(1.0 + par30.Konsumkredite);
        /// </example>
        public static implicit operator double(Prozent op) => op.Wert;

        /// <summary>
        /// implizite Konvertierung von 'Prozent' nach 'decimal'
        /// </summary>
        /// <param name="op">zu konvertierender Prozentwert</param>
        /// <returns>Ergebnis der Konvertierung ist der Wert der Eigenschaft 'Wert' des Prozentwerts</returns>
        /// <example>
        /// Aus
        /// Konsumkredit = volumenNeugeschäft.Konsumkredite * Convert.ToDecimal(1.0 + par30.Konsumkredite);
        /// wird so
        /// Konsumkredit = volumenNeugeschäft.Konsumkredite * (1.0M + par30.Konsumkredite);
        /// </example>
        public static implicit operator decimal(Prozent op) => Convert.ToDecimal(op.Wert);

        /// <summary>
        /// druckbare Darstellung des Prozentwertes
        /// </summary>
        /// <returns>druckbare Darstellung</returns>
        /// <remarks>
        /// Darstellung als Prozentwert
        /// (zu standardmäßigen Zahlenformatzeichenfolgen siehe https://docs.microsoft.com/de-de/dotnet/standard/base-types/standard-numeric-format-strings)
        /// </remarks>
        /// <example>bei einem Wert von 0.1 ergibt sich die Darstellung "10,0 %"</example>
        public override string ToString() => String.Format("{0:P1}", Wert);

        /// <summary>
        /// Berechnung des Hashwertes für einen Prozentwert
        /// </summary>
        /// <returns>Hashwert</returns>
        public override int GetHashCode() => Wert.GetHashCode();

        /// <summary>
        /// Vergleich zweier Prozentwerte auf Wertgleichheit
        /// </summary>
        /// <param name="obj">zu vergleichender Prozentwert</param>
        /// <returns>true bei Übereinstimmung der Werte, false sonst</returns>
        public override bool Equals(object obj) => (obj is Prozent p) && Wert.Equals(p.Wert);

        /// <summary>
        /// Vergleich zweier Prozentwerte auf Wertgleichheit
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
        public static bool operator ==(Prozent wert1, Prozent wert2) => wert1.Equals(wert2);

        /// <summary>
        /// Vergleich zweier Prozentwerte auf Wertungleichheit
        /// </summary>
        /// <param name="wert1">erster Wert</param>
        /// <param name="wert2">zweiter wert</param>
        /// <returns>true bei Nicht-Übereinstimmung der Werte, false sonst</returns>
        /// <remarks>Negierung des Operators ==</remarks>
        public static bool operator !=(Prozent wert1, Prozent wert2) => !(wert1 == wert2);
    }
}
