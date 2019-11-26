package org.kraaknet.poatapi.web.dto;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class PowerOfAttorneyReferenceDTO {

    @NonNull
    private String id;
}

