package org.kraaknet.poatapi.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kraaknet.poatapi.backend.http.PoatRepository;
import org.kraaknet.poatapi.backend.http.model.PowerOfAttorney;
import org.kraaknet.poatapi.backend.http.model.PowerOfAttorneyReference;
import org.kraaknet.poatapi.service.mappers.AuthorizationMapper;
import org.kraaknet.poatapi.service.mappers.DirectionMapper;
import org.kraaknet.poatapi.service.mappers.PowerOfAttorneyReferenceMapper;
import org.kraaknet.poatapi.web.dto.AccountDTO;
import org.kraaknet.poatapi.web.dto.PowerOfAttorneyDTO;
import org.kraaknet.poatapi.web.dto.PowerOfAttorneyReferenceDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
public class PoatService {

    @NonNull
    private final PoatRepository repository;

    @NonNull
    private final CardService cardService;

    @NonNull
    private final AccountService accountService;

    @NonNull
    private final PowerOfAttorneyReferenceMapper mapper;

    @NonNull
    private final AuthorizationMapper authorizationMapper;

    @NonNull
    private final DirectionMapper directionMapper;


    public List<PowerOfAttorneyReferenceDTO> getAllPowerOfAttorneyReferences() {
        Set<PowerOfAttorneyReference> references = repository.getAllPowerOfAttorneyReferences();
        return references.stream()
                .map(mapper::toDTO)
                .collect(toList());
    }

    public List<PowerOfAttorneyDTO> getPowerOfAttorneyView(String userName) {
        Set<PowerOfAttorneyReference> references = repository.getAllPowerOfAttorneyReferences();
        log.debug("List of references: {}", references);

        List<PowerOfAttorney> powers = references.stream()
                .map(PowerOfAttorneyReference::getId)
                .map(Long::valueOf)
                .map(repository::getPowerOfAttorney)
                .filter(power -> power.userHasAccess(userName))
                .collect(toList());
        log.debug("List of actual powers: {}", powers);

        List<PowerOfAttorneyDTO> result = powers.stream()
                .map(this::createPowerOfAttorney)
                .flatMap(Optional::stream)
                .collect(toList());

        log.debug("Result: {}", result);
        return result;
    }

    private Optional<PowerOfAttorneyDTO> createPowerOfAttorney(PowerOfAttorney power) {
        Optional<AccountDTO> accountOptional = accountService.findAccount(power.getAccount());
        if (accountOptional.isEmpty()) {
            return Optional.empty();
        }

        AccountDTO account = accountOptional.get();
        return Optional.of(PowerOfAttorneyDTO.builder()
                .id(power.getId())
                .grantee(power.getGrantee())
                .grantor(power.getGrantor())
                .account(account)
                .authorizations(authorizationMapper.toDTOs(power.getAuthorizations()))
                .direction(directionMapper.toDTO(power.getDirection()))
                .cards(cardService.lookupCards(power.getCards()))
                .build());
    }


}
