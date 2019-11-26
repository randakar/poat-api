package org.kraaknet.poatapi.service.mappers;

import org.kraaknet.poatapi.backend.http.model.Direction;
import org.kraaknet.poatapi.web.dto.DirectionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DirectionMapper {

    DirectionDTO toDTO(Direction direction);
}
