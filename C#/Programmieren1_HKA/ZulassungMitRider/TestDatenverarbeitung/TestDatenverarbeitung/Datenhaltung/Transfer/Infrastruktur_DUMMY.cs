// Infrastruktur_DUMMY.cs (zu Zulassungsaufgabe 22W)

namespace EasyBankingBilanz.Datenhaltung.Transfer
{
    public record Infrastruktur(int AnzahlFilialen,
                                Währung AnschaffungskostenJeFiliale,
                                Währung ITWert,
                                Währung ITInvestitionen);
}