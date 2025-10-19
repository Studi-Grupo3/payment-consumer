package com.studi.payment.consumer.dto.internal;

import jakarta.validation.constraints.NotBlank;

public record IdentificationDTO(
        @NotBlank String type,
        @NotBlank String number
) {
}