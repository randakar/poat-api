package org.kraaknet.poatapi.web.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;
import org.kraaknet.poatapi.backend.http.model.CardStatus;

@Getter
@SuperBuilder
public class CardDTO {

    @NonNull
    private final String id;

    @NonNull
    private final CardTypeDTO type;

    @NonNull
    private final CardStatus status;

    @NonNull
    private final Integer cardNumber;

    @NonNull
    private final Integer sequenceNumber;

    @NonNull
    private final String cardHolder;

}
