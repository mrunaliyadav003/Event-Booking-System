# 🎓 StudentMeetUp — Student Event Booking System

> A RESTful API-based student event booking platform built with Java Spring Boot, MongoDB, and a Bootstrap frontend. Students can discover, publish, and book university events with real-time weather forecasts and public event integration.

---

## 🌐 Live Application

| Service | URL |
|---|---|
| Frontend | `http://studentmeetup.local` |
| Swagger API Docs | `http://localhost:8081/swagger-ui/index.html` |
| MongoDB | `localhost:28017` |

---

## 📋 Table of Contents

- [Features](#-features)
- [System Architecture](#-system-architecture)
- [Tech Stack](#-tech-stack)
- [Getting Started](#-getting-started)
- [API Endpoints](#-api-endpoints)
- [JMeter Load Testing](#-jmeter-load-testing)
- [Docker & Load Balancer](#-docker--load-balancer)
- [External APIs](#-external-apis)
- [Screenshots](#-screenshots)

---

## ✨ Features

### Student Features
- 📝 **Register & Login** — Create an account and subscribe to the system
- 🔒 **Subscription Gate** — Must subscribe before publishing or booking events
- 📅 **Browse Events** — View all student events with weather forecasts
- 🔍 **Advanced Search** — Filter events by text, type, date, or location
- 🎟️ **Book Events** — Register attendance (capacity enforced automatically)
- ❌ **Cancel Bookings** — Cancel any booking from My Bookings page
- ⭐ **Rate & Review** — Submit star ratings and feedback after events
- 💬 **Review Count** — See how many reviews each event has received

### Publisher Features
- ➕ **Create Events** — Publish new events with title, type, date, venue, cost, max participants
- ✏️ **Edit Events** — Update event details
- 🗑️ **Delete Events** — Remove events from the system
- 👥 **Track Attendance** — See real-time attendee count per event

### Admin Features
- 📊 **Dashboard** — View total events, bookings, students, and ratings
- 📋 **All Bookings** — View and cancel any booking
- 👥 **All Students** — View registered student list
- 🗑️ **Delete Events** — Remove any event from the system

### External Integrations
- 🌤️ **Weather API** — Live weather forecast for event location and date
- 🎪 **Skiddle API** — Public event discovery from Skiddle.com
- 📍 **Location Search** — Search Skiddle events by city and radius

---

## 🏗️ System Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                    Docker Compose Environment                     │
│                                                                   │
│  ┌─────────────────────────────────────────────────────────┐    │
│  │              Nginx Load Balancer (:8080)                  │    │
│  │                   Round-robin routing                     │    │
│  └──────────┬──────────────┬──────────────┬─────────────────┘    │
│             │              │              │                        │
│    ┌────────▼──┐  ┌────────▼──┐  ┌───────▼───┐                  │
│    │   App 1   │  │   App 2   │  │   App 3   │                   │
│    │Spring Boot│  │Spring Boot│  │Spring Boot│                   │
│    └────────┬──┘  └────────┬──┘  └───────┬───┘                  │
│             └──────────────┼──────────────┘                       │
│                     ┌──────▼──────┐                               │
│                     │   MongoDB   │                               │
│                     │  (:28017)   │                               │
│                     └─────────────┘                               │
└─────────────────────────────────────────────────────────────────┘
         ▲                                    ▲
         │                                    │
┌────────┴────────┐                  ┌────────┴────────┐
│ Frontend Client │                  │  External APIs   │
│ HTML/Bootstrap  │                  │ Skiddle + Weather│
└─────────────────┘                  └─────────────────┘
```

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Backend API | Java 21, Spring Boot 3.4.5 |
| Database | MongoDB (Docker container) |
| Frontend | HTML5, Bootstrap 5, jQuery |
| Load Balancer | Nginx (Round-robin, 3 instances) |
| Containerisation | Docker + Docker Compose |
| API Documentation | Swagger / OpenAPI 3.1 |
| External APIs | Skiddle API, Open-Meteo Weather API |
| Load Testing | Apache JMeter 5.6.3 |

---

## 🚀 Getting Started

### Prerequisites
- Docker Desktop
- Java 21
- Node.js (for live-server)
- Maven

### Run with Docker (Recommended)

```bash
# Clone the repository
git clone https://olympus.ntu.ac.uk/N1375908/Event-Booking-System.git
cd Event-Booking-System/ApiProject/StudentMeetUp

# Build and start all containers
docker compose up --build
```

This starts:
- ✅ MongoDB on port `28017`
- ✅ 3 Spring Boot app instances
- ✅ Nginx load balancer on port `8080`

### Run Frontend

```bash
# Install live-server
npm install -g live-server

# Serve frontend
cd MeetUpClient
live-server --port=80 --host=studentmeetup.local
```

Add to Windows hosts file (`C:\Windows\System32\drivers\etc\hosts`):
```
127.0.0.1    studentmeetup.local
```

Open: **http://studentmeetup.local**

### Default Admin Account
```
Email:    admin@localhost.com
Password: admin@123
```

---

## 📡 API Endpoints

### Authentication
| Method | Endpoint | Description |
|---|---|---|
| POST | `/auth/register` | Register new user |
| POST | `/auth/login` | Login user |

### Events
| Method | Endpoint | Description |
|---|---|---|
| GET | `/events/getAll` | Get all events |
| GET | `/events/getById` | Get event by ID |
| GET | `/events/SeachByText` | Search events by text |
| GET | `/events/getByDate` | Filter by date |
| GET | `/events/getLocation` | Filter by location |
| GET | `/events/getType` | Filter by type |
| POST | `/events/create` | Create new event |
| PUT | `/events/update` | Update event |
| DELETE | `/events/delete` | Delete event |

### Bookings
| Method | Endpoint | Description |
|---|---|---|
| POST | `/Booking/create` | Create booking |
| GET | `/Booking/getMyBookings` | Get user's bookings |
| GET | `/Booking/getAll` | Get all bookings (admin) |
| GET | `/Booking/getByEventId` | Get bookings by event |
| GET | `/Booking/getAllListCount` | Get attendee counts |
| DELETE | `/Booking/cancel/{id}` | Cancel booking |

### External
| Method | Endpoint | Description |
|---|---|---|
| GET | `/external/weather` | Get weather for location/date |
| GET | `/skiddleEventData/search` | Search Skiddle public events |

---

## 📊 JMeter Load Testing

Load testing was performed on the `GET /events/getAll` endpoint using Apache JMeter 5.6.3 to measure the impact of the Nginx load balancer.

### Test Configuration

| Setting | Test 1 (Before LB) | Test 2 (After LB) |
|---|---|---|
| Concurrent Users | 50 | 100 |
| Ramp-up Period | 10 seconds | 10 seconds |
| Iterations per user | 5 | 10 |
| Total Requests | 250 | 1000 |
| Target Endpoint | GET /events/getAll | GET /events/getAll |

### Results Comparison

| Metric | Test 1 — Single Instance | Test 2 — Nginx Load Balancer | Improvement |
|---|---|---|---|
| **Total Requests** | 250 | 1000 | 4× more load |
| **Average Response** | 20ms | 16ms | ✅ 20% faster |
| **Min Response** | 10ms | 6ms | ✅ 40% faster |
| **Max Response** | 127ms | 91ms | ✅ 28% faster |
| **Standard Deviation** | 11.56ms | 8.42ms | ✅ More stable |
| **Error Rate** | 0.00% | 0.00% | ✅ Perfect |
| **Throughput** | 25.7 req/sec | 98.4 req/sec | ✅ **4× higher** |
| **90th Percentile** | 33ms | 25ms | ✅ 24% faster |
| **95th Percentile** | 39ms | 30ms | ✅ 23% faster |
| **99th Percentile** | 68ms | 56ms | ✅ 18% faster |

### Key Finding

> Despite **doubling** the number of concurrent users from 50 to 100, the Nginx load balancer with 3 application instances achieved a **4× increase in throughput** (25.7 → 98.4 req/sec) and **improved response times across all percentiles**, while maintaining a **0% error rate**. This demonstrates significant QoS improvement through horizontal scaling.

### JMeter Test File
The JMeter test configuration file is included in the repository:
```
StudentMeetUpLoadTest.jmx
```

---

## 🐳 Docker & Load Balancer

### Container Overview

```bash
docker ps
```

| Container | Image | Port | Role |
|---|---|---|---|
| nginx-load-balancer | nginx:latest | 8080:80 | Load balancer |
| app-container-1 | studentmeetup-app | - | App instance 1 |
| app-container-2 | studentmeetup-app | - | App instance 2 |
| app-container-3 | studentmeetup-app | - | App instance 3 |
| mongodb-container | mongo:latest | 28017:27017 | Database |

### Nginx Configuration

Nginx uses **round-robin** load balancing distributing requests evenly across all 3 application instances:

```nginx
upstream studentmeetup_backend {
    server app1:8080;
    server app2:8080;
    server app3:8080;
}
```

### Useful Commands

```bash
# Start everything
docker compose up --build

# Stop everything
docker compose down

# View logs
docker logs nginx-load-balancer --tail=20
docker logs app-container-1 --tail=20

# Check container status
docker ps
```

---

## 🌍 External APIs

### Weather API (Open-Meteo)
- **Endpoint:** `GET /external/weather?location={city}&date={date}`
- **Purpose:** Displays weather forecast on event booking page
- **Data:** Temperature range, precipitation probability
- **Free tier:** No API key required

### Skiddle API
- **Endpoint:** `GET /skiddleEventData/search?lat={lat}&lon={lon}&radius={miles}`
- **Purpose:** Discover public events in a given area
- **Coverage:** UK-wide event database
- **API Key:** Configured in `application.properties`

---

## 📁 Project Structure

```
Event-Booking-System/
├── ApiProject/StudentMeetUp/     # Spring Boot backend
│   ├── src/main/java/            # Java source code
│   │   └── com/eventbookingapi/studentmeetup/
│   │       ├── collection/       # MongoDB models
│   │       ├── config/           # CORS, app config
│   │       ├── controller/       # REST endpoints
│   │       ├── repository/       # MongoDB repositories
│   │       └── Service/          # Business logic
│   ├── src/main/resources/
│   │   └── application.properties
│   ├── Dockerfile
│   ├── docker-compose.yml
│   └── nginx.conf
├── MeetUpClient/                 # Frontend
│   ├── index.html                # Homepage
│   ├── Event.html                # Event listing
│   ├── Booking.html              # Book event
│   ├── MyBookings.html           # My bookings
│   ├── Admin.html                # Admin dashboard
│   ├── Login.html                # Login
│   ├── UserRegister.html         # Register
│   ├── Rating.html               # Rate event
│   ├── SkiddleEvent.html         # Public events
│   ├── Register.html             # Create event
│   ├── RegisterEdit.html         # Edit event
│   └── EventRegisterList.html    # Manage events
└── StudentMeetUpLoadTest.jmx     # JMeter test config
```

---

## 👨‍💻 Module Information

- **Module:** ISYS40061 — Service-Oriented Cloud Technologies
- **University:** Nottingham Trent University
- **Module Leader:** Dr Taha Osman
- **Academic Year:** 2025/2026
