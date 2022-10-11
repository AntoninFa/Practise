// Daten.cs (zu V01-Vorratsschrank)

using System;

namespace Daten
{
    // ein Lebensmittel hat Bezeichnung und Verfallsdatum
    class Lebensmittel
    {
        public string Bezeichnung;
        public DateTime Verfallsdatum;

        public Lebensmittel(string bezeichnung, DateTime verfallsdatum)
        {
            Bezeichnung = bezeichnung;
            Verfallsdatum = verfallsdatum;
        }
    }

    // ein Vorratsschrank hat eine Auflistung von Lebensmitteln
    // diese Auflistung ist nach Verfallsdatum sortiert (ältestes zuerst)
    // siehe: System.DateTime.CompareTo
    class Vorratsschrank
    {
        public Lebensmittel[] Schrank;
        public int Anzahl;

        public Vorratsschrank(int größe)
        {
            Schrank = new Lebensmittel[größe];
            Anzahl = 0;
        }

        public void Zufügen(Lebensmittel lebensmittel)
        {
            int index;
            for (index = Anzahl; index > 0 && Schrank[index - 1].Verfallsdatum.CompareTo(lebensmittel.Verfallsdatum) > 0; index--)
                Schrank[index] = Schrank[index - 1];
            Schrank[index] = lebensmittel;
            Anzahl++;
        }

        public void EntnehmenVor(DateTime entnahmedatum)
        {
            int index;
            for (index = 0; index < Anzahl && Schrank[index].Verfallsdatum.CompareTo(entnahmedatum) < 0; index++) ;
            if (index > 0)
            {
                for (int index2 = index; index2 < Anzahl; index2++)
                    Schrank[index2 - index] = Schrank[index2];
                Anzahl = Anzahl - index;
            }
        }

        public Lebensmittel LebensmittelAnIndex(int index)
        {
            return Schrank[index];
        }
    }
}