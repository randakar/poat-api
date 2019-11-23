package org.kraaknet.poatapi.service.mappers;

import org.kraaknet.poatapi.backend.http.model.PowerOfAttorneyReference;
import org.kraaknet.poatapi.web.dto.PowerOfAttorneyReferenceDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PowerOfAttorneyReferenceMapper {

    PowerOfAttorneyReferenceDTO sourceToDestination(PowerOfAttorneyReference dto);

    PowerOfAttorneyReference destinationToSource(PowerOfAttorneyReferenceDTO dto);

}
