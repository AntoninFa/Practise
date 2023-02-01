// Ablauf.cs (zu V05-Waagen)

using System;
using Daten;

namespace Ablauf
{
    class Program
    {
        /// <summary>
        /// Fehlerzähler
        /// </summary>
        private static int _anzahlFehler = 0;

        /// <summary>
        /// erhöhe Fehlerzähler falls Bedingung erfüllt ist
        /// </summary>
        /// <param name="bedingung">Bedingung für Fehlerfall</param>
        /// <param name="kennung">Kennung im Fehlerfall, wird auf Console ausgegeben</param>
        private static void FehlerFalls(bool bedingung, string kennung)
        {
            if (bedingung)
            {
                Console.WriteLine("!!! FEHLER " + kennung);
                _anzahlFehler++;
            }
        }

        /// <summary>
        /// Methode zum Prüfen zweier Objekte auf Wertgleichheit und Ausgabe des Ergebnisses auf Console
        /// </summary>
        /// <param name="objekt">1. Vergleichsobjekt</param>
        /// <param name="vergleichsobjekt">2. Vergleichsobjekt</param>
        /// <param name="sollWert">
        /// Sollwert des Vergleichs
        /// <list type="bullet">
        /// <item>true - Werte sollen übereinstimmen</item>
        /// <item>false - Werte sollen nicht übereinsimmen</item>
        /// <item>Vorgabe: true</item>
        /// </list>
        /// </param>
        private static void VergleichenUndDrucken(object objekt, object vergleichsobjekt, bool sollWert = true)
        {
            bool istWert;
            if (objekt is double)
                istWert = Math.Abs(Convert.ToDouble(objekt) - Convert.ToDouble(vergleichsobjekt)) < Double.Epsilon * 100.0;
            else
                istWert = objekt.Equals(vergleichsobjekt);
            string op = sollWert ? "==" : "!=";
            bool ergebnisOk = istWert == sollWert;
            string ergebnisText = ergebnisOk ? "ok" : "FALSCH";
            Console.WriteLine($"{objekt.ToString()} {op} {vergleichsobjekt.ToString()} --> {ergebnisText}");
            FehlerFalls(!ergebnisOk, "(siehe vorherige Zeile)");
        }

        /// <summary>
        /// Methode zum Prüfen von ToString, Equals, GetHashCode
        /// </summary>
        /// <param name="objektArray">
        /// Auflistung der Vergleichsobjekte
        /// <list type="bullet">
        /// <item>1. Objekt Vorgabe</item>
        /// <item>2. Objekt wertgleich zur Vorgabe</item>
        /// <item>übrige Objekte wertverschieden zur Vorgabe in je einem Parameter des Konstruktors</item>
        /// </list>
        /// </param>
        private static void ObjektTest(object[] objektArray)
        {
            string klasse = objektArray[0].GetType().Name;
            Console.WriteLine($"\nKlasse: {klasse}\n");

            Console.WriteLine("\n--- ToString()\n");

            foreach (object o in objektArray)
                Console.WriteLine(o.ToString());

            Console.WriteLine("\n--- GetHashCode()\n");

            int vglHashCode = objektArray[0].GetHashCode();
            Console.Write($"{1} ");
            VergleichenUndDrucken(objektArray[1].GetHashCode(), vglHashCode, true);
            for (int i = 2; i < objektArray.Length; i++)
            {
                Console.Write($"{i} ");
                VergleichenUndDrucken(objektArray[i].GetHashCode(), vglHashCode, false);
            }

            Console.WriteLine("\n--- Equals()\n");

            Console.Write($"{1} ");
            VergleichenUndDrucken(objektArray[1], objektArray[0], true);
            for (int i = 2; i < objektArray.Length; i++)
            {
                Console.Write($"{i} ");
                VergleichenUndDrucken(objektArray[i], objektArray[0], false);
            }
            Console.Write($"X ");
            VergleichenUndDrucken(new Object(), objektArray[0], false);
        }

        /// <summary>
        /// Prüfung von ToString, Equals und GetHashCode für zwei Objekte der Klasse 'ElektrischeWaageMignon'
        /// </summary>
        /// <param name="elektrischeWaageMignon1">erstes Vergleichsobjekt</param>
        /// <param name="elektrischeWaageMignon2">zweites Vergleichsobjekt</param>
        /// <param name="kennung">Kennung im Fehlerfall, wird auf Console ausgegeben</param>
        /// <param name="sollWert">
        /// Sollwert des Vergleichs
        /// <list type="bullet">
        /// <item>true - Werte sollen übereinstimmen</item>
        /// <item>false - Werte sollen nicht übereinsimmen</item>
        /// <item>Vorgabe: true</item>
        /// </list>
        /// </param>
        static void VergleicheElektrischeWaageMignon(ElektrischeWaageMignon elektrischeWaageMignon1, ElektrischeWaageMignon elektrischeWaageMignon2, string kennung, bool sollWert = true)
        {
            Console.WriteLine(elektrischeWaageMignon1.ToString() + " <-> " + elektrischeWaageMignon2.ToString());
            bool istWert = elektrischeWaageMignon1.GetHashCode() == elektrischeWaageMignon2.GetHashCode();
            FehlerFalls(istWert != sollWert, kennung + "a");
            istWert = elektrischeWaageMignon1.Equals(elektrischeWaageMignon2);
            FehlerFalls(istWert != sollWert, kennung + "b");
        }

        /// <summary>
        /// Hauptablauffaden
        /// </summary>
        /// TEST weil wollte Main frei haben
        static void MainTEST()
        {
            Console.WriteLine("\n--- class Batterie ---\n");

            Console.WriteLine("\n- Test des Konstruktors und der zughörigen Eigenschaften -\n");

            VergleichenUndDrucken(new Batterie(1.5, 25.0).Spannung, 1.5);
            VergleichenUndDrucken(new Batterie(1.5, 25.0).Gewicht, 25.0);

            Console.WriteLine("\n- Plausibilitatsprüfungen -\n");

            try
            {
                new Batterie(0.0, 1.0);
                FehlerFalls(true, "A1");
            }
            catch { }
            try
            {
                new Batterie(1.0, 0.0);
                FehlerFalls(true, "A2");
            }
            catch { }

            Console.WriteLine("\n- Prüfung von ToString, Equals, GetHashCode -\n");

            object[] testarray = new object[4];
            testarray[0] = new Batterie(1.5, 25.0);
            testarray[1] = new Batterie(1.5, 25.0);
            testarray[2] = new Batterie(1.0, 25.0);
            testarray[3] = new Batterie(1.5, 20.0);
            ObjektTest(testarray);

            Console.WriteLine("\n--- class Mignonzelle ---\n");

            Console.WriteLine("\n- Test des Konstruktors -\n");

            VergleichenUndDrucken(new Mignonzelle().Spannung, 1.5);
            VergleichenUndDrucken(new Mignonzelle().Gewicht, 25.0);

            Console.WriteLine("\n- Prüfung von ToString, Equals, GetHashCode -\n");

            testarray = new object[2];
            testarray[0] = new Mignonzelle();
            testarray[1] = new Mignonzelle();
            ObjektTest(testarray);

            Console.WriteLine("\n--- class ElektrischeWaage ---\n");

            // Reflection - Infos über Klasse zur Laufzeit abfragen
            Type typeElektrischeWaage = typeof(ElektrischeWaage);
            // abstrakte Klasse?
            FehlerFalls(!typeElektrischeWaage.IsAbstract, "B1");
            // 'Spannung' abstrakte Eigenschaft?
            FehlerFalls(!typeElektrischeWaage.GetProperty("Spannung").GetGetMethod().IsAbstract, "B2");
            // Eigenschaft 'Last' vorhanden?
            FehlerFalls(typeElektrischeWaage.GetProperty("Last") == null, "B3");
            // Methoden 'Auflegen' und 'Wegnehmen' vorhanden?
            FehlerFalls(typeElektrischeWaage.GetMethod("Auflegen") == null, "B4");
            FehlerFalls(typeElektrischeWaage.GetMethod("Wegnehmen") == null, "B5");

            Console.WriteLine("\n--- class ElektrischeWaageMignon ---\n");

            Mignonzelle mignonzelle1 = new Mignonzelle();
            Mignonzelle mignonzelle2 = new Mignonzelle();
            Mignonzelle mignonzelle3 = new Mignonzelle();

            ElektrischeWaageMignon elektrischeWaageMignon = new ElektrischeWaageMignon();
            ElektrischeWaageMignon elektrischeWaageMignon0 = new ElektrischeWaageMignon();
            // gleiche Ojekte sollten wertgleich sein
            VergleicheElektrischeWaageMignon(elektrischeWaageMignon, elektrischeWaageMignon0, "C1", true);

            ElektrischeWaageMignon kopie0 = elektrischeWaageMignon.Clone() as ElektrischeWaageMignon;
            // korrkte Kopie sollte wertgleich sein
            VergleicheElektrischeWaageMignon(elektrischeWaageMignon0, kopie0, "C2", true);

            // es kann nicht nichts eingelegt werden
            try
            {
                elektrischeWaageMignon.Einlegen(null);
                FehlerFalls(true, "C3");
            }
            catch { }

            elektrischeWaageMignon.Einlegen(mignonzelle1);
            // das Einlegen der Batterie sollte zu wertungleichen Objekten führen
            VergleicheElektrischeWaageMignon(elektrischeWaageMignon, kopie0, "C4", false);
            ElektrischeWaageMignon kopie1 = elektrischeWaageMignon.Clone() as ElektrischeWaageMignon;
            // eingelegte Batterie sollte abrufbar sein
            FehlerFalls(elektrischeWaageMignon.Batterie != mignonzelle1, "C5");
            // Spannung sollte der eingelegten Batterie entsprechen
            FehlerFalls(elektrischeWaageMignon.Spannung != mignonzelle1.Spannung, "C6");

            // wenn Batteriefach belegt ist sollte keine Batterie mehr zugefügt werden
            try
            {
                elektrischeWaageMignon.Einlegen(mignonzelle3);
                FehlerFalls(true, "C7");
            }
            catch { }

            // es kann nicht nichts aufgelegt werden
            try
            {
                elektrischeWaageMignon.Auflegen(null);
                FehlerFalls(true, "C8");
            }
            catch { }

            elektrischeWaageMignon.Auflegen(mignonzelle2);
            // Auflegen der Mignonzelle sollte zu Wertungleichheit führen
            VergleicheElektrischeWaageMignon(elektrischeWaageMignon, kopie1, "C9", false);
            ElektrischeWaageMignon kopie2 = elektrischeWaageMignon.Clone() as ElektrischeWaageMignon;
            // aufgelegte Last sollte abrufbar sein
            FehlerFalls(elektrischeWaageMignon.Last != mignonzelle2, "C10");

            // wenn Waage belegt ist darf nichts Neues aufgelegt werden
            try
            {
                elektrischeWaageMignon.Auflegen(mignonzelle3);
                FehlerFalls(true, "C11");
            }
            catch { }

            IGewichtHabend gewichtHabend;
            elektrischeWaageMignon.Wegnehmen(out gewichtHabend);
            // Wegnehmen sollte zu Wertungleichheit zum vorherigen Zustand führen, aber zur Wertgleichheit zum vorvorherigen
            VergleicheElektrischeWaageMignon(elektrischeWaageMignon, kopie1, "C12", true);
            VergleicheElektrischeWaageMignon(elektrischeWaageMignon, kopie2, "C13", false);
            // Last sollte zurück gegeben worden sein
            FehlerFalls(gewichtHabend == null, "C14");
            Mignonzelle mignonzelle;
            if (gewichtHabend != null)
            {
                mignonzelle = gewichtHabend as Mignonzelle;
                // es sollte auch die tatsächliche Last zurück gegeben worden sein
                FehlerFalls(mignonzelle != mignonzelle2, "C15");
            }

            elektrischeWaageMignon.Entnehmen(out mignonzelle);
            // Entnehmen sollte zu Wertungleichheit zum vorherigen Zustand führen, aber zur Wertgleichheit zum vorvorvorherigen
            VergleicheElektrischeWaageMignon(elektrischeWaageMignon, kopie0, "C16", true);
            VergleicheElektrischeWaageMignon(elektrischeWaageMignon, kopie1, "C17", false);
            // Batterie sollte zurück gegeben worden sein
            FehlerFalls(mignonzelle == null, "C18");
            if (mignonzelle != null)
                // es sollte auch die tatsächliche Batterie zurück gegeben worden sein
                FehlerFalls(mignonzelle != mignonzelle1, "C19");
            // es sollte nun keine Last mehr aufliegen
            FehlerFalls(elektrischeWaageMignon.Last != null, "C20");
            // Batteriefach sollte leer sein
            FehlerFalls(elektrischeWaageMignon.Batterie != null, "C21");

            Console.WriteLine("\nFEHLER: " + _anzahlFehler.ToString());
            if (_anzahlFehler > 0)
                throw new Exception();
        }
    }
}
