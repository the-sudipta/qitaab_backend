# Qitaab Backend — Book selling API (Spring Boot)

**Status:** In progress • Java 17 • Spring Boot • JPA/Hibernate • REST • (Oracle PL/SQL planned)

## What it does
Minimal library API: manage **books, members, loans**. Clean layered design (controller–service–repository).

## Run locally
1. Java 17, Maven.  
2. `cp src/main/resources/application-example.properties src/main/resources/application.properties`  
3. `mvn spring-boot:run`

## Endpoints (current)
- `GET /health` → app status
- `GET/POST /books` • `GET/PUT/DELETE /books/{id}`
- `GET/POST /members` • `GET/PUT/DELETE /members/{id}`
- **Planned:** `POST /loans`, `POST /returns`, fines

## Database
Dev: H2/MySQL.  
**Planned:** Oracle XE with PL/SQL packages (`LOAN_PKG`, `FINE_PKG`).

## Security
Spring Security (JWT) — planned.

## Postman
`/postman/Qitaab.postman_collection.json`

## Roadmap
- [ ] Oracle XE profile + schema
- [ ] PL/SQL `LOAN_PKG.issue_loan(...)`, `FINE_PKG.calc_fine(...)`
- [ ] JWT auth & roles
- [ ] Unit tests (service layer)
