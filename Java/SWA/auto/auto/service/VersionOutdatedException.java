package com.acme.auto.service;

import lombok.Getter;

/**
 * Exception, falls die Versionsnummer nicht aktuell ist.
 *
 * @author <a href="mailto:A@h-ka.de">A A</a>
 */
@Getter
public class VersionOutdatedException extends RuntimeException {
    private final int version;

    VersionOutdatedException(final int version) {
        super("Die Versionsnummer " + version + " ist veraltet.");
        this.version = version;
    }
}
