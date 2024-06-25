// Daten.cs (zu V08-Telefonverzeichnis)

using System;
using System.Collections.Generic;
using System.Collections;

namespace Telefonverzeichnis.Daten
{

    public interface IInventarisierbar
    {
         string Inventarnummer { get; }
         string Bezeichnung { get; }

    }
/*'
    public class Inventurverzeichnis : IEnumerable<IInventarisierbar>
    {
        private List<IInventarisierbar> _verzeichnis;
        public Inventarverzeichnis() => _verzeichnis = new List<IInventarisierbar>();
        public string[] Inventarnummern => _verzeichnis.Select(i => i.Inventarnummer).ToArray();

        public bool ContainsNum(string Inventarnummer)
        {
            return Inventarnummern.Contains(Inventarnummer ?? throw new ArgumentNullException());
        }
        

        
        public IEnumerator<IInventarisierbar> GetEnumerator()
        {
            foreach (IInventarisierbar inventarisierbar in _verzeichnis)
            {
                yield return inventarisierbar;
            }
        }
        

        public IEnumerator<IInventarisierbar> GetEnumerator() => _verzeichnis.GetEnumerator();
    }
    */
    class Vorwahl
    {
        public string Vorwahlnummer { get; }
        public string Ort { get; }

        public Vorwahl(string vorwahlnummer, string ort)
        {
            Vorwahlnummer = String.IsNullOrWhiteSpace(vorwahlnummer) ? throw new ArgumentNullException("vorwahlnummer") : vorwahlnummer;
            Ort = String.IsNullOrWhiteSpace(ort) ? throw new ArgumentNullException("ort") : ort;
        }

        public override string ToString() => Ort + " (" + Vorwahlnummer + ")";
    }

    class Verzeichnis : IEnumerable<Vorwahl>
    {
        private readonly List<Vorwahl> _verzeichnis;

        public string this[string vorwahlnummer]
        {
            get => Suchen(vorwahlnummer).Ort;
            set => Zufügen(new Vorwahl(vorwahlnummer, value));
        }

        public Verzeichnis() => _verzeichnis = new List<Vorwahl>();

        public void Zufügen(Vorwahl vorwahl)
        {
            if (vorwahl == null)
                throw new ArgumentNullException();

            if (IstVorwahlnummer(vorwahl.Vorwahlnummer))
                throw new ArgumentException();

            _verzeichnis.Add(vorwahl);
        }

        private int InternesSuchen(string vorwahlnummer)
        {
            if (String.IsNullOrWhiteSpace(vorwahlnummer))
                throw new ArgumentNullException();

            for (int i = 0; i < _verzeichnis.Count; i++)
                if (_verzeichnis[i].Vorwahlnummer.Equals(vorwahlnummer))
                    return i;
            return -1;
        }

        public Vorwahl Suchen(string vorwahlnummer)
        {
            int i = InternesSuchen(vorwahlnummer);
            return i < 0 ? throw new KeyNotFoundException() : _verzeichnis[i];
        }

        public bool IstVorwahlnummer(string vorwahlnummer) => InternesSuchen(vorwahlnummer) >= 0;

        IEnumerator IEnumerable.GetEnumerator() => GetEnumerator();

        public IEnumerator<Vorwahl> GetEnumerator() => _verzeichnis.GetEnumerator();
    }
}