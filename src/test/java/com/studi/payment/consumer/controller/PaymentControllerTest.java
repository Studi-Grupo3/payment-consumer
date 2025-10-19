package com.studi.payment.consumer.controller;

import com.studi.payment.consumer.dto.internal.PreferenceDTO;
import com.studi.payment.consumer.dto.request.PaymentRequestDTO;
import com.studi.payment.consumer.dto.request.QueueMessageRequestDTO;
import com.studi.payment.consumer.service.PaymentService;
import com.studi.payment.consumer.service.PreferenceService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PaymentControllerTest {

    @Test
    void testConsumePayment_callsProcessPayment() throws Exception {
        PaymentService paymentService = Mockito.mock(PaymentService.class);
        PreferenceService preferenceService = Mockito.mock(PreferenceService.class);
        PaymentController controller = new PaymentController(paymentService, preferenceService);

        QueueMessageRequestDTO message = Mockito.mock(QueueMessageRequestDTO.class);
        Mockito.when(message.type()).thenReturn("payment");
        PaymentRequestDTO paymentDto = Mockito.mock(PaymentRequestDTO.class);
        Mockito.when(message.payment()).thenReturn(paymentDto);

        controller.consumePayment(message);

        Mockito.verify(paymentService).processPayment(Mockito.any());
        Mockito.verifyNoInteractions(preferenceService);
    }

    @Test
    void testConsumePayment_callsCreatePreference() throws Exception {
        PaymentService paymentService = Mockito.mock(PaymentService.class);
        PreferenceService preferenceService = Mockito.mock(PreferenceService.class);
        PaymentController controller = new PaymentController(paymentService, preferenceService);

        QueueMessageRequestDTO message = Mockito.mock(QueueMessageRequestDTO.class);
        Mockito.when(message.type()).thenReturn("preference");
        PreferenceDTO preference = Mockito.mock(PreferenceDTO.class);
        Mockito.when(message.preference()).thenReturn(preference);
        Mockito.when(preference.amount()).thenReturn(100.0);
        Mockito.when(preference.payer_email()).thenReturn("email@test.com");

        controller.consumePayment(message);

        Mockito.verify(preferenceService).createPreference(100.0, "email@test.com");
        Mockito.verifyNoInteractions(paymentService);
    }
}