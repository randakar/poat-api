package org.kraaknet.poatapi.backend.http.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class CreditCard {

    @NonNull
    private String id;

    @NonNull
    private Integer cardNumber;

    @NonNull
    private Integer sequenceNumber;

    @NonNull
    private String cardHolder;

    @NonNull
    private Integer monthlyLimit;

}

