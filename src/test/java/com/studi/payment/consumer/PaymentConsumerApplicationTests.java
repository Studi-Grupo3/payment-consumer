package com.studi.payment.consumer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.rabbitmq.host=localhost",
    "spring.rabbitmq.port=5672"
})
class PaymentConsumerApplicationTests {

    @Test
    void contextLoads() {
        // This test verifies that the Spring application context loads successfully
    }
}
