# Smart Companion — Clinical Monitoring Platform

A healthcare analytics platform built with **Spring Boot** and **Weka Machine Learning** that remotely monitors patient health data and generates AI-powered predictions for glucose trends and blood pressure risk.

The frontend is a standalone HTML dashboard that connects directly to the Spring Boot REST API — no build tools or Node.js required.

---

## Features

- **Glucose Monitoring** — log patient glucose readings with optional HbA1c, get automatic risk classification (Normal / Borderline / High / Critical)
- **AI Predictions** — Weka Linear Regression model predicts each patient's next glucose value based on population trend data
- **Blood Pressure Tracking** — record systolic/diastolic readings with automatic hypertension staging (Normal → Elevated → Stage 1 → Stage 2 → Crisis)
- **Clinician Profiles** — register and sync clinician accounts, with optional Supabase JWT authentication
- **Data Simulation** — one-click endpoint to seed the database with realistic mock patient data
- **Live Dashboard** — dark-themed frontend with real-time stats, tabbed patient tables, activity feed, and inline forms

---

## Tech Stack

| Layer | Technology |
|---|---|
| Backend | Spring Boot 3.5, Spring Data JPA, Spring Security |
| AI / ML | Weka 3.8.6 (Linear Regression) |
| Database | H2 (in-memory, dev) |
| Auth | Spring OAuth2 Resource Server + Supabase JWT |
| Frontend | Vanilla HTML/CSS/JS (no framework required) |

---

## Prerequisites

- Java JDK 20 or higher
- Maven 3.8+
- A modern browser (Chrome, Firefox, Edge)

---

## Getting Started

**1. Clone and build**
```bash
git clone <your-repo-url>
cd smart_companion
mvn clean install
```

**2. Start the server**
```bash
mvn spring-boot:run
```
The API will be available at `http://localhost:8080`.

**3. Open the dashboard**

Open `index.html` directly in your browser (double-click the file or use Live Server in VS Code). It connects automatically to the backend on port 8080.

**4. View the raw database (optional)**

Navigate to `http://localhost:8080/h2-console` with these credentials:

| Field | Value |
|---|---|
| JDBC URL | `jdbc:h2:mem:testdb` |
| Username | `sa` |
| Password | *(leave blank)* |

---

## API Reference

All endpoints are open (no auth required) during development.

### Health

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/health` | Returns server status |

### Clinician

Base path: `/api/clinitians`

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/clinitians` | Create a new clinician profile |
| `POST` | `/api/clinitians/sync` | Sync a profile using a Supabase JWT (requires `Authorization` header) |
| `GET` | `/api/clinitians/me` | Return the currently authenticated clinician (requires JWT) |

**POST body:**
```json
{
  "firstName": "Amanda",
  "lastName": "Osei",
  "specialization": "Endocrinology"
}
```

### Diabetes

Base path: `/api/diabetes`

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/diabetes/readings` | Save a new glucose reading |
| `GET` | `/api/diabetes/readings` | Return all glucose readings |
| `GET` | `/api/diabetes/readings/{id}` | Return a single patient — triggers AI prediction |

When `GET /readings/{id}` is called, the `PredictiveAnalysisService` trains a Weka Linear Regression model on all stored glucose values and writes `predictedNextValue` and `riskAssessment` back to the record.

**POST body:**
```json
{
  "patientName": "John Doe",
  "glucoseLevel": 115.5,
  "hba1c": 6.2
}
```

**Response includes:**
```json
{
  "id": 1,
  "patientName": "John Doe",
  "glucoseLevel": 115.5,
  "hba1c": 6.2,
  "measuredAt": "2026-02-22T14:30:00",
  "predictedNextValue": 118.3,
  "riskAssessment": "Borderline — pre-diabetic range, monitor closely"
}
```

### Hypertension

Base path: `/api/hypertension`

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/hypertension/readings` | Save a blood pressure reading |
| `GET` | `/api/hypertension/readings` | Return all BP readings |
| `GET` | `/api/hypertension/readings/{id}` | Return a single BP reading |

**POST body:**
```json
{
  "patientName": "Jane Smith",
  "systolic": 142,
  "diastolic": 91
}
```

---

## cURL Examples

**Add a diabetes reading**
```bash
curl -X POST http://localhost:8080/api/diabetes/readings \
  -H "Content-Type: application/json" \
  -d '{"patientName": "John Doe", "glucoseLevel": 115.5}'
```

**Get AI analysis for patient #1**
```bash
curl http://localhost:8080/api/diabetes/readings/1
```

**Add a blood pressure reading**
```bash
curl -X POST http://localhost:8080/api/hypertension/readings \
  -H "Content-Type: application/json" \
  -d '{"patientName": "Jane Smith", "systolic": 138, "diastolic": 88}'
```

---

## Risk Classification Reference

**Glucose (mg/dL)**

| Range | Classification |
|---|---|
| ≤ 100 | Normal |
| 101 – 126 | Borderline (pre-diabetic) |
| 127 – 180 | High (diabetic threshold) |
| > 180 | Critical |

**Blood Pressure (mmHg)**

| Systolic / Diastolic | Category |
|---|---|
| < 120 / < 80 | Normal |
| 120–129 / < 80 | Elevated |
| 130–139 / 80–89 | Stage 1 Hypertension |
| ≥ 140 / ≥ 90 | Stage 2 Hypertension |
| ≥ 180 / ≥ 120 | Hypertensive Crisis |

---

## Project Structure

```
smart_companion/
├── src/main/java/com/comps/companion/
│   ├── CompanionApplication.java
│   ├── config/
│   │   └── SecurityConfig.java          # CORS + Spring Security setup
│   ├── controller/
│   │   ├── DashboardController.java     # Health check endpoint
│   │   ├── ClinitianController.java     # Clinician CRUD + JWT sync
│   │   ├── DiabetesController.java      # Glucose readings + AI trigger
│   │   └── HypertensionController.java  # Blood pressure readings
│   ├── entity/
│   │   ├── Clinitian.java
│   │   ├── DiabetesPatient.java
│   │   └── HypertensionPatient.java
│   ├── repository/
│   │   ├── ClinitianRepository.java
│   │   ├── DiabetesRepository.java
│   │   └── HypertensionRepository.java
│   └── service/
│       ├── ClinitianService.java
│       ├── DataSimulationService.java   # Mock data generation
│       ├── DiabetesService.java
│       ├── HypertensionService.java
│       └── PredictiveAnalysisService.java  # Weka ML model
├── src/main/resources/
│   └── application.properties
└── pom.xml

index.html                               # Standalone frontend dashboard
```

---

## Notes

- The H2 database is **in-memory** — all data is lost when the server restarts. To persist data, swap the datasource in `application.properties` for a file-based H2 URL or a PostgreSQL connection.
- The AI prediction requires **at least 3 glucose readings** in the database before it can generate a trend. Fewer than 3 readings will return `null` for `predictedNextValue`.
- The Supabase JWT secret in `application.properties` is a development placeholder. Rotate it before any production deployment.
