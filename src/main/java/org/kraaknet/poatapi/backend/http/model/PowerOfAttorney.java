package org.kraaknet.poatapi.backend.http.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.Collections;
import java.util.List;

@Value
@Builder
@JsonDeserialize(builder = PowerOfAttorney.PowerOfAttorneyBuilder.class)
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
    @Builder.Default
    private List<Authorization> authorizations = Collections.emptyList();

    @NonNull
    @Builder.Default
    private List<CardReference> cards = Collections.emptyList();


    /**
     * Users have access if they are listed as grantee or as grantor
     *
     * @param userName the name of the user
     * @return whether the user has access
     */
    public boolean userHasAccess(String userName) {
        return userName.equalsIgnoreCase(grantee) || userName.equalsIgnoreCase(grantor);
    }


    @JsonPOJOBuilder(withPrefix = "")
    public static final class PowerOfAttorneyBuilder {
    }

}

