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
//TODO Plausi Prüfung 
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

        public Währung AktiviaAutokredit()
        {
            
        }
    }
}