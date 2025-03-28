# Sp_2FitnessMealsDb
FitMeal API 🍽️🏃
A meal planning and workout tracking API that integrates with external APIs to provide users with meals and workouts 
while allowing them to log their fitness journey.

📌 Introduction
The FitMeal API is a RESTful backend service designed to provide users with meal suggestions and workout tracking. 
The API fetches data from external sources, allowing users to explore meals and workouts, save their favorites, and 
log their fitness progress.

This API is built using Java, Javalin, JPA, Hibernate, and PostgreSQL, following RESTful principles with structured 
endpoints for retrieving, saving, and managing meal and workout data.

It includes full CRUD functionality, proper error handling, and will soon be secured using JWT authentication for 
role-based access control (e.g., USER, ADMIN).

📌 Features
🍽️ Meal Management
✅ Fetch random meals from TheMealDB API and store them in a database.
✅ Retrieve all stored meals or fetch a specific meal by ID.
✅ Manually add new meals to the system for full customization.
✅ Delete meals that are no longer needed.

🏋️ Workout Management
✅ Fetch random workouts from WGER API and save them for later use.
✅ Retrieve all stored workouts or find a specific one by ID.
✅ Manually add custom workouts to the system.
✅ Delete workouts when needed.

🔒 Security & Authorization
✅ Coming soon: Role-based authentication with JWT (USER, ADMIN).
✅ Error handling & proper HTTP status codes for validation.

📊 Bonus Features (Future Enhancements)
User-Created Meals: Users can add their own meals and include them in random meal plans.

Calorie-Based Portions: Based on user weight & activity level, the system suggests portion sizes.

User Authentication: Secure login/logout with JWT & role-based permissions.

Weekly Meal Plans: The API could generate full meal plans for an entire week instead of single meals.

📌 Tech Stack
🔹 Java 21
🔹 Javalin (Lightweight web framework)
🔹 JPA & Hibernate (ORM for database management)
🔹 PostgreSQL (Relational database)
🔹 HTTP Client (Fetching external meal & workout data)
🔹 JUnit, Hamcrest, Rest Assured (Testing framework)
🔹 Docker, Digital Ocean, Caddy (For deployment)

📌 API Endpoints & Documentation
🍽️ Meal Endpoints
Method	URL	Request Body	Response
POST	/meals/fetch-random	None	Fetches and saves a random meal from TheMealDB API
GET	/meals	None	Returns a list of all stored meals
GET	/meals/{id}	None	Fetches a specific meal by ID
POST	/meals/add	Meal data (without ID)	Adds a new meal to the database
DELETE	/meals/{id}	None	Deletes a meal from the database
🏋️ Workout Endpoints
Method	URL	Request Body	Response
POST	/workouts/fetch-random	None	Fetches and saves a random workout from WGER API
GET	/workouts	None	Returns a list of all stored workouts
GET	/workouts/{id}	None	Fetches a specific workout by ID
POST	/workouts/add	Workout data (without ID)	Adds a new workout to the database
DELETE	/workouts/{id}	None	Deletes a workout from the database
📌 Deployment & Testing
🌍 Deployment
The API is deployed using Docker & Digital Ocean, secured with Caddy for HTTPS support.

Live API Link: (To be added after deployment)

🛠️ Testing
1️⃣ DAO & Service Layer Tests → Uses JUnit, Hamcrest, Testcontainers
2️⃣ Endpoint Tests → Uses Rest Assured to validate CRUD operations
3️⃣ Security Tests (Coming Soon) → Verifies JWT role-based authentication

📌 How to Run Locally
📍 Prerequisites
Java 21

PostgreSQL (or use Docker)

IntelliJ IDEA / VS Code

📍 Steps
1️⃣ Clone the Repository:

sh
Copy
Edit
git clone https://github.com/your-repo/fitmeal-api.git
cd fitmeal-api
2️⃣ Set Up the Database:
Modify application.properties or use environment variables:

properties
Copy
Edit
spring.datasource.url=jdbc:postgresql://localhost:5432/FitnessMealsDb
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
3️⃣ Run the Application:

sh
Copy
Edit
./mvnw spring-boot:run
4️⃣ Test API Endpoints using Postman, cURL, or http files.

📌 To-Do List
✅ Completed
✅ Fetched & saved multiple meals from TheMealDB
✅ Fetched & saved multiple workouts from WGER
✅ Implemented CRUD for Meals & Workouts
✅ Added structured API endpoints

🚀 Next Steps
🔹 Implement JWT authentication for securing endpoints
🔹 Add meal/workout logging per user
🔹 Write DAO & service-layer tests
🔹 Deploy the API on Digital Ocean

📌 Contribution & License
🔹 Developers: Your Team Names
🔹 License: MIT License

🎯 FitMeal API – Fuel Your Fitness, One Meal at a Time! 🍽️🏃

FitMeal API 🍽️🏃
A simple RESTful API for meal planning and workout tracking

📌 What is This Project?
FitMeal API is a Java-based REST API that helps users find random meals and workouts, save their favorites, and track their fitness journey.

The API fetches data from external sources like TheMealDB and WGER, allowing users to get random meals & workouts, store them in a database, and manage them with full CRUD functionality (Create, Read, Update, Delete).

🚀 Built with: Java, Javalin, JPA, Hibernate, and PostgreSQL

📌 Features
🍽️ Meals
✅ Fetch random meals from TheMealDB API
✅ Save meals to the database
✅ Get all meals or find one by ID
✅ Add new meals manually
✅ Delete meals

🏋️ Workouts
✅ Fetch random workouts from WGER API
✅ Save workouts to the database
✅ Get all workouts or find one by ID
✅ Add custom workouts
✅ Delete workouts

🔒 Coming Soon
🔹 JWT Authentication (User/Admin roles)
🔹 Error handling with proper status codes
🔹 API security improvements

📌 API Endpoints
🍽️ Meal Endpoints
Method	URL	Description
POST	/meals/fetch-random	Fetches & saves a random meal from TheMealDB
GET	/meals	Returns all stored meals
GET	/meals/{id}	Fetches a meal by ID
POST	/meals/add	Adds a new meal to the database
DELETE	/meals/{id}	Deletes a meal from the database
🏋️ Workout Endpoints
Method	URL	Description
POST	/workouts/fetch-random	Fetches & saves a random workout from WGER API
GET	/workouts	Returns all stored workouts
GET	/workouts/{id}	Fetches a workout by ID
POST	/workouts/add	Adds a new workout to the database
DELETE	/workouts/{id}	Deletes a workout from the database
📌 Tech Stack
🔹 Java 21
🔹 Javalin (Lightweight web framework)
🔹 JPA & Hibernate (Database management)
🔹 PostgreSQL (Relational database)
🔹 JUnit, Hamcrest, Rest Assured (Testing framework)
🔹 Docker & Digital Ocean (Deployment)

📌 How to Run Locally
📍 Prerequisites
Java 21

PostgreSQL

IntelliJ IDEA / VS Code

📍 Steps
1️⃣ Clone the Repository:

sh
Copy
Edit
git clone https://github.com/your-repo/fitmeal-api.git
cd fitmeal-api
2️⃣ Set Up the Database:
Modify application.properties:

properties
Copy
Edit
spring.datasource.url=jdbc:postgresql://localhost:5432/FitnessMealsDb
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
3️⃣ Run the Application:

sh
Copy
Edit
./mvnw spring-boot:run
4️⃣ Test API Endpoints using Postman, cURL, or http files.

📌 Deployment & Testing
🌍 Deployment
The API will be deployed on Digital Ocean using Docker & Caddy for HTTPS.

🛠️ Testing
✅ DAO & Service Layer tests (JUnit, Testcontainers)
✅ API Endpoint tests (Rest Assured)
✅ Security tests (coming soon)

📌 To-Do List
✅ Completed
✅ Fetch & save meals from TheMealDB
✅ Fetch & save workouts from WGER
✅ Implement CRUD for Meals & Workouts

🚀 Next Steps
🔹 Add JWT authentication (User/Admin roles)
🔹 Implement meal/workout logging for users
🔹 Write more unit & integration tests
🔹 Deploy the API on Digital Ocean

📌 Contribution & License
🔹 Developers: Your Name(s)
🔹 License: MIT License

🎯 FitMeal API – Your personal fitness and meal planner! 🍽️🏃

📌 What’s Next?
✅ Copy-Paste this README into your project
✅ Modify API URL once deployed
✅ Continue with JWT, logging & deployment


# FitMeal API 🍽️🏃
**A meal planning and workout tracking API that integrates with external APIs to provide users with meals and workouts while allowing them to log their fitness journey.**

---

## 📌 Introduction
The FitMeal API is a **RESTful backend service** designed to provide users with **meal suggestions and workout tracking**.  
The API fetches data from external sources, allowing users to explore meals and workouts, save their favorites, and log their fitness progress.

This API is built using **Java, Javalin, JPA, Hibernate, and PostgreSQL**, following **RESTful principles** with structured endpoints for retrieving, saving, and managing meal and workout data.

It includes **full CRUD functionality**, proper error handling, and will soon be secured using **JWT authentication** for role-based access control (e.g., USER, ADMIN).

---

## 📌 Features

### **🍽️ Meal Management**
✅ Fetch **random meals** from TheMealDB API and store them in a database.  
✅ Retrieve all stored meals or fetch a specific meal by ID.  
✅ Manually add new meals to the system for full customization.  
✅ Delete meals that are no longer needed.

### **🏋️ Workout Management**
✅ Fetch **random workouts** from WGER API and save them for later use.  
✅ Retrieve all stored workouts or find a specific one by ID.  
✅ Manually add custom workouts to the system.  
✅ Delete workouts when needed.

### **🔒 Security & Authorization**
✅ Coming soon: **Role-based authentication** with JWT (USER, ADMIN).  
✅ Error handling & proper HTTP status codes for validation.

---

## 📊 **Bonus Features (Future Enhancements)**
🍛 **User-Created Meals:** Users can add their own meals and include them in random meal plans.  
📊 **Calorie-Based Portions:** Based on user weight & activity level, the system suggests portion sizes.  
🔑 **User Authentication:** Secure login/logout with JWT & role-based permissions.  
📅 **Weekly Meal Plans:** The API could generate full meal plans for an entire week instead of single meals.

---

## 📌 Tech Stack
🔹 **Java 21**  
🔹 **Javalin** (Lightweight web framework)  
🔹 **JPA & Hibernate** (ORM for database management)  
🔹 **PostgreSQL** (Relational database)  
🔹 **HTTP Client** (Fetching external meal & workout data)  
🔹 **JUnit, Hamcrest, Rest Assured** (Testing framework)  
🔹 **Docker, Digital Ocean, Caddy** (For deployment)

---

## 📌 API Endpoints & Documentation

### **🍽️ Meal Endpoints**

| Method     | URL                   | Request Body           | Response Description                               |
|------------|-----------------------|------------------------|----------------------------------------------------|
| **POST**   | `/meals/fetch-random` | None                   | Fetches and saves a random meal from TheMealDB API |
| **GET**    | `/meals`              | None                   | Returns a list of all stored meals                 |
| **GET**    | `/meals/{id}`         | None                   | Fetches a specific meal by ID                      |
| **POST**   | `/meals/add`          | Meal data (without ID) | Adds a new meal to the database                    |
| **DELETE** | `/meals/{id}`         | None                   | Deletes a meal from the database                   |

### **🏋️ Workout Endpoints**

| Method     | URL                      | Request Body              | Response Description                            |
|------------|--------------------------|---------------------------|-------------------------------------------------|
| **POST**   | `/workouts/fetch-random` | None                      | Fetches and saves a random workout from WGER API|
| **GET**    | `/workouts`              | None                      | Returns a list of all stored workouts           |
| **GET**    | `/workouts/{id}`         | None                      | Fetches a specific workout by ID                |
| **POST**   | `/workouts/add`          | Workout data (without ID) | Adds a new workout to the database              |
| **DELETE** | `/workouts/{id}`         | None                      | Deletes a workout from the database             |

---

## 📌 Deployment & Testing

### 🌍 **Deployment**
The API is deployed using **Docker & Digital Ocean**, secured with **Caddy** for HTTPS support.

🔗 **Live API Link:** _(To be added after deployment)_

### 🛠️ **Testing**
✅ DAO & Service Layer Tests → Uses JUnit, Hamcrest, Testcontainers  
✅ Endpoint Tests → Uses Rest Assured to validate CRUD operations  
✅ Security Tests (Coming Soon) → Verifies JWT role-based authentication

---

## How to Run Locally

### Prerequisites
- **Java 21**
- **PostgreSQL** (or use Docker)
- **IntelliJ IDEA / VS Code**

### **📍 Steps**

1️⃣ **Clone the Repository:**
```sh
git clone https://github.com/your-repo/fitmeal-api.git
cd fitmeal-api
