using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;

namespace Daten
{

    public class Gegenstand
    {
        private bool PflegeBed;

        public bool IstPflegebedürftig
        {
            get => PflegeBed;
        }

        public Gegenstand(bool pflegeBed)
        {
            this.PflegeBed = pflegeBed;
        }

        public Gegenstand() : this(false)
        {
        }

        public void PflegebedürftigMachen()
        {
            PflegeBed = true;
        }

        public void Pflegen()
        {
            PflegeBed = false;
        }

    }

    public class Vorderrad : Gegenstand
    {
        public Vorderrad(bool pflegeBed) : base(pflegeBed)
        {
        }
    }

    public class Hinterrad : Gegenstand
    {
        public Hinterrad(bool pflegeBed) : base(pflegeBed)
        {
            
        }
    }
    
    public class Rahmen : Gegenstand
    {
        public Rahmen(bool pflegeBed) : base(pflegeBed)
        {
        }
    }

    public class Fahrrad
    {
        private readonly Gegenstand vR;
        private readonly Gegenstand hR;
        private readonly Gegenstand r;
        // protected since it's used in the subclass
        protected int NumOfUses;

        public bool VorderradIstPflegebedürftig
        {
            get => vR.IstPflegebedürftig;
        }

        public bool HinterradIstPflegebedürftig
        {
            get => hR.IstPflegebedürftig;
        }

        public bool RahmenIstPflegebedürftig
        {
            get => r.IstPflegebedürftig;
        }

        public bool IstPutzbedürftig
        {
            get
            {
                return (VorderradIstPflegebedürftig || HinterradIstPflegebedürftig || RahmenIstPflegebedürftig);
            }
        }
        

        public Fahrrad()
        {
            vR = new Gegenstand();
            hR = new Gegenstand();
            r = new Gegenstand();
            NumOfUses = 0;
        }

        public void Putzen()
        {
            vR.Pflegen();
            hR.Pflegen();
            r.Pflegen();
        }

        public void Fahren()
        {
            if (NumOfUses == 0)
            {
                vR.PflegebedürftigMachen();
            } else if (NumOfUses == 1)
            {
                hR.PflegebedürftigMachen();
            } else if (NumOfUses == 2)
            {
                r.PflegebedürftigMachen();
            }

            NumOfUses++;

        }

        public string Darstellung()
        {
            string s = " vR: " + vR.IstPflegebedürftig + " hR: " + hR.IstPflegebedürftig + " R: " +
                       r.IstPflegebedürftig ;
            return s;
        }
        

    }

    public class VerkehrssicheresFahrrad : Fahrrad
    {
        private readonly Gegenstand lAnlage;
        public bool LichtanlageIstPflegebedürftig
        {
            get
            {
                return lAnlage.IstPflegebedürftig;
            }
        }

        public bool IstPutzbedürftig
        {
            get
            {
                return (base.IstPutzbedürftig || lAnlage.IstPflegebedürftig);
            }
        }

        public VerkehrssicheresFahrrad()
        {
            lAnlage = new Gegenstand();
        }

        public void Fahren()
        {
            if (NumOfUses == 3)
            {
                lAnlage.PflegebedürftigMachen();
            }
            base.Fahren();
        }

        public void Putzen()
        {
            lAnlage.Pflegen();
            base.Putzen();
        }
        
    }



}