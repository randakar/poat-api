package org.kraaknet.poatapi.backend.http.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public enum Direction {

    GIVEN("GIVEN"),
    RECEIVED("RECEIVED");

    @NonNull
    private final String value;

}

