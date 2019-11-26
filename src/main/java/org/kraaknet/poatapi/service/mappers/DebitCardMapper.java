package org.kraaknet.poatapi.service.mappers;

import org.kraaknet.poatapi.backend.http.model.DebitCard;
import org.kraaknet.poatapi.web.dto.DebitCardDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DebitCardMapper {

    @Mapping(target = "type", constant = "DEBIT_CARD")
    DebitCardDTO toDTO(DebitCard card);
}
