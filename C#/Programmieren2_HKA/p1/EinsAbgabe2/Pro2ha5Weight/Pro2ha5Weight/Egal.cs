
using System;

namespace Verdeckung_Polymorph
{
    class Program
    {
        static void Main()
        {
            Tier meinTier = new Hund("Lucky", "Labrador");

            Console.WriteLine(meinTier.Ausgabe());

            Hund neuesTier = meinTier as Hund;

            Console.WriteLine(neuesTier.Ausgabe());

            Mensch anna = new Student("Wirtschaftsinformatik", "Anna");

            Console.WriteLine(anna.Ausgabe());
        }
    }

    class Tier
    {
        public string Art;

        public Tier(string art)
        {
            Art = art;
        }

        public string Ausgabe()
        {
            return $"{Art}";
        }
    }

    class Hund : Tier
    {
        public string Name;
        public Hund(string name, string art) : base(art)
        {
            Name = name;
        }

        public new string Ausgabe()
        {
            return $"{Art} {Name}";
        }
    }

    class Mensch
    {
        public string Name;

        public Mensch(string name)
        {
            Name = name;
        }

        public virtual string Ausgabe()
        {
            return $"{Name}";
        }
    }

    class Student : Mensch
    {
        public string Studienfach;
        public Student(string studienfach, string name) : base(name)
        {
            Studienfach = studienfach;
        }

        public override string Ausgabe()
        {
            return $"{Name} {Studienfach}";
        }
    }
}
