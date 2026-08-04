package com.gokhan.startupheroes.config;

import com.gokhan.startupheroes.dto.DeliveredOrder;
import com.gokhan.startupheroes.producer.OrderProducer;
import com.gokhan.startupheroes.repository.OrderRepository;
import com.gokhan.startupheroes.service.OrderService;
import com.gokhan.startupheroes.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Order(2)
public class StartupRunner implements CommandLineRunner {

    private final OrderService orderService;
    private final OrderProducer orderProducer;
    private final OrderRepository orderRepository;
    private final ReportService reportService;

    @Override
    public void run(String... args) {

        List<DeliveredOrder> deliveredOrders =
                orderService.getLastWeekDeliveredOrders();

        long onTime = deliveredOrders.stream()
                .filter(DeliveredOrder::getOrderInTime)
                .count();

        long late = deliveredOrders.size() - onTime;

        System.out.println("""
                
==========================================
          STARTUP HEROES CASE
==========================================
Total Orders in Database : %d
Orders in Last 7 Days    : %d
On Time Deliveries       : %d
Late Deliveries          : %d
==========================================
"""
                .formatted(
                        orderRepository.count(),
                        deliveredOrders.size(),
                        onTime,
                        late
                ));

        deliveredOrders.forEach(orderProducer::send);

        reportService.generateReport(
                orderRepository.count(),
                onTime,
                late,
                deliveredOrders
        );

        System.out.println("""
                
==========================================
All messages have been sent to Kafka.
Report generated successfully.
==========================================
""");
    }
}