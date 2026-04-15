# 🧩 Saga Microservice Orchestration System

A distributed microservice-based system implementing the **Saga Pattern** using Spring Boot, Feign Clients, and Resilience4j for fault tolerance and consistency management across services.

---

## 🚀 Overview

This project demonstrates a **distributed transaction management system** using the Saga Pattern with orchestration approach.

It coordinates business flow between:
- 🛒 Order Service
- 💳 Payment Service
- 🧠 Saga Service (Orchestrator)

If any step fails, compensating actions are triggered to maintain data consistency.

---

## 🏗️ Architecture

Client
   ↓
Saga Service (Orchestrator)
   ↓
Order Service  ←→  Payment Service
   ↓                 ↓
Database (Saga Transaction Logs)

---

## ⚙️ Tech Stack

- ☕ Java 17+
- 🌱 Spring Boot
- 🔗 Spring Cloud OpenFeign
- 🧠 Resilience4j (CircuitBreaker, Retry)
- 🐘 PostgreSQL / MySQL
- 📦 Maven
- 🐳 Docker (optional)
- 🪵 SLF4J + Logback

---

## 📌 Key Features

- ✔️ Saga Orchestration Pattern
- ✔️ Distributed transaction management
- ✔️ Order & Payment coordination
- ✔️ Compensation (rollback-like behavior)
- ✔️ Circuit Breaker protection
- ✔️ Retry mechanism for resilience
- ✔️ Centralized logging of saga steps
- ✔️ Fault tolerance for microservices

---

## 🔄 Saga Flow

### 1. Order Creation
- Order Service creates order
- Status: `PENDING`

### 2. Payment Processing
- Payment Service processes payment

### 3. Success Flow
- Payment → `CONFIRMED`
- Order → `CONFIRMED`

### 4. Failure Flow
- Payment fails → Order cancelled
- Order fails → Saga aborted

---

## 🧠 Saga States

| Step | Status |
|------|--------|
| ORDER_CREATED | PENDING |
| PAYMENT_SUCCESS | CONFIRMED |
| PAYMENT_FAILED | CANCELLED |
| ORDER_CANCELLED | CANCELLED |

---

## 📡 Services Communication

- REST API communication via Feign Clients
- Synchronous orchestration
- Central Saga Service controls workflow

---

## 🛡️ Fault Tolerance

Implemented using Resilience4j:

- 🔁 Retry → Automatic retry on failure
- 🔥 Circuit Breaker → Prevent cascading failures
- 🧯 Fallback → Graceful degradation

---

## 🧾 Logging

All saga steps are stored in database:

- Order ID
- Current Step
- Status
- Timestamp

Example:

```

ORDER\_PENDING → PAYMENT\_CONFIRMED → ORDER\_CONFIRMED
