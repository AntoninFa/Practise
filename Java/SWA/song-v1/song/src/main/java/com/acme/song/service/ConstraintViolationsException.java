package com.acme.song.service;
import com.acme.song.entity.Song;
import jakarta.validation.ConstraintViolation;
import lombok.Getter;
import java.util.Collection;

/**
 * Exception, falls es mindestens ein verletztes Constraint gibt.
 *
 * @author <a href="mailto:Juergen.Zimmermann@h-ka.de">Jürgen Zimmermann</a>
 */
@Getter
public class ConstraintViolationsException extends RuntimeException {
    /**
     * Die verletzten Constraints.
     */
    private final Collection<ConstraintViolation<Song>> violations;
    ConstraintViolationsException(
        @SuppressWarnings("ParameterHidesMemberVariable")
        final Collection<ConstraintViolation<Song>> violations
    ) {
        super("Constraints sind verletzt");
        this.violations = violations;
    }
}
