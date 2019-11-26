package org.kraaknet.poatapi.web.dto;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class LimitDTO {

    @NonNull
    private Integer limit;

    @NonNull
    private PeriodUnitDTO periodUnit;

}

