package org.kraaknet.poatapi.backend.http.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@Builder
@RequiredArgsConstructor
public class CardReference {

    @NonNull
    private final String id;

    @NonNull
    private final CardType type;
}

