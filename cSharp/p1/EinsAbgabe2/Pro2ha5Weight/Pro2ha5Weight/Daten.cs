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
        private readonly double _gewicht;
        private readonly double _spannung;
        
        public override bool Equals(object obj)
        {
            Mignonzelle b = obj as Mignonzelle;
            return b != null && _gewicht.Equals(b._gewicht) && _spannung.Equals(b._spannung);
        }
        public Mignonzelle() : base(25,1.5)
        {
            
        }
        
    }
    public class Last : IGewichtHabend
    {
        private double _gewicht;
        public double Gewicht
        {
            get => _gewicht;
        }
    }
    public abstract class ElektrischeWaage
    {
        private Last _last;
        private double _spannung;

        public double Spannung
        {
            get => _spannung;
        }

        public void Auflegen(Last last)
        {
            if (_last != null)
                throw new Exception("voll");
            this._last = last;
        }

        public void Wegnehmen()
        {
            if (_last == null)
                throw new Exception("leer");
            this._last = null;
        }
        
        public override string ToString()
        {
            if (_last == null)
                return $"Elektrische Waage, Leer, Spannung: {_spannung}";
            return $"Elektrische Waage, Last: {_last.Gewicht}, Spannung: {_spannung}";
        }

        public override int GetHashCode()
        {
            return _last.GetHashCode() ^ _spannung.GetHashCode();
        }

        public override bool Equals(object obj)
        {
            Last l = obj as Last;
            ElektrischeWaage eW = obj as ElektrischeWaage;
            if (l != null )
                return eW != null && _last.Gewicht.Equals(eW._last.Gewicht) && _spannung.Equals(eW.Spannung);
            return eW != null && _spannung.Equals(eW.Spannung);
        }
        
        
    }
    
    
}