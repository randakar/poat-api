package org.kraaknet.poatapi.backend.http;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kraaknet.poatapi.backend.http.model.PowerOfAttorneyReference;
import org.kraaknet.poatapi.config.ApplicationConfigProperties;
import org.kraaknet.poatapi.exceptions.BackendException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;
import java.util.Set;

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
        try {
            String url = createBaseUriBuilder()
                    .pathSegment("/power-of-attorneys")
                    .build(true).toUriString();

            ParameterizedTypeReference<Set<PowerOfAttorneyReference>> reference = new ParameterizedTypeReference<>() {
            };

            ResponseEntity<Set<PowerOfAttorneyReference>> result = restTemplate.exchange(url, HttpMethod.GET, entity, reference);

            log.debug("Result: {}", result);
            return Optional.ofNullable(result.getBody())
                    .orElseThrow(() -> new BackendException("Empty response while requesting the list of power of attorneys."));
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.debug("Exception while talking to {}: {}", SERVICE_NAME, e.getMessage());
            HttpStatus status = e.getStatusCode();
            throw statusConverter.convertHttpErrorStatus(status, SERVICE_NAME);
        }
    }

    private UriComponentsBuilder createBaseUriBuilder() {
        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(applicationConfigProperties.getBackendHost())
                .port(applicationConfigProperties.getBackendPort());
    }
}
