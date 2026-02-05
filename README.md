# 🏦 Banking Activity Simulation - Backend API

A high-performance Java-based Banking API built with the **Spark Framework**. This repository serves as the core engine for banking operations, providing a RESTful interface for account management and real-time security notifications.

## 🚀 Backend Key Features
* **RESTful API**: Provides endpoints for Account Creation, Deposits, Withdrawals, and Transfers.
* **Real-time Email Alerts**: Automated low-balance triggers ($1000 limit) that notify users via Gmail SMTP.
* **Fast Data Processing**: Utilizes Gson for high-speed JSON serialization and SparkJava for lightweight request handling.
* **In-Memory Storage**: Uses a thread-safe Concurrent Repository for local data persistence during simulation.

## 🛠️ Tech Stack
* **Language**: Java 21
* **Framework**: Spark Framework (SparkJava)
* **JSON Library**: Google Gson
* **Communication**: Jakarta Mail for SMTP
* **Build Tool**: Maven

## 📂 Project Structure (Maven Standard)
* `src/main/java/com/bank/BankSimulator/ApiServer.java`: Main server entry point and route definitions.
* `.../model/Account.java`: Account data model using BigDecimal for financial accuracy.
* `.../repository/AccountRepository.java`: In-memory data management.
* `.../util/EmailUtil.java`: SMTP utility for sending real-time alerts.

## 💻 How to Run Locally
1. Clone the repository.
2. Ensure Maven is installed and run `mvn clean install` to sync dependencies.
3. Run `ApiServer.java` as a Java Application.
4. The server will start locally on `http://localhost:8080`.# banking-simulator
