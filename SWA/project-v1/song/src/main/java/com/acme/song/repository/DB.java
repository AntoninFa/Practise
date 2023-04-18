package com.acme.song.repository;

import com.acme.song.entity.Song;

import java.util.List;

public class DB {

    /**
     * Liste der Kunden zur Emulation der DB.
     */
    @SuppressWarnings("StaticCollection")
    static final List<Song> SONGS = getSongs();

    private DB() {
    }

    private static List<Song> getSongs() {
        //TODO

    }
}
