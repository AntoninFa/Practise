/*
 * Copyright (C) 2022 - present Juergen Zimmermann, Hochschule Karlsruhe
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.acme.song;

import java.util.List;

import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;

import static com.acme.song.rest.SongGetController.REST_PATH;
import static com.acme.song.security.Rolle.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.Customizer.withDefaults;
//import static com.acme.song.rest.SongGetController.NACHNAME_PATH;
//import static com.acme.song.security.Rolle.ACTUATOR;
//import static com.acme.song.security.Rolle.ADMIN;
//import static com.acme.song.security.Rolle.KUNDE;
import static org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder;

/**
 * Security-Konfiguration.
 *
 * @author <a href="mailto:Juergen.Zimmermann@h-ka.de">Jürgen Zimmermann</a>
 */
// https://github.com/spring-projects/spring-security/tree/master/samples
interface SecurityConfig {
    /**
     * Bean-Definition, um den Zugriffsschutz an der REST-Schnittstelle zu konfigurieren.
     *
     * @param http Injiziertes Objekt von HttpSecurity als Ausgangspunkt für die Konfiguration.
     * @return Objekt von SecurityFilterChain
     * @throws Exception Wegen HttpSecurity.authorizeHttpRequests()
     */
    @Bean
    @SuppressWarnings("LambdaBodyLength")
    default SecurityFilterChain securityFilterChainFn(final HttpSecurity http) throws Exception {
        return http
            .authorizeHttpRequests(authorize -> {
                final var restPathKundeId = REST_PATH + "/*";
                authorize

                    // https://spring.io/blog/2020/06/30/url-matching-with-pathpattern-in-spring-mvc
                    // https://docs.spring.io/spring-security/reference/6.0.1/servlet/integrations/mvc.html
                    .requestMatchers(GET, REST_PATH).hasRole(ADMIN.name())
                    //TODO war path um nachname zu finden, könnte bei mir dann irnen Attribut werden
                    //.requestMatchers(GET, REST_PATH + NACHNAME_PATH + "/*").hasRole(ADMIN.name())
                    .requestMatchers(GET, restPathKundeId).hasAnyRole(ADMIN.name(), KUNDE.name())
                    .requestMatchers(PUT, restPathKundeId).hasRole(ADMIN.name())
                    .requestMatchers(PATCH, restPathKundeId).hasRole(ADMIN.name())
                    .requestMatchers(DELETE, restPathKundeId).hasRole(ADMIN.name())

                    .requestMatchers(EndpointRequest.to(HealthEndpoint.class)).permitAll()
                    .requestMatchers(EndpointRequest.toAnyEndpoint()).hasRole(ACTUATOR.name())

                    .requestMatchers(POST, REST_PATH).permitAll()
                    .requestMatchers(POST, "/graphql").permitAll()
                    .requestMatchers(GET, "/v3/api-docs.yaml").permitAll()
                    .requestMatchers(GET, "/v3/api-docs").permitAll()
                    .requestMatchers(GET, "/graphiql").permitAll()
                    .requestMatchers("/h2-console", "/h2-console/*").permitAll()
                    .requestMatchers("/error").permitAll()
                    // TODO richtig, dass ich hier authi anstatt perimitAll() gemacht habe?
                    .anyRequest().authenticated();
            })
            .httpBasic(withDefaults())
            .formLogin(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .headers(headers -> headers.frameOptions(FrameOptionsConfig::sameOrigin))
            .build();
    }

    /**
     * Bean-Definition, um den Verschlüsselungsalgorithmus für Passwörter bereitzustellen. Es wird der
     * Default-Algorithmus von Spring Security verwendet: bcrypt.
     *
     * @return Objekt für die Verschlüsselung von Passwörtern.
     */
    @Bean
    default PasswordEncoder passwordEncoder() {
        return createDelegatingPasswordEncoder();
    }

    /**
     * Bean, um Test-User anzulegen. Dazu gehören jeweils ein Benutzername, ein Passwort und diverse Rollen.
     * Das wird in Beispiel 2 verbessert werden.
     *
     * @param passwordEncoder Injiziertes Objekt zur Passwort-Verschlüsselung
     * @return Ein Objekt, mit dem diese (Test-) User verwaltet werden, z.B. für die künftige Suche.
     */
    @Bean
    default UserDetailsService userDetailsService(final PasswordEncoder passwordEncoder) {
        final var password = passwordEncoder.encode("p");

        final var users = List.of(
            User.withUsername("admin")
                .password(password)
                .roles("ADMIN", "KUNDE", "ACTUATOR")
                .build(),
            User.withUsername("alpha")
                .password(password)
                .roles("KUNDE")
                .build()
        );

        return new InMemoryUserDetailsManager(users);
    }
}
