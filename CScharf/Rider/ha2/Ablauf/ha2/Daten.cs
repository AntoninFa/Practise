
namespace Daten;


class Straße
{
    private string _name;

    public Straße(string name)
    {
        if (!String.IsNullOrEmpty(name) && !String.IsNullOrWhiteSpace(name))
        {
            _name = name;
        }
        else
        {
            throw new ArgumentException();
        }
    }


    public Straße(Straße straße)
    {
        if (straße == null) throw new ArgumentNullException();
        _name = straße._name;
    }


    public string Name
    {
        get => _name;
        set
        {
            if (!String.IsNullOrWhiteSpace(value) && !String.IsNullOrEmpty(value))
            {
                _name = value;
            }
            else
            {
                throw new ArgumentException();
            }

            
        }
    }
}

class Adresse
{
    private Straße _straße;
    private int _hausnummer;

    public Straße Straße
    {
        get => _straße;
    }

    public int Hausnummer
    {
        get => _hausnummer;

        set
        {
            if (value > 0)
            {
                _hausnummer = value;
            }
            else
            {
                throw new ArgumentNullException();
            }
        }
    }

    public Adresse(Straße straße, int hausnummer)
    {
        if (straße != null && hausnummer > 0)
        {
            this._straße = straße;
            this._hausnummer = hausnummer;
        }
        else
        {
            throw new ArgumentNullException();
        }
    }

    public Adresse Kopie()
    {
        return new Adresse(new Straße(_straße), _hausnummer);
    }
}

class Person
{
    private string _name;
    private Adresse _adresse;

    public string Name
    {
        get => _name;
    }

    public Adresse Adresse
    {
        get => _adresse.Kopie();
    }

    public Person(string name, Adresse adresse)
    {
        if (!String.IsNullOrEmpty(name) && !String.IsNullOrWhiteSpace(name) && adresse != null)
        {
            this._name = name;
            this._adresse = adresse;
        }
        else
        {
            throw new Exception();
        }
    }
}