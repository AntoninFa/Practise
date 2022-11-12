namespace Daten
{
    
    interface IGewichtHabend
    {
        double Gewicht { get; }
    }

    public class Batterie : IGewichtHabend
    {
        private double _gewicht;
        public double Gewicht
        {
            get => _gewicht;
        }
        private double _spannung;

        public double Spannung
        {
            get => _spannung;
        }

        public Batterie(double gewicht, double spannung)
        {
            this._gewicht = gewicht;
            this._spannung = spannung;
        }

        public Batterie()
        {
            //TODO Sollte ich den Aufruf hier verhindern?
        }

        public override string ToString()
        {
            return $"Batterie, Gewicht: {_gewicht}, Spannung: {_spannung}";
        }

        public override int GetHashCode()
        {
            return _gewicht.GetHashCode() ^ _spannung.GetHashCode();
        }

        public override bool Equals(object obj)
        {
            Batterie b = obj as Batterie;
            return b != null && _gewicht.Equals(b._gewicht) && _spannung.Equals(b._spannung);
        }
        
    }

    public class Mignonzelle : Batterie
    {
        private readonly double _gewicht = 25;
        private readonly double _spannung = 1.5;
        
        public override bool Equals(object obj)
        {
            Mignonzelle b = obj as Mignonzelle;
            return b != null && _gewicht.Equals(b._gewicht) && _spannung.Equals(b._spannung);
        }
        
    }
}