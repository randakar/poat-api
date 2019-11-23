package org.kraaknet.poatapi.web;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * Basic message object for HTTP status code response messages.
 *
 * This could be internationalized by doing message lookups in translation tables,
 * but that is not implemented right now.
 */
@Value
@Builder(toBuilder = true)
public class ErrorMessage {

    @NonNull
    private final String message;
}

