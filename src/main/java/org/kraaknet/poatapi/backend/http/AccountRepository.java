package org.kraaknet.poatapi.backend.http;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kraaknet.poatapi.backend.http.model.Account;
import org.kraaknet.poatapi.config.ApplicationConfigProperties;
import org.kraaknet.poatapi.exceptions.ApiException;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountRepository {

    private static final String SERVICE_NAME = "account";

    private static final Pattern PATTERN = Pattern.compile("NL23RABO(\\p{Digit}{9})");

    @NonNull
    private final RestTemplate restTemplate;

    @NonNull
    private final HttpEntity<String> entity;

    @NonNull
    private final ApplicationConfigProperties applicationConfigProperties;

    @NonNull
    private final HttpClientErrorStatusConverter statusConverter;

    public Optional<Account> findAccount(String accountNumber) {

        Matcher matcher = PATTERN.matcher(accountNumber);
        if (!matcher.find()) {
            return Optional.empty();
        }
        String id = matcher.group(1);

        String url = createBaseUriBuilder()
                .pathSegment("accounts")
                .pathSegment(id)
                .build(true).toUriString();
        try {
            ResponseEntity<Account> result = restTemplate.exchange(url, HttpMethod.GET, entity, Account.class);

            log.debug("Result: {}", result);
            return Optional.ofNullable(result.getBody());
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
