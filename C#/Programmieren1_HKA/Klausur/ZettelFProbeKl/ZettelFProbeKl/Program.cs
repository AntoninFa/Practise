// Daten.cs (zu Sonderübung 14 - Aufgabe 3)

using System;

namespace Aufgabe3.Daten
{
    
    //      Eine Person hat einen Vornamen (string) und einen Nachnamen
    //      (string). Schreiben Sie ein verkapseltes, unveränderliches Struct Person.
    struct Person
    {
        public string Vorname { get; }
        public string Nachname { get; }

        public Person(string vorname, string nachname)
        {
            if (String.IsNullOrEmpty(vorname) || String.IsNullOrEmpty(nachname))
                throw new Exception();

            Vorname = vorname;
            Nachname = nachname;
        }

        // kein Kopierkonstruktor, da alles Werttypen
        //       public Person(Person original)
        //       {
        //           Vorname = original.Vorname;
        //           Nachname = original.Nachname;
        //       }

        public override string ToString() // nicht Teil der Aufgabenstellung
            => Vorname + " " + Nachname;
    }

    class Eltern
    {
        public Person Mutter { get; }
        public Person Vater { get; }

        public Eltern(Person mutter, Person vater)
        {
            // bei Klasse: Abfrage auf null

            Mutter = mutter;
            Vater = vater;
        }

        //Ergänzen Sie die Klasse Eltern um eine Kopiermethode. Braucht es eine
        //tiefe Kopie? Begründen Sie!
        
        public Eltern Kopie()
        {
            // keine tiefe Kopie, da alles Werttypen
            // bei Klasse: keine tiefe Kopie, da Person unveränderlich
            return new Eltern(Mutter, Vater);
        }

        public override string ToString() // nicht Teil der Aufgabenstellung
            => "Mutter: " + Mutter.ToString() + "  Vater: " + Vater.ToString();
    }
}