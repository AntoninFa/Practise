package com.acme.song.dev;
import org.springframework.context.annotation.Profile;
import static com.acme.song.dev.DevConfig.DEV;

/**
 * Konfigurationsklasse für die Anwendung bzw. den Microservice, falls das Profile dev aktiviert ist.
 */
@Profile(DEV)
@SuppressWarnings({"ClassNamePrefixedWithPackageName", "HideUtilityClassConstructor"})
public class DevConfig implements Flyway {
    /**
     * Konstante für das Spring-Profile "dev".
     */
    public static final String DEV = "dev";
    DevConfig() {
    }
}
