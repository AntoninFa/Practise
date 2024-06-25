// Periode_DUMMY.cs (zu Zulassungsaufgabe 22W)

using System;

namespace EasyBankingBilanz.Datenhaltung.Transfer
{
    public record Periode(int Nummer,
                          DateTime Beginn,
                          DateTime Ende)
    {
        public override string ToString()
        {
            return String.Format("{0,2} ({1}-{2})", Nummer.ToString(), Beginn.ToShortDateString(), Ende.ToShortDateString());
        }
    }
}