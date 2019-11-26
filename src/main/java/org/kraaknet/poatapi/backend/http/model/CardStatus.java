package org.kraaknet.poatapi.backend.http.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum CardStatus {

    ACTIVE("ACTIVE"),
    BLOCKED("BLOCKED");

    @NonNull
    private final String value;

}
