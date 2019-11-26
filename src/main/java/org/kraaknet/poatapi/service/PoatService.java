package org.kraaknet.poatapi.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kraaknet.poatapi.backend.http.PoatRepository;
import org.kraaknet.poatapi.backend.http.model.*;
import org.kraaknet.poatapi.service.mappers.*;
import org.kraaknet.poatapi.web.dto.CardDTO;
import org.kraaknet.poatapi.web.dto.PowerOfAttorneyDTO;
import org.kraaknet.poatapi.web.dto.PowerOfAttorneyReferenceDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
public class PoatService {

    @NonNull
    private final PoatRepository repository;

    @NonNull
    private final PowerOfAttorneyReferenceMapper mapper;

    @NonNull
    private final AuthorizationMapper authorizationMapper;

    @NonNull
    private final DirectionMapper directionMapper;

    @NonNull
    private final CreditCardMapper creditCardMapper;

    @NonNull
    private final DebitCardMapper debitCardMapper;

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
                .map(power -> PowerOfAttorneyDTO.builder()
                        .id(power.getId())
                        .grantee(power.getGrantee())
                        .grantor(power.getGrantor())
                        .account(power.getAccount())
                        .authorizations(authorizationMapper.toDTOs(power.getAuthorizations()))
                        .direction(directionMapper.toDTO(power.getDirection()))
                        .cards(lookupCards(power.getCards()))
                        .build())
                .collect(toList());

        log.debug("Result: {}", result);
        return result;
    }

    private List<CardDTO> lookupCards(List<CardReference> cards) {
        return cards.stream()
                .map(this::lookupCard)
                .collect(toList());
    }

    private CardDTO lookupCard(CardReference cardReference) {
        CardType type = cardReference.getType();
        long id = Long.parseLong(cardReference.getId());

        // This would be an ideal place for a switch expression.
        if (type == CardType.CREDIT_CARD) {
            CreditCard creditCard = repository.getCreditCard(id);
            return creditCardMapper.toDTO(creditCard);
        } else if (type == CardType.DEBIT_CARD) {
            DebitCard debitCard = repository.getDebitCard(id);
            return debitCardMapper.toDTO(debitCard);
        }
        throw new IllegalStateException("Unknown card type");
    }

}
