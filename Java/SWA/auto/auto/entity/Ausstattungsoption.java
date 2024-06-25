package com.acme.auto.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

import static jakarta.persistence.FetchType.LAZY;

/**
 * Ausstattungsoption für die Anwendungslogik und zum Abspeichern in der DB.
 *

 */
@Entity
@Table(name = "ausstattungsoption")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@SuppressWarnings({"JavadocDeclaration", "RequireEmptyLineBeforeBlockTagGroup", "MissingSummary", "ClassFanOutComplexity", "LineLength"})
public class Ausstattungsoption {
    /**
     * Kleinster Wert für den Preis der Ausstattungsoption.
     */
    public static final long MIN_PREIS = 1L;


    @Id
    @GeneratedValue
    @Column(updatable = false)
    @JsonIgnore
    private UUID id;

    /**
     * Der Name der Ausstattungsoption.
     * @param name Der Name der Ausstattungsoption als String
     * @return Der Name der Ausstattungsoption als String
     */
    @NotNull
    private String name;

    /**
     * Der Preis der Ausstattungsoption.
     * @param preis Der Preis der Ausstattungsoption als BigDecimal
     * @return Der Preis der Ausstattungsoption als BigDecimal
     */
    @Min(MIN_PREIS)
    private BigDecimal preis;

    /**
     * Die Währung des Preises.
     * @param waehrung Die Währung des Preises als Currency.
     * @return Die Währung des Preises als Currency.
     */
    @NotNull
    private Currency waehrung;

    /**
     * Das zugehörige Auto.
     * @param auto Das Auto.
     * @return Das Auto.
     */
    @ManyToOne(optional = false, fetch = LAZY)
    @JoinColumn(updatable = false)
    @JsonIgnore
    @ToString.Exclude
    // NICHT @NotNull, weil beim Anlegen ueber REST der Rueckwaertsverweis noch nicht existiert
    private Auto auto;
}
