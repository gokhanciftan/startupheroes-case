package com.gokhan.startupheroes.config.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.apache.kafka.clients.admin.NewTopic;

@Configuration
public class KafkaTopicConfig {

    @Value("${app.kafka.topic}")
    private String topicName;

    @Bean
    public NewTopic orderDeliveryStatisticsTopic() {

        return TopicBuilder.name(topicName)
                .partitions(3)
                .replicas(1)
                .build();
    }

}