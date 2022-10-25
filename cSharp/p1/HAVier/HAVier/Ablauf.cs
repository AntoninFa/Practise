// Ablauf.cs (zu V04-Fahrrad)

using System;
using Daten;

namespace Ablauf
{
    class Program
    {
        private static int _anzahlFehler = 0;

        static void FehlerFalls(bool bedingung, string kennung)
        {
            if (bedingung)
            {
                Console.WriteLine("Fehler " + kennung);
                _anzahlFehler++;
            }
        }

        static void FahrradDrucken(Fahrrad fahrrad)
        {
            Console.WriteLine(fahrrad.Darstellung()
                + "   insgesamt: " + (fahrrad.IstPutzbedürftig ? "putzbedürftig" : "sauber"));
        }

        static void GegenstandDrucken(Gegenstand gegenstand)
        {
            Console.WriteLine("Gegenstand: " + (gegenstand.IstPflegebedürftig ? "pflegebedürftig" : "OK"));
        }

        static void Main()
        {
            Console.WriteLine("\n--- Fahrrad\n");

            Fahrrad fahrrad = new Fahrrad();
            FahrradDrucken(fahrrad);
            FehlerFalls(fahrrad.VorderradIstPflegebedürftig, "A1");
            FehlerFalls(fahrrad.HinterradIstPflegebedürftig, "A2");
            FehlerFalls(fahrrad.RahmenIstPflegebedürftig, "A3");
            FehlerFalls(fahrrad.IstPutzbedürftig, "A4");
            fahrrad.Fahren();
            FahrradDrucken(fahrrad);
            FehlerFalls(!fahrrad.VorderradIstPflegebedürftig, "B1");
            FehlerFalls(fahrrad.HinterradIstPflegebedürftig, "B2");
            FehlerFalls(fahrrad.RahmenIstPflegebedürftig, "B3");
            FehlerFalls(!fahrrad.IstPutzbedürftig, "B4");
            fahrrad.Fahren();
            FahrradDrucken(fahrrad);
            FehlerFalls(!fahrrad.VorderradIstPflegebedürftig, "C1");
            FehlerFalls(!fahrrad.HinterradIstPflegebedürftig, "C2");
            FehlerFalls(fahrrad.RahmenIstPflegebedürftig, "C3");
            FehlerFalls(!fahrrad.IstPutzbedürftig, "C4");
            fahrrad.Fahren();
            FahrradDrucken(fahrrad);
            FehlerFalls(!fahrrad.VorderradIstPflegebedürftig, "D1");
            FehlerFalls(!fahrrad.HinterradIstPflegebedürftig, "D2");
            FehlerFalls(!fahrrad.RahmenIstPflegebedürftig, "D3");
            FehlerFalls(!fahrrad.IstPutzbedürftig, "D4");
            fahrrad.Putzen();
            FahrradDrucken(fahrrad);
            FehlerFalls(fahrrad.VorderradIstPflegebedürftig, "E1");
            FehlerFalls(fahrrad.HinterradIstPflegebedürftig, "E2");
            FehlerFalls(fahrrad.RahmenIstPflegebedürftig, "E3");
            FehlerFalls(fahrrad.IstPutzbedürftig, "E4");

            Console.WriteLine("\n--- VerkehrssicheresFahrrad\n");

            VerkehrssicheresFahrrad verkehrssicheresFahrrad = new VerkehrssicheresFahrrad();
            FahrradDrucken(verkehrssicheresFahrrad);
            FehlerFalls(verkehrssicheresFahrrad.VorderradIstPflegebedürftig, "F1");
            FehlerFalls(verkehrssicheresFahrrad.HinterradIstPflegebedürftig, "F2");
            FehlerFalls(verkehrssicheresFahrrad.RahmenIstPflegebedürftig, "F3");
            FehlerFalls(verkehrssicheresFahrrad.LichtanlageIstPflegebedürftig, "F4");
            FehlerFalls(verkehrssicheresFahrrad.IstPutzbedürftig, "F5");
            verkehrssicheresFahrrad.Fahren();
            FahrradDrucken(verkehrssicheresFahrrad);
            FehlerFalls(!verkehrssicheresFahrrad.VorderradIstPflegebedürftig, "G1");
            FehlerFalls(verkehrssicheresFahrrad.HinterradIstPflegebedürftig, "G2");
            FehlerFalls(verkehrssicheresFahrrad.RahmenIstPflegebedürftig, "G3");
            FehlerFalls(verkehrssicheresFahrrad.LichtanlageIstPflegebedürftig, "G4");
            FehlerFalls(!verkehrssicheresFahrrad.IstPutzbedürftig, "G5");
            verkehrssicheresFahrrad.Fahren();
            FahrradDrucken(verkehrssicheresFahrrad);
            FehlerFalls(!verkehrssicheresFahrrad.VorderradIstPflegebedürftig, "H1");
            FehlerFalls(!verkehrssicheresFahrrad.HinterradIstPflegebedürftig, "H2");
            FehlerFalls(verkehrssicheresFahrrad.RahmenIstPflegebedürftig, "H3");
            FehlerFalls(verkehrssicheresFahrrad.LichtanlageIstPflegebedürftig, "H4");
            FehlerFalls(!verkehrssicheresFahrrad.IstPutzbedürftig, "H5");
            verkehrssicheresFahrrad.Fahren();
            FahrradDrucken(verkehrssicheresFahrrad);
            FehlerFalls(!verkehrssicheresFahrrad.VorderradIstPflegebedürftig, "I1");
            FehlerFalls(!verkehrssicheresFahrrad.HinterradIstPflegebedürftig, "I2");
            FehlerFalls(!verkehrssicheresFahrrad.RahmenIstPflegebedürftig, "I3");
            FehlerFalls(verkehrssicheresFahrrad.LichtanlageIstPflegebedürftig, "I4");
            FehlerFalls(!verkehrssicheresFahrrad.IstPutzbedürftig, "I5");
            verkehrssicheresFahrrad.Fahren();
            FahrradDrucken(verkehrssicheresFahrrad);
            FehlerFalls(!verkehrssicheresFahrrad.VorderradIstPflegebedürftig, "J1");
            FehlerFalls(!verkehrssicheresFahrrad.HinterradIstPflegebedürftig, "J2");
            FehlerFalls(!verkehrssicheresFahrrad.RahmenIstPflegebedürftig, "J3");
            FehlerFalls(!verkehrssicheresFahrrad.LichtanlageIstPflegebedürftig, "J4");
            FehlerFalls(!verkehrssicheresFahrrad.IstPutzbedürftig, "J5");
            verkehrssicheresFahrrad.Putzen();
            FahrradDrucken(verkehrssicheresFahrrad);
            FehlerFalls(verkehrssicheresFahrrad.VorderradIstPflegebedürftig, "K1");
            FehlerFalls(verkehrssicheresFahrrad.HinterradIstPflegebedürftig, "K2");
            FehlerFalls(verkehrssicheresFahrrad.RahmenIstPflegebedürftig, "K3");
            FehlerFalls(verkehrssicheresFahrrad.LichtanlageIstPflegebedürftig, "K4");
            FehlerFalls(verkehrssicheresFahrrad.IstPutzbedürftig, "K5");

            Console.WriteLine("\n--- Gegenstand\n");

            Gegenstand gegenstand = new Gegenstand();
            GegenstandDrucken(gegenstand);
            FehlerFalls(gegenstand.IstPflegebedürftig, "L1");
            gegenstand.PflegebedürftigMachen();
            GegenstandDrucken(gegenstand);
            FehlerFalls(!gegenstand.IstPflegebedürftig, "L2");
            gegenstand.Pflegen();
            GegenstandDrucken(gegenstand);
            FehlerFalls(gegenstand.IstPflegebedürftig, "L3");

            Console.WriteLine("\nFEHLER: " + _anzahlFehler.ToString());
            if (_anzahlFehler > 0)
                throw new Exception();
        }
    }
}
