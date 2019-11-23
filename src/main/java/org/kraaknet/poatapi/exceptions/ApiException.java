package org.kraaknet.poatapi.exceptions;

import static java.lang.String.format;

/**
 * Base class for all internal exceptions.
 * <p>
 * If this is thrown we
 * - run into a situation that we cannot handle other than providing a HTTP error status code.
 * - Know exactly what the problem is - so not a bug.
 * <p>
 * This class maps to HTTP 500 (Internal Server Error) in the exception handlers.
 * Subclasses of this correspond to different HTTP status codes and may or may not provide addition fields.
 */
public class ApiException extends RuntimeException {

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String toString() {
        Throwable cause = this.getCause();
        if (cause == null) {
            return super.getMessage();
        } else {
            return format("Message: %s, cause: %s", super.getMessage(), cause);
        }
    }
}