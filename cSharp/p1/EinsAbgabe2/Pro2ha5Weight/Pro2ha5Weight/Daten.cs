namespace Daten
{
    
    public interface IGewichtHabend
    {
        public double Gewicht { get; }
    }

    public class Batterie : Last, IGewichtHabend
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

        public Batterie(double spannung, double gewicht)
        {
            if (gewicht <= 0)
                throw new Exception();
            this._gewicht = gewicht;
            if (spannung <= 0)
                throw new Exception();
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
        // wegen base wird base-constructor von Batterie aufgerufen und die Attribute gesetzt
        public Mignonzelle() : base(1.5,25)
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
        private Last? _last;

        public  Last? Last
        {
            get => _last;
        }

        public abstract double Spannung
        {
            get;
        }

        public void Auflegen(Last last)
        {
            if (_last != null)
                throw new Exception("voll");
            if (last == null)
                throw new Exception("übergebene Last ist null");
            
            this._last = last;
        }

        public void Wegnehmen(out IGewichtHabend gh)
        {
            if (Last == null)
                throw new Exception("leer");
            gh = this.Last;
            this._last = null;

        }
        
        
        public override string ToString()
        {
            if (_last == null)
                return $"Leer, Spannung: {Spannung}";
            return $"Last: {_last.Gewicht}, Spannung: {Spannung}";
        }

        public override int GetHashCode()
        {
            //TODO last auf null checken
            return _last.GetHashCode() ^ Spannung.GetHashCode();
        }

        public override bool Equals(object obj)
        {
            ElektrischeWaage eW = obj as ElektrischeWaage;
            Last l = eW._last;
            if (l != null )
                return eW != null && _last.Gewicht.Equals(eW._last.Gewicht) && Spannung.Equals(eW.Spannung);
            return eW != null && Spannung.Equals(eW.Spannung);
        }
        
        
    }

    public class ElektrischeWaageMignon : ElektrischeWaage, ICloneable
    {
        private Mignonzelle? _batterie;
        public Mignonzelle? Batterie
        {
            get => _batterie;
        }

       

        public override double Spannung
        {
            get
            {
                if (_batterie == null)
                    throw new Exception("keine Batterie eingelegt");
                return _batterie.Spannung;
            }
        }

        public void Einlegen(Mignonzelle mZelle)
        {
            if (this._batterie != null)
                throw new Exception("Es ist bereits eine Batterie eingelegt");
            if (mZelle == null)
                throw new Exception("Übergebene mZelle ist null");
            this._batterie = mZelle;
        }

        public void Entnehmen(out Mignonzelle m)
        {
            if (Batterie == null)
                throw new Exception("Keine Batterie enthalten");
            m = Batterie;
            this._batterie = null;
        }
    
        
        public object Clone()
        {
            return MemberwiseClone();
        }
        
        public override string ToString()
        {
            if (base.Last == null && _batterie == null)
                return $"Elektrische Waage Mignon, Keine Last und keine Batterie";
            if (base.Last == null && _batterie != null)
                return $"Elektrische Waage Mignon, Leer,Spannung: {Spannung}";
            if (base.Last != null && _batterie == null)
                return $"Elektrische Waage Mignon, Last: {base.Last.Gewicht}, keine Batterie";
            return $"Elektrische Waage Mignon, Last: {base.Last.Gewicht}, Spannung: {Spannung}";
        }

        public override int GetHashCode()
        {
            if (_batterie == null)
                return (Last?.GetHashCode() ?? 0) ^ 0;

            return (Last?.GetHashCode() ?? 0) ^ Spannung.GetHashCode();
        }

        public override bool Equals(object obj)
        {
            ElektrischeWaageMignon eWM = obj as ElektrischeWaageMignon;
            return eWM != null && (Last?.Gewicht ?? 0) == (eWM.Last?.Gewicht ?? 0) &&
                   (Batterie?.Spannung ?? 0) == (eWM.Batterie?.Spannung ?? 0);
            
        }
    }
    
    
}