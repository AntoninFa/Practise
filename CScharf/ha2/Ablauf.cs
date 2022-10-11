// Ablauf.cs (zu V02-Personenverzeichnis)

using System;
using Daten;

namespace Ablauf
{
    class Program
    {
        // wirft Ausnahme falls Bedingung (condition) zutrifft
        static void ExceptionOn(bool condition)
        {
            if (condition)
                throw new Exception();
        }

        // Vergleich der Angaben einer Person mit Vorgaben
        static void PersonVergleichen(Person person, string name, string straßenname, int hausnummer)
        {
            ExceptionOn(person.Name != name
                || person.Adresse.Straße.Name != straßenname
                || person.Adresse.Hausnummer != hausnummer);
        }

        // Ausgabe einer Person auf Console
        static void PersonDrucken(Person person)
        {
            Console.WriteLine($"{person.Name}, {person.Adresse.Straße.Name} {person.Adresse.Hausnummer}");
        }

        static void Main()
        {
            // einige korrekte Objekte

            Straße straße1 = new Straße("General Graf von Doppelwumms-Platz");
            Straße straße2 = new Straße("Blumendorfer Landstraße");

            Adresse adresse1 = new Adresse(straße1, 2);
            Adresse adresse2 = new Adresse(straße1, 3);
            Adresse adresse3 = new Adresse(straße2, 4);

            Person person1 = new Person("Peters, Hans", adresse1);
            Person person2 = new Person("Meier, Sybille", adresse1);
            Person person3 = new Person("Schmitz, Hubert", adresse2);
            Person person4 = new Person("Müller, Katrin", adresse3);
            Person person5 = new Person("Hufnagel, Susanne", adresse3);

            PersonDrucken(person1);
            PersonDrucken(person2);
            PersonDrucken(person3);
            PersonDrucken(person4);
            PersonDrucken(person5);

            Console.WriteLine();

            // Änderungen an der Straße führt auch zur Änderung an Adresse und Person
            // Änderung an Adresse führt auch zur Änderung an Person

            straße1.Name = "Bertha Blumendorf-Platz";
            adresse3.Hausnummer = 14;

            PersonDrucken(person1);
            PersonDrucken(person2);
            PersonDrucken(person3);
            PersonDrucken(person4);
            PersonDrucken(person5);

            Console.WriteLine();

            PersonVergleichen(person1, "Peters, Hans", "Bertha Blumendorf-Platz", 2);
            PersonVergleichen(person2, "Meier, Sybille", "Bertha Blumendorf-Platz", 2);
            PersonVergleichen(person3, "Schmitz, Hubert", "Bertha Blumendorf-Platz", 3);
            PersonVergleichen(person4, "Müller, Katrin", "Blumendorfer Landstraße", 14);
            PersonVergleichen(person5, "Hufnagel, Susanne", "Blumendorfer Landstraße", 14);

            // Änderungsversuche an Person sollen wirkungslos sein

            person1.Adresse.Hausnummer = 42;
            person2.Adresse.Straße.Name = "Edward Snowden-Platz";

            PersonVergleichen(person1, "Peters, Hans", "Bertha Blumendorf-Platz", 2);
            PersonVergleichen(person2, "Meier, Sybille", "Bertha Blumendorf-Platz", 2);

            // Änderungsversuche an der Straße über die Adresse sollen wirkungslos sein

            adresse1.Straße.Name = "Edward Snowden-Platz";
            ExceptionOn(adresse1.Straße.Name != "Bertha Blumendorf-Platz");

            // Kopien sollen tief sein

            Straße straße1Kopie = new Straße(straße1);
            Adresse adresse2Kopie = adresse2.Kopie();
            straße1.Name = "No Name-Platz";
            adresse2.Hausnummer = 211;
            ExceptionOn(straße1Kopie.Name != "Bertha Blumendorf-Platz");
            ExceptionOn(adresse2Kopie.Hausnummer != 3);
            ExceptionOn(adresse2Kopie.Straße.Name != "Bertha Blumendorf-Platz");

            // Plausiprüfs

            int anzahlFehler = 0;

            try
            {
                new Straße((string)null);
                Console.WriteLine("Fehler 1");
                anzahlFehler++;
            }
            catch { }
            try
            {
                new Straße(" ");
                Console.WriteLine("Fehler 2");
                anzahlFehler++;
            }
            catch { }
            try
            {
                new Straße((Straße)null);
                Console.WriteLine("Fehler 3");
                anzahlFehler++;
            }
            catch { }
            Straße teststraße = new Straße("Name");
            try
            {
                teststraße.Name = null;
                Console.WriteLine("Fehler 4");
                anzahlFehler++;
            }
            catch { }
            try
            {
                teststraße.Name = " ";
                Console.WriteLine("Fehler 5");
                anzahlFehler++;
            }
            catch { }
            try
            {
                new Adresse(null, 1);
                Console.WriteLine("Fehler 6");
                anzahlFehler++;
            }
            catch { }
            try
            {
                new Adresse(teststraße, 0);
                Console.WriteLine("Fehler 7");
                anzahlFehler++;
            }
            catch { }
            Adresse testadresse = new Adresse(teststraße, 1);
            try
            {
                testadresse.Hausnummer = 0;
                Console.WriteLine("Fehler 8");
                anzahlFehler++;
            }
            catch { }
            try
            {
                new Person(null, testadresse);
                Console.WriteLine("Fehler 9");
                anzahlFehler++;
            }
            catch { }
            try
            {
                new Person(" ", testadresse);
                Console.WriteLine("Fehler 10");
                anzahlFehler++;
            }
            catch { }
            try
            {
                new Person("Name", null);
                Console.WriteLine("Fehler 11");
                anzahlFehler++;
            }
            catch { }

            if (anzahlFehler > 0)
                throw new Exception();
        }
    }
}
