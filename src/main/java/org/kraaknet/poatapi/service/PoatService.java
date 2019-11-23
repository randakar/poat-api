package org.kraaknet.poatapi.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kraaknet.poatapi.backend.http.PoatRepository;
import org.kraaknet.poatapi.backend.http.model.PowerOfAttorneyReference;
import org.kraaknet.poatapi.service.mappers.PowerOfAttorneyReferenceMapper;
import org.kraaknet.poatapi.web.dto.PowerOfAttorneyReferenceDTO;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PoatService {

    @NonNull
    private final PoatRepository repository;

    @NonNull
    private final PowerOfAttorneyReferenceMapper mapper;

    public List<PowerOfAttorneyReferenceDTO> getAllPowerOfAttorneyReferences() {
        Set<PowerOfAttorneyReference> references = repository.getAllPowerOfAttorneyReferences();
        return references.stream()
                .map(mapper::sourceToDestination)
                .collect(Collectors.toList());
    }

}