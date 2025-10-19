package com.studi.payment;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
        classes = com.studi.payment.consumer.PaymentConsumerApplication.class,
        properties = "MERCADOPAGO_ACCESS_TOKEN=teste"
)
class PaymentConsumerApplicationTests {
    @Test
    void contextLoads() {
    }
}