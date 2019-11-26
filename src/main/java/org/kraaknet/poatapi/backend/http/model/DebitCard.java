package org.kraaknet.poatapi.backend.http.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class DebitCard {

    @NonNull
    private String id;

    @NonNull
    private Integer cardNumber;

    @NonNull
    private CardStatus status;

    @NonNull
    private Integer sequenceNumber;

    @NonNull
    private String cardHolder;

    @NonNull
    private Limit atmLimit;

    @NonNull
    private Limit posLimit;

    @NonNull
    private Boolean contactless;
}

