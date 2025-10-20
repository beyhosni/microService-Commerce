# E-commerce Microservices Skeleton (Java/Spring)

Stack: Spring Boot 3, Java 21, Spring Cloud Gateway, Eureka, Config Server, Spring Kafka, Postgres, Prometheus, Grafana.

## Services
- `eureka-server` – Service discovery
- `config-server` – Centralized configuration (Git-like folder `config-repo`)
- `gateway` – API Gateway
- `catalog-service` – Products (CRUD minimal)
- `order-service` – Orders + Kafka producer (OrderCreated)
- `inventory-service` – Reserves stock (Kafka consumer for OrderCreated)
- `payment-service` – Dummy payment + Kafka producer (PaymentCaptured)

## Quick start (Docker Compose)
```bash
# Build jars (requires Maven + JDK 21)
./mvnw -N io.takari:maven:wrapper
./mvnw -q -DskipTests package

# Start infra + services
docker compose up -d --build
```

Prometheus scrapes `/actuator/prometheus`. Grafana pre-provisioned (admin/admin).
Access:
- Eureka: http://localhost:8761
- Gateway: http://localhost:8080
- Grafana: http://localhost:3000
- Prometheus: http://localhost:9090
- Kafka: localhost:9092
- Postgres: localhost:5432

> NOTE: This is a minimal educational skeleton (not production). 
```



## OAuth2 / Keycloak
- Keycloak démarre sur http://localhost:8085 (admin/admin). Realm `ecommerce` importé, client `gateway`.
- Le Gateway est un client OAuth2 (Authorization Code) avec `TokenRelay` vers les services (resource servers).
- Les services valident les JWT via `issuer-uri: http://keycloak:8080/realms/ecommerce`.

### Login (flux dev)
1. Naviguer sur http://localhost:8080
2. Se connecter via Keycloak ; le token est relayé aux services.

## Resilience4j (order → payment)
- Endpoint: `POST /orders/pay/{orderId}`
- Configuration: `application-resilience.yml` avec `paymentCb` (timeout 3s, fallback "DEFERRED").

## Helm charts
- Chaque service a un chart dans `helm/<service>`.
- Exemple:
  ```bash
  helm install order ./helm/order-service --set image.repository=registry/order-service --set image.tag=1.0.0
  ```

## Argo Rollouts
- Canary: `argo/order-service-canary.yaml` (+ services dans `argo/services.yaml`)
- Blue-Green: `argo/order-service-bluegreen.yaml`
- Appliquer:
  ```bash
  kubectl apply -f argo/services.yaml
  kubectl apply -f argo/order-service-canary.yaml
  # ou
  kubectl apply -f argo/order-service-bluegreen.yaml
  ```

## Frontend Angular
Dossier: `frontend/angular-shop`  
- Dev: `npm install && npm start` (http://localhost:4200) — proxy `/api` → Gateway  
- Docker: service `frontend` exposé sur `http://localhost:4200` (Nginx)  
- Auth via Keycloak (realm `ecommerce`, client `gateway`).

