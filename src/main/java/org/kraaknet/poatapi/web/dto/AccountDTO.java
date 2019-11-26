package org.kraaknet.poatapi.web.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
@JsonDeserialize(builder = AccountDTO.AccountDTOBuilder.class)
public class AccountDTO {

    @NonNull
    private String number;

    @NonNull
    private String owner;

    @NonNull
    private Integer balance;

    @NonNull
    private String created;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class AccountDTOBuilder {
    }
}
