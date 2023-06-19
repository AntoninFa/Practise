CREATE TABLE IF NOT EXISTS login (
  id       UUID PRIMARY KEY,
  username VARCHAR(20) UNIQUE NOT NULL,
  password VARCHAR(180) NOT NULL,
  rollen   VARCHAR(32)
  );

CREATE TABLE IF NOT EXISTS song (
    id            UUID PRIMARY KEY,
    version       INTEGER NOT NULL DEFAULT 0,
    titel         VARCHAR(40) NOT NULL,
    erscheinungsDatum  DATE CHECK (erscheinungsDatum <= current_date),
    genre         VARCHAR(20) CHECK (genre ~ 'POP|DANCEPOP|PUNKROCK|NEUEDEUTSCHEWELLE|KINDERMUSIK|RAP|FOLK'),
    musikLabel    VARCHAR(32),
    username      VARCHAR(20) NOT NULL UNIQUE REFERENCES login(username),
    erzeugt       TIMESTAMP NOT NULL,
    aktualisiert  TIMESTAMP NOT NULL
  );

CREATE INDEX IF NOT EXISTS song_titel_idx ON song(titel);

CREATE TABLE IF NOT EXISTS duration (
  id              UUID PRIMARY KEY,
  hours           INTEGER NOT NULL CHECK (hours >= 0),
  minutes         INTEGER NOT NULL CHECK (minutes >= 0),
  seconds         INTEGER NOT NULL CHECK (seconds >= 0),
  song_id         UUID NOT NULL UNIQUE REFERENCES song
  );
