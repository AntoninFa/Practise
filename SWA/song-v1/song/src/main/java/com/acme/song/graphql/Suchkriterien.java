package com.acme.song.graphql;

import com.acme.song.entity.GenreType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

record Suchkriterien (
    String titel,
    List<String> genres
    ) {

    Map<String, List<String>> toMap() {
        final Map<String, List<String>> map = new HashMap<>(2, 1);
        if(titel != null) {
            map.put("titel", List.of(titel));
        }
        if(genres != null) {
            map.put("genre", genres);
        }
        return map;
    }
}
