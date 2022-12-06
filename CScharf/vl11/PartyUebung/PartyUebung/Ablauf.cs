// Ablauf.cs (zu V11-Party)

using System;
using System.Threading.Tasks;
using System.Collections.Generic;

namespace Ablauf
{
    class Person
    {
        public string Name { get; }

        public Person(string name) => Name = String.IsNullOrWhiteSpace(name) ? throw new ArgumentNullException() : name;
    }

    class Party
    {
        private static Random _zufallsgenerator = new Random();
        public Person Gastgeber { get; }

        public Party(Person gastgeber)
        {
            Gastgeber = gastgeber ?? throw new ArgumentNullException(nameof(gastgeber));
            Console.WriteLine($"Willkommen zur Party von {gastgeber.Name}!\n");
        }

        private async Task GetränkMixenAsync(Person gast, string getränk)
        {
            Console.WriteLine($"Bitte ein(e) {getränk} für {gast.Name}!");
            await Task.Delay(_zufallsgenerator.Next(500, 1500));
            Console.WriteLine($"{getränk} für {gast.Name} ist " +
                $"fertig!");
        }

        private void GastgeberBegrüßen(Person gast)
        {
            //TODO warum ging um Methodenaufruf nicht?
            // Damit immer nur eine Person den Gastgeber begrüßt
            lock (Gastgeber)
            {
                Console.WriteLine($"{gast.Name} begrüßt Gastgeber/in {Gastgeber.Name}!");
                Task.Delay(_zufallsgenerator.Next(500, 1500)).Wait();
                Console.WriteLine($"{gast.Name} ist fertig mit der Begrüßung von {Gastgeber.Name}!");
            }
        }

        private void GetränkGenießen(Person gast, string getränk)
        {
            Console.WriteLine($"{gast.Name} genießt ihre(n)/seine(n) {getränk}!");
            Task.Delay(_zufallsgenerator.Next(500, 1500)).Wait();
        }

        public void Besuchen(Person gast, string getränk)
        {
            _ = gast ?? throw new ArgumentNullException(nameof(gast));
            _ = String.IsNullOrWhiteSpace(getränk) ? throw new ArgumentNullException(nameof(getränk)) : getränk;

            Console.WriteLine($"Gast {gast.Name} kommt zur Party von {Gastgeber.Name}!");

            // Mixen wird asynchron gemacht + wir warten halt mit dem genieß dass das fertig Wird
            // Task mixen = Task.Run(()=>GetränkMixen(gast, getränk)); wäre einfache Lösung wie in VL

            Task mixen = GetränkMixenAsync(gast, getränk);
            GastgeberBegrüßen(gast);

            mixen.Wait();
            GetränkGenießen(gast, getränk);

            Console.WriteLine($"Gast {gast.Name} verlässt die Party von {Gastgeber.Name}!");
        }
    }


    class Program
    {
        static void Main()
        {
            Party party = new Party(new Person("Claudia"));
            List<Task> gäste = new List<Task>();

            foreach ((string Person, string Getränk) gast in new (string, string)[]
            {
                ("Paul", "Bora Bora"),
                ("Ella", "Pink Lemonade Margarita"),
                ("Anne", "Chai Bubble Tea Latte"),
                ("Jonathan", "Raspberry Lemonade")
            })
            {
                Task.Delay(500).Wait();
                //Task.Run(()=>party.Besuchen(new Person(gast.Person), gast.Getränk)); ohne Liste haben wir das Problem, dass 
                // unser Program beim erreichen von the party ist over vom ersten Thread schon durch ist
                gäste.Add(Task.Run(()=>party.Besuchen(new Person(gast.Person), gast.Getränk)));
            }

            // Wait all will nen Array und keine Liste
            Task.WaitAll(gäste.ToArray());
            Console.WriteLine("\nthe party is over!");
        }
    }
}
