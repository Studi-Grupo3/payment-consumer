package com.studi.payment.consumer.dto.request;

import com.studi.payment.consumer.dto.internal.PreferenceDTO;

public record QueueMessageRequestDTO(
        String type,
        PaymentRequestDTO payment,
        PreferenceDTO preference
) {}