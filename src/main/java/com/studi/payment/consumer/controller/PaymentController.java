package com.studi.payment.consumer.controller;

import com.studi.payment.consumer.service.PaymentService;
import com.studi.payment.consumer.service.PreferenceService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.studi.payment.consumer.dto.request.QueueMessageRequestDTO;

@Controller
public class PaymentController {

    private final PaymentService paymentService;
    private final PreferenceService preferenceService;

    @Autowired
    public PaymentController(PaymentService paymentService, PreferenceService preferenceService) {
        this.paymentService = paymentService;
        this.preferenceService = preferenceService;
    }

    @RabbitListener(queues = "payment-queue")
    public void consumePayment(QueueMessageRequestDTO message) throws Exception {
        if ("payment".equals(message.type())) {
            paymentService.processPayment(message.payment());
        } else if ("preference".equals(message.type())) {
            preferenceService.createPreference(
                    message.preference().amount(),
                    message.preference().payer_email()
            );
        }
    }
}