package com.studi.payment.consumer.dto.request;

import com.studi.payment.consumer.dto.internal.PayerDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PaymentRequestDTO(
        @NotNull @Positive BigDecimal transactionAmount,
        @NotBlank String token,
        @NotBlank String description,
        @NotNull @Positive Integer installments,
        @NotNull String paymentMethodId,
        @Valid PayerDTO payer
) {
}