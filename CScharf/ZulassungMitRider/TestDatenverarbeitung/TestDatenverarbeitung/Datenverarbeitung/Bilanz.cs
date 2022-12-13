// @Author Antonin Fahning faan1011 
// Matrikelnummer: 85560

using EasyBankingBilanz.Datenhaltung.Transfer;


namespace EasyBankingBilanz.Datenverarbeitung
{
    /// <summary>
    /// Klasse zur Berechnung der Bilanz für die aktuelle Periode (P1) 
    /// </summary>
    public class Bilanz
    {
        private readonly Infrastruktur _infrastruktur;
        private readonly Infrastruktur _infrastrukturVP;
        private readonly Kreditinstitute _kreditinstitute;
        private readonly Kreditinstitute _kreditinstituteVP;
        private readonly Kreditinstitute _kreditinstituteVVP;
        private readonly Kreditinstitute _kreditinstituteVVVP;
        private readonly PAR30 _par30;
        private readonly VolumenNeugeschäft _volumenNeugeschäft;
        private readonly VolumenNeugeschäft _volumenNeugeschäftVP;
        private readonly VolumenNeugeschäft _volumenNeugeschäftVVP;
        private readonly VolumenNeugeschäft _volumenNeugeschäftVVVP;
        private readonly VolumenNeugeschäft _volumenNeugeschäftVVVVP;
        private readonly VorgabenAktuellePeriode _vorgabenAktuellePeriode;


        /// <summary>
        /// Methode zur Berechnung der Bilanz für die aktuelle Periode (P1)
        /// Konstruktor der Bilanz Klasse
        /// </summary>
        /// <param name="infrastruktur">Zeile der Tabelle 'Infrastruktur' für die aktuelle Periode (P1)</param>
        /// <param name="infrastrukturVP">Zeile der Tabelle 'Infrastruktur' für die Vorperiode (P0)</param>
        /// <param name="kreditinstitute">Zeile der Tabelle 'Kreditinstitute' für die aktuelle Periode (P1</param>
        /// <param name="kreditinstituteVP">Zeile der Tabelle 'Kreditinstitute' für die Vorperiode (P0)</param>
        /// <param name="kreditinstituteVVP">Zeile der Tabelle 'Kreditinstitute' für die Vorvorperiode (P-1)</param>
        /// <param name="kreditinstituteVVVP">Zeile der Tabelle 'Kreditinstitute' für die Vorvorvorperiode (P-2)</param>
        /// <param name="par30">Zeile der Tabelle 'PAR30' für die aktuelle Periode (P1)</param>
        /// <param name="volumenNeugeschäft">Zeile der Tabelle 'VolumenNeugeschäft' für die aktuelle Periode (P1)</param>
        /// <param name="volumenNeugeschäftVP">Zeile der Tabelle 'VolumenNeugeschäft' für die Vorperiode (P0)</param>
        /// <param name="volumenNeugeschäftVVP">Zeile der Tabelle 'VolumenNeugeschäft' für die Vorvorperiode (P-1)</param>
        /// <param name="volumenNeugeschäftVVVP">Zeile der Tabelle 'VolumenNeugeschäft' für die Vorvorvorperiode (P-2)</param>
        /// <param name="volumenNeugeschäftVVVVP">Zeile der Tabelle 'VolumenNeugeschäft' für die Vorvorvorvorperiode (P-3)</param>
        /// <param name="vorgabenAktuellePeriode">Vorgaben für die aktuelle Periode (P1)</param>
        public Bilanz(
            Infrastruktur infrastruktur,
            Infrastruktur infrastrukturVP,
            Kreditinstitute kreditinstitute,
            Kreditinstitute kreditinstituteVP,
            Kreditinstitute kreditinstituteVVP,
            Kreditinstitute kreditinstituteVVVP,
            PAR30 par30,
            VolumenNeugeschäft volumenNeugeschäft,
            VolumenNeugeschäft volumenNeugeschäftVP,
            VolumenNeugeschäft volumenNeugeschäftVVP,
            VolumenNeugeschäft volumenNeugeschäftVVVP,
            VolumenNeugeschäft volumenNeugeschäftVVVVP,
            VorgabenAktuellePeriode vorgabenAktuellePeriode
        )
        {
            _infrastruktur = infrastruktur ?? throw new ArgumentNullException(nameof(infrastruktur), "ist null");
            _infrastrukturVP = infrastrukturVP ?? throw new ArgumentNullException(nameof(infrastrukturVP)," ist null");
            _kreditinstitute = kreditinstitute ?? throw new ArgumentNullException(nameof(kreditinstitute), " ist null");
            _kreditinstituteVP = kreditinstituteVP ?? throw new ArgumentNullException(nameof(kreditinstituteVP), " ist null");
            _kreditinstituteVVP = kreditinstituteVVP ?? throw new ArgumentNullException(nameof(kreditinstituteVVP), " ist null");
            _kreditinstituteVVVP = kreditinstituteVVVP ?? throw new ArgumentNullException(nameof(kreditinstituteVVVP), " ist null");
            _par30 = par30 ?? throw new ArgumentNullException(nameof(par30), " ist null");
            _volumenNeugeschäft = volumenNeugeschäft ?? throw new ArgumentNullException(nameof(volumenNeugeschäft), " ist null");
            _volumenNeugeschäftVP = volumenNeugeschäftVP ?? throw new ArgumentNullException(nameof(volumenNeugeschäftVP), " ist null");
            _volumenNeugeschäftVVP = volumenNeugeschäftVVP ?? throw new ArgumentNullException(nameof(volumenNeugeschäftVVP), " ist null");
            _volumenNeugeschäftVVVP = volumenNeugeschäftVVVP ?? throw new ArgumentNullException(nameof(volumenNeugeschäftVVVP), " ist null");
            _volumenNeugeschäftVVVVP = volumenNeugeschäftVVVVP ?? throw new ArgumentNullException(nameof(volumenNeugeschäftVVVVP), " ist null");
            _vorgabenAktuellePeriode = vorgabenAktuellePeriode ?? throw new ArgumentNullException(nameof(vorgabenAktuellePeriode), " ist null");
        }
        

        /// <summary>
        /// Neugeschäft Autokredite in P1 zzgl. 50% Neugeschäft Autokredite in P0 zzgl. PAR30 Autokredite in P1
        /// </summary>
        /// <exception cref="OverflowException"></exception>
        public Währung AktivaAutokredit
        {
            get
            {
                Währung p0AutoKrediteFityPercent = MultiplyConst(_volumenNeugeschäftVP.Autokredite, 0.5);
                Währung autoKred = decimal.Add(_volumenNeugeschäft.Autokredite, p0AutoKrediteFityPercent);
                Währung par30Ak = decimal.Add(1, _par30.Autokredite);
                Währung res = decimal.Multiply(autoKred, par30Ak);
                return res;
            }
        }

        /// <summary>
        /// Produkt aus Anzahl Filialen in P1 und Anschaffungskosten je Filiale in P1 abzüglich Abschreibungen in P1
        /// </summary>
        public Währung AktivaFilialen
        {
            get
            {
                Währung filP1 = decimal.Multiply(_infrastruktur.AnzahlFilialen,
                    _infrastruktur.AnschaffungskostenJeFiliale);
                return MultiplyConst(filP1, _calcAbschreibungenFilialenP1());
            }
        }


        /// <summary>
        /// Summe der Forderungen an andere Kreditinstitute über P1 bis P-2 
        /// </summary>
        public Währung AktivaForderungAnKreditinstitute
        {
            get
            {
                Währung res = decimal.Add(_kreditinstitute.Forderungen, decimal.Add(_kreditinstituteVP.Forderungen,
                    decimal.Add(_kreditinstituteVVP.Forderungen, _kreditinstituteVVVP.Forderungen)));
                return res;
            }
        }

        /// <summary>
        /// Summe aus Konsum-, Auto- und Hypothekenkredite
        /// </summary>
        public Währung AktivaForderungenAnKundenBrutto
        {
            get
            {
                Währung res = decimal.Add(AktivaKonsumkredit, decimal.Add(AktivaAutokredit, AktivaHypothekenkredit));
                return res;
            }
        }

        /// <summary>
        ///  Aktiva Forderungen an Kunden brutto abzüglich Risikovorsorge
        /// </summary>
        public Währung AktivaForderungenAnKundenNetto =>
            // Ergebnis aus drüber - EgAusDrüber* Vorgaben.Risikovorsorge
            decimal.Subtract(AktivaForderungenAnKundenBrutto,
                AktivaRisikovorsorge);

        /// <summary>
        /// Summe aus IT-Wert in P0 und IT-Investitionen in P1 abzüglich Abschreibung IT in P1
        /// </summary>
        public Währung AktivaIT
        {
            get
            {
                Währung valITp0PitInvp1 = decimal.Add(_infrastrukturVP.ITWert, _infrastruktur.ITInvestitionen);
                return MultiplyConst(valITp0PitInvp1, _calcAbschreibungenITP1());
            }
        }


        /// <summary>
        /// Neugeschäft Konsumkredite in P1 zzgl. PAR30 Konsumkredite in P1
        /// </summary>
        public Währung AktivaKonsumkredit => MultiplyConst(_volumenNeugeschäft.Konsumkredite, 1.05);

        /// <summary>
        /// Summe aus: Neugeschäft Hypothekenkredite in P1, 80% Neugeschäft Hypothekenkredite in P0vp,
        /// 60% Neugeschäft Hypothekenkredite in P-1 vvp, 40% Neugeschäft Hypothekenkredite in P-2 vvvp,
        /// 20% Neugeschäft Hypothekenkredite in P-3 vvvvp, zzgl. PAR30 Hypothekenkredite in P1 
        /// </summary>
        public Währung AktivaHypothekenkredit
        {
            get
            {
                Währung p1p0 = decimal.Add(_volumenNeugeschäft.Hypothekenkredite,
                    MultiplyConst(_volumenNeugeschäftVP.Hypothekenkredite, 0.8));
                // p-1+ p-2
                Währung pm1Ppm2 = decimal.Add(MultiplyConst(_volumenNeugeschäftVVP.Hypothekenkredite, 0.6),
                    MultiplyConst(_volumenNeugeschäftVVVP.Hypothekenkredite, 0.4));
                Währung leftSide = decimal.Add(p1p0, decimal.Add(pm1Ppm2,
                    MultiplyConst(_volumenNeugeschäftVVVVP.Hypothekenkredite, 0.2)));
                return decimal.Multiply(leftSide, decimal.Add(_par30.Hypothekenkredite, 1));
            }
        }

        /// <summary>
        /// Differenz aus Summe Passiva ohne Überziehungskredit und Summe Aktiva ohne liquide Mittel, mindestens jedoch 50 Mio€
        /// </summary>
        public Währung AktivaLiquideMittel
        {
            get
            {
                Währung res = decimal.Subtract(PassivaSummeOhneÜberziehungskredit, AktivaSummeOhneLiquideMittel);
                return Math.Max(new decimal(50000000), res);
            }
        }

        /// <summary>
        /// Summe aus Verbindlichkeiten gegenüber Kreditinstituten, Verbindlichkeiten gegenüber Kunden und Eigenkapital
        /// </summary>
        public Währung PassivaSummeOhneÜberziehungskredit =>
            AddThreeDec(PassivaVerbindlichkeitenGegenüberKreditinstituten,
                PassivaVerbindlichkeitenGegenüberKunden, PassivaEigenkapital);

        /// <summary>
        /// Summe aus gezeichnetem Kapital, Gewinnrücklage nach Ausschüttung, Verlustvortrag und Periodenüberschuss
        /// </summary>
        public Währung PassivaEigenkapital =>
            AddFourDec(PassivaGezeichnetesKapital, PassivaGewinnrücklageNachAusschüttung,
                PassivaVerlustvortrag, PassivaPeriodenüberschuss);

        /// <summary>
        /// gezeichnetes Kapital in P1 aus Vorgaben Klasse
        /// </summary>
        public Währung PassivaGezeichnetesKapital => _vorgabenAktuellePeriode.GezeichnetesKapital;

        /// <summary>
        /// Gewinnrücklage brutto abzüglich Dividendensumme
        /// </summary>
        public Währung PassivaGewinnrücklageNachAusschüttung => decimal.Subtract(PassivaGewinnrücklageBrutto, PassivaDividendensumme);

        /// <summary>
        /// prozentualer Anteil (Dividende in P1) an der Brutto-Gewinnrücklage
        /// </summary>
        public Währung PassivaDividendensumme => MultiplyConst(PassivaGewinnrücklageBrutto, _vorgabenAktuellePeriode.Dividende);

        /// <summary>
        ///  Gewinnrücklage aus P0 zzgl. Periodenüberschuss und Verlustvortrag aus P0, falls in Summe positiv
        /// </summary>
        public Währung PassivaGewinnrücklageBrutto =>
            decimal.Add(_vorgabenAktuellePeriode.GewinnrücklageVorperiode,
                Math.Max(0,
                    decimal.Add(_vorgabenAktuellePeriode.PeriodenüberschussVorperiode,
                        _vorgabenAktuellePeriode.VerlustvortragVorperiode)));

        /// <summary>
        ///  Summe aus Periodenüberschuss und Verlustvortrag aus P0, falls negativ, sonst 0
        /// </summary>
        public Währung PassivaVerlustvortrag =>
            Math.Min(0,
                decimal.Add(_vorgabenAktuellePeriode.Periodenüberschuss,
                    _vorgabenAktuellePeriode.VerlustvortragVorperiode));

        /// <summary>
        ///  Periodenüberschuss aus P1
        /// </summary>
        public Währung PassivaPeriodenüberschuss => _vorgabenAktuellePeriode.Periodenüberschuss;

        /// <summary>
        /// Summe der Passiva Girokonten, Spareinlagen und Termingelder
        /// </summary>
        public Währung PassivaVerbindlichkeitenGegenüberKunden => AddThreeDec(PassivaGirokonto, PassivaSpareinlage, PassivaTermingeld);

        /// <summary>
        /// Neuvolumen Girokonten in P1 zzgl. Zinsen
        /// </summary>
        public Währung PassivaGirokonto => MultiplyConst(_volumenNeugeschäft.Girokonten, 1.001);

        /// <summary>
        /// Neuvolumen Spareinlagen in P1 zzgl. Zinsen
        /// </summary>
        public Währung PassivaSpareinlage => MultiplyConst(_volumenNeugeschäft.Spareinlagen, 1.01);

        /// <summary>
        /// Neuvolumina Termingelder aus P1 und P0 zzgl. Zinsen
        /// </summary>
        public Währung PassivaTermingeld =>
            MultiplyConst(
                Decimal.Add(_volumenNeugeschäft.Termingelder,
                    MultiplyConst(_volumenNeugeschäftVP.Termingelder, 1.02)), 1.015);

        /// <summary>
        /// Summe Aktiva ohne liquide Mittel zzgl. liquide Mittel
        /// </summary>
        public Währung AktivaSumme
        {
            get
            {
                Währung res = decimal.Add(AktivaSummeOhneLiquideMittel, AktivaLiquideMittel);
                return res;
            }
        }

        /// <summary>
        /// Summe aus Forderung an Kreditinstitute,Forderung an Kunden netto,Filialen IT
        /// </summary>
        public Währung AktivaSummeOhneLiquideMittel =>
            AddFourDec(AktivaForderungAnKreditinstitute, AktivaForderungenAnKundenNetto, AktivaFilialen,
                AktivaIT);

        /// <summary>
        ///  Produkt aus Forderungen an Kunden brutto und prozentualer Anteil der Risikovorsorge
        /// </summary>
        public Währung AktivaRisikovorsorge =>
            decimal.Multiply(AktivaForderungenAnKundenBrutto, _vorgabenAktuellePeriode.Risikovorsorge);

        /// <summary>
        /// Summe der Verbindlichkeiten gegenüber anderen Kreditinstituten über P1 bis P-2 
        /// </summary>
        public Währung PassivaVerbindlichkeitenGegenüberKreditinstituten =>
            AddFourDec(_kreditinstitute.Verbindlichkeiten, _kreditinstituteVP.Verbindlichkeiten,
                _kreditinstituteVVP.Verbindlichkeiten, _kreditinstituteVVVP.Verbindlichkeiten);

        /// <summary>
        /// Differenz aus Summe Aktiva abzüglich Summe Passiva ohne Überziehungskredit, falls positiv
        /// </summary>
        public Währung PassivaÜberziehungskredit => Math.Max(decimal.Subtract(AktivaSumme, PassivaSummeOhneÜberziehungskredit), 0);

        /// <summary>
        ///Summe Passiva ohne Überziehungskredit zzgl. Überziehungskredit
        /// </summary>
        public Währung PassivaSumme => decimal.Add(PassivaSummeOhneÜberziehungskredit, PassivaÜberziehungskredit);

        private double _calcAbschreibungenFilialenP1()
        {
            return ((double)1 - _vorgabenAktuellePeriode.AbschreibungFilialen);
        }

        private double _calcAbschreibungenITP1()
        {
            return ((double)1 - _vorgabenAktuellePeriode.AbschreibungIT);
        }
        
        private static decimal AddThreeDec(decimal s1, decimal s2, decimal s3)
        {
            return decimal.Add(s1, decimal.Add(s2, s3));
        }

        private static decimal AddFourDec(decimal s1, decimal s2, decimal s3, decimal s4)
        {
            return AddThreeDec(s1, s2, decimal.Add(s3, s4));
        }

        private static decimal MultiplyConst(decimal value, double c)
        {
            return decimal.Multiply(new decimal(c), value);
        }
    }
}