package org.kraaknet.poatapi.web.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public enum DirectionDTO {

    GIVEN("GIVEN"),
    RECEIVED("RECEIVED");

    @NonNull
    private final String value;

}

