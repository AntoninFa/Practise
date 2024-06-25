package com.acme.auto;

import com.acme.auto.repository.AutohausRestRepository;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientSsl;
import org.springframework.context.annotation.Bean;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Duration;

/**
 * Beans für die REST-Schnittstelle zu "autohaus" (WebClient) und für die GraphQL-Schnittstelle zu "autohaus"
 * (HttpGraphQlClient).
 *
 * @author <a href="mailto:Juergen.Zimmermann@h-ka.de">Jürgen Zimmermann</a>
 */
interface HttpClientConfig {
    String GRAPHQL_PATH = "/graphql";
    int AUTOHAUS_DEFAULT_PORT = 8080;
    int TIMEOUT_IN_SECONDS = 10;

    @Bean
    default WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    @SuppressWarnings("CallToSystemGetenv")
    default UriComponentsBuilder uriComponentsBuilder() {
        // Umgebungsvariable in Kubernetes
        final var autohausHostEnv = System.getenv("AUTOHAUS_SERVICE_HOST");
        final var autohausPortEnv = System.getenv("AUTOHAUS_SERVICE_PORT");

        @SuppressWarnings("VariableNotUsedInsideIf")
        final var schema = autohausHostEnv == null ? "https" : "http";
        final var autohausHost = autohausHostEnv == null ? "localhost" : autohausHostEnv;
        final int autohausPort = autohausPortEnv == null ? AUTOHAUS_DEFAULT_PORT : Integer.parseInt(autohausPortEnv);

        final var log = LoggerFactory.getLogger(HttpClientConfig.class);
        log.error("autohausHost: {}, autohausPort: {}", autohausHost, autohausPort);

        return UriComponentsBuilder.newInstance()
            .scheme(schema)
            .host(autohausHost)
            .port(autohausPort);
    }

    // siehe org.springframework.web.reactive.function.client.DefaultWebClient
    @Bean
    default WebClient webClient(
        final WebClient.Builder webClientBuilder,
        final UriComponentsBuilder uriComponentsBuilder,
        final WebClientSsl ssl
    ) {
        final var baseUrl = uriComponentsBuilder.build().toUriString();
        return webClientBuilder
            .baseUrl(baseUrl)
            // siehe Property spring.ssl.bundle.jks.microservice
            // https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#features.ssl
            // https://spring.io/blog/2023/06/07/securing-spring-boot-applications-with-ssl
            .apply(ssl.fromBundle("microservice"))
            .build();
    }

    @Bean
    default AutohausRestRepository autohausRestRepository(final WebClient builder) {
        final var clientAdapter = WebClientAdapter.forClient(builder);
        final var proxyFactory = HttpServiceProxyFactory
            .builder(clientAdapter)
            .blockTimeout(Duration.ofSeconds(TIMEOUT_IN_SECONDS))
            .build();
        return proxyFactory.createClient(AutohausRestRepository.class);
    }

    // siehe org.springframework.graphql.client.DefaultHttpGraphQlClientBuilder.DefaultHttpGraphQlClient
    @Bean
    default HttpGraphQlClient graphQlClient(
        final WebClient.Builder webClientBuilder,
        final UriComponentsBuilder uriComponentsBuilder
    ) {
        final var baseUrl = uriComponentsBuilder
            .path(GRAPHQL_PATH)
            .build()
            .toUriString();
        final var webclient = webClientBuilder
            .baseUrl(baseUrl)
            .build();
        return HttpGraphQlClient.builder(webclient).build();
    }
}
