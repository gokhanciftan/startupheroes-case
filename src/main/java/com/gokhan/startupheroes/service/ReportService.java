package com.gokhan.startupheroes.service;

import com.gokhan.startupheroes.dto.DeliveredOrder;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ReportService {

    private static final Path REPORT_PATH =
            Path.of("logs", "order-delivery-report.txt");

    public void generateReport(
            long totalOrders,
            long onTime,
            long late,
            List<DeliveredOrder> deliveredOrders
    ) {

        try {

            Files.createDirectories(REPORT_PATH.getParent());

            try (BufferedWriter writer = Files.newBufferedWriter(REPORT_PATH)) {

                writer.write("""
==========================================
          STARTUP HEROES CASE
==========================================
""");

                writer.newLine();

                writer.write("Generated At             : "
                        + LocalDateTime.now().format(
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

                writer.newLine();
                writer.newLine();

                writer.write("Total Orders in Database : " + totalOrders);
                writer.newLine();

                writer.write("Orders in Last 7 Days    : " + deliveredOrders.size());
                writer.newLine();

                writer.write("On Time Deliveries       : " + onTime);
                writer.newLine();

                writer.write("Late Deliveries          : " + late);
                writer.newLine();

                writer.newLine();

                writer.write("""
==========================================
ORDER DETAILS
==========================================
""");

                writer.newLine();

                for (DeliveredOrder order : deliveredOrders) {

                    writer.write("Order ID            : " + order.getId());
                    writer.newLine();

                    writer.write("Created At          : " + order.getCreatedAt());
                    writer.newLine();

                    writer.write("Collection Duration : " + order.getCollectionDuration());
                    writer.newLine();

                    writer.write("Delivery Duration   : " + order.getDeliveryDuration());
                    writer.newLine();

                    writer.write("Lead Time           : " + order.getLeadTime());
                    writer.newLine();

                    writer.write("ETA                 : " + order.getEta());
                    writer.newLine();

                    writer.write("Order In Time       : " + order.getOrderInTime());
                    writer.newLine();

                    writer.write("------------------------------------------");
                    writer.newLine();
                    writer.newLine();
                }

                writer.write("""
==========================================
END OF REPORT
==========================================
""");
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to generate report.", e);
        }
    }
}