/*
 * Power of attorney
 * This is a sample server power of attorney service
 *
 * OpenAPI spec version: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package org.kraaknet.poatapi.web.dto;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class CreditCardDTO {

  @NonNull
  private String id;

  @NonNull
  private Integer cardNumber;

  @NonNull
  private Integer sequenceNumber;

  @NonNull
  private String cardHolder;

  @NonNull
  private LimitDTO limit;

}

