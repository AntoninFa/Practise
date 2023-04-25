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
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.acme.kunde.rest;

import com.acme.kunde.entity.InteresseType;
import com.jayway.jsonpath.JsonPath;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Base64;
import java.util.List;
import javax.net.ssl.TrustManagerFactory;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.hateoas.mediatype.hal.HalLinkDiscoverer;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.util.UriComponentsBuilder;
import static com.acme.kunde.dev.DevConfig.DEV;
import static com.acme.kunde.entity.Kunde.NACHNAME_PATTERN;
import static com.acme.kunde.rest.KundeGetController.REST_PATH;
import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.file.StandardOpenOption.READ;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowableOfType;
import static org.junit.jupiter.api.condition.JRE.JAVA_19;
import static org.junit.jupiter.api.condition.JRE.JAVA_21;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Tag("integration")
@Tag("rest")
@Tag("rest-get")
@DisplayName("REST-Schnittstelle fuer GET-Requests testen")
@ExtendWith(SoftAssertionsExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles(DEV)
@EnabledForJreRange(min = JAVA_19, max = JAVA_21)
@SuppressWarnings({"WriteTag", "ClassFanOutComplexity", "MissingJavadoc", "MissingJavadocType", "JavadocVariable"})
public class KundeGetRestTest {
    public static final ReactorClientHttpConnector CLIENT_CONNECTOR = getReactorClientHttpConnector();
    public static final String SCHEMA = "https";
    public static final String HOST = "localhost";
    public static final String ADMIN_BASIC_AUTH;
    static final String USER_ADMIN = "admin";
    private static final String PASSWORD = "p";

    private static final String ID_VORHANDEN = "00000000-0000-0000-0000-000000000001";
    private static final String ID_NICHT_VORHANDEN = "ffffffff-ffff-ffff-ffff-ffffffffffff";
    private static final String NACHNAME = "Alpha";

    private static final String NACHNAME_PARAM = "nachname";
    private static final String INTERESSE_PARAM = "interesse";
    private static final String LESEN = "L";
    private static final String REISEN = "R";

    private final String baseUrl;
    private final KundeRepository kundeRepo;

    @InjectSoftAssertions
    private SoftAssertions softly;

    static {
        final String credentialsAdmin = USER_ADMIN + ":" + PASSWORD;
        ADMIN_BASIC_AUTH = "Basic " +
            new String(Base64.getEncoder().encode(credentialsAdmin.getBytes(ISO_8859_1)), ISO_8859_1);
    }

    KundeGetRestTest(@LocalServerPort final int port, final ApplicationContext ctx) {
        final var getController = ctx.getBean(KundeGetController.class);
        assertThat(getController).isNotNull();

        final var uriComponents = UriComponentsBuilder.newInstance()
            .scheme(SCHEMA)
            .host(HOST)
            .port(port)
            .path(REST_PATH)
            .build();
        baseUrl = uriComponents.toUriString();
        final var client = WebClient
            .builder()
            .baseUrl(baseUrl)
            .build();
        final var clientAdapter = WebClientAdapter.forClient(client);
        final var proxyFactory = HttpServiceProxyFactory
            .builder(clientAdapter)
            .build();
        kundeRepo = proxyFactory.createClient(KundeRepository.class);
    }

    @SuppressWarnings({"LocalVariableNamingConvention", "LocalCanBeFinal"})
    private static ReactorClientHttpConnector getReactorClientHttpConnector() {
        final var truststorePath = Path.of("src", "main", "resources", "truststore.p12");

        final SslContext sslContext;
        try (var truststoreInputStream = Files.newInputStream(truststorePath, READ)) {
            final var truststore = KeyStore.getInstance(KeyStore.getDefaultType());
            truststore.load(truststoreInputStream, "zimmermann".toCharArray());
            final var trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(truststore);
            sslContext = SslContextBuilder
                .forClient()
                // alternativ: io.netty.handler.ssl.util.InsecureTrustManagerFactory.INSTANCE
                .trustManager(trustManagerFactory)
                .build();
        } catch (final IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
            throw new IllegalStateException(e);
        }

        final var httpClient = reactor.netty.http.client.HttpClient
            .create()
            .secure(sslContextSpec -> sslContextSpec.sslContext(sslContext));
        return new ReactorClientHttpConnector(httpClient);
    }

    @Test
    @DisplayName("Immer erfolgreich")
    void immerErfolgreich() {
        assertThat(true).isTrue();
    }

    @Test
    @DisplayName("Noch nicht fertig")
    @Disabled
    void nochNichtFertig() {
        //noinspection DataFlowIssue
        assertThat(false).isTrue();
    }

    @Test
    @DisplayName("Suche nach allen Kunden")
    void findAll() {
        // given
        final MultiValueMap<String, String> suchkriterien = new LinkedMultiValueMap<>();

        // when
        final var kunden = kundeRepo.getKunden(suchkriterien, ADMIN_BASIC_AUTH);

        // then
        softly.assertThat(kunden).isNotNull();
        final var embedded = kunden._embedded();
        softly.assertThat(embedded).isNotNull();
        final var embeddedKunden = embedded.kunden();
        softly.assertThat(embeddedKunden)
            .isNotNull()
            .isNotEmpty();
    }

    @ParameterizedTest(name = "[{index}] Suche mit vorhandenem Nachnamen: nachname={0}")
    @ValueSource(strings = NACHNAME)
    @DisplayName("Suche mit vorhandenem Nachnamen")
    void findByNachname(final String nachname) {
        // given
        final MultiValueMap<String, String> suchkriterien = new LinkedMultiValueMap<>();
        suchkriterien.add(NACHNAME_PARAM, nachname);

        // when
        final var kunden = kundeRepo.getKunden(suchkriterien, ADMIN_BASIC_AUTH);

        // then
        softly.assertThat(kunden).isNotNull();
        final var embedded = kunden._embedded();
        softly.assertThat(embedded).isNotNull();
        final var kundenList = embedded.kunden();
        softly.assertThat(kundenList)
            .isNotNull()
            .isNotEmpty();
        kundenList
            .stream()
            .map(KundeDownload::nachname)
            .forEach(nachnameTmp -> softly.assertThat(nachnameTmp).isEqualTo(nachname));
    }

    @ParameterizedTest(name = "[{index}] Suche mit einem Interesse: interesse={0}")
    @ValueSource(strings = {LESEN, REISEN})
    @DisplayName("Suche mit einem Interesse")
    void findByInteresse(final String interesseStr) {
        // given
        final MultiValueMap<String, String> suchkriterien = new LinkedMultiValueMap<>();
        suchkriterien.add(INTERESSE_PARAM, interesseStr);

        // when
        final var kunden = kundeRepo.getKunden(suchkriterien, ADMIN_BASIC_AUTH);

        // then
        assertThat(kunden).isNotNull();
        final var embedded = kunden._embedded();
        softly.assertThat(embedded).isNotNull();
        final var kundenList = embedded.kunden();
        softly.assertThat(kundenList)
            .isNotNull()
            .isNotEmpty();
        @SuppressWarnings("OptionalGetWithoutIsPresent")
        final var interesse = InteresseType.of(interesseStr).get();
        kundenList.forEach(kunde -> {
            final var interessen = kunde.interessen();
            softly.assertThat(interessen)
                .isNotNull()
                .doesNotContainNull()
                .contains(interesse);
        });
    }

    @ParameterizedTest(name = "[{index}] Suche mit mehreren Interessen: interesse1={0}, interesse1={1}")
    @CsvSource(LESEN + ',' + REISEN)
    @DisplayName("Suche mit mehreren Interessen")
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    void findByInteressen(final String interesse1Str, final String interesse2Str) {
        // given
        final MultiValueMap<String, String> suchkriterien = new LinkedMultiValueMap<>();
        suchkriterien.add(INTERESSE_PARAM, interesse1Str);
        suchkriterien.add(INTERESSE_PARAM, interesse2Str);

        // when
        final var kunden = kundeRepo.getKunden(suchkriterien, ADMIN_BASIC_AUTH);

        // then
        assertThat(kunden).isNotNull();
        final var embedded = kunden._embedded();
        softly.assertThat(embedded).isNotNull();
        final var kundenList = embedded.kunden();
        assertThat(kundenList)
            .isNotNull()
            .isNotEmpty();
        final var interesse1 = InteresseType.of(interesse1Str).get();
        final var interesse2 = InteresseType.of(interesse2Str).get();
        final var interessenList = List.of(interesse1, interesse2);
        kundenList.forEach(kunde -> {
            final var interessen = kunde.interessen();
            softly.assertThat(interessen)
                .isNotNull()
                .doesNotContainNull()
                .containsAll(interessenList);
        });
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Nested
    @DisplayName("Suche anhand der ID")
    class FindById {
        @ParameterizedTest(name = "[{index}] Suche mit vorhandener ID und JsonPath: id={0}")
        @ValueSource(strings = ID_VORHANDEN)
        @DisplayName("Suche mit vorhandener ID und JsonPath")
        void findByIdJson(final String id) {
            // given

            // when
            final var response = kundeRepo.getKundeString(id, ADMIN_BASIC_AUTH);

            // then
            final var body = response.getBody();
            assertThat(body).isNotNull().isNotBlank();

            final var nachnamePath = "$.nachname";
            final String nachname = JsonPath.read(body, nachnamePath);
            softly.assertThat(nachname).matches(NACHNAME_PATTERN);

            final var emailPath = "$.email";
            final String email = JsonPath.read(body, emailPath);
            softly.assertThat(email).contains("@");

            final LinkDiscoverer linkDiscoverer = new HalLinkDiscoverer();
            final var selfLink = linkDiscoverer.findLinkWithRel("self", body).get().getHref();
            softly.assertThat(selfLink).isEqualTo(baseUrl + '/' + id);
        }

        @ParameterizedTest(name = "[{index}] Suche mit vorhandener ID: id={0}")
        @ValueSource(strings = ID_VORHANDEN)
        @DisplayName("Suche mit vorhandener ID")
        void findById(final String id) {
            // given

            // when
            final var response = kundeRepo.getKunde(id, ADMIN_BASIC_AUTH);

            // then
            final var kunde = response.getBody();
            assertThat(kunde).isNotNull();
            softly.assertThat(kunde.nachname()).isNotNull();
            softly.assertThat(kunde.email()).isNotNull();
            softly.assertThat(kunde.adresse().plz()).isNotNull();
            softly.assertThat(kunde._links().self().href()).endsWith("/" + id);
        }

        @ParameterizedTest(name = "[{index}] Suche mit syntaktisch ungueltiger oder nicht-vorhandener ID: {0}")
        @ValueSource(strings = ID_NICHT_VORHANDEN)
        @DisplayName("Suche mit syntaktisch ungueltiger oder nicht-vorhandener ID")
        void findByIdNichtVorhanden(final String id) {
            // when
            final var exc = catchThrowableOfType(
                () -> kundeRepo.getKunde(id, ADMIN_BASIC_AUTH),
                WebClientResponseException.NotFound.class
            );

            // then
            assertThat(exc.getStatusCode()).isEqualTo(NOT_FOUND);
        }
    }
}
