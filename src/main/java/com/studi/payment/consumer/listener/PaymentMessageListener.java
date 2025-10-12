package com.studi.payment.consumer.listener;

import com.studi.payment.consumer.model.PaymentMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentMessageListener {

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void consumePaymentMessage(PaymentMessage paymentMessage) {
        log.info("Received payment message: {}", paymentMessage);
        
        try {
            // Process the payment message
            processPayment(paymentMessage);
            
            log.info("Successfully processed payment: {}", paymentMessage.getPaymentId());
        } catch (Exception e) {
            log.error("Error processing payment message: {}", paymentMessage.getPaymentId(), e);
            // Implement your error handling strategy here (e.g., dead letter queue, retry logic)
        }
    }

    private void processPayment(PaymentMessage paymentMessage) {
        // TODO: Implement your business logic here
        // For example:
        // - Validate payment data
        // - Update database
        // - Call external services
        // - Send notifications
        
        log.info("Processing payment for order: {} with amount: {} {}",
                paymentMessage.getOrderId(),
                paymentMessage.getAmount(),
                paymentMessage.getCurrency());
    }
}
