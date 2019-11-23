package org.kraaknet.poatapi.exceptions;

import lombok.ToString;

@ToString(callSuper = true)
public class BadRequestException extends ApiException {

    public BadRequestException(String message) {
        super(message);
    }

}
