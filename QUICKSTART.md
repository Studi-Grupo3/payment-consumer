# Quick Start Guide

## Build and Run

### 1. Build the project
```bash
mvn clean package
```

### 2. Start RabbitMQ with Docker
```bash
docker-compose up -d
```

### 3. Run the application
```bash
# Using Maven
mvn spring-boot:run

# Or using the JAR file
java -jar target/payment-consumer-1.0.0-SNAPSHOT.jar

# Or with development profile
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

## RabbitMQ Management

- Access RabbitMQ Management UI: http://localhost:15672
- Default credentials: guest/guest

## Testing the Consumer

### Using RabbitMQ Management UI

1. Go to http://localhost:15672
2. Navigate to "Queues" tab
3. Click on "payment.queue"
4. Under "Publish message", paste this JSON:

```json
{
  "paymentId": "PAY-12345",
  "orderId": "ORD-67890",
  "customerId": "CUST-11111",
  "amount": 99.99,
  "currency": "USD",
  "paymentMethod": "CREDIT_CARD",
  "status": "PENDING",
  "timestamp": "2025-10-12T19:25:25"
}
```

5. Click "Publish message"
6. Check your application logs to see the message being processed

## Application Endpoints

- Application: http://localhost:8080
- Health Check: http://localhost:8080/actuator/health
- Metrics: http://localhost:8080/actuator/metrics

## Stopping

```bash
# Stop the application with Ctrl+C

# Stop RabbitMQ
docker-compose down
```

## Next Steps

1. Implement your business logic in `PaymentMessageListener.processPayment()`
2. Add database integration (e.g., Spring Data JPA)
3. Add proper error handling and retry logic
4. Add comprehensive tests
5. Configure Dead Letter Queue for failed messages
6. Add monitoring and alerting
