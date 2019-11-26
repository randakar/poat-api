package org.kraaknet.poatapi.backend.http.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public enum PeriodUnit {

    PER_DAY("PER_DAY"),
    PER_WEEK("PER_WEEK"),
    PER_MONTH("PER_MONTH");

    @NonNull
    private final String value;

}

