// VolumenNeugeschäft_DUMMY.cs (zu Zulassungsaufgabe 22W)

namespace EasyBankingBilanz.Datenhaltung.Transfer
{
    public record VolumenNeugeschäft(Währung Konsumkredite,
                                     Währung Autokredite,
                                     Währung Hypothekenkredite,
                                     Währung Girokonten,
                                     Währung Spareinlagen,
                                     Währung Termingelder);
}