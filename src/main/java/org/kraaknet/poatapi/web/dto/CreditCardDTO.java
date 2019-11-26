package org.kraaknet.poatapi.web.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CreditCardDTO extends CardDTO {

    @NonNull
    private LimitDTO limit;

}

