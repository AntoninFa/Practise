package com.acme.auto.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import static com.acme.auto.entity.Auto.AUSSTATTUNG_GRAPH;
import static jakarta.persistence.EnumType.STRING;

/**
 * Daten eines Autos. In DDD das Auto ist ein Aggregate Root.
 * <img src="../../../../../asciidoc/Auto.svg" alt="Klassendiagramm">
 *
 * @author <a href="mailto:A@h-ka.de">A A</a>
 */
// https://thorben-janssen.com/java-records-hibernate-jpa
@Entity
@Table(name = "auto")
@NamedEntityGraph(name = AUSSTATTUNG_GRAPH, attributeNodes = @NamedAttributeNode("ausstattung"))
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Getter
@Setter
@ToString
@Builder
@SuppressWarnings({
    "ClassFanOutComplexity",
    "RequireEmptyLineBeforeBlockTagGroup",
    "DeclarationOrder",
    "MagicNumber",
    "JavadocDeclaration",
    "MissingSummary",
    "RedundantSuppression"})
public class Auto {
    /**
     * NamedEntityGraph für das Attribut "ausstattung".
     */
    public static final String AUSSTATTUNG_GRAPH = "Auto.ausstattung";

    /**
     * Muster für ein gültige FIN.
     */
    public static final String FIN_PATTERN = "^[A-Z0-9]{17}$";

    /**
     * Kleinster Wert für den Kilometerstand.
     */
    public static final long MIN_KILOMETERSTAND = 0L;

    /**
     * Kleinster Wert für das Baujahr.
     */
    public static final long MIN_BAUJAHR = 1880L;

    /**
     * Kleinster Wert für den Hubraum.
     */
    public static final long MIN_HUBRAUM = 300L;

    /**
     * Kleinster Wert für die Leistung.
     */
    public static final long MIN_LEISTUNG = 1L;

    /**
     * Kleinster Wert für den Grundpreis.
     */
    public static final String MIN_GRUNDPREIS = "1.0";

    /**
     * Die ID des Autos.
     * @param id Die ID.
     * @return Die ID.
     */
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private UUID id;

    /**
     * Versionsnummer für optimistische Synchronisation.
     */
    @Version
    private int version;

    /**
     * Der Hersteller des Autos.
     * @param hersteller Der Hersteller.
     * @return Der Hersteller.
     */
    @NotNull
    private String hersteller;

    /**
     * Die Modellbezeichnung des Autos.
     * @param modellbezeichnung Die Modellbezeichnung.
     * @return Die Modellbezeichnung.
     */
    @NotNull
    private String modellbezeichnung;

    /**
     * Die FIN des Autos.
     * @param fin Die FIN.
     * @return Die FIN.
     */
    @NotNull
    @Pattern(regexp = FIN_PATTERN)
    private String fin;

    /**
     * Der Kilometerstand des Autos.
     * @param kilometerstand Der Kilometerstand.
     * @return Der Kilometerstand.
     */
    @Min(MIN_KILOMETERSTAND)
    private int kilometerstand;

    /**
     * Das Baujahr des Autos.
     * @param baujahr Das Baujahr.
     * @return Das Baujahr.
     */
    @Min(MIN_BAUJAHR)
    private int baujahr;

    /**
     * Der Hubraum des Autos.
     * @param hubraum Der Hubraum.
     * @return Der Hubraum.
     */
    @Min(MIN_HUBRAUM)
    private int hubraum;

    /**
     * Die Leistung des Autos in PS.
     * @param leistung Die Leistung.
     * @return Die Leistung.
     */
    @Min(MIN_LEISTUNG)
    private int leistung;

    /**
     * Die Getriebeart des Autos.
     * @param getriebeart Die Getriebeart.
     * @return Die Getriebeart.
     */
    @Enumerated(STRING)
    private GetriebeType getriebeart;

    /**
     * Die Treibstoffart des Autos.
     * @param treibstoffart Die Treibstoffart.
     * @return Die Treibstoffart.
     */
    @Enumerated(STRING)
    private TreibstoffartType treibstoffart;

    /**
     * Der Fahrzeuggrundpreis des Autos.
     * @param grundpreis Der Fahrzeuggrundpreis des Autos.
     * @return Der Fahrzeuggrundpreis des Autos.
     */
    @DecimalMin(MIN_GRUNDPREIS)
    private BigDecimal grundpreis;

    /**
     * Die Währung des Grundpreises.
     * @param waehrung Die Währung des Grundpreises als Currency.
     * @return Die Währung des Grundpreises als Currency.
     */
    private Currency waehrung;

    /**
     * Die Ausstattung des Autos.
     * @param ausstattung Die Ausstattung.
     * @return Die Ausstattung.
     */
    // Default: fetch=LAZY
    @OneToMany(mappedBy = "auto", cascade = {PERSIST, REMOVE}, orphanRemoval = true)
    @OrderColumn(name = "auc", nullable = false)
    @ToString.Exclude
    private List<Ausstattungsoption> ausstattung;

    @CreationTimestamp
    private LocalDateTime erzeugt;

    @UpdateTimestamp
    private LocalDateTime aktualisiert;

    /**
     * Autodaten überschreiben.
     *
     * @param auto Neue Autodaten.
     */
    public void set(final Auto auto) {
        hersteller = auto.hersteller;
        modellbezeichnung = auto.modellbezeichnung;
        fin = auto.fin;
        kilometerstand = auto.kilometerstand;
        baujahr = auto.baujahr;
        hubraum = auto.hubraum;
        leistung = auto.leistung;
        getriebeart = auto.getriebeart;
        treibstoffart = auto.treibstoffart;
        grundpreis = auto.grundpreis;
        waehrung = auto.waehrung;
    }
}
