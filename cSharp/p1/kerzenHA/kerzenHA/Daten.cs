namespace Daten
{

    class Lampe
    {
        private int _lichtstrom;

        public int Lichtstrom
        {
            get => _lichtstrom;
        }

        public Lampe(int lichtstrom)
        {
            if (lichtstrom >= 0)
            {
                this._lichtstrom = lichtstrom;
            }
            else
            {
                throw new ArgumentException("lumen should be positive");
            }
        }
    }

    class ElektrischeLampe : Lampe
    {
        private int _spannung; // volt
        private int _leistungsaufnahme; //watt
        private double _lichtausbeute;

        public int Spannung
        {
            get => _spannung;
            set { _spannung = value; }
        }

        public int Leistungsaufnahme
        {
            get => _leistungsaufnahme;
        }

        public double Lichtausbeute
        {
            get => _lichtausbeute;
            set { _lichtausbeute = value; }
        }

        public ElektrischeLampe(int lichtstrom, int spannung, int strom) : base(lichtstrom)
        {

            if (spannung < 0) throw new ArgumentException();
            this._spannung = spannung;
            if (strom < 0) throw new ArgumentException();
            this._leistungsaufnahme = strom;
            this._lichtausbeute = (double)lichtstrom / strom;

        }
    }

    class Glühlampe : ElektrischeLampe
    {
        private readonly string _glühfaden;

        public string Glühfaden
        {
            get => _glühfaden;
        }

        public Glühlampe(int lichtstrom, int spannung, int strom, string glühfaden) : base(lichtstrom, spannung, strom)
        {
            if (String.IsNullOrEmpty(glühfaden) || String.IsNullOrWhiteSpace(glühfaden)) throw new ArgumentException();
            this._glühfaden = glühfaden;

        }
    }

    class ChemischPhysikalischeLampe : Lampe
    {
        public ChemischPhysikalischeLampe(int lichtstrom) : base(lichtstrom)
        {
        }
    }

    class Kerze : ChemischPhysikalischeLampe
    {
        private readonly string _brennstoff;

        public string Brennstoff
        {
            get => _brennstoff;
        }

        public Kerze(int lichtstrom, string brennstoff) : base(lichtstrom)
        {
            if (String.IsNullOrWhiteSpace(brennstoff) || String.IsNullOrEmpty(brennstoff))
                throw new ArgumentException();
            this._brennstoff = brennstoff;
        }
    }

}