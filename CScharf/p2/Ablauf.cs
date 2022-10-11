// Ablauf.cs (zu V01-Vorratsschrank)

using System;
using Daten;

namespace Ablauf
{
    class Program
    {
        static void VorratsschrankDrucken(Vorratsschrank vorratsschrank)
        {
            for (int index = 0; index < vorratsschrank.Anzahl; index++)
            {
                Lebensmittel lebensmittel = vorratsschrank.LebensmittelAnIndex(index);
                Console.WriteLine($"{lebensmittel.Bezeichnung} ({lebensmittel.Verfallsdatum.ToShortDateString()})");
            }
        }

    static void Main()
        {
            // der brave Programmteil

            Vorratsschrank kühlschrank = new Vorratsschrank(10);
            kühlschrank.Zufügen(new Lebensmittel("Milch", new DateTime(2022, 10, 8)));
            kühlschrank.Zufügen(new Lebensmittel("Aufschnitt", new DateTime(2022, 10, 5)));
            kühlschrank.Zufügen(new Lebensmittel("Eier", new DateTime(2022, 10, 7)));
            kühlschrank.Zufügen(new Lebensmittel("Saft", new DateTime(2022, 11, 1)));
            VorratsschrankDrucken(kühlschrank);

            Console.WriteLine();

            kühlschrank.EntnehmenVor(new DateTime(2022, 10, 8));
            VorratsschrankDrucken(kühlschrank);

            Console.WriteLine();

            kühlschrank.Zufügen(new Lebensmittel("Butter", new DateTime(2022, 10, 11)));
            kühlschrank.Zufügen(new Lebensmittel("Bananen", new DateTime(2022, 10, 13)));
            VorratsschrankDrucken(kühlschrank);

            Console.WriteLine();

            // der böse Programmteil

            kühlschrank.LebensmittelAnIndex(3).Verfallsdatum = new DateTime(2022, 9, 15); // böse Anweisung 1
            kühlschrank.Schrank[1] = new Lebensmittel("Weintrauben", new DateTime(2022, 9, 20)); // böse Anweisung 2
            kühlschrank.Zufügen(new Lebensmittel("Pudding", new DateTime(2022, 11, 25)));
            VorratsschrankDrucken(kühlschrank);

            Console.WriteLine();

            kühlschrank.Anzahl--; // böse Anweisung 3
            kühlschrank.EntnehmenVor(new DateTime(2022, 10, 8));
            VorratsschrankDrucken(kühlschrank);

            Console.WriteLine();

            // der prüfende Programmteil

            int tick = 0;

            try
            {
                new Lebensmittel(null, DateTime.Now);
                tick++;
            }
            catch { }
            try
            {
                new Lebensmittel(" ", DateTime.Now);
                tick++;
            }
            catch { }
            try
            {
                new Vorratsschrank(0);
                tick++;
            }
            catch(Exception e)
            {
                if (e.GetType().Name != "Exception")
                    tick++;
            }
            try
            {
                new Vorratsschrank(1).Zufügen(null);
                tick++;
            }
            catch (Exception e)
            {
                if (e.GetType().Name != "Exception")
                    tick++;
            }
            try
            {
                Vorratsschrank temp = new Vorratsschrank(1);
                temp.Zufügen(new Lebensmittel("L1", DateTime.Now));
                temp.Zufügen(new Lebensmittel("L2", DateTime.Now));
                tick++;
            }
            catch (Exception e)
            {
                if (e.GetType().Name != "Exception")
                    tick++;
            }
            try
            {
                Vorratsschrank temp = new Vorratsschrank(1);
                temp.Zufügen(new Lebensmittel("L1", DateTime.Now));
                temp.LebensmittelAnIndex(1);
                tick++;
            }
            catch (Exception e)
            {
                if (e.GetType().Name != "Exception")
                    tick++;
            }
            try
            {
                Vorratsschrank temp = new Vorratsschrank(1);
                temp.Zufügen(new Lebensmittel("L1", DateTime.Now));
                temp.LebensmittelAnIndex(-1);
                tick++;
            }
            catch (Exception e)
            {
                if (e.GetType().Name != "Exception")
                    tick++;
            }

            Console.WriteLine($"TICKS: {tick}");
            if (tick > 0)
                throw new Exception();
        }
    }
}
