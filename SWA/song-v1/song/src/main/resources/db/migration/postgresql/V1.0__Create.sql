-- Copyright (C) 2022 - present Juergen Zimmermann, Hochschule Karlsruhe
--
-- This program is free software: you can redistribute it and/or modify
-- it under the terms of the GNU General Public License as published by
-- the Free Software Foundation, either version 3 of the License, or
-- (at your option) any later version.
--
-- This program is distributed in the hope that it will be useful,
-- but WITHOUT ANY WARRANTY; without even the implied warranty of
-- MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
-- GNU General Public License for more details.
--
-- You should have received a copy of the GNU General Public License
-- along with this program.  If not, see <https://www.gnu.org/licenses/>.

-- docker compose exec postgres bash
-- psql --dbname=song --username=song [--file=/sql/V1.0__Create.sql]

-- https://www.postgresql.org/docs/current/sql-createtable.html
-- https://www.postgresql.org/docs/current/datatype.html
-- BEACHTE: user ist ein Schluesselwort
CREATE TABLE IF NOT EXISTS login (
             -- https://www.postgresql.org/docs/current/datatype-uuid.html
             -- https://www.postgresql.org/docs/current/ddl-constraints.html#DDL-CONSTRAINTS-PRIMARY-KEYS
             -- impliziter Index fuer Primary Key
    id       uuid PRIMARY KEY USING INDEX TABLESPACE songspace,
    username varchar(20) NOT NULL UNIQUE USING INDEX TABLESPACE songspace,
    password varchar(180) NOT NULL,
    rollen   varchar(32)
) TABLESPACE songspace;

-- https://www.postgresql.org/docs/current/sql-createtype.html
-- https://www.postgresql.org/docs/current/datatype-enum.html
-- CREATE TYPE geschlecht AS ENUM ('MAENNLICH', 'WEIBLICH', 'DIVERS');

CREATE TABLE IF NOT EXISTS song (
    id            uuid PRIMARY KEY USING INDEX TABLESPACE songspace,
                  -- https://www.postgresql.org/docs/current/datatype-numeric.html#DATATYPE-INT
    version       integer NOT NULL DEFAULT 0,
    nachname      varchar(40) NOT NULL,
                  -- impliziter Index als B-Baum durch UNIQUE
                  -- https://www.postgresql.org/docs/current/ddl-constraints.html#DDL-CONSTRAINTS-UNIQUE-CONSTRAINTS
    email         varchar(40) NOT NULL UNIQUE USING INDEX TABLESPACE songspace,
                  -- https://www.postgresql.org/docs/current/ddl-constraints.html#DDL-CONSTRAINTS-CHECK-CONSTRAINTS
    kategorie     integer NOT NULL CHECK (kategorie >= 0 AND kategorie <= 9),
                  -- https://www.postgresql.org/docs/current/datatype-boolean.html
    has_newsletter boolean NOT NULL DEFAULT FALSE,
                  -- https://www.postgresql.org/docs/current/datatype-datetime.html
    geburtsdatum  date CHECK (geburtsdatum < current_date),
    homepage      varchar(40),
                  -- https://www.postgresql.org/docs/current/ddl-constraints.html#id-1.5.4.6.6
                  -- https://www.postgresql.org/docs/current/functions-matching.html#FUNCTIONS-POSIX-REGEXP
    geschlecht    varchar(9) CHECK (geschlecht ~ 'MAENNLICH|WEIBLICH|DIVERS'),
    familienstand varchar(11) CHECK (familienstand ~ 'LEDIG|VERHEIRATET|GESCHIEDEN|VERWITWET'),
    interessen    varchar(32),
    username      varchar(20) NOT NULL UNIQUE USING INDEX TABLESPACE songspace REFERENCES login(username),
                  -- https://www.postgresql.org/docs/current/datatype-datetime.html
    erzeugt       timestamp NOT NULL,
    aktualisiert  timestamp NOT NULL
) TABLESPACE songspace;

-- default: btree
-- https://www.postgresql.org/docs/current/sql-createindex.html
CREATE INDEX IF NOT EXISTS song_nachname_idx ON song(nachname) TABLESPACE songspace;

CREATE TABLE IF NOT EXISTS adresse (
    id        uuid PRIMARY KEY USING INDEX TABLESPACE songspace,
    plz       char(5) NOT NULL CHECK (plz ~ '\d{5}'),
    ort       varchar(40) NOT NULL,
    -- https://www.postgresql.org/docs/current/ddl-constraints.html#DDL-CONSTRAINTS-FK
    song_id  uuid NOT NULL UNIQUE USING INDEX TABLESPACE songspace REFERENCES song
) TABLESPACE songspace;
CREATE INDEX IF NOT EXISTS adresse_plz_idx ON adresse(plz) TABLESPACE songspace;

CREATE TABLE IF NOT EXISTS umsatz (
    id        uuid PRIMARY KEY USING INDEX TABLESPACE songspace,
              -- https://www.postgresql.org/docs/current/datatype-numeric.html#DATATYPE-NUMERIC-DECIMAL
              -- https://www.postgresql.org/docs/current/datatype-money.html
              -- 10 Stellen, davon 2 Nachkommastellen
    betrag    decimal(10,2) NOT NULL,
    waehrung  char(3) NOT NULL CHECK (waehrung ~ '[A-Z]{3}'),
    song_id  uuid NOT NULL REFERENCES song,
    idx       integer NOT NULL DEFAULT 0
) TABLESPACE songspace;
CREATE INDEX IF NOT EXISTS umsatz_song_id_idx ON umsatz(song_id) TABLESPACE songspace;
