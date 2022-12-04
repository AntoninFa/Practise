using System.ComponentModel;
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
            //TODO Plausibilitätsprüfung :DDDDDDDDDDD
            _infrastruktur = infrastruktur;
            _infrastrukturVP = infrastrukturVP;
            _kreditinstitute = kreditinstitute;
            _kreditinstituteVP = kreditinstituteVP;
            _kreditinstituteVVP = kreditinstituteVVP;
            _kreditinstituteVVVP = kreditinstituteVVVP;
            _par30 = par30;
            _volumenNeugeschäft = volumenNeugeschäft;
            _volumenNeugeschäftVP = volumenNeugeschäftVP;
            _volumenNeugeschäftVVP = volumenNeugeschäftVVP;
            _volumenNeugeschäftVVVP = volumenNeugeschäftVVVP;
            _volumenNeugeschäftVVVVP = volumenNeugeschäftVVVVP;
            _vorgabenAktuellePeriode = vorgabenAktuellePeriode;
        }

        private double _calcAbschreibungenP1()
        {
            return ((double)1 - _vorgabenAktuellePeriode.AbschreibungFilialen);
        }
        /// <summary>
        /// Neugeschäft Autokredite in P1 zzgl. 50% Neugeschäft Autokredite in P0 zzgl. PAR30 Autokredite in P1
        /// </summary>
        /// <exception cref="OverflowException"></exception>
        public Währung AktivaAutokredit {
            get {

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
                return MultiplyConst(filP1, _calcAbschreibungenP1());
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
                //TODO WOher soll ich jetzt wissen, welle Periode gemeint ist???, Müsste laufen wenn Hypokredit da ist
                Währung res = decimal.Add(AktivaKonsumkredit, decimal.Add(AktivaAutokredit, AktivaHypothekenkredit));
                return res;
            }
        }

        /// <summary>
        ///  Aktiva Forderungen an Kunden brutto abzüglich Risikovorsorge
        /// </summary>
        public Währung AktivaForderungenAnKundenNetto
        {
            get
            {
                // Ergebnis aus drüber - EgAusDrüber* Vorgaben.Risikovorsorge
                return decimal.Subtract(AktivaForderungenAnKundenBrutto,
                    AktivaRisikovorsorge);
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
                    MultiplyConst( _volumenNeugeschäftVVVVP.Hypothekenkredite, 0.2)));
                return decimal.Multiply(leftSide, decimal.Add(_par30.Hypothekenkredite, 1));

            }
        }

        /// <summary>
        ///  Produkt aus Forderungen an Kunden brutto und prozentualer Anteil der Risikovorsorge
        /// </summary>
        public Währung AktivaRisikovorsorge =>
            decimal.Multiply(AktivaForderungenAnKundenBrutto, _vorgabenAktuellePeriode.Risikovorsorge);

        private decimal MultiplyConst(decimal value, double c)
        {
            return decimal.Multiply(new decimal(c), value );
        }
    }
}