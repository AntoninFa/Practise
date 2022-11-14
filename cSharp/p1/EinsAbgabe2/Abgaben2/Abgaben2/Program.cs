using System;
using System.Text;
using System.Diagnostics;

namespace Abgaben2
{
    class Program
    {
        static void Main()
        {
            string input = Console.ReadLine();
            var gnirts = "abcdefghijklmnopqrstuvwxyz";
            var sW = new StringWorker();

            /*Console.WriteLine(sW.Reverse(gnirts));
            Console.WriteLine(sW.Reverse("1234567890"));
            var testMessage =
                "Program.cs(14, 21): [CS0029] Der Typ \"void\" kann nicht implizit in \"bool\" konvertiert werden.";
            
            
            var egasseMtset = sW.Reverse(testMessage);
            Trace.Assert(testMessage == sW.Reverse(egasseMtset));
            
            Console.WriteLine(egasseMtset);
            Console.WriteLine(sW.Reverse(egasseMtset));
            */
            Console.WriteLine(sW.Reverse(input));
            string test = Console.ReadLine();
            
        }
    }

    class StringWorker
    {
        
        public string Reverse(string wert)
        {
            if (wert.Length <= 1)
                return wert[0].ToString();
            
            return Reverse(lawnMower(wert, (int)wert.Length/2))  + "" +    Reverse(guillotine(wert, ((int)wert.Length/2)));
        }

        public string guillotine(string s, int last)
        {
            var bob = new StringBuilder("", last);
            var stringAsChars = s.ToCharArray();
            var headAsChars = new char[last];
            for (int i = 0; i < last; i++)
            {
                headAsChars[i] = stringAsChars[i];
            }

            bob.Append(headAsChars);
            return bob.ToString();
        }

        private String lawnMower(string s, int first)
        {
            var bob = new StringBuilder("", s.Length-first);
            var stringAsChars = s.ToCharArray();
            var tailAsChars = new char[s.Length - first];
            for (int i = first; i < s.Length; i++)
            {
                tailAsChars[i-first] = stringAsChars[i];
            }

            bob.Append(tailAsChars);
            return bob.ToString();
        }
    }
    
    
    
}

