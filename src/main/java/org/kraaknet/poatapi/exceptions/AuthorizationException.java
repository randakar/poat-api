package org.kraaknet.poatapi.exceptions;

import lombok.ToString;

@ToString(callSuper = true)
public class AuthorizationException extends ApiException {

    public AuthorizationException(String message) {
        super(message);
    }

}
