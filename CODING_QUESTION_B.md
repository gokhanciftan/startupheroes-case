# Coding Question (b)

## Weekly Process Automation

To automate the weekly processing of delivered orders, I would use **Apache Airflow** as the workflow orchestration platform.

Since this process represents a recurring batch workflow rather than a simple scheduled task, Airflow provides a reliable and scalable solution for scheduling, monitoring, and managing the execution.

The workflow would be scheduled to run **every Monday at 09:00**.

---

## Workflow

```
Monday 09:00
        │
        ▼
Apache Airflow Scheduler
        │
        ▼
Start Spring Boot Application
        │
        ▼
Retrieve Delivered Orders
        │
        ▼
Map Order → DeliveredOrder
        │
        ▼
Calculate Business Metrics
        │
        ▼
Publish Messages to Kafka
        │
        ▼
Generate Execution Report
        │
        ▼
Workflow Completed
```

---

## Why Apache Airflow?

Apache Airflow is designed to orchestrate data workflows and provides significantly more capabilities than a traditional scheduler.

The main advantages include:

- Centralized workflow orchestration
- Reliable scheduling
- Automatic retry on failures
- Execution history
- Centralized logging
- Monitoring through the Airflow UI
- Easy maintenance and extensibility

Because this application processes data, applies business rules, publishes messages to Kafka, and generates reports, I believe it is more appropriate to treat it as a workflow rather than a simple scheduled task.

---

## Future Extensions

One of the key benefits of Apache Airflow is the ability to extend the workflow as business requirements evolve.

Possible future enhancements include:

- Sending email notifications after successful execution
- Loading processed data into a Data Warehouse
- Triggering downstream analytics pipelines
- Generating weekly dashboards
- Performing data quality validation
- Sending Slack or Microsoft Teams notifications

These tasks can be added to the DAG without changing the overall workflow structure.

---

## Alternative Approaches

Other scheduling solutions could also satisfy the requirement, depending on the deployment environment.

Examples include:

- Spring Scheduler (`@Scheduled`)
- Kubernetes CronJob
- Jenkins Scheduled Pipeline
- Cloud Scheduler services

However, I selected Apache Airflow because it offers scheduling, orchestration, monitoring, logging, and retry capabilities within a single platform, making it well suited for production-grade data workflows.

---

## Benefits

Using Apache Airflow provides the following advantages:

- Automated weekly execution
- Centralized workflow management
- Better observability
- Fault tolerance through retries
- Simplified maintenance
- Easy scalability for future workflow enhancements
- Production-ready orchestration

---

## Summary

Apache Airflow is a natural choice for this use case because it combines workflow orchestration, scheduling, monitoring, and operational management in a single platform.

Rather than treating the application as an isolated scheduled job, Airflow enables it to become part of a scalable and maintainable data processing pipeline.