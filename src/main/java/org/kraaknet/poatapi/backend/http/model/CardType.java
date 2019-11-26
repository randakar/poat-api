package org.kraaknet.poatapi.backend.http.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public enum CardType {

    DEBIT_CARD("DEBIT_CARD"),
    CREDIT_CARD("CREDIT_CARD");

    @NonNull
    private final String value;

}

