# payment-consumer

A Spring Boot application that consumes payment messages from RabbitMQ.

## Overview

This is a RabbitMQ consumer application built with Spring Boot that processes payment messages. It demonstrates a basic implementation of message-driven architecture for payment processing.

## Technologies

- Java 17
- Spring Boot 3.2.0
- Spring AMQP (RabbitMQ)
- Maven
- Lombok
- Jackson (JSON processing)

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- RabbitMQ server running on localhost:5672 (or update configuration)

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/studi/payment/consumer/
│   │       ├── PaymentConsumerApplication.java    # Main application class
│   │       ├── config/
│   │       │   └── RabbitMQConfig.java           # RabbitMQ configuration
│   │       ├── listener/
│   │       │   └── PaymentMessageListener.java   # Message consumer
│   │       └── model/
│   │           └── PaymentMessage.java           # Payment DTO
│   └── resources/
│       └── application.properties                 # Application configuration
└── test/
    └── java/
        └── com/studi/payment/consumer/
```

## Configuration

The application can be configured via `src/main/resources/application.properties`:

```properties
# RabbitMQ Configuration
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

# Queue Configuration
rabbitmq.queue.name=payment.queue
rabbitmq.exchange.name=payment.exchange
rabbitmq.routing.key=payment.routing.key
```

## Running the Application

### 1. Start RabbitMQ

Make sure RabbitMQ is running. You can start it using Docker:

```bash
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
```

Access RabbitMQ Management UI at: http://localhost:15672 (guest/guest)

### 2. Build the project

```bash
mvn clean install
```

### 3. Run the application

```bash
mvn spring-boot:run
```

Or run the JAR file:

```bash
java -jar target/payment-consumer-1.0.0-SNAPSHOT.jar
```

## Testing the Consumer

You can test the consumer by sending a message to the RabbitMQ queue. Here's an example message format:

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

You can send messages using:
- RabbitMQ Management UI
- A producer application
- Command-line tools like `rabbitmqadmin`

## Monitoring

The application exposes actuator endpoints for monitoring:

- Health: http://localhost:8080/actuator/health
- Metrics: http://localhost:8080/actuator/metrics

## Development

### Adding Business Logic

Edit `PaymentMessageListener.java` to implement your payment processing logic:

```java
private void processPayment(PaymentMessage paymentMessage) {
    // Add your business logic here
    // - Validate payment
    // - Update database
    // - Call external services
    // - Send notifications
}
```

### Error Handling

The current implementation includes basic error handling. For production use, consider:
- Implementing a Dead Letter Queue (DLQ)
- Adding retry mechanisms
- Implementing circuit breakers
- Adding comprehensive logging and monitoring

## License

This project is part of Studi-Grupo3.
