// Ablauf.cs (zu V03-Leuchte)

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

        static void Main()
        {
            Lampe lampe = new Lampe(1000);
            Console.WriteLine($"Lampe: {lampe.Lichtstrom} lm");
            ExceptionOn(lampe.Lichtstrom != 1000);

            ElektrischeLampe elektrischeLampe = new ElektrischeLampe(860, 12, 35); // Halogen 12V
            Console.WriteLine($"Elektrische Lampe: {elektrischeLampe.Lichtstrom} lm, {elektrischeLampe.Spannung} V, {elektrischeLampe.Leistungsaufnahme} W, {elektrischeLampe.Lichtausbeute:0.00} lm/W");
            ExceptionOn(elektrischeLampe.Lichtstrom != 860);
            ExceptionOn(elektrischeLampe.Spannung != 12);
            ExceptionOn(elektrischeLampe.Leistungsaufnahme != 35);
            ExceptionOn(Math.Abs(elektrischeLampe.Lichtausbeute - 860.0 / 35.0) > 1E-10);

            Glühlampe glühlampe = new Glühlampe(720, 230, 60, "Wolfram");
            Console.WriteLine($"Glühlampe: {glühlampe.Lichtstrom} lm, {glühlampe.Spannung} V, {glühlampe.Leistungsaufnahme} W, {glühlampe.Lichtausbeute:0.00} lm/W, {glühlampe.Glühfaden}");
            ExceptionOn(glühlampe.Lichtstrom != 720);
            ExceptionOn(glühlampe.Spannung != 230);
            ExceptionOn(glühlampe.Leistungsaufnahme != 60);
            ExceptionOn(Math.Abs(glühlampe.Lichtausbeute - 720.0 / 60.0) > 1e-10);
            ExceptionOn(glühlampe.Glühfaden != "Wolfram");

            ChemischPhysikalischeLampe chemischPhysikalischeLampe = new ChemischPhysikalischeLampe(500);
            Console.WriteLine($"Chem.-phys. Lampe: {chemischPhysikalischeLampe.Lichtstrom} lm");
            ExceptionOn(chemischPhysikalischeLampe.Lichtstrom != 500);

            Kerze kerze = new Kerze(10, "Wachs");
            Console.WriteLine($"Kerze: {kerze.Lichtstrom} lm, {kerze.Brennstoff}");
            ExceptionOn(kerze.Lichtstrom != 10);
            ExceptionOn(kerze.Brennstoff != "Wachs");

            int anzahlFehler = 0;

            try
            {
                new Lampe(-1);
                Console.WriteLine("Fehler A1");
                anzahlFehler++;
            }
            catch { }
            try
            {
                new ElektrischeLampe(-1, 12, 35);
                Console.WriteLine("Fehler B1");
                anzahlFehler++;
            }
            catch { }
            try
            {
                new ElektrischeLampe(860, -1, 35);
                Console.WriteLine("Fehler B2");
                anzahlFehler++;
            }
            catch { }
            try
            {
                new ElektrischeLampe(860, 12, -1);
                Console.WriteLine("Fehler B3");
                anzahlFehler++;
            }
            catch { }
            try
            {
                new Glühlampe(-1, 230, 60, "Wolfram");
                Console.WriteLine("Fehler C1");
                anzahlFehler++;
            }
            catch { }
            try
            {
                new Glühlampe(720, -1, 60, "Wolfram");
                Console.WriteLine("Fehler C2");
                anzahlFehler++;
            }
            catch { }
            try
            {
                new Glühlampe(720, 230, -1, "Wolfram");
                Console.WriteLine("Fehler C3");
                anzahlFehler++;
            }
            catch { }
            try
            {
                new Glühlampe(720, 230, 60, null);
                Console.WriteLine("Fehler C4");
                anzahlFehler++;
            }
            catch { }
            try
            {
                new Glühlampe(720, 230, 60, " ");
                Console.WriteLine("Fehler C5");
                anzahlFehler++;
            }
            catch { }
            try
            {
                new ChemischPhysikalischeLampe(-1);
                Console.WriteLine("Fehler D1");
                anzahlFehler++;
            }
            catch { }
            try
            {
                new Kerze(-1, "Wachs");
                Console.WriteLine("Fehler E1");
                anzahlFehler++;
            }
            catch { }
            try
            {
                new Kerze(10, null);
                Console.WriteLine("Fehler E2");
                anzahlFehler++;
            }
            catch { }
            try
            {
                new Kerze(10, " ");
                Console.WriteLine("Fehler E3");
                anzahlFehler++;
            }
            catch { }

            Console.WriteLine($"\nFEHLER: {anzahlFehler}");

            if (anzahlFehler > 0)
                throw new Exception();
        }
    }
}
