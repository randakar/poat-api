package org.kraaknet.poatapi.backend.http.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class PowerOfAttorneyReference {

    @NonNull
    private String id;
}

