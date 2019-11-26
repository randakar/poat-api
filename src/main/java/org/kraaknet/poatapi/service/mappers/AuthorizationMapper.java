package org.kraaknet.poatapi.service.mappers;

import org.kraaknet.poatapi.backend.http.model.Authorization;
import org.kraaknet.poatapi.web.dto.AuthorizationDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorizationMapper {

    List<AuthorizationDTO> toDTOs(List<Authorization> authorization);
}
