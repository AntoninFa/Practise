using System;
using System.Text;

namespace Abgaben2
{
    class Program
    {
        static void Main()
        {
            var gnirts = "abcdefghijklmnop";
            var sW = new StringWorker();
            Console.Write(sW.Reverse(gnirts));
            
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

