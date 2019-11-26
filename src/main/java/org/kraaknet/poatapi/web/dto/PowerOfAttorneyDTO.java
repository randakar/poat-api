package org.kraaknet.poatapi.web.dto;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class PowerOfAttorneyDTO {

    @NonNull
    private String id;

    @NonNull
    private String grantor;

    @NonNull
    private String grantee;

    @NonNull
    private AccountDTO account;

    @NonNull
    private DirectionDTO direction;

    @NonNull
    private List<AuthorizationDTO> authorizations;

    @NonNull
    private List<CardDTO> cards;

}

