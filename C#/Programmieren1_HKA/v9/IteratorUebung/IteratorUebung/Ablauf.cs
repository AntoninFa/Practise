namespace IteratorUebung;

public class Ablauf
{
    static void Main()
    {
        //var w = new BinBaum();
        /*
         * new BinBaumNode(13,
               new BinBaumNode(7,
                   new BinBaumNode(4),
                   new BinBaumNode(8)),
               new BinBaumNode(15,
                   new BinBaumNode(14),
                   new BinBaumNode(19)));
         */

        var list = new MyList();
        for (int i = 10; i >= 0; i--)
            list.Add(i);

        foreach (var n in list)
            Console.WriteLine(n.Data);
    }
}