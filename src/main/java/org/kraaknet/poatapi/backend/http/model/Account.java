package org.kraaknet.poatapi.backend.http.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.Optional;

@Value
@Builder
@JsonDeserialize(builder = Account.AccountBuilder.class)
public class Account {

    @NonNull
    private String owner;

    @NonNull
    private Integer balance;

    @NonNull
    private String created;

    @NonNull
    @Builder.Default
    private Optional<String> ended = Optional.empty();

    @JsonPOJOBuilder(withPrefix = "")
    public static final class AccountBuilder {
    }
}
