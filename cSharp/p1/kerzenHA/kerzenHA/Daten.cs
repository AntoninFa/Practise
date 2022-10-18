namespace Daten;

class Lampe
{
    protected int _lichtstrom;

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
    protected int _spannung; // volt
    protected int _leistungsaufnahme; //watt
    protected double _lichtausbeute;

    public int Spannung
    {
        get => Spannung;
        set
        {
            _spannung = value;
        }
    }

    public int Leistungsaufnahme
    {
        get => _leistungsaufnahme;
    }

    public double Lichtausbeute
    {
        get => _lichtausbeute;
        set
        {
            _lichtausbeute = value;
        }
    }

    public ElektrischeLampe(int lichtstrom, int spannung, int strom) : base (lichtstrom)
    {
        this._spannung = spannung;
        this._leistungsaufnahme = strom;
        this._lichtausbeute =(double) spannung / _leistungsaufnahme;


    }
}

class Glühlampe : ElektrischeLampe
{
    private string _glühfaden;

    public  string Glühfaden
    {
        get => _glühfaden;
    }

    public Glühlampe(int lichtstrom, int spannung, int strom, string glühfaden) : base(lichtstrom, spannung, strom)
    {
        this._spannung = spannung;
        this._leistungsaufnahme = strom;
        this.Lichtausbeute =(double) spannung / _leistungsaufnahme;
        this._glühfaden = glühfaden;
    }
}

class ChemischPhysikalischeLampe : Lampe
{
    public ChemischPhysikalischeLampe(int lichtstrom) : base (lichtstrom)
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

class Kerze : ChemischPhysikalischeLampe
{
    private string _brennstoff;

    public string Brennstoff
    {
        get => _brennstoff;
    }

    public Kerze(int lichtstrom, string brennstoff) : base(lichtstrom)
    {
        this._brennstoff = brennstoff;
    }
}