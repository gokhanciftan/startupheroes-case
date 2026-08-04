package com.gokhan.startupheroes.producer;

import com.gokhan.startupheroes.dto.DeliveredOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderProducer {

    @Value("${app.kafka.topic}")
    private String topicName;

    private final KafkaTemplate<String, DeliveredOrder> kafkaTemplate;

    public void send(DeliveredOrder deliveredOrder) {

        kafkaTemplate.send(
                topicName,
                deliveredOrder.getId().toString(),
                deliveredOrder
        ).whenComplete((result, exception) -> {

            if (exception == null) {
                System.out.println(
                        "Message sent to Kafka. Order ID = "
                                + deliveredOrder.getId()
                );
            } else {
                System.err.println(
                        "Failed to send Order ID = "
                                + deliveredOrder.getId()
                );

                exception.printStackTrace();
            }
        });
    }
}