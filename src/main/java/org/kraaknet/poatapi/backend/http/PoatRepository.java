package org.kraaknet.poatapi.backend.http;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kraaknet.poatapi.backend.http.model.PowerOfAttorney;
import org.kraaknet.poatapi.backend.http.model.PowerOfAttorneyReference;
import org.kraaknet.poatapi.config.ApplicationConfigProperties;
import org.kraaknet.poatapi.exceptions.ApiException;
import org.kraaknet.poatapi.exceptions.BackendException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;
import java.util.Set;

import static java.lang.String.format;

@Slf4j
@Component
@RequiredArgsConstructor
public class PoatRepository {

    private static final String SERVICE_NAME = "backend";

    @NonNull
    private final RestTemplate restTemplate;

    @NonNull
    private final HttpEntity<String> entity;

    @NonNull
    private final ApplicationConfigProperties applicationConfigProperties;

    @NonNull
    private final HttpClientErrorStatusConverter statusConverter;

    public Set<PowerOfAttorneyReference> getAllPowerOfAttorneyReferences() {
        String url = createBaseUriBuilder()
                .pathSegment("power-of-attorneys")
                .build(true).toUriString();

        ParameterizedTypeReference<Set<PowerOfAttorneyReference>> reference = new ParameterizedTypeReference<>() {
        };

        try {
            ResponseEntity<Set<PowerOfAttorneyReference>> result = restTemplate.exchange(url, HttpMethod.GET, entity, reference);

            log.debug("Result: {}", result);
            return Optional.ofNullable(result.getBody())
                    .orElseThrow(() -> new BackendException("Empty response while requesting the list of power of attorneys."));
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw handleException(e);
        }
    }

    public PowerOfAttorney getPowerOfAttorney(long id) {
        String prefixedId = String.format("%04d", id);
        String url = createBaseUriBuilder()
                .pathSegment("power-of-attorneys")
                .pathSegment(prefixedId)
                .build(true).toUriString();
        try {
            ResponseEntity<PowerOfAttorney> result = restTemplate.exchange(url, HttpMethod.GET, entity, PowerOfAttorney.class);

            log.debug("Result: {}", result);
            return Optional.ofNullable(result.getBody())
                    .orElseThrow(() -> new BackendException(format("Empty response while requesting power of attorney: %s", id)));
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw handleException(e);
        }
    }

    private UriComponentsBuilder createBaseUriBuilder() {
        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(applicationConfigProperties.getBackendHost())
                .port(applicationConfigProperties.getBackendPort());
    }

    private ApiException handleException(HttpStatusCodeException e) {
        log.debug("Exception while talking to {}: {}", SERVICE_NAME, e.getMessage());
        HttpStatus status = e.getStatusCode();
        return statusConverter.convertHttpErrorStatus(status, SERVICE_NAME);
    }
}
