# AI-Powered Expense Tracker (Backend)

This repository contains the backend for the AI-Powered Expense Tracker application. It's built on a distributed microservices architecture designed for scalability, resilience, and intelligent data processing.

## 🏛️ Architecture Overview

The backend consists of several independent services that communicate via an API Gateway and an event-driven message bus.

![Architecture Diagram](image_33921b.png)

## 🛠️ Core Services

### Kong (API Gateway)

* The single entry point for all client requests (from the mobile app).
* Manages request routing to the appropriate upstream service.
* Integrates with the Auth Service to authenticate requests before they reach other services.

### Auth Service

* Handles all user authentication and authorization.
* Responsible for issuing, validating, and refreshing JWT (Access and Refresh) tokens.

### User Service

* Manages all user-related data, such as profile information (images, usernames, etc.).
* Provides user data to other services when required.

### ds-service (Data Science Service)

* The "brains" of the operation.
* Receives raw SMS text data from the mobile app.
* Uses LangChain and an LLM to parse the unstructured text and extract structured data (e.g., sender, amount, date).
* Once processed, it produces an event to the Kafka message bus.

### Kafka (Message Broker)

* Acts as the asynchronous communication backbone.
* The `ds-service` produces processed transaction events to a topic.
* This decouples the parsing logic from the storage logic, allowing the system to handle bursts of messages and retry failed processing.

### Expense Service

* The primary data store for all transactions.
* Acts as a "ledger" for all user expenses.
* Consumes processed transaction events from the Kafka topic.
* Stores the final, structured transaction data in its database.
* Exposes endpoints for the mobile app to fetch and display expense history.

## 🔄 Workflow: New SMS Transaction

1.  The Android app detects a new SMS via a `BroadcastReceiver`.
2.  The app sends an API request to the **Kong API Gateway** with the raw SMS content. The request includes an `Authorization` header with the user's Access Token.
3.  **Kong** intercepts the request and validates the Access Token with the **Auth Service**.
4.  Once authenticated, **Kong** forwards the request (now associated with a `userId`) to the **ds-service**.
5.  The **ds-service** uses its LLM/LangChain model to parse the SMS text and extracts the transaction amount and sender.
6.  The **ds-service** produces a new message (e.g., `{ "userId": "...", "amount": 15.75, "sender": "..." }`) to a Kafka topic.
7.  The **Expense Service**, which is subscribed to this topic, consumes the message.
8.  The **Expense Service** saves the new transaction to its database, linking it to the `userId`.
9.  The mobile app can later query the **Expense Service** (via Kong) to display the updated list of expenses.

## 💻 Tech Stack

* **API Gateway:** Kong
* **Service Communication:** REST, gRPC (optional), and Kafka
* **Message Broker:** Apache Kafka
* **AI/NLP:** LangChain, Python, LLMs (e.g., GPT, Llama)
* **Authentication:** JWT (Access/Refresh Tokens)
* **Databases:** (Per-service databases, e.g., PostgreSQL, MongoDB)
* **Containerization:** Docker / Docker Compose

## 🚀 Getting Started

1.  Clone this repository.
2.  Ensure Docker and Docker Compose are installed.
3.  Configure environment variables for each service in their respective `.env` files (database credentials, JWT secrets, Kafka broker addresses).
4.  Run the following command to build and start all services:

    ```bash
    docker-compose up -d --build
    ```
