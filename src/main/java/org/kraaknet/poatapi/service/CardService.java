package org.kraaknet.poatapi.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kraaknet.poatapi.backend.http.CardRepository;
import org.kraaknet.poatapi.backend.http.model.*;
import org.kraaknet.poatapi.service.mappers.CreditCardMapper;
import org.kraaknet.poatapi.service.mappers.DebitCardMapper;
import org.kraaknet.poatapi.web.dto.CardDTO;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardService {

    @NonNull
    private CardRepository repository;

    @NonNull
    private final CreditCardMapper creditCardMapper;

    @NonNull
    private final DebitCardMapper debitCardMapper;

    public List<CardDTO> lookupCards(List<CardReference> cards) {
        return cards.stream()
                .map(this::lookupCard)
                .filter(card -> CardStatus.ACTIVE.equals(card.getStatus()))
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
