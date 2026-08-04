package com.gokhan.startupheroes.config;

import com.gokhan.startupheroes.entity.Order;
import com.gokhan.startupheroes.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@org.springframework.core.annotation.Order(1)
public class DataLoader implements CommandLineRunner {

    private final OrderRepository orderRepository;

    @Override
    public void run(String... args) {

        if (orderRepository.count() > 0) {
            return;
        }

        LocalDateTime now = LocalDateTime.now();

        List<Order> orders = new ArrayList<>();

// LAST 7 DAYS - ON TIME
// =====================

        orders.add(createDeliveredOrder(now.minusDays(1), 10, 15, 40, 1001L));
        orders.add(createDeliveredOrder(now.minusDays(1).minusHours(3), 12, 18, 45, 1002L));
        orders.add(createDeliveredOrder(now.minusDays(2), 15, 15, 45, 1003L));
        orders.add(createDeliveredOrder(now.minusDays(2).minusHours(4), 10, 10, 35, 1004L));
        orders.add(createDeliveredOrder(now.minusDays(3), 15, 15, 45, 1005L));


// =====================
// LAST 7 DAYS - LATE
// =====================

        orders.add(createDeliveredOrder(now.minusDays(3).minusHours(2), 20, 30, 30, 1006L));
        orders.add(createDeliveredOrder(now.minusDays(4), 15, 40, 35, 1007L));
        orders.add(createDeliveredOrder(now.minusDays(5), 25, 35, 40, 1008L));
        orders.add(createDeliveredOrder(now.minusDays(6), 15, 45, 35, 1009L));
        orders.add(createDeliveredOrder(now.minusDays(6).minusHours(23), 20, 40, 45, 1010L));


// =====================
// LAST 7 DAYS - NOT DELIVERED
// =====================

        orders.add(createUndeliveredOrder(now.minusDays(1).minusHours(5), 30, 1011L));
        orders.add(createUndeliveredOrder(now.minusDays(4).minusHours(2), 20, 1012L));
        orders.add(createUndeliveredOrder(now.minusDays(6).minusHours(1), 25, 1013L));


// =====================
// OLDER THAN 7 DAYS
// SHOULD NOT BE RETURNED
// =====================

        orders.add(createDeliveredOrder(now.minusDays(8), 15, 20, 45, 1014L));
        orders.add(createDeliveredOrder(now.minusDays(9), 10, 20, 40, 1015L));
        orders.add(createDeliveredOrder(now.minusDays(10), 15, 15, 40, 1016L));
        orders.add(createDeliveredOrder(now.minusDays(11), 20, 20, 45, 1017L));
        orders.add(createDeliveredOrder(now.minusDays(12), 15, 30, 50, 1018L));


// =====================
// OLDER THAN 7 DAYS
// NOT DELIVERED
// =====================

        orders.add(createUndeliveredOrder(now.minusDays(8), 25, 1019L));
        orders.add(createUndeliveredOrder(now.minusDays(14), 30, 1020L));


        orderRepository.saveAll(orders);

        System.out.println("Sample orders inserted: " + orders.size());
    }
    private Order createDeliveredOrder(
            LocalDateTime createdAt,
            int collectionDuration,
            int deliveryDuration,
            int eta,
            long customerId
    ) {

        LocalDateTime collectionStartedAt = createdAt.plusMinutes(10);

        LocalDateTime collectedAt = collectionStartedAt.plusMinutes(collectionDuration);

        LocalDateTime deliveryStartedAt = collectedAt.plusMinutes(5);

        LocalDateTime deliveredAt = deliveryStartedAt.plusMinutes(deliveryDuration);

        return Order.builder()
                .createdAt(createdAt)
                .collectionStartedAt(collectionStartedAt)
                .collectedAt(collectedAt)
                .deliveryStartedAt(deliveryStartedAt)
                .deliveredAt(deliveredAt)
                .lastUpdatedAt(deliveredAt)
                .eta(eta)
                .customerId(customerId)
                .build();
    }

    private Order createUndeliveredOrder(
            LocalDateTime createdAt,
            int eta,
            long customerId
    ) {

        return Order.builder()
                .createdAt(createdAt)
                .lastUpdatedAt(createdAt.plusMinutes(5))
                .eta(eta)
                .customerId(customerId)
                .build();
    }}