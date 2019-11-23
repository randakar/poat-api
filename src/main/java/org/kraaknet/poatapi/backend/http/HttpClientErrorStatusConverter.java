package org.kraaknet.poatapi.backend.http;

import lombok.extern.slf4j.Slf4j;
import org.kraaknet.poatapi.exceptions.ApiException;
import org.kraaknet.poatapi.exceptions.AuthorizationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

@Slf4j
@Component
public class HttpClientErrorStatusConverter {

    public ApiException convertHttpErrorStatus(HttpStatus status, String serviceName) {
        log.debug("convertHttpErrorStatus({}, {})", status, serviceName);
        if (HttpStatus.FORBIDDEN.equals(status)) {
            return new AuthorizationException(format("Access forbidden to %s", serviceName));
        } else if (HttpStatus.UNAUTHORIZED.equals(status)) {
            return new AuthorizationException(format("Access denied by %s", serviceName));
        } else if (HttpStatus.BAD_REQUEST.equals(status)) {
            return new ApiException(format("Bad request according to %s", serviceName));
        } else {
            return new ApiException(format("Unable to communicate with %s", serviceName));
        }
    }

}
