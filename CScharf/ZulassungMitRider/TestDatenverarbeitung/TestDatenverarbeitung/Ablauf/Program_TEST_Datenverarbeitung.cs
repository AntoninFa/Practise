// Program_TEST_Datenverarbeitung.cs (zu Zulassungsaufgabe 22W)
// Test des Namensraums 'Datenverarbeitung'
// Konsolenanwendung

using System;
using EasyBankingBilanz.Datenhaltung.Transfer;
using EasyBankingBilanz.Datenverarbeitung;
using static Testing.TestSupport;

namespace EasyBankingBilanz.TestDatenverarbeitung.Ablauf
{
    class Program
    {
        /// <summary>
        /// Hauptmethode
        /// </summary>
        static void Main()
        {
            #region Test auf Nichtänderbarkeit der Eigenschaften

            PL(); NonwriteableProperties<Bilanz>();

            #endregion

            #region Test auf korrekte Werte der Ergebnisse

            Infrastruktur infrastruktur = new Infrastruktur(150, 542000.0M, 62000000.0M, 15000000.0M);
            Infrastruktur infrastrukturVP = new Infrastruktur(152, 490000.0M, 60000000.0M, 11000000.0M);
            Kreditinstitute kreditinstitute = new Kreditinstitute(20000000.0M, 50000000.0M);
            Kreditinstitute kreditinstituteVP = new Kreditinstitute(10000000.0M, 20000000.0M);
            Kreditinstitute kreditinstituteVVP = new Kreditinstitute(10000000.0M, 20000000.0M);
            Kreditinstitute kreditinstituteVVVP = new Kreditinstitute(10000000.0M, 10000000.0M);
            PAR30 par30 = new PAR30(.05, .03, .015);
            VolumenNeugeschäft volumenNeugeschäft = new VolumenNeugeschäft(1000000000.0M, 1000000000.0M, 1000000000.0M,
                                                                           1000000000.0M, 2000000000.0M, 1500000000.0M);
            VolumenNeugeschäft volumenNeugeschäftVP = new VolumenNeugeschäft(1500000000.0M, 1000000000.0M, 1000000000.0M,
                                                                             1350000000.0M, 2400000000.0M, 1500000000.0M);
            VolumenNeugeschäft volumenNeugeschäftVVP = new VolumenNeugeschäft(1350000000.0M, 1000000000.0M, 1000000000.0M,
                                                                              1300000000.0M, 2200000000.0M, 1100000000.0M);
            VolumenNeugeschäft volumenNeugeschäftVVVP = new VolumenNeugeschäft(1400000000.0M, 1100000000.0M, 1000000000.0M,
                                                                               1400000000.0M, 2220000000.0M, 1150000000.0M);
            VolumenNeugeschäft volumenNeugeschäftVVVVP = new VolumenNeugeschäft(1500000000.0M, 1080000000.0M, 1000000000.0M,
                                                                                1350000000.0M, 2400000000.0M, 1200000000.0M);
            VorgabenAktuellePeriode vorgabenAktuellePeriode = new VorgabenAktuellePeriode();

            PL(); Bilanz bilanz = new Bilanz(infrastruktur,
                                             infrastrukturVP,
                                             kreditinstitute,
                                             kreditinstituteVP,
                                             kreditinstituteVVP,
                                             kreditinstituteVVVP,
                                             par30,
                                             volumenNeugeschäft,
                                             volumenNeugeschäftVP,
                                             volumenNeugeschäftVVP,
                                             volumenNeugeschäftVVVP,
                                             volumenNeugeschäftVVVVP,
                                             vorgabenAktuellePeriode);

            PL(); CompareAndPrint(bilanz.AktivaForderungAnKreditinstitute.Betrag, 50000000.0M);
            PL(); CompareAndPrint(bilanz.AktivaKonsumkredit.Betrag, 1050000000.0M);                                                                                   
            PL(); CompareAndPrint(bilanz.AktivaAutokredit.Betrag, 1545000000.0M);
            PL(); CompareAndPrint(bilanz.AktivaHypothekenkredit.Betrag, 3045000000.0M);
            PL(); CompareAndPrint(bilanz.AktivaForderungenAnKundenBrutto.Betrag, 5640000000.0M);
            //PL(); CompareAndPrint(bilanz.AktivaRisikovorsorge.Betrag, 28200000.0M);
            PL(); CompareAndPrint(bilanz.AktivaForderungenAnKundenNetto.Betrag, 5611800000.0M);
            /*
            PL(); CompareAndPrint(bilanz.AktivaFilialen.Betrag, 79267500.0M);
            PL(); CompareAndPrint(bilanz.AktivaIT.Betrag, 60000000.0M);
            PL(); CompareAndPrint(bilanz.AktivaSummeOhneLiquideMittel.Betrag, 5801067500.0M);
            PL(); CompareAndPrint(bilanz.AktivaLiquideMittel.Betrag, 660382500.0M);
            PL(); CompareAndPrint(bilanz.AktivaSumme.Betrag, 6461450000.0M);

            PL(); CompareAndPrint(bilanz.PassivaVerbindlichkeitenGegenüberKreditinstituten.Betrag, 100000000.0M);
            PL(); CompareAndPrint(bilanz.PassivaGirokonto.Betrag, 1001000000.0M);
            PL(); CompareAndPrint(bilanz.PassivaSpareinlage.Betrag, 2020000000.0M);
            PL(); CompareAndPrint(bilanz.PassivaTermingeld.Betrag, 3075450000.0M);
            PL(); CompareAndPrint(bilanz.PassivaVerbindlichkeitenGegenüberKunden.Betrag, 6096450000.0M);
            PL(); CompareAndPrint(bilanz.PassivaGezeichnetesKapital.Betrag, 150000000.0M);
            PL(); CompareAndPrint(bilanz.PassivaGewinnrücklageBrutto.Betrag, 100000000.0M);
            PL(); CompareAndPrint(bilanz.PassivaDividendensumme.Betrag, 25000000.0M);
            PL(); CompareAndPrint(bilanz.PassivaGewinnrücklageNachAusschüttung.Betrag, 75000000.0M);
            PL(); CompareAndPrint(bilanz.PassivaVerlustvortrag.Betrag, 0.0M);
            PL(); CompareAndPrint(bilanz.PassivaPeriodenüberschuss.Betrag, 40000000.0M);
            PL(); CompareAndPrint(bilanz.PassivaEigenkapital.Betrag, 265000000.0M);
            PL(); CompareAndPrint(bilanz.PassivaSummeOhneÜberziehungskredit.Betrag, 6461450000.0M);
            PL(); CompareAndPrint(bilanz.PassivaÜberziehungskredit.Betrag, 0.0M);
            PL(); CompareAndPrint(bilanz.PassivaSumme.Betrag, 6461450000.0M);
            */

            #endregion

            #region Test der Plausibilitätsprüfungen

            PL(); ProvokeException(() => new Bilanz(null,
                                                    infrastrukturVP,
                                                    kreditinstitute,
                                                    kreditinstituteVP,
                                                    kreditinstituteVVP,
                                                    kreditinstituteVVVP,
                                                    par30,
                                                    volumenNeugeschäft,
                                                    volumenNeugeschäftVP,
                                                    volumenNeugeschäftVVP,
                                                    volumenNeugeschäftVVVP,
                                                    volumenNeugeschäftVVVVP,
                                                    vorgabenAktuellePeriode));

            PL(); ProvokeException(() => new Bilanz(infrastruktur,
                                                    null,
                                                    kreditinstitute,
                                                    kreditinstituteVP,
                                                    kreditinstituteVVP,
                                                    kreditinstituteVVVP,
                                                    par30,
                                                    volumenNeugeschäft,
                                                    volumenNeugeschäftVP,
                                                    volumenNeugeschäftVVP,
                                                    volumenNeugeschäftVVVP,
                                                    volumenNeugeschäftVVVVP,
                                                    vorgabenAktuellePeriode));

            PL(); ProvokeException(() => new Bilanz(infrastruktur,
                                                    infrastrukturVP,
                                                    null,
                                                    kreditinstituteVP,
                                                    kreditinstituteVVP,
                                                    kreditinstituteVVVP,
                                                    par30,
                                                    volumenNeugeschäft,
                                                    volumenNeugeschäftVP,
                                                    volumenNeugeschäftVVP,
                                                    volumenNeugeschäftVVVP,
                                                    volumenNeugeschäftVVVVP,
                                                    vorgabenAktuellePeriode));

            PL(); ProvokeException(() => new Bilanz(infrastruktur,
                                                    infrastrukturVP,
                                                    kreditinstitute,
                                                    null,
                                                    kreditinstituteVVP,
                                                    kreditinstituteVVVP,
                                                    par30,
                                                    volumenNeugeschäft,
                                                    volumenNeugeschäftVP,
                                                    volumenNeugeschäftVVP,
                                                    volumenNeugeschäftVVVP,
                                                    volumenNeugeschäftVVVVP,
                                                    vorgabenAktuellePeriode));

            PL(); ProvokeException(() => new Bilanz(infrastruktur,
                                                    infrastrukturVP,
                                                    kreditinstitute,
                                                    kreditinstituteVP,
                                                    null,
                                                    kreditinstituteVVVP,
                                                    par30,
                                                    volumenNeugeschäft,
                                                    volumenNeugeschäftVP,
                                                    volumenNeugeschäftVVP,
                                                    volumenNeugeschäftVVVP,
                                                    volumenNeugeschäftVVVVP,
                                                    vorgabenAktuellePeriode));

            PL(); ProvokeException(() => new Bilanz(infrastruktur,
                                                    infrastrukturVP,
                                                    kreditinstitute,
                                                    kreditinstituteVP,
                                                    kreditinstituteVVP,
                                                    null,
                                                    par30,
                                                    volumenNeugeschäft,
                                                    volumenNeugeschäftVP,
                                                    volumenNeugeschäftVVP,
                                                    volumenNeugeschäftVVVP,
                                                    volumenNeugeschäftVVVVP,
                                                    vorgabenAktuellePeriode));

            PL(); ProvokeException(() => new Bilanz(infrastruktur,
                                                    infrastrukturVP,
                                                    kreditinstitute,
                                                    kreditinstituteVP,
                                                    kreditinstituteVVP,
                                                    kreditinstituteVVVP,
                                                    null,
                                                    volumenNeugeschäft,
                                                    volumenNeugeschäftVP,
                                                          volumenNeugeschäftVVP,
                                                    volumenNeugeschäftVVVP,
                                                    volumenNeugeschäftVVVVP,
                                                    vorgabenAktuellePeriode));

            PL(); ProvokeException(() => new Bilanz(infrastruktur,
                                                    infrastrukturVP,
                                                    kreditinstitute,
                                                    kreditinstituteVP,
                                                    kreditinstituteVVP,
                                                    kreditinstituteVVVP,
                                                    par30,
                                                    null,
                                                    volumenNeugeschäftVP,
                                                    volumenNeugeschäftVVP,
                                                    volumenNeugeschäftVVVP,
                                                    volumenNeugeschäftVVVVP,
                                                    vorgabenAktuellePeriode));

            PL(); ProvokeException(() => new Bilanz(infrastruktur,
                                                    infrastrukturVP,
                                                    kreditinstitute,
                                                    kreditinstituteVP,
                                                    kreditinstituteVVP,
                                                    kreditinstituteVVVP,
                                                    par30,
                                                    volumenNeugeschäft,
                                                    null,
                                                    volumenNeugeschäftVVP,
                                                    volumenNeugeschäftVVVP,
                                                    volumenNeugeschäftVVVVP,
                                                    vorgabenAktuellePeriode));

            PL();  ProvokeException(() => new Bilanz(infrastruktur,
                                                    infrastrukturVP,
                                                    kreditinstitute,
                                                    kreditinstituteVP,
                                                    kreditinstituteVVP,
                                                    kreditinstituteVVVP,
                                                    par30,
                                                    volumenNeugeschäft,
                                                    volumenNeugeschäftVP,
                                                    null,
                                                    volumenNeugeschäftVVVP,
                                                    volumenNeugeschäftVVVVP,
                                                    vorgabenAktuellePeriode));

            PL(); ProvokeException(() => new Bilanz(infrastruktur,
                                                    infrastrukturVP,
                                                    kreditinstitute,
                                                    kreditinstituteVP,
                                                    kreditinstituteVVP,
                                                    kreditinstituteVVVP,
                                                    par30,
                                                    volumenNeugeschäft,
                                                    volumenNeugeschäftVP,
                                                    volumenNeugeschäftVVP,
                                                    null,
                                                    volumenNeugeschäftVVVVP,
                                                    vorgabenAktuellePeriode));

            PL(); ProvokeException(() => new Bilanz(infrastruktur,
                                                    infrastrukturVP,
                                                    kreditinstitute,
                                                    kreditinstituteVP,
                                                    kreditinstituteVVP,
                                                    kreditinstituteVVVP,
                                                    par30,
                                                    volumenNeugeschäft,
                                                    volumenNeugeschäftVP,
                                                    volumenNeugeschäftVVP,
                                                    volumenNeugeschäftVVVP,
                                                    null,
                                                    vorgabenAktuellePeriode));

            PL(); ProvokeException(() => new Bilanz(infrastruktur,
                                                    infrastrukturVP,
                                                    kreditinstitute,
                                                    kreditinstituteVP,
                                                    kreditinstituteVVP,
                                                    kreditinstituteVVVP,
                                                    par30,
                                                    volumenNeugeschäft,
                                                    volumenNeugeschäftVP,
                                                    volumenNeugeschäftVVP,
                                                    volumenNeugeschäftVVVP,
                                                    volumenNeugeschäftVVVVP,
                                                    null));

            #endregion

            Console.WriteLine("\n\n--- ERGEBNIS ---\n");

            PrintResult();
        }
    }
}
