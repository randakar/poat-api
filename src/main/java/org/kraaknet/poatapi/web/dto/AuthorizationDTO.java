package org.kraaknet.poatapi.web.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public enum AuthorizationDTO {

    DEBIT_CARD("DEBIT_CARD"),
    CREDIT_CARD("CREDIT_CARD"),
    VIEW("VIEW"),
    PAYMENT("PAYMENT");

    @NonNull
    private final String value;

}

