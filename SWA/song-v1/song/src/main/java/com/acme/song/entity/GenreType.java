package com.acme.song.entity;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enum für die verschiedenen Musik Genres.
 */
public enum GenreType {

    /**
     * Pop-Musik Genre.
     */
    POP("POP"),

    /**
     * Dance-Pop Genre.
     */
    DANCEPOP("DANCEPOP"),

    /**
     * Punkrock Genre.
     */
    PUNKROCK("PUNKROCK"),

    /**
     * Neue Deutsche Welle Genre.
     */
    NEUEDEUTSCHEWELLE("NEUEDEUTSCHEWELLE"),

    /**
     * Kindermusik Genre.
     */
    KINDERMUSIK("KINDERMUSIK"),

    /**
     * Rap Genre.
     */
    RAP("RAP"),
    /**
     * Disco Genre.
     */
    DISCO("DISCO"),

    /**
     * Folk Genre.
     */
    FOLK("FOLK");
    private final String value;
    GenreType(final String value) {
        this.value = value;
    }

    /**
     * Gibt den String Wert eines Enums an.
     * Dieser Wert wird durch Jackson in einem JSON-Datensatz verwendet.
     *
     * @return String des Enum Typs
     */
    @JsonValue
    @Override
    public String toString() {
        return value;
    }

    /**
     * Konvertierung von String zu enum.
     *
     * @param value Der String, zu dem ein passender Enum-Wert ermittelt werden soll.
     * @return Passender Enum-Wert oder null.
     */
    public static Optional<GenreType> of(final String value) {
        return Stream.of(values())
            .filter(genre -> Objects.equals(genre.toString(), value))
            .findFirst();
    }
}
