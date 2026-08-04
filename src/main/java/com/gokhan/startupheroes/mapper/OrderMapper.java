package com.gokhan.startupheroes.mapper;

import com.gokhan.startupheroes.dto.DeliveredOrder;
import com.gokhan.startupheroes.entity.Order;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class OrderMapper {

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public DeliveredOrder toDeliveredOrder(Order order) {

        int collectionDuration = calculateMinutes(
                order.getCollectionStartedAt(),
                order.getCollectedAt()
        );

        int deliveryDuration = calculateMinutes(
                order.getDeliveryStartedAt(),
                order.getDeliveredAt()
        );

        int leadTime = calculateMinutes(
                order.getCreatedAt(),
                order.getDeliveredAt()
        );

        return DeliveredOrder.builder()
                .id(order.getId())
                .createdAt(formatDateTime(order.getCreatedAt()))
                .lastUpdatedAt(formatDateTime(order.getLastUpdatedAt()))
                .collectionDuration(collectionDuration)
                .deliveryDuration(deliveryDuration)
                .eta(order.getEta())
                .leadTime(leadTime)
                .orderInTime(leadTime <= order.getEta())
                .build();
    }

    private int calculateMinutes(LocalDateTime start, LocalDateTime end) {

        if (start == null || end == null) {
            return 0;
        }

        return (int) Duration.between(start, end).toMinutes();
    }

    private String formatDateTime(LocalDateTime dateTime) {

        if (dateTime == null) {
            return null;
        }

        return dateTime.format(DATE_TIME_FORMATTER);
    }
}