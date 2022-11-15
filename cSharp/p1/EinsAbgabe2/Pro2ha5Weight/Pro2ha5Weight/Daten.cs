namespace Daten
{
    
    interface IGewichtHabend
    {
        double Gewicht { get; }
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
        // wegen base wird base-constructor von Batterie aufgerufen und die Attribute gesetzt
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

        public  Last Last
        {
            get => _last;
        }
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
                return $"Leer, Spannung: {_spannung}";
            return $"Last: {_last.Gewicht}, Spannung: {_spannung}";
        }

        public override int GetHashCode()
        {
            //TODO last auf null checken
            return _last.GetHashCode() ^ _spannung.GetHashCode();
        }

        public override bool Equals(object obj)
        {
            ElektrischeWaage eW = obj as ElektrischeWaage;
            Last l = eW._last;
            if (l != null )
                return eW != null && _last.Gewicht.Equals(eW._last.Gewicht) && _spannung.Equals(eW.Spannung);
            return eW != null && _spannung.Equals(eW.Spannung);
        }
        
        
    }

    public class ElektrischeWaageMignon : ElektrischeWaage, ICloneable
    {
        private Mignonzelle _batterie;
        public Mignonzelle Batterie
        {
            get => _batterie;
        }

        public double Spannung
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
            this._batterie = mZelle;
        }

        public void Entnehmen()
        {
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
            return base.Last.GetHashCode() ^ Spannung.GetHashCode();
        }

        public override bool Equals(object obj)
        {
            ElektrischeWaageMignon eWM = obj as ElektrischeWaageMignon;
            Last l = eWM.Last;
            if (base.Last == null && _batterie == null)
                return eWM != null && eWM.Last == null && eWM._batterie == null;
            if (base.Last == null && _batterie != null)
                return eWM != null && eWM.Last == null && _batterie.Spannung.Equals(eWM._batterie);
                       //TODO Equals für Batt?
            if (base.Last != null && _batterie == null)
                           return eWM != null && Last.Equals(eWM.Last) && eWM == null;
            //TODO Equals für Plast?
            return eWM != null && Last.Equals(eWM.Last) && _batterie.Spannung.Equals(eWM._batterie);
                       

                       /*'if (l != null )
                           return eWM != null && _last.Gewicht.Equals(eW._last.Gewicht) && _spannung.Equals(eW.Spannung);
                       return eW != null && _spannung.Equals(eW.Spannung);
                       */
        }
    }
    
    
}