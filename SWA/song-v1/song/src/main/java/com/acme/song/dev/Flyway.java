package com.acme.song.dev;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;

/**
 * Migrationsstrategie für Flyway im Profile "dev": Tabellen, Indexe etc. löschen und dann neu aufbauen.
 */
interface Flyway {
    /**
     * Migrationsstrategie für Flyway im Profile "dev" bereitstellen.
     *
     * @return FlywayMigrationStrategy.
     */
    @Bean
    default FlywayMigrationStrategy cleanMigrateStrategy() {
        return flyway -> {
            flyway.clean();
            flyway.migrate();
        };
    }
}
