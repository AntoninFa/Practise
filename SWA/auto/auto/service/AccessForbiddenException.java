package com.acme.auto.service;
import com.acme.auto.security.Rolle;
import java.util.Collection;
import lombok.Getter;

/**
 * Exception, falls der Zugriff wegen fehlender Rollen nicht erlaubt ist.
 *
 * @author <a href="mailto:A@h-ka.de">A A</a>
 */
@Getter
public class AccessForbiddenException extends RuntimeException {
    /**
     * Vorhandene Rollen.
     */
    private final Collection<Rolle> rollen;

    @SuppressWarnings("ParameterHidesMemberVariable")
    AccessForbiddenException(final Collection<Rolle> rollen) {
        super("Unzureichende Rollen: " + rollen);
        this.rollen = rollen;
    }
}
