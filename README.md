# Client-Server-Architectures---Coursework-2025-26-

# Smart Campus Sensor API

This project is a RESTful API built using **JAX-RS (Jersey)** and a lightweight **Grizzly HTTP server**.
It manages rooms, sensors, and sensor readings for a smart campus monitoring system.

---

## 📌 Overview

The API follows REST principles and uses in-memory data structures (`HashMap`, `ArrayList`) instead of a database.

---

## ⚙️ Tech Stack

* Java
* JAX-RS (Jersey)
* Grizzly HTTP Server
* Maven

---

## 🌐 Base URL

http://localhost:8081/api/v1

---

## 🚀 Features

* Room Management (CRUD)
* Sensor Management (CRUD + validation)
* Sensor Readings (history tracking)
* Sub-resource architecture
* Exception handling with proper HTTP status codes
* Request/Response logging filter

---

## 📡 API Endpoints

### 🏢 ROOMS

* `GET    /rooms` → Get all rooms
* `POST   /rooms` → Create a room
* `GET    /rooms/{id}` → Get a specific room
* `PUT    /rooms/{id}` → Update a room
* `DELETE /rooms/{id}` → Delete a room

---

### 🌡️ SENSORS

* `POST   /sensors` → Create a sensor
* `GET    /sensors/{id}` → Get a sensor
* `PUT    /sensors/{id}` → Update a sensor
* `DELETE /sensors/{id}` → Delete a sensor

---

### 📊 SENSOR READINGS

* `GET    /sensors/{sensorId}/readings` → Get all readings
* `POST   /sensors/{sensorId}/readings` → Add a reading

---

### 🔍 Discovery Endpoint

* `GET /` → Lists available API routes

---

## ⚠️ Error Handling

| Issue                        | HTTP Code |
| ---------------------------- | --------- |
| Room not found               | 404       |
| Room does not exist (sensor) | 422       |
| Room has active sensors      | 409       |
| Sensor unavailable           | 403       |
| Server error                 | 500       |

---

## ▶️ How to Run

1. Open the project in NetBeans / IntelliJ
2. Run `Main.java`
3. Server starts at:
   http://localhost:8081/api/v1
4. Use Postman to test endpoints

---

## 🧪 Sample cURL Commands

```bash
curl -X GET http://localhost:8081/api/v1/rooms

curl -X POST http://localhost:8081/api/v1/rooms \
-H "Content-Type: application/json" \
-d '{"id":"R1","name":"Lab"}'

curl -X GET http://localhost:8081/api/v1/sensors
```

---

## 👩‍💻 Author

**Senuthmi Thimansa**
Computer Science Undergraduate
