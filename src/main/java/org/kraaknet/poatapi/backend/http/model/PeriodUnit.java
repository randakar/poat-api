package org.kraaknet.poatapi.backend.http.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public enum PeriodUnit {

    DAY("PER_DAY"),
    WEEK("PER_WEEK"),
    MONTH("PER_MONTH");

    @NonNull
    private final String value;

}

