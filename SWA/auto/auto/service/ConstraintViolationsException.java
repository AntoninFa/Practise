package com.acme.auto.service;

import com.acme.auto.entity.Auto;
import jakarta.validation.ConstraintViolation;
import java.util.Collection;
import lombok.Getter;

/**
 * Exception, wenn mindestens ein Constraint verletzt wurde.
 *
 * @author <a href="mailto:A@h-ka.de">A A</a>
 */
@Getter
public class ConstraintViolationsException extends RuntimeException {
    /**
     * Collection der verletzten Constraints.
     */
    private final Collection<ConstraintViolation<Auto>> violations;

    ConstraintViolationsException(
        @SuppressWarnings("ParameterHidesMemberVariable")
        final Collection<ConstraintViolation<Auto>> violations
    ) {
        super("Constraints sind verletzt");
        this.violations = violations;
    }
}
