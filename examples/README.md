# Example Files

This directory contains example code snippets to help you understand and test the payment-consumer application.

## PaymentProducerExample.java

A simple Spring Boot application that acts as a producer to send payment messages to RabbitMQ.

### How to use:

1. Create a new Spring Boot project
2. Add the following dependencies to your `pom.xml`:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
<dependency>
    <groupId>com.fasterxml.jackson.datatype</groupId>
    <artifactId>jackson-datatype-jsr310</artifactId>
</dependency>
```

3. Copy the `PaymentProducerExample.java` code to your project
4. Add the following to your `application.properties`:

```properties
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

rabbitmq.exchange.name=payment.exchange
rabbitmq.routing.key=payment.routing.key
```

5. Run the producer application
6. Check the payment-consumer logs to see the message being processed

## Alternative Testing Methods

### Using curl with RabbitMQ Management API

```bash
curl -u guest:guest -H "content-type:application/json" \
  -X POST http://localhost:15672/api/exchanges/%2F/payment.exchange/publish \
  -d '{
    "properties":{},
    "routing_key":"payment.routing.key",
    "payload":"{\"paymentId\":\"PAY-12345\",\"orderId\":\"ORD-67890\",\"customerId\":\"CUST-11111\",\"amount\":99.99,\"currency\":\"USD\",\"paymentMethod\":\"CREDIT_CARD\",\"status\":\"PENDING\",\"timestamp\":\"2025-10-12T19:25:25\"}",
    "payload_encoding":"string"
  }'
```

### Using Python

```python
import pika
import json
from datetime import datetime

# Connect to RabbitMQ
connection = pika.BlockingConnection(pika.ConnectionParameters('localhost'))
channel = connection.channel()

# Create message
message = {
    "paymentId": "PAY-12345",
    "orderId": "ORD-67890",
    "customerId": "CUST-11111",
    "amount": 99.99,
    "currency": "USD",
    "paymentMethod": "CREDIT_CARD",
    "status": "PENDING",
    "timestamp": datetime.now().isoformat()
}

# Publish message
channel.basic_publish(
    exchange='payment.exchange',
    routing_key='payment.routing.key',
    body=json.dumps(message)
)

print("Message sent!")
connection.close()
```

### Using Node.js

```javascript
const amqp = require('amqplib');

async function sendMessage() {
    const connection = await amqp.connect('amqp://localhost');
    const channel = await connection.createChannel();
    
    const message = {
        paymentId: "PAY-12345",
        orderId: "ORD-67890",
        customerId: "CUST-11111",
        amount: 99.99,
        currency: "USD",
        paymentMethod: "CREDIT_CARD",
        status: "PENDING",
        timestamp: new Date().toISOString()
    };
    
    channel.publish(
        'payment.exchange',
        'payment.routing.key',
        Buffer.from(JSON.stringify(message))
    );
    
    console.log("Message sent!");
    await channel.close();
    await connection.close();
}

sendMessage();
```
