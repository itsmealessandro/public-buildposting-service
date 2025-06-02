# 📢 Public Billposting Booking System

Final Project Assignment for the course  
**Business Process Development**  
Instructor: Prof. Massimo Tivoli  
Email: massimo.tivoli@univaq.it  
Acknowledgements: Dott. Gianluca Filippone (GSSI)

---

## 📝 Project Overview

This application is a **public billposting booking system**, developed using the **Camunda BPM platform** embedded in a **Spring Boot** application. The system allows users to reserve billposting zones across multiple cities according to their preferences and budget.

The process is modeled using **BPMN** and orchestrated via Camunda, integrating multiple REST and SOAP services.

---

## 🚀 Features

- User can submit a booking request via API
- The system:
  - Retrieves user and zone data from external REST services
  - Selects optimal zones based on budget and preferences
  - Interacts with a SOAP posting service
  - Handles user confirmation or cancellation
  - Stores confirmed booking information in files
- Robust error handling and message correlation
- Easily extendable architecture

---

## 🔧 Technologies Used

- Java 8
- Spring Boot
- Camunda BPM (embedded engine)
- REST APIs
- SOAP Web Services
- BPMN 2.0
- Maven (build system)

---

## 📦 External Services

These are required for full functionality. Launch them with:

```bash
java -jar user-service.jar
java -jar zones-service.jar
java -jar posting-service.jar
```

### External Service Endpoints

| Service           | Type | Endpoint / WSDL                                                | Description                    |
|------------------|------|------------------------------------------------------------------|--------------------------------|
| `user-service`   | REST | `GET http://localhost:9080/user/{username}`                     | Gets user information          |
| `zones-service`  | REST | `GET http://localhost:9090/zones/{format}`                      | Gets zone availability         |
| `posting-service`| SOAP | `http://localhost:8888/postingservice/postingservice.wsdl`       | Handles posting requests       |

---

## 🔁 Process Workflow

1. **Request Submission**
   - User sends:
     - Username
     - List of cities
     - Poster format (e.g. `60x80`)
     - Maximum price per city

2. **Data Fetching**
   - User info from `user-service`
   - Zone info from `zones-service`

3. **Zone Selection**
   - For each city: select most expensive zones within budget

4. **SOAP Posting**
   - Send selected zones + user info to `posting-service`
   - Receive `Request ID`

5. **Response to User**
   - Return selected zones and request ID

6. **User Decision**
   - User sends a confirmation (`confirm`) or cancellation (`cancel`)

7. **Finalization**
   - On `confirm`: finalize order, receive billing info, save file, return billing
   - On `cancel`: cancel posting, print notice, return empty bill

---

## 📂 API Endpoints

### 1. `POST /request-availability`

- **Description**: Starts the booking process
- **Input**:
  ```json
  {
    "username": "mariorossi",
    "cities": ["L'Aquila", "Rome"],
    "posterFormat": "60x80",
    "maxPricePerCity": 30.0
  }
  ```
- **Output**:
  ```json
  {
    "requestId": "03NzgXNyQE",
    "selectedZones": {
      "L'Aquila": ["Zone1", "Zone2"],
      "Rome": ["Zone3"]
    },
    "totalPrice": 29.5
  }
  ```

### 2. `POST /send-decision`

- **Description**: Confirms or cancels a booking
- **Input**:
  ```json
  {
    "requestId": "03NzgXNyQE",
    "decision": "confirm"
  }
  ```
- **Output (confirm)**:
  ```json
  {
    "invoiceNumber": "2057718223",
    "amountDue": 29.5
  }
  ```
- **Output (cancel)**:
  ```json
  {
    "message": "Booking canceled.",
    "bill": {}
  }
  ```

---

## 🗂 File Generation

- Confirmed orders are stored as files (1 per request) in the format:
  ```
  username: mariorossi
  requestId: 03NzgXNyQE
  invoiceNumber: 2057718223
  amountDue: 29.5
  selectedZones:
    - L'Aquila: Zone1, Zone2
    - Rome: Zone3
  ```

- Or (optional): log all confirmed orders in `posting_requests.txt`

---

## ❗ Error Handling & Validation

- All inputs are validated
- Appropriate HTTP status codes are returned (e.g., 400, 404)
- The process prevents deadlocks or unhandled exceptions
- Input correlation is performed using Camunda message correlation

---

## 🌟 Extra Features (Nice to Have)

- Pluggable zone selection strategies (greedy, balanced, random, etc.)
- Input validation and sanitization
- Templates for user communication or logs
- Better file formatting using templates
- Enhanced error feedback

---

## ▶️ How to Run

1. Start external services:
   ```bash
   java -jar user-service.jar
   java -jar zones-service.jar
   java -jar posting-service.jar
   ```

2. Run the Spring Boot app:
   ```bash
   mvn spring-boot:run
   ```

3. Access Camunda:
   - Dashboard: [http://localhost:8080](http://localhost:8080)
   - REST APIs: as described above

---

## 🧑‍💻 Authors

This project has been developed as part of the **Business Process Development** course at the University of L'Aquila.