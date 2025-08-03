# Spring Boot + Apache Kafka Integration Demo

This is a **demo project** that integrates **Spring Boot** with **Apache Kafka** to expose **REST API endpoints** for performing various Kafka operations.

---

## 🔧 Technologies Used

- Java 17  
- Spring Boot  
- Apache Kafka  

---

## ✅ Prerequisites

Before running the Spring Boot application, make sure the following Kafka components are up and running:

1. **Zookeeper**  
2. **Kafka Server**

---

## 🚀 How to Run

1. Start **Zookeeper**:
   ```bash
  .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties

2. Start **Kafka-server**:
   ```bash
  .\bin\windows\kafka-server-start.bat .\config\server.properties

🧪 Available REST APIs
POST /kafka/topic/create – Create Kafka topic

GET /kafka/topics – Get list of Kafka topics

DELETE /kafka/topic/{topicName} – Delete a Kafka topic

GET /kafka/topic/details/{topicName} – Get topic details

GET /kafka/topic/partitions/{topicName} – Fetch topic partition details

GET /kafka/broker/details – Fetch broker details

PUT /kafka/topic/partitions/increase – Increase partition count for a topic















