package com.studi.payment.consumer.dto.internal;
import jakarta.validation.constraints.NotBlank;

public record AddressDTO(
        @NotBlank String streetName,
        @NotBlank String streetNumber,
        @NotBlank String zipCode
) {
}