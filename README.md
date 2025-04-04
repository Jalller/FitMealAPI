# 🥗 FitMealAPI

A secure and production-ready Java backend for tracking healthy meals and workouts.

Supports login with JWT, role-based access control (`USER` & `ADMIN`), full CRUD functionality, and integration with 
real external APIs.

Deployed and accessible here:  
🌍 [https://jjldomain.dk](https://jjldomain.dk)

---

## 📌 Table of Contents

- [📖 Overview](#-overview)
- [🧱 Tech Stack](#-tech-stack)
- [🔐 Authentication & Roles](#-authentication--roles)
- [🚀 How to Run](#-how-to-run)
- [🧪 Testing](#-testing)
- [📮 API Endpoints](#-api-endpoints)
- [📊 Diagrams](#-diagrams)
- [🚧 Roadmap & Known Issues](#-roadmap--known-issues)
- [💬 Developer Notes](#-developer-notes)
- [🧑‍💻 Author](#-author)

---

## 📖 Overview

FitMealAPI enables users to:

✅ Fetch meals & workouts from real APIs  
✅ Store, update, and delete entries in a local DB  
✅ Secure access using JWT  
✅ Differentiate access between `USER` and `ADMIN`  
✅ Test and run with Docker or locally  
✅ Deploy to cloud with custom domain + HTTPS

---

## 🧱 Tech Stack

| Layer        | Tech Stack                                              |
|--------------|---------------------------------------------------------|
| Language     | Java 17                                                 |
| Framework    | Spring Boot 3.1.2                                       |
| Database     | PostgreSQL (via Docker)                                 |
| ORM          | JPA + Hibernate                                         |
| Security     | Spring Security + JWT                                   |
| APIs         | [TheMealDB](https://themealdb.com), [WGER](https://wger.de) |
| Testing      | JUnit 5, Mockito, Testcontainers, MockMVC               |
| Build Tool   | Maven                                                   |
| Deployment   | Docker + Compose, DigitalOcean, Caddy (HTTPS)          |

---

## 🔐 Authentication & Roles

| Role   | Permissions                                        |
|--------|----------------------------------------------------|
| USER   | Can access `/workouts/**`                          |
| ADMIN  | Can access `/api/meals/**` + all authenticated routes |

- Login via `/auth/login` to receive a JWT.
- Add token to headers:  
  `Authorization: Bearer <token>`
- Stateless session, protected with filters.

---

## 🚀 How to Run

### 🔄 Docker (Recommended)

```bash
docker compose up -d
Runs both DB and API in containers.

Visit:
🌐 https://jjldomain.dk

💻 Manual (Local Dev)
bash
Copy
# Clone
git clone https://github.com/Jalller/FitMealAPI.git
cd FitMealAPI

# Run with Maven (Java 17+)
./mvnw spring-boot:run
Update DB config in src/main/resources/application.properties

🧪 Testing
Layer	Tooling	Classes Tested
DAO	JUnit 5 + Testcontainers	MealDAOTests, WorkoutDAOTests
Service	JUnit 5 + Mockito	MealServiceTests, WorkoutServiceTests
Controller	Spring MockMVC	MealControllerTest, WorkoutControllerTest
✅ Test DB uses isolated container
✅ Startup data handled via PopulatorConfig
✅ All core paths tested

📮 API Endpoints
🔐 Auth
Method	Endpoint	Description
POST	/auth/login	Login + get JWT
🍽️ Meals (ADMIN Only)
Method	Endpoint	Description
GET	/api/meals	Get all meals
POST	/api/meals	Fetch + save random meal
PUT	/api/meals/{id}	Update a meal by ID
DELETE	/api/meals/{id}	Delete a meal by ID
🏋️ Workouts (All Users)
Method	Endpoint	Description
GET	/workouts	Get all workouts
POST	/workouts/fetch-random	Save 1 random workout
POST	/workouts/fetch-multiple	Save multiple workouts
PUT	/workouts/update/{id}	Update a workout
DELETE	/workouts/delete/{id}	Delete a workout by ID
📊 Diagrams
All PlantUML files live in:
📁 src/main/resources/PlantUML/

Diagram Type	File
Class Diagram	ClassDiagram.puml
Use Case Diagram	UseCaseDiagram.puml
Sequence Diagram	SequenceDiagram.puml
Auth Flow	JwtAuthFlow.puml
🚧 Roadmap & Known Issues
Limitation	Plan / Fix
No user-specific data	Add @ManyToOne User → Meal/Workout
No registration flow	Add /auth/register route
No frontend	Build React SPA
No Swagger / OpenAPI docs	Add Swagger (Springdoc / Springfox)
💬 Developer Notes
⚠️ About Javalin vs Spring Boot
The assignment mentioned Javalin — but I misunderstood and implemented everything in Spring Boot from the start.
This was a deliberate final choice, as it allowed faster development, strong security, full test coverage, and easy 
deployment — all within the deadline.

🔒 Root path ("/") returns 403 Forbidden — expected!
No frontend is exposed. Only JWT-protected routes are available.

🧑‍💻 Author
👤 Jantie Joy Larsen
🐾 Assistant: Max the cat (probably stepped on the keyboard once or twice)

📍 GitHub Repo
🌍 Live API


