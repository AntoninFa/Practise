// Program.cs (zu V08-Telefonverzeichnis)

using System;
using System.Linq;
using System.Collections;
using System.Text;
using Telefonverzeichnis.Daten;

namespace Telefonverzeichnis.Ablauf
{
    static class Program
    {
        static void AufzählbarDrucken(this IEnumerable obj)
        {
            foreach (object o in obj ?? throw new ArgumentNullException())
                Console.WriteLine(o.ToString());
        }

        static void ObjektDrucken(this object o) => Console.WriteLine((o ?? throw new ArgumentNullException()).ToString());

        static void Main()
        {
            int[] arr = { 1, 2, 3, 4, 5, 6, 7, 8 };
            arr.Sum().ObjektDrucken();
            arr.Average().ObjektDrucken();
            arr.Where(j => j % 2 == 0).AufzählbarDrucken();
            arr.Where(j => j % 2 == 0).Aggregate(new StringBuilder(), (a, j) => a.Append(j.ToString() + " ")).ObjektDrucken();

            Console.WriteLine();

            Vorwahl v1 = new Vorwahl("0721", "Karlsruhe");

            Verzeichnis vz = new Verzeichnis();
            vz.Zufügen(v1);
            vz.Zufügen(new Vorwahl("0221", "Köln"));
            vz["040"] = "Hamburg";

            vz.Suchen("0221").Ort.ObjektDrucken();
            vz["040"].ObjektDrucken();
            vz["0721"].ObjektDrucken();

            Console.WriteLine();

            AufzählbarDrucken(vz);
            vz.AufzählbarDrucken();

            vz.Where(v => v.Vorwahlnummer.StartsWith("04")).AufzählbarDrucken();

            Console.ReadKey();
        }
    }
}
