package com.acme.auto;

import java.util.List;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static com.acme.auto.rest.AutoGetController.REST_PATH;
import static com.acme.auto.security.Rolle.ACTUATOR;
import static com.acme.auto.security.Rolle.ADMIN;
import static com.acme.auto.security.Rolle.USER;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;

import static org.springframework.security.config.Customizer.withDefaults;

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
                final var restPathAutoId = REST_PATH + "/*";
                authorize
                    // https://spring.io/blog/2020/06/30/url-matching-with-pathpattern-in-spring-mvc
                    // https://docs.spring.io/spring-security/reference/6.0.1/servlet/integrations/mvc.html
                    .requestMatchers(GET, REST_PATH).permitAll()
                    .requestMatchers(GET, restPathAutoId).permitAll()
                    .requestMatchers(PUT, restPathAutoId).permitAll()

                    .requestMatchers(EndpointRequest.to(HealthEndpoint.class)).permitAll()
                    .requestMatchers(EndpointRequest.toAnyEndpoint()).hasRole(ACTUATOR.name())

                    .requestMatchers(POST, REST_PATH).permitAll()
                    .requestMatchers(POST, "/graphql").permitAll()
                    .requestMatchers(GET, "/v3/api-docs.yaml").permitAll()
                    .requestMatchers(GET, "/v3/api-docs").permitAll()
                    .requestMatchers(GET, "/graphiql").permitAll()
                    .requestMatchers("/h2-console", "/h2-console/*").permitAll()
                    .requestMatchers("/error").permitAll()

                    .anyRequest().permitAll();
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
                .roles(ADMIN.name(), USER.name(), ACTUATOR.name())
                .build(),
            User.withUsername("alpha")
                .password(password)
                .roles(USER.name())
                .build()
        );

        return new InMemoryUserDetailsManager(users);
    }
}
