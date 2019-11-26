package org.kraaknet.poatapi.backend.http;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kraaknet.poatapi.backend.http.model.CreditCard;
import org.kraaknet.poatapi.backend.http.model.DebitCard;
import org.kraaknet.poatapi.config.ApplicationConfigProperties;
import org.kraaknet.poatapi.exceptions.ApiException;
import org.kraaknet.poatapi.exceptions.BackendException;
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

import static java.lang.String.format;

@Slf4j
@Component
@RequiredArgsConstructor
public class CardRepository {

    private static final String SERVICE_NAME = "card";

    @NonNull
    private final RestTemplate restTemplate;

    @NonNull
    private final HttpEntity<String> entity;

    @NonNull
    private final ApplicationConfigProperties applicationConfigProperties;

    @NonNull
    private final HttpClientErrorStatusConverter statusConverter;

    public DebitCard getDebitCard(long id) {
        String prefixedId = String.format("%04d", id);
        String url = createBaseUriBuilder()
                .pathSegment("debit-cards")
                .pathSegment(prefixedId)
                .build(true).toUriString();
        try {
            ResponseEntity<DebitCard> result = restTemplate.exchange(url, HttpMethod.GET, entity, DebitCard.class);

            log.debug("Result: {}", result);
            return Optional.ofNullable(result.getBody())
                    .orElseThrow(() -> new BackendException(format("Empty response while requesting power of attorney: %s", id)));
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            throw handleException(e);
        }
    }

    /**
     * Note: Similar enough to getDebitCard that we can probably generify them both into a single method.
     * I'm going to leave that as an exercise for later, if there is time.
     *
     * @param id the id of the card
     * @return the creditcard requested
     */
    public CreditCard getCreditCard(long id) {
        String prefixedId = String.format("%04d", id);
        String url = createBaseUriBuilder()
                .pathSegment("credit-cards")
                .pathSegment(prefixedId)
                .build(true).toUriString();
        try {
            ResponseEntity<CreditCard> result = restTemplate.exchange(url, HttpMethod.GET, entity, CreditCard.class);

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
