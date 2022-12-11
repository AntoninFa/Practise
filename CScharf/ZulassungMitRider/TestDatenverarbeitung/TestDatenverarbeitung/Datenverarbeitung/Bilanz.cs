// @Autor Antonin Fahning faan1011 

using EasyBankingBilanz.Datenhaltung.Transfer;


namespace EasyBankingBilanz.Datenverarbeitung
{
    public class Bilanz
    {
        private Infrastruktur _infrastruktur;
        private Infrastruktur _infrastrukturVP;
        private Kreditinstitute _kreditinstitute;
        private Kreditinstitute _kreditinstituteVP;
        private Kreditinstitute _kreditinstituteVVP;
        private Kreditinstitute _kreditinstituteVVVP;
        private PAR30 _par30;
        private VolumenNeugeschäft _volumenNeugeschäft;
        private VolumenNeugeschäft _volumenNeugeschäftVP;
        private VolumenNeugeschäft _volumenNeugeschäftVVP;
        private VolumenNeugeschäft _volumenNeugeschäftVVVP;
        private VolumenNeugeschäft _volumenNeugeschäftVVVVP;
        private VorgabenAktuellePeriode _vorgabenAktuellePeriode;


        /// <summary>
        /// 
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
            if (infrastruktur == null) 
                throw new ArgumentNullException("infrastruktur ist null");
            _infrastruktur = infrastruktur;
            if (infrastrukturVP == null) 
                throw new ArgumentNullException("infrastrukturVP ist null");
            _infrastrukturVP = infrastrukturVP;
            if (kreditinstitute == null) 
                throw new ArgumentNullException("kreditinstitute ist null");
            _kreditinstitute = kreditinstitute;
            //TODO PLAUSIII 
            if (kreditinstituteVP == null) 
                throw new ArgumentNullException("kreditinstituteVP ist null");
            _kreditinstituteVP = kreditinstituteVP;
            if (kreditinstituteVVP == null) 
                throw new ArgumentNullException("kreditinstituteVVP ist null");
            _kreditinstituteVVP = kreditinstituteVVP;
            if (kreditinstituteVVVP == null) 
                throw new ArgumentNullException("kreditinstituteVVVP ist null");
            _kreditinstituteVVVP = kreditinstituteVVVP;
            if (par30 == null) 
                throw new ArgumentNullException("par30 ist null");
            _par30 = par30;
            if (volumenNeugeschäft == null) 
                throw new ArgumentNullException("volumenNeugeschäft ist null");
            _volumenNeugeschäft = volumenNeugeschäft;
            if (volumenNeugeschäftVP == null) 
                throw new ArgumentNullException("volumenNeugeschäftVP ist null");
            _volumenNeugeschäftVP = volumenNeugeschäftVP;
            if (volumenNeugeschäftVVP == null) 
                throw new ArgumentNullException("volumenNeugeschäftVVP ist null");
            _volumenNeugeschäftVVP = volumenNeugeschäftVVP;
            if (volumenNeugeschäftVVVP == null) 
                throw new ArgumentNullException("volumenNeugeschäftVVVP ist null");
            _volumenNeugeschäftVVVP = volumenNeugeschäftVVVP;
            if (volumenNeugeschäftVVVVP == null) 
                throw new ArgumentNullException("volumenNeugeschäftVVVVP ist null");
            _volumenNeugeschäftVVVVP = volumenNeugeschäftVVVVP;
            if (vorgabenAktuellePeriode == null) 
                throw new ArgumentNullException("vorgabenAktuellePeriode ist null");
            _vorgabenAktuellePeriode = vorgabenAktuellePeriode;
        }
        


        private double _calcAbschreibungenFilialenP1()
        {
            return ((double)1 - _vorgabenAktuellePeriode.AbschreibungFilialen);
        }

        private double _calcAbschreibungenITP1()
        {
            return ((double)1 - _vorgabenAktuellePeriode.AbschreibungIT);
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
                //TODO Hab hier nur eine Bekommen, why?
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
        public Währung PassivaSummeOhneÜberziehungskredit
        {
            get
            {
                return addThree(PassivaVerbindlichkeitenGegenüberKreditinstituten,
                    PassivaVerbindlichkeitenGegenüberKunden, PassivaEigenkapital);
            }
        }

        /// <summary>
        /// Summe aus gezeichnetem Kapital, Gewinnrücklage nach Ausschüttung, Verlustvortrag und Periodenüberschuss
        /// </summary>
        public Währung PassivaEigenkapital
        {
            get
            {
                return addFour(PassivaGezeichnetesKapital, PassivaGewinnrücklageNachAusschüttung,
                    PassivaVerlustvortrag, PassivaPeriodenüberschuss);
            }
        }

        /// <summary>
        /// gezeichnetes Kapital in P1 aus Vorgaben Klasse
        /// </summary>
        public Währung PassivaGezeichnetesKapital
        {
            get { return _vorgabenAktuellePeriode.GezeichnetesKapital; }
        }

        /// <summary>
        /// Gewinnrücklage brutto abzüglich Dividendensumme
        /// </summary>
        public Währung PassivaGewinnrücklageNachAusschüttung
        {
            get { return decimal.Subtract(PassivaGewinnrücklageBrutto, PassivaDividendensumme); }
        }

        public Währung PassivaDividendensumme
        {
            get { return MultiplyConst(PassivaGewinnrücklageBrutto, _vorgabenAktuellePeriode.Dividende); }
        }

        /// <summary>
        ///  Gewinnrücklage aus P0 zzgl. Periodenüberschuss und Verlustvortrag aus P0, falls in Summe positiv
        /// </summary>
        public Währung PassivaGewinnrücklageBrutto
        {
            get
            {
                return decimal.Add(_vorgabenAktuellePeriode.GewinnrücklageVorperiode,
                    Math.Max(0,
                        decimal.Add(_vorgabenAktuellePeriode.PeriodenüberschussVorperiode,
                            _vorgabenAktuellePeriode.VerlustvortragVorperiode)));
            }
        }


        public Währung PassivaVerlustvortrag
        {
            get
            {
                return Math.Min(0,
                    _vorgabenAktuellePeriode.Periodenüberschuss + _vorgabenAktuellePeriode.VerlustvortragVorperiode);
            }
        }

        public Währung PassivaPeriodenüberschuss
        {
            get { return _vorgabenAktuellePeriode.Periodenüberschuss; }
        }

        /// <summary>
        /// Summe der Passiva Girokonten, Spareinlagen und Termingelder
        /// </summary>
        public Währung PassivaVerbindlichkeitenGegenüberKunden
        {
            get { return addThree(PassivaGirokonto, PassivaSpareinlage, PassivaTermingeld); }
        }

        public Währung PassivaGirokonto
        {
            get { return MultiplyConst(_volumenNeugeschäft.Girokonten, 1.001); }
        }

        public Währung PassivaSpareinlage
        {
            get { return MultiplyConst(_volumenNeugeschäft.Spareinlagen, 1.01); }
        }

        public Währung PassivaTermingeld
        {
            get
            {
                return MultiplyConst(
                    Decimal.Add(_volumenNeugeschäft.Termingelder,
                        MultiplyConst(_volumenNeugeschäftVP.Termingelder, 1.02)), 1.015);
            }
        }

        /// <summary>
        /// Summe Aktiva ohne liquide Mittel zzgl. liquide Mittel
        /// </summary>
        public Währung AktivaSumme
        {
            //TODO Aktiva Liquide Mittel Implementieren
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
            addFour(AktivaForderungAnKreditinstitute, AktivaForderungenAnKundenNetto, AktivaFilialen,
                AktivaIT);

        /// <summary>
        ///  Produkt aus Forderungen an Kunden brutto und prozentualer Anteil der Risikovorsorge
        /// </summary>
        public Währung AktivaRisikovorsorge =>
            decimal.Multiply(AktivaForderungenAnKundenBrutto, _vorgabenAktuellePeriode.Risikovorsorge);

        /// <summary>
        /// Summe der Verbindlichkeiten gegenüber anderen Kreditinstituten über P1 bis P-2 
        /// </summary>
        public Währung PassivaVerbindlichkeitenGegenüberKreditinstituten
        {
            get
            {
                return addFour(_kreditinstitute.Verbindlichkeiten, _kreditinstituteVP.Verbindlichkeiten,
                    _kreditinstituteVVP.Verbindlichkeiten, _kreditinstituteVVVP.Verbindlichkeiten);
            }
        }

        /// <summary>
        /// Differenz aus Summe Aktiva abzüglich Summe Passiva ohne Überziehungskredit, falls positiv
        /// </summary>
        public Währung PassivaÜberziehungskredit
        {
            get { return Math.Max(decimal.Subtract(AktivaSumme, PassivaSummeOhneÜberziehungskredit), 0); }
        }

        /// <summary>
        ///Summe Passiva ohne Überziehungskredit zzgl. Überziehungskredit
        /// </summary>
        public Währung PassivaSumme
        {
            get { return decimal.Add(PassivaSummeOhneÜberziehungskredit, PassivaÜberziehungskredit); }
        }


        private decimal addThree(decimal s1, decimal s2, decimal s3)
        {
            return decimal.Add(s1, decimal.Add(s2, s3));
        }

        private decimal addFour(decimal s1, decimal s2, decimal s3, decimal s4)
        {
            return addThree(s1, s2, decimal.Add(s3, s4));
        }

        private decimal MultiplyConst(decimal value, double c)
        {
            return decimal.Multiply(new decimal(c), value);
        }
    }
}