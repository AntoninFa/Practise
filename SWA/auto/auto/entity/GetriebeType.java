package com.acme.auto.entity;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Enum für den GetriebeTyp.
 *
 * @author <a href="mailto:A@h-ka.de">A A</a>
 */
public enum GetriebeType {
    /**
     * Enum-Wert MANUELL steht für manuelle Getriebe.
     */
    MANUELL("M"),

    /**
     *  Der Enum-Wert AUTOMATIK steht für Automatikgetriebe.
     */
    AUTOMATIK("A");

    private final String value;

    GetriebeType(final String value) {
        this.value = value;
    }

    /**
    * Einen enum-Wert als String mit dem internen Wert ausgeben.
    * Dieser Wert wird durch Jackson in einem JSON-Datensatz verwendet.
    * [<a href="https://github.com/FasterXML/jackson-databind/wiki">Wiki-Seiten</a>]
    *
    * @return Der interne Wert.
    */
    @JsonValue
    @Override
    public String toString() {
        return value;
    }

    /**
     * Konvertierung eines Strings in einen Enum-Wert.
     *
     * @param value Der String, zu dem ein passender Enum-Wert ermittelt werden soll.
     * @return Passender Enum-Wert oder null.
     */
    public static Optional<GetriebeType> of(final String value) {
        return Stream.of(values())
            .filter(getriebe -> getriebe.value.equalsIgnoreCase(value))
            .findFirst();
    }
}
