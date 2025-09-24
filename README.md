# URL Shortener — Spring Boot + Angular + Keycloak + Traefik + Jenkins

A production-style URL shortener with **anonymous** and **registered** modes:
- **Anonymous**: create and use short links (no account).
- **Registered (Keycloak)**: link expiration, update/delete, click counter, traffic-source stats (facebook / instagram / ads / generic).
- **Infra**: Docker Compose + Traefik reverse proxy + PostgreSQL + Jenkins CI/CD.

---

## Stack

- **Backend**: Java 21, Spring Boot 3, Spring Web, Spring Data JPA, Flyway, OAuth2 Resource Server (JWT).
- **Auth**: Keycloak (self-hosted).
- **DB**: PostgreSQL.
- **Frontend**: Angular (placeholder in this scaffold).
- **Proxy**: Traefik v3.5.
- **CI/CD**: Jenkins (Declarative Pipeline).

---

## Repository Layout

- `backend/` — Spring Boot API, domain, services, Flyway migrations.
- `frontend/` — placeholder (replace with your Angular app).
- `infra/` — `docker-compose.yml`, Traefik labels.
- `jenkins/` — `Jenkinsfile` (build, tests, Docker images, staging deploy).
- `README.md` — this document.

*(No long sentences in tables; prose stays here.)*

---

## Requirements

- Docker + Docker Compose  
- Java 21 (local builds)  
- Node.js 18+ (for Angular)  
- Maven (optional; CI can build it)

---

## Quick Start (infrastructure first)

```bash
cd infra
docker compose build
docker compose up -d

## Service URLs (dev)

- **Traefik dashboard**: [http://localhost:8090](http://localhost:8090)  
- **Keycloak (via Traefik)**: [http://auth.localhost](http://auth.localhost)  
  or direct port: [http://localhost:8091](http://localhost:8091)  
- **Backend API**: [http://api.localhost/api](http://api.localhost/api)  
- **Frontend (placeholder)**: [http://app.localhost](http://app.localhost)  

> 💡 Tip: most OSes resolve `*.localhost` to `127.0.0.1`.  
> If yours does not, add these to your hosts file:
> ```
> 127.0.0.1 auth.localhost
> 127.0.0.1 api.localhost
> 127.0.0.1 app.localhost
> ```

---

## Keycloak Minimal Setup

1. Log in: [http://auth.localhost](http://auth.localhost) (or [http://localhost:8091](http://localhost:8091)) as **admin/admin** (change in compose).  
2. Create **Realm**: `url-shortener`.  
3. Create **Clients**:  
   - `backend` → *confidential*  
   - `frontend` → *public*, Redirect URI: `http://app.localhost/*`  
4. Create **Roles**: `ROLE_USER` (and optional `ROLE_ADMIN`).  
5. Create test users and assign `ROLE_USER`.  

The backend validates JWTs via:
SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI=http://keycloak:8080/realms/url-shortener

*(already wired in `infra/docker-compose.yml`)*

---

## Backend (Spring Boot)

### Build & run locally

```bash
cd backend
mvn clean package
mvn spring-boot:run

Config (overridable via env vars)

DB: SPRING_DATASOURCE_URL, SPRING_DATASOURCE_USERNAME, SPRING_DATASOURCE_PASSWORD

JWT: SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI

App: APP_BASE_URL (e.g., http://api.localhost or http://short.localhost)

Schema (Flyway V1__init.sql)

links: id, short_code (unique), original_url, created_at, expires_at?, owner_id? (UUID), clicks

click_metric: id, link_id, clicked_at, ip_address, user_agent, referer

API Contract (MVP)

POST /api/shorten

Body:

{
  "url": "https://example.com",
  "expires_at": "2025-12-31T23:59:59Z"
}


(expires_at only if authenticated)

Response:

{
  "short_url": "http://api.localhost/r/abc123",
  "original_url": "https://example.com"
}


GET /r/{short_code}

302 → original URL

If expired → 410 Gone

Logs click_metric and increments links.clicks

GET /api/links (auth: ROLE_USER) — list your links.

PUT /api/links/{short_code} (auth+owner) — update original_url / expires_at.

DELETE /api/links/{short_code} (auth+owner) — delete link.

GET /api/stats/{short_code} (auth+owner) —

{
  "clicks": 42,
  "sources": {
    "facebook": 20,
    "instagram": 10,
    "ads": 5,
    "generic": 7
  }
}
Note: the scaffold’s Traefik rule exposes /api/*.
To expose GET /r/{short_code} via Traefik, add a second router rule (e.g., PathPrefix(/r)) or a host like short.localhost.

Frontend (Angular)

Placeholder served by Nginx. To add Angular:

cd frontend
npx @angular/cli new url-shortener-client --routing --style=scss
npm i angular-oauth2-oidc

Implement

Public page → shorten links (no login).

User dashboard → list/edit/delete links.

Charts → traffic sources (Chart.js / ngx-charts).

OAuth2/OIDC with Keycloak → attach Authorization: Bearer <token> to API calls.

Testing
Backend
cd backend
mvn test


Unit tests: JUnit 5 (short code gen, expiry rules).

Integration: Spring Boot Test + Testcontainers (PostgreSQL).

Frontend

Unit: Jasmine/Karma.

e2e: Cypress/Playwright (optional).

CI/CD (Jenkins)

Pipeline at jenkins/Jenkinsfile:

Stages: Checkout → Build Backend → Test Backend → Build Frontend → Build Docker Images → Deploy Staging

Artifacts: Backend JAR, JUnit reports

Jenkins UI (from compose): http://localhost:8088

Agent prerequisites:

Docker daemon access

JDK 21

Node 18

Production Notes

Domains:

short.example.com → frontend

api.short.example.com → backend

auth.short.example.com → Keycloak

TLS: enable Let’s Encrypt (ACME) in Traefik.

Export/import Keycloak realm for reproducibility.

Add monitoring (Prometheus/Grafana) and centralised logging if needed.

Why this order (industry practice)

Infrastructure & security first (Docker, Traefik, Keycloak).

Domain model & migrations (Flyway).

Backend API (business rules, tests).

Frontend UI (consumes stable API).

CI/CD automation (repeatable builds & deploys).

Documentation (this README).

