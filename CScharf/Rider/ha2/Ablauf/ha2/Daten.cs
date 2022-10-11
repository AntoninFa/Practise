//Daten.cs (zu V02-Personenverzeichnis)

namespace Daten;

internal class Straße
{
    private string _name;

    public Straße(string name)
    {
        _name = name;
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
            if (value == null) throw new ArgumentNullException();
            _name = value;
        }
    }
}

internal class Adresse
{
}