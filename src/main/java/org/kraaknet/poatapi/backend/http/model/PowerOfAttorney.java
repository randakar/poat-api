package org.kraaknet.poatapi.backend.http.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class PowerOfAttorney {

    @NonNull
    private String id;

    @NonNull
    private String grantor;

    @NonNull
    private String grantee;

    @NonNull
    private String account;

    @NonNull
    private Direction direction;

    @NonNull
    private List<Authorization> authorizations;

    @NonNull
    private List<CardReference> cards;

}

