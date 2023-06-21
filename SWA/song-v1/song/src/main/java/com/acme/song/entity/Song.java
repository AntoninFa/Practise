package com.acme.song.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.Version;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.UniqueElements;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.FetchType.LAZY;
import static java.util.Collections.emptyList;

/**
 * Daten von einem Song.
 * <img src="../../../../../../../build/docs/asciidoc/Song.svg" alt="Klassendiagramm">
 */
@Entity
@Table(name = "song")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Getter
@Setter
@ToString
@SuppressWarnings("ClassFanOutComplexity")
public class Song {
    /**
     * NamedEntityGraph für das Attribut "duration".
     */
    public static final String DURATION_GRAPH = "Song.duration";
    private static final int MAX_STRING_SIZE = 42;

    /**
     * ID die einen Song eindeutig identifiziert.
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
     * Titel des Songs.
     */
    @NotNull
    @NotBlank
    @Size(max = MAX_STRING_SIZE)
    private String titel;

    /**
     * Datum, an dem der Song offiziell erschienen ist.
     */
    @Past
    private LocalDate erscheinungsDatum;

    /**
     * Liste an Genres des Songs.
     */
    @Transient
    @UniqueElements
    @ToString.Exclude
    private List<GenreType> genre;

    @Column(name = "genre")
    private String genreStr;

    /**
     * Musik-Label, unter welchem der Song erschienen ist.
     */
    @Size(max = MAX_STRING_SIZE)
    private String musikLabel;

    /**
     * Dauer des Songs in Stunden:Minute:Sekunden.
     */
    @OneToOne(mappedBy = "song", cascade = {PERSIST, REMOVE}, fetch = LAZY, orphanRemoval = true)
    @Valid
    @ToString.Exclude
    private SongDuration duration;

    @CreationTimestamp
    private LocalDateTime erzeugt;

    @UpdateTimestamp
    private LocalDateTime aktualisiert;

    /**
     * Daten eines Songs neu setzen.
     *
     * @param song Neue Daten eines Songs.
     */
    public void set(final Song song) {
        this.titel = song.titel;
        this.erscheinungsDatum = song.erscheinungsDatum;
        this.genre = song.genre;
        this.musikLabel = song.musikLabel;
    }

    @PrePersist
    private void buildGenreStr() {
        if (genre == null || genre.isEmpty()) {
            genreStr = null;
        } else {
            genreStr = String.join(",", genre.stream()
                .map(Enum::name)
                .toList());
        }
    }

    @PostLoad
    private void loadGenre() {
        if (genreStr == null) {
            genre = emptyList();
        } else {
            final var genreAsArray = genreStr.split(",");
            genre = Arrays.stream(genreAsArray).map(GenreType::valueOf).collect(Collectors.toList());
        }
    }
}
