package com.acme.auto.entity;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Enum für TreibstoffartType.
 *
 * @author <a href="mailto:A@h-ka.de">A A</a>
 */
public enum TreibstoffartType {

    /**
     *  Der Enum-Wert BENZIN steht für die Treibstoffart Benzin.
     */
    BENZIN("B"),
    /**
     *  Der Enum-Wert DIESEL steht für die Treibstoffart Diesel.
     */
    DIESEL("D"),
    /**
     *  Der Enum-Wert ELEKTRO steht für die Treibstoffart Elektro.
     */
    ELEKTRO("E");

    private final String value;

    TreibstoffartType(final String value) {
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
    public static Optional<TreibstoffartType> of(final String value) {
        return Stream.of(values())
            .filter(treibstoff -> treibstoff.value.equalsIgnoreCase(value))
            .findFirst();
    }
}
