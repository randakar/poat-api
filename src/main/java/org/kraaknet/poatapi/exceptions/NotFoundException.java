package org.kraaknet.poatapi.exceptions;

import lombok.NonNull;
import lombok.ToString;

@ToString(callSuper = true)
public class NotFoundException extends ApiException {

    @NonNull
    private final String identifier;

    public NotFoundException(String message, String identifier) {
        super(message);
        this.identifier = identifier;
    }
}
