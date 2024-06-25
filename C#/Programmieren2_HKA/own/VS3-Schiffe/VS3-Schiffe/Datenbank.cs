// Datenbank.cs (zu VS3-Schiffe)

using System;
using System.Data;
using System.Data.OleDb;
using System.IO; // Für den Datenbank zugriff
using System.Collections.Generic; // Für die Listen

namespace Schiffe.Datenbank
{
    // NIch KL Rel :) 
    // Abkürzung, erstellt eine Unveränderliche Klasse mit diesen Angaben(Werden zu 4 Eigenschaften in jeweils einem Feld)
    // Dafür aber ohne plausibilitätsprüfung
    // Transferklasse, sollen wir wohl in der Zulassungsaufgabe aber selber schreiben mit Überprüfung
    
    record Schiff(string Name, string Schiffstyp, int JahrIndienststellung, int JahrAußerdienststellung);

    static class Schiffsdatenbank
    {
        // System.Data.DataTable da soll die Tabelle rein, am Anfang obv. null
        private static DataTable _tabelleSchiffe = null;
        private static List<int> _ids = null;

        public static int AnzahlSchiffe {  get { return _tabelleSchiffe.Rows.Count; } }
        
        public static bool IstGeladen => _ids != null;
        
        public static int[] IDs
        {
            get
            {
                AufGeladenPrüfen();
                // geben wir als Kopie zurück um die Liste zu schützen
                return _ids.ToArray();
            }
        }

        // Laden der Tabelle in den Hauptspeicher
        // Von der Festplatte in den Hauptspeicher
        public static void DatenbankAuslesen(string pfadZurDatenbank)
        {
            if (!File.Exists(pfadZurDatenbank))
                throw new Exception("Datenbank existiert nicht!");

            // schwarze Magie, oledb will wissen welchen Typ von Datenbank wir nehmen
            string connectionString = @"provider=Microsoft.ACE.OLEDB.12.0; data source = " + pfadZurDatenbank;

            // sql Anweisung für die Schiffe
            OleDbDataAdapter adapterSchiffe = new OleDbDataAdapter("Select * from Schiffe", connectionString);

            // Sammlung der Gespiegelten Daten, z.B. bei der HA wichtig, wenn wir mehr als eine Tabelle haben
            DataSet dataSet = new DataSet();

            try
            {
                //Ergebnis der Abgrage wird in dataSet geschrieben, Wir geben dem noch den Namen "Schiffe"
                adapterSchiffe.Fill(dataSet, "Schiffe");
            }
            catch (Exception exception)
            {
                throw new Exception("Laden der Datentabelle fehlgeschlagen!", exception);
            }
            // aus dem dataSet, und die Schiffe Tabelle hatten wir "Schiffe" genannt
            _tabelleSchiffe = dataSet.Tables["Schiffe"];

            
            _ids = new List<int>();
            // gehen wir Zeile für Zeile durch
            foreach (DataRow row in _tabelleSchiffe.Rows)
                // primärschlüssel ist als ganzZahl abgespeichert, müssen aber von obj konvertieren
                _ids.Add(Convert.ToInt32(row["ID"]));
        }

        private static void AufGeladenPrüfen()
        {
            if (!IstGeladen)
                throw new Exception("Datenbank nicht geladen!");
        }

        // Tabellenzeile anhand des Primärschlüssels auslesen
        public static Schiff Schiff(int id)
        {
            AufGeladenPrüfen();

            // Für jede Zeile
            foreach (DataRow row in _tabelleSchiffe.Rows)
                if (id == Convert.ToInt32(row["ID"]))
                {
                    // Erstelen neues Schiff mit Atrributen Name, Schiffstyp, Ja...
                    return new Schiff(row["Name"].ToString(),
                                      row["Schiffstyp"].ToString(),
                                      Convert.ToInt32(row["JahrIndienststellung"]),
                                      Convert.ToInt32(row["JahrAußerdienststellung"]));
                }

            // falls wir in der Ganzen Tabelle kein solches gefunden haben
            throw new Exception("unbekannte ID");
        }
    }
}