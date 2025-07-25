
# 📘 Product Requirements Document (PRD)
## Smart eCommerce Platform (Microservices Edition)

---

## 🧩 1. Objective

Design a distributed, scalable, secure, and observable microservices-based eCommerce platform that covers all fundamental and advanced microservices architecture principles. This platform should support customers, sellers, product catalog, orders, payments, reviews, notifications, and monitoring.

---

## 🗺️ 2. High-Level Architecture

```plaintext
                            +--------------------+
                            |    React UI (SPA)  |
                            +----------+---------+
                                       |
                                       ↓
                         +-------------+-------------+
                         |       API Gateway         |
                         |  (Spring Cloud Gateway)   |
                         +-------------+-------------+
                                       |
      ----------------------------------------------------------------
      |        |          |         |         |         |           |
      ↓        ↓          ↓         ↓         ↓         ↓           ↓
+------------+ +------------+ +-------------+ +-------------+ +--------------+
| Auth Svc   | | User Svc   | | Product Svc | | Order Svc   | | Payment Svc  |
+------------+ +------------+ +-------------+ +-------------+ +--------------+
      ↓                     ↓                     ↓              ↓
+---------------------+ +----------------+ +----------------+ +------------------+
| Inventory Service   | | Review Service | | Shipping Svc   | | Notification Svc |
+---------------------+ +----------------+ +----------------+ +------------------+

  ↕                                ↕                          ↕
 Kafka / RabbitMQ            PostgreSQL / Redis          MongoDB / MySQL

  ↘                                ↓                          ↙
         +----------------------+    +----------------------+
         |   Config Server      |    | Service Registry     |
         | (Spring Cloud Config)|    |   (Eureka/Nacos)     |
         +----------------------+    +----------------------+

                 ↘
               Monitoring
        (OpenTelemetry, Grafana,
         Prometheus, Zipkin)

```

---

## 🧱 3. Microservices Overview

| Service            | Responsibility                                      | Protocols     |
|--------------------|------------------------------------------------------|---------------|
| **API Gateway**     | Routing, rate limiting, CORS, SSL                   | HTTP, HTTPS   |
| **Auth Service**    | OAuth2/OIDC, JWT, Keycloak integration              | HTTP, HTTPS   |
| **User Service**    | Manage customer/admin accounts and roles            | REST          |
| **Product Service** | Catalog CRUD, filters, categories                   | REST          |
| **Order Service**   | Place, cancel, update orders                        | REST, Events  |
| **Payment Service** | Integrate with mock Stripe/Razorpay                 | REST, Events  |
| **Shipping Service**| Manage shipment, status updates                     | REST          |
| **Review Service**  | Ratings and customer reviews                        | REST          |
| **Notification Svc**| Send SMS/Email/Push based on events                 | REST + Kafka  |
| **Inventory Svc**   | Stock control, reservations                         | REST + Events |
| **Config Server**   | Central config management                           | Spring Cloud  |
| **Service Registry**| Service discovery                                   | Eureka/Nacos  |
| **Monitoring Stack**| Metrics, tracing, alerting                          | Prom/Grafana  |

---

## 🔒 4. Cross-Cutting Concerns

- **Authentication**: JWT, OAuth2, Role-based Access
- **Authorization**: Admin/User/Seller policies
- **Rate Limiting**: Per IP, User (Bucket4j + Redis)
- **Resilience**: Retry, Circuit Breaker, Timeout (Resilience4j)
- **Saga Pattern**: Order-Payment-Inventory via Orchestration & Kafka
- **Data Consistency**: Eventual + Strong where needed (Transactional Outbox)
- **Monitoring**: Grafana + Prometheus + Zipkin
- **Logging**: ELK / Loki + Centralized Logging
- **Testing**: Unit, Integration, Contract (Pact), e2e

---

## ⚙️ 5. Tech Stack

| Layer            | Technology                  |
|------------------|-----------------------------|
| Gateway          | Spring Cloud Gateway        |
| Services         | Spring Boot + WebFlux       |
| Auth             | Keycloak + OAuth2/JWT       |
| Messaging        | Kafka / RabbitMQ            |
| Storage          | PostgreSQL, MongoDB, Redis  |
| Config Mgmt      | Spring Cloud Config         |
| Service Registry | Eureka / Nacos              |
| Monitoring       | OpenTelemetry + Prometheus + Grafana |
| Containerization | Docker + Docker Compose     |
| CI/CD            | GitHub Actions / Jenkins    |
| Orchestration    | Kubernetes (Optional Phase 2)|

---

## 📦 6. Project Structure

```
smart-ecommerce/
├── api-gateway/
├── auth-service/
├── config-server/
├── service-registry/
├── user-service/
├── product-service/
├── order-service/
├── payment-service/
├── shipping-service/
├── inventory-service/
├── review-service/
├── notification-service/
├── monitoring/
│   ├── prometheus/
│   ├── grafana/
│   ├── zipkin/
├── docker-compose/
├── common-lib/          # Shared DTOs, exception handling, interceptors
└── docs/
    └── smart-ecommerce-prd.md
```

---

## 📈 7. MVP Functional Scope

- [x] Register/Login user (JWT)
- [x] Add/View Products
- [x] Place Orders (Saga Pattern)
- [x] Payment flow via Kafka
- [x] Notification on Order Success
- [x] Dashboard with real-time metrics
- [x] Circuit Breakers on Shipping Svc
- [x] Monitoring and Tracing dashboard

---

## 🚀 8. Deployment Strategy

- Phase 1: Docker Compose (local setup)
- Phase 2: CI/CD + Kubernetes Helm Charts
- Phase 3: Chaos Testing with Gremlin or custom failures

---

## 📚 9. Learning Goals Achieved

| Concept                 | Covered In                              |
|--------------------------|-----------------------------------------|
| Gateway, Reverse Proxy   | Spring Cloud Gateway                    |
| Load Balancer            | Eureka/Ribbon + NGINX                   |
| Auth & Authorization     | Keycloak + JWT                          |
| Observability            | OpenTelemetry + Grafana + Zipkin        |
| Resilience               | Retry, Circuit Breaker, Timeout         |
| Data Consistency         | Kafka + Saga + CQRS                     |
| Testing Strategy         | Unit, Integration, Contract, e2e        |
| Event-driven Architecture| Kafka Pub/Sub                           |
| Config/Secrets Mgmt      | Spring Cloud Config + Vault             |

---

## 📌 10. Future Enhancements

- Admin UI (React + Spring Boot)
- Product recommendation engine
- AI chatbot with Gemini or OpenAI
- Multi-tenancy
- Service Mesh (Istio)

---
