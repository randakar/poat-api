package org.kraaknet.poatapi.backend.http.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum Authorization {
  
  DEBIT_CARD("DEBIT_CARD"),
  CREDIT_CARD("CREDIT_CARD"),
  VIEW("VIEW"),
  PAYMENT("PAYMENT");

  @NonNull
  private final String value;
  
}

