package com.studi.payment.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Sample RabbitMQ Producer to test the payment-consumer
 * 
 * This is a simple example to demonstrate how to send messages to RabbitMQ.
 * You can create a separate project with this code to test the consumer.
 * 
 * Dependencies needed in pom.xml:
 * - spring-boot-starter-amqp
 * - jackson-datatype-jsr310
 */
@SpringBootApplication
public class PaymentProducerExample {

    public static void main(String[] args) {
        SpringApplication.run(PaymentProducerExample.class, args);
    }

    @Bean
    public CommandLineRunner runner(RabbitTemplate rabbitTemplate,
                                     @Value("${rabbitmq.exchange.name}") String exchange,
                                     @Value("${rabbitmq.routing.key}") String routingKey) {
        return args -> {
            // Create a sample payment message
            PaymentMessage payment = new PaymentMessage();
            payment.setPaymentId("PAY-" + System.currentTimeMillis());
            payment.setOrderId("ORD-" + System.currentTimeMillis());
            payment.setCustomerId("CUST-12345");
            payment.setAmount(new BigDecimal("99.99"));
            payment.setCurrency("USD");
            payment.setPaymentMethod("CREDIT_CARD");
            payment.setStatus("PENDING");
            payment.setTimestamp(LocalDateTime.now());

            // Send message to RabbitMQ
            rabbitTemplate.convertAndSend(exchange, routingKey, payment);
            
            System.out.println("Sent payment message: " + payment.getPaymentId());
        };
    }

    // Inner class for PaymentMessage (same as in consumer)
    public static class PaymentMessage {
        private String paymentId;
        private String orderId;
        private String customerId;
        private BigDecimal amount;
        private String currency;
        private String paymentMethod;
        private String status;
        private LocalDateTime timestamp;

        // Getters and setters
        public String getPaymentId() { return paymentId; }
        public void setPaymentId(String paymentId) { this.paymentId = paymentId; }
        
        public String getOrderId() { return orderId; }
        public void setOrderId(String orderId) { this.orderId = orderId; }
        
        public String getCustomerId() { return customerId; }
        public void setCustomerId(String customerId) { this.customerId = customerId; }
        
        public BigDecimal getAmount() { return amount; }
        public void setAmount(BigDecimal amount) { this.amount = amount; }
        
        public String getCurrency() { return currency; }
        public void setCurrency(String currency) { this.currency = currency; }
        
        public String getPaymentMethod() { return paymentMethod; }
        public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
        
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        
        public LocalDateTime getTimestamp() { return timestamp; }
        public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    }
}
