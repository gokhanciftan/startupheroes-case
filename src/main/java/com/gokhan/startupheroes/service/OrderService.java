package com.gokhan.startupheroes.service;

import com.gokhan.startupheroes.dto.DeliveredOrder;
import com.gokhan.startupheroes.entity.Order;
import com.gokhan.startupheroes.mapper.OrderMapper;
import com.gokhan.startupheroes.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public List<DeliveredOrder> getLastWeekDeliveredOrders() {

        LocalDateTime end = LocalDateTime.now();
        LocalDateTime start = end.minusDays(7);

        List<Order> orders =
                orderRepository.findByCreatedAtBetweenAndDeliveredAtIsNotNull(
                        start,
                        end
                );

        return orders.stream()
                .map(orderMapper::toDeliveredOrder)
                .toList();
    }
}