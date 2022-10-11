//Daten.cs (zu V02-Personenverzeichnis)

using System;

namespace Daten
{
    class Straße
    {
        private string _name;
        public string Name
        {
            get { return _name; }
            set
            {
                if (String.IsNullOrWhiteSpace(value))
                    throw new Exception();
                _name = value;
            }
        }
        public Straße(string name)
        {
            Name = name;
        }
        public Straße(Straße vorlage)
        {
            if (vorlage == null)
                throw new Exception();

            _name = vorlage._name;
        }
    }

    class Adresse
    {
        private readonly Straße _straße;
        private int _hausnummer;
        public Straße Straße {  get { return new Straße(_straße); } }
        public int Hausnummer
        {
            get { return _hausnummer; }
            set
            {
                if (value < 1)
                    throw new Exception();
                _hausnummer = value;
            }
        }
        public Adresse(Straße straße, int hausnummer)
        {
            if (straße == null)
                throw new Exception();
            _straße = straße;
            Hausnummer = hausnummer;
        }

        public Adresse Kopie()
        {
            return new Adresse(new Straße(_straße), _hausnummer);
        }
    }

    class Person
    {
        private readonly string _name;
        private readonly Adresse _adresse;

        public string Name { get { return _name; } }
        public Adresse Adresse {  get { return _adresse.Kopie(); } }

        public Person(string name, Adresse adresse)
        {
            if (String.IsNullOrWhiteSpace(name) || adresse == null)
                throw new Exception();

            _name = name;
            _adresse = adresse;
        }
    }
}