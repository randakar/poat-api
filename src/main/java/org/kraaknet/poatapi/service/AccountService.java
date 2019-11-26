package org.kraaknet.poatapi.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kraaknet.poatapi.backend.http.AccountRepository;
import org.kraaknet.poatapi.web.dto.AccountDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    @NonNull
    private final AccountRepository repository;

    // TODO: Cache this lookup.
    public Optional<AccountDTO> findAccount(String accountNumber) {
        return repository.findAccount(accountNumber)
                // Filter out inactive ones
                .map(account -> account.getEnded().isPresent() ? null : account)
                .map(account -> AccountDTO.builder()
                        .balance(account.getBalance())
                        .created(account.getCreated())
                        .owner(account.getOwner())
                        .number(accountNumber)
                        .build());
    }

}
