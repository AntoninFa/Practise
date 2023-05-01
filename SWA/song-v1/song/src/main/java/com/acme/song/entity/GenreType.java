package com.acme.song.entity;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Enum für die Verschiedenen Musik Genres.
 */
public enum GenreType {

    /**
     * Pop-Musik Genre.
     */
    POP,

    /**
     * Dance-Pop Genre.
     */
    DANCEPOP,


    /**
     * Punkrock Genre.
     */
    PUNKROCK,

    /**
     * Neue Deutsche Welle Genre.
     */
    NEUEDEUTSCHEWELLE,

    /**
     * Kindermusik Genre.
     */
    KINDERMUSIK,

    /**
     * Rap Genre.
     */
    RAP,

    /**
     * Folk Genre.
     */
    FOLK;

    /**
     * Konvertierung von String zu enum.
     * @param value Der String, zu dem ein passender Enum-Wert ermittelt werden soll.
     * @return Passender Enum-Wert oder null.
     */
    public static Optional<GenreType> of(final String value) {
        return Stream.of(values())
            .filter(genre -> Objects.equals(genre, value))
            .findFirst();
    }
}
