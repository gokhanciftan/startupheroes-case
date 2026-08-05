# StartupHeroes Case Study

This project is a solution for the StartupHeroes Backend Case Study.

The application retrieves delivered orders created within the last 7 days from PostgreSQL, transforms them into the required DTO format, publishes them to a Kafka topic, and generates a detailed execution report.

---

## Tech Stack

- Java 21
- Spring Boot 3
- Spring Data JPA
- PostgreSQL
- Apache Kafka
- Docker & Docker Compose
- Lombok

---

## Project Structure

```
src/main/java/com/gokhan/startupheroes

├── config
│   ├── DataLoader.java
│   ├── StartupRunner.java
│   └── kafka/
│
├── dto
│   └── DeliveredOrder.java
│
├── entity
│   └── Order.java
│
├── mapper
│   └── OrderMapper.java
│
├── producer
│   └── OrderProducer.java
│
├── repository
│   └── OrderRepository.java
│
├── service
│   ├── OrderService.java
│   └── ReportService.java
│
└── StartupheroesCaseApplication.java
```

---

## Application Flow

```
Application Starts
        │
        ▼
DataLoader
        │
        ▼
PostgreSQL
        │
        ▼
OrderRepository
        │
        ▼
OrderService
        │
        ▼
OrderMapper
        │
        ▼
DeliveredOrder DTO
        │
        ├──────────────► ReportService
        │                       │
        │                       ▼
        │              order-delivery-report.txt
        │
        ▼
Kafka Producer
        │
        ▼
Kafka Topic
```

---

## Implemented Features

- Order entity created using JPA
- PostgreSQL integration
- Dockerized PostgreSQL and Kafka
- Automatic sample data generation
- Retrieve delivered orders created within the last 7 days
- Mapping Order entity to DeliveredOrder DTO
- Lead Time calculation
- Order In Time calculation
- Kafka Producer implementation
- Automatic Kafka topic creation
- Startup execution using StartupRunner
- Report generation after publishing

---

---

## Business Rules

The application follows the business requirements defined in the case study.

### Order Selection

- Only **delivered** orders are processed.
- Only orders **created within the last 7 days** are considered.
- Orders without a `deliveredAt` timestamp are ignored.

### Duration Calculations

**Collection Duration**

Time between:

```
collectionStartedAt
        ↓
collectedAt
```

---

**Delivery Duration**

Time between:

```
deliveryStartedAt
        ↓
deliveredAt
```

---

**Lead Time**

Total elapsed time between:

```
createdAt
        ↓
deliveredAt
```

---

### Order In Time

An order is considered **on time** if:

```
Lead Time <= ETA
```

Otherwise:

```
Lead Time > ETA
```

and the order is marked as **late**.

---

### Kafka Publishing

Each eligible order is transformed into a `DeliveredOrder` DTO and published as an individual JSON message to the following Kafka topic:

```
order_delivery_statistics
```

---

### Report Generation

After all eligible orders are published, the application automatically generates a report under:

```
logs/order-delivery-report.txt
```

The report includes:

- Execution timestamp
- Total number of orders
- Number of processed orders
- On-time deliveries
- Late deliveries
- Details of every published order


---

## Processing Pipeline

```
Application Startup
        │
        ▼
Load Sample Orders (Development Only)
        │
        ▼
Retrieve Delivered Orders (Last 7 Days)
        │
        ▼
Map Order → DeliveredOrder
        │
        ▼
Calculate Lead Time
        │
        ▼
Determine Order In Time
        │
        ▼
Publish to Kafka
        │
        ▼
Generate Execution Report
```

## DeliveredOrder Fields

Each published message contains:

| Field | Description |
|--------|-------------|
| id | Order identifier |
| createdAt | Order creation time |
| lastUpdatedAt | Last update time |
| collectionDuration | Minutes between collection start and collection completion |
| deliveryDuration | Minutes between delivery start and delivery completion |
| eta | Expected delivery time |
| leadTime | Total duration from order creation until delivery |
| orderInTime | Indicates whether Lead Time is less than or equal to ETA |

---

## Kafka

Topic Name

```
order_delivery_statistics
```

Each DeliveredOrder object is published as a JSON message.

---

## Report

After publishing messages to Kafka, the application generates:

```
logs/order-delivery-report.txt
```

The report contains:

- Execution time
- Total order count
- Last 7 days statistics
- On-time deliveries
- Late deliveries
- Details of every published order

---

## Running the Project

### Clone the repository

```bash
git clone https://github.com/gokhanciftan/startupheroes-case.git
```

### Start Docker containers

```bash
cd docker
docker compose up -d
```

### Run the Spring Boot application

```bash
./mvnw spring-boot:run
```

or directly from IntelliJ IDEA.

---

## Docker Services

The project starts the following services:

- PostgreSQL
- Apache Kafka
- Kafka UI

Kafka UI

```
http://localhost:8080
```

Application

```
http://localhost:8081
```

---

## Notes

For demonstration purposes, the application automatically inserts sample orders into PostgreSQL on the first startup.

After processing, only delivered orders created within the last 7 days are published to Kafka.

A detailed execution report is generated under the `logs` directory.