package org.kraaknet.poatapi.web.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public enum CardTypeDTO {

    DEBIT_CARD("DEBIT_CARD"),
    CREDIT_CARD("CREDIT_CARD");

    @NonNull
    private final String value;

}

