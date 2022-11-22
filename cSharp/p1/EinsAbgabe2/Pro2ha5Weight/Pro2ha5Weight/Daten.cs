namespace Daten
{
    
    public static class GlobalVar
    {
        public const double TOLERANCE = 0.0000001;
    }
    

    public interface IGewichtHabend
    {
        public double Gewicht { get; }
    }

    public class Batterie : ILast
    {
        private readonly double _gewicht;
        public double Gewicht
        {
             get => _gewicht;
        }
        private readonly double _spannung;

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

        public override bool Equals(object? obj)
        {
            var b = obj as Batterie;
            return b != null && _gewicht.Equals(b._gewicht) && _spannung.Equals(b._spannung);
        }
        
    }

    public class Mignonzelle : Batterie
    {
        // wegen base wird base-constructor von Batterie aufgerufen und die Attribute gesetzt
        public Mignonzelle() : base(1.5,25)
        {
        }
        
    }
    public interface ILast : IGewichtHabend
    {
    }
    public abstract class ElektrischeWaage
    {
        private ILast? _last;

        public ILast? Last => _last;

        public abstract double Spannung
        {
            get;
        }

        public void Auflegen(ILast last)
        {
            if (_last != null)
                throw new Exception("voll");

            this._last = last ?? throw new Exception("übergebene Last ist null");
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
            return _last == null ? $"Leer, Spannung: {Spannung}" : $"Last: {_last.Gewicht}, Spannung: {Spannung}";
        }

        public override int GetHashCode()
        {
            return (_last?.GetHashCode() ?? 0) ^ Spannung.GetHashCode();
        }

        protected bool IsWeightEqual(ElektrischeWaage eW)
        {
            return Math.Abs((Last?.Gewicht ?? 0) - (eW.Last?.Gewicht ?? 0)) < GlobalVar.TOLERANCE;
        }

        public override bool Equals(object? obj)
        {
            var eW = obj as ElektrischeWaage;
            return eW != null && IsWeightEqual(eW) && Math.Abs(Spannung - eW.Spannung) < GlobalVar.TOLERANCE;
            
        }
        
        
    }

    public class ElektrischeWaageMignon : ElektrischeWaage, ICloneable
    {
        private Mignonzelle? _batterie;
        public Mignonzelle? Batterie => _batterie;
        
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
            if (Last == null && _batterie == null)
                return $"Elektrische Waage Mignon, Keine Last und keine Batterie";
            if (Last == null && _batterie != null)
                return $"Elektrische Waage Mignon, Leer,Spannung: {Spannung}";
            if (Last != null && _batterie == null)
                return $"Elektrische Waage Mignon, Last: {Last.Gewicht}, keine Batterie";
            return $"Elektrische Waage Mignon, Last: {Last.Gewicht}, Spannung: {Spannung}";
        }

        public override int GetHashCode()
        {
            if (_batterie == null)
                return (Last?.GetHashCode() ?? 0) ^ 0;
            
            return (Last?.GetHashCode() ?? 0) ^ Spannung.GetHashCode();
        }

        public override bool Equals(object? obj)
        {
            var eWM = obj as ElektrischeWaageMignon;
            return eWM != null && IsWeightEqual(eWM) &&
                   Math.Abs((Batterie?.Spannung ?? 0) - (eWM.Batterie?.Spannung ?? 0)) < GlobalVar.TOLERANCE;
            
        }
    }
    
    
}