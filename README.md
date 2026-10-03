# ⚓ HEXAKADAL: AI-Based Ocean Freight Engine & Commercial DSS

**HexaKadal** is an AI-based Ocean Freight Procurement Engine and Commercial Decision Support System (DSS) designed for chartering managers and port operations importing bulk cargo to India's East Coast (Paradip, Visakhapatnam, Dhamra, Haldia, Gopalpur).

This repository has been fully migrated from a legacy Python/Streamlit prototype into a modern, decoupled **Spring Boot Backend + React Frontend** production-ready architecture.

---

## 🏗️ 1. Final Project Structure

```
HexaKadal/
│
├── frontend/                     # React Single Page Application (SPA)
│   ├── src/
│   │   ├── components/           # Navbar, KpiCard, AlertBanner
│   │   ├── pages/                # DashboardPage, ForecastingPage, PortsPage, MarketPage, WeatherPage, OptimizationPage, AlertsPage
│   │   ├── services/             # Axios API Client (`api.js`)
│   │   ├── App.jsx               # Main Layout & Tab Router
│   │   ├── main.jsx              # React Entry Point
│   │   └── index.css             # Tailwind CSS Directives
│   ├── public/
│   ├── vercel.json               # Vercel Deployment Config
│   ├── vite.config.js            # Vite Dev Server & API Proxy
│   └── package.json
│
├── backend/                      # Spring Boot 3 Java Application
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/hexakadal/
│   │   │   │   ├── controller/   # REST API Controllers (Forecast, Optimization, Ports, Market, Weather, Dashboard, Alerts)
│   │   │   │   ├── service/      # Core Business Logic Services
│   │   │   │   ├── model/        # Jackson Serialized DTOs & Domain Models
│   │   │   │   ├── repository/   # CsvDataRepository for in-memory datasets
│   │   │   │   ├── ml/           # XGBoostFreightForecaster (XGBoost4J Machine Learning)
│   │   │   │   ├── optimization/ # VesselRoutingOptimizer (Google OR-Tools Java API)
│   │   │   │   ├── integration/  # OpenMeteoClient with Resilience4j CircuitBreaker & Retry
│   │   │   │   ├── config/       # CORS & Resilience4j Configurations
│   │   │   │   └── exception/    # Global Exception Handler
│   │   │   └── resources/
│   │   │       └── application.properties
│   ├── Dockerfile                # Docker Build container for Railway
│   └── pom.xml                   # Maven Build Config
│
├── data/                         # CSV Datasets
│   ├── baltic.csv                # Baltic Freight Index historical time-series
│   ├── congestion.csv            # UNCTAD port congestion turnaround dataset
│   ├── ports.csv                 # World and Indian East Coast Port Database
│   └── vessels.csv               # Global Bulk Carrier fleet specs
│
├── .env.example                  # Environment Variables Template
└── README.md                     # Documentation
```

---

## 🛠️ 2. Technologies Used

### Backend Stack
- **Language & Framework**: Java 17/19, Spring Boot 3.2.3
- **Data & JSON**: Jackson Databind, OpenCSV
- **Machine Learning**: **XGBoost4J** (`ml.dmlc:xgboost4j_2.12:1.7.5`) for time-series regression
- **Optimization Engine**: **Google OR-Tools Java API** (`com.google.ortools:ortools-java:9.8.3296`)
- **Resilience & Fault Tolerance**: **Resilience4j** (`resilience4j-spring-boot3:2.2.0`) with `@CircuitBreaker` and `@Retry`
- **HTTP Integration**: Spring `RestTemplate` / `RestClient` calling Open-Meteo Marine API

### Frontend Stack
- **Framework**: React 18, Vite 5
- **Styling**: Tailwind CSS 3
- **Icons**: Lucide React
- **Data Visualization**: Recharts (Interactive Area & Line Charts)
- **HTTP Client**: Axios

---

## 🔌 3. REST API Endpoint Reference

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/api/dashboard` | Returns aggregated financial metrics (NDV, savings, demurrage loss, queue hours), signals, and alerts |
| `POST` | `/api/forecast` | Runs XGBoost4J 15-day regression forecasting for given cargo and voyage parameters |
| `GET` | `/api/forecast/history` | Fetches historical Baltic Freight Index time-series dataset |
| `POST` | `/api/optimization` | Executes Google OR-Tools constraint solver to determine optimal vessel & demurrage trade-off |
| `GET` | `/api/ports` | Retrieves port draft database and berth depth limits |
| `GET` | `/api/ports/{name}` | Retrieves specific port details by name |
| `GET` | `/api/market/fleet` | Returns global bulk carrier fleet specifications (DWT, draft, dimensions) |
| `GET` | `/api/market/congestion` | Returns UNCTAD port turnaround time benchmarks |
| `GET` | `/api/weather` | Fetches live wave height telemetry from Open-Meteo Marine API with Resilience4j fallback |
| `GET` | `/api/alerts` | Fetches active operational alerts and decision notifications |

---

## 🚀 4. How to Run Backend

### Prerequisites
- JDK 17 or higher
- Maven 3.8+ (or NetBeans bundled `mvn.cmd`)

### Command
```bash
cd backend
mvn spring-boot:run
```
*The Spring Boot server starts at `http://localhost:8080`.*

---

## 💻 5. How to Run Frontend

### Prerequisites
- Node.js v18+ and npm

### Command
```bash
cd frontend
npm install
npm run dev
```
*The React application will launch at `http://localhost:5173`.*

---

## 🔑 6. Environment Variables Required

See `.env.example`:
```ini
# Backend
PORT=8080
SPRING_PROFILES_ACTIVE=prod
HEXAKADAL_DATA_PATH=./data
OPEN_METEO_API_URL=https://marine-api.open-meteo.com/v1/marine
CORS_ALLOWED_ORIGINS=http://localhost:5173,https://hexakadal-frontend.vercel.app

# Frontend
VITE_API_URL=http://localhost:8080/api
```

---

## 🤖 7. How Machine Learning Freight Forecasting Works (XGBoost4J)

1. **Feature Engineering**: The `XGBoostFreightForecaster` module ingests historical Baltic Index spot rates from `baltic.csv`. It calculates 5-day and 20-day moving averages (`MA5`, `MA20`), momentum deltas, and volatility features.
2. **Regression Pipeline**: Ingests features into `ml.dmlc.xgboost4j.java.DMatrix` and executes XGBoost tree regression predicting the target spot rate over a 15-day horizon.
3. **Resilient Fallback**: If native XGBoost C++ dynamic link libraries (`.dll`/`.so`) are missing on the target host OS environment, a pure Java Gradient Boosted Regression Engine seamlessly computes the feature scoring without throwing native linkage errors or crashing.

---

## ⚙️ 8. How Optimization Works (Google OR-Tools Java API)

1. **Solver Formulation**: `VesselRoutingOptimizer` initializes Google OR-Tools integer programming solver (`MPSolver.createSolver("CBC")`).
2. **Decision Variables**: Binary variables $x_i \in \{0, 1\}$ for candidate vessel classes (Valemax 400k DWT, Capesize 180k DWT, Panamax 75k DWT, Supramax 55k DWT).
3. **Constraints**:
   - $\sum x_i = 1$ (Select exactly 1 vessel)
   - $\text{Vessel DWT}(x_i) \ge \text{Cargo Volume}$
   - $\text{Vessel Draft}(x_i) \le \text{Port Max Draft} - 0.5\text{m}$ (Draft clearance margin)
4. **Objective Function**: Minimize Total Financial Exposure $Z$:
   $$\text{Minimize } Z = \text{Demurrage Rate}(x_i) \times \left(\frac{\text{Anchorage Queue Hours}}{24}\right)$$
5. **Executive Decision Matrix**: Calculates Net Decision Value (NDV = Freight Savings - Demurrage Risk Cost) and emits actionable signals (`WAIT / DEFER FIXING`, `ENTER / FIX NOW`, `WATCH / REROUTE`, `WATCH`) accompanied by XAI explanations.

---

## 🌐 9. Deployment Steps

### Frontend Deployment (Vercel)
1. Push the `frontend/` directory to GitHub.
2. Connect repository in [Vercel](https://vercel.com).
3. Set Build Command to `npm run build` and Output Directory to `dist`.
4. Set Environment Variable `VITE_API_URL` to your live Railway backend URL (`https://hexakadal-backend.up.railway.app/api`).

### Backend Deployment (Railway)
1. Create a new service on [Railway](https://railway.app).
2. Connect repository and set root directory to `/backend`.
3. Railway will build using the provided `Dockerfile` or Maven buildpack.
4. Set environment variable `PORT=8080`.

---

## 📌 10. Migration Notes & Features Retained

- **100% Logic Preservation**: All core equations (Net Decision Value, Freight Savings, Demurrage Loss Risk, Draft Clearances, Weather Multipliers) match the original Streamlit application.
- **Technology Upgrades**: Streamlit UI is upgraded to a high-performance React dashboard; Python ML logic is upgraded to **XGBoost4J**; rule-based optimization is upgraded to **Google OR-Tools Java API**; external calls are wrapped with **Resilience4j** circuit breakers.
