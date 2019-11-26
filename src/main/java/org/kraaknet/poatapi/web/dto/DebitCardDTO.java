package org.kraaknet.poatapi.web.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DebitCardDTO extends CardDTO {

    @NonNull
    private LimitDTO atmLimit;

    @NonNull
    private LimitDTO posLimit;

    @NonNull
    private Boolean contactless;
}

