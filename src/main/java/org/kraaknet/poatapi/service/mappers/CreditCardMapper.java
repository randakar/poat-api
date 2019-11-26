package org.kraaknet.poatapi.service.mappers;

import org.kraaknet.poatapi.backend.http.model.CreditCard;
import org.kraaknet.poatapi.web.dto.CreditCardDTO;
import org.kraaknet.poatapi.web.dto.LimitDTO;
import org.kraaknet.poatapi.web.dto.PeriodUnitDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CreditCardMapper {

    @Mapping(target = "type", constant = "CREDIT_CARD")
    @Mapping(target = "limit", source = "monthlyLimit", qualifiedByName = "limitConverter")
    CreditCardDTO toDTO(CreditCard card);

    @Named("limitConverter")
    default LimitDTO limitConverter(Integer monthlyLimit) {
        return LimitDTO.builder()
                .limit(monthlyLimit)
                .periodUnit(PeriodUnitDTO.PER_MONTH)
                .build();
    }

}
