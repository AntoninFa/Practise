using System.Runtime.InteropServices;

namespace Ablauf
{

    public class Gegenstand
    {
        private bool PflegeBed = false;

        public bool IstPflegebedürftig
        {
            get => PflegeBed;
        }

        public Gegenstand(bool pflegeBed)
        {
            this.PflegeBed = pflegeBed;
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
        private Vorderrad vR;
        private Hinterrad hR;
        private Rahmen r;
        

    }



}