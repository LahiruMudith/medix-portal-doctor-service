# Doctor Service

## Student Information
* **Name:** Lahiru Mudith
* **Student Number:** CMS-ST-2026
* **GCP Project ID:** `cms-cloud-architecture`

---

## Service Overview
The **Doctor Service** manages medical officer registers, area qualifications, specializations, and schedule availability metrics. It connects to MongoDB to store officer document structures.

* **Default Port:** `8001`
* **Data Store:** MongoDB (Database: `cms_doctor`, collection: `doctors`)
* **Target Environment:** OpenJDK 25, Spring Boot 4.1.1, Spring Cloud 2025.1.3.

---

## REST Endpoints (Routed via Gateway Port `7000`)

| Method | Endpoint | Description | Request Format |
|---|---|---|---|
| **POST** | `/api/v1/doctors` | Add new medical officer | JSON |
| **GET** | `/api/v1/doctors` | List all doctors and profiles | JSON Array |
| **GET** | `/api/v1/doctors/{doctorId}` | Get single doctor details | JSON |
| **PUT** | `/api/v1/doctors/{doctorId}` | Modify officer specifications | JSON |
| **DELETE** | `/api/v1/doctors/{doctorId}` | Remove doctor profile | None |

### Sample Register JSON payload:
```json
{
  "name": "Dr. Jane Watson",
  "specialization": "Pediatrician",
  "qualifications": "MBBS, MD",
  "contactNumber": "0777123456",
  "email": "dr.jane@medix.com",
  "availableDays": ["Monday", "Wednesday", "Friday"]
}
```

---

## Build and Run

### Build Command:
```bash
mvn clean package -DskipTests
```

### Execution Command:
```bash
java -jar target/Doctor-Service-1.0.0.jar
```

---

## 🧪 Testing with Postman
A dedicated testing collection is available locally:
👉 **[Doctor-Service-Postman-Collection.json](file:///C:/Users/Lahiru%20Mudith/Documents/Study/IJSE/4th%20Sem/Enterprise%20Cloud%20Architectur/Projects/Clinic-Management-System/cms-services/service-doctor/Doctor-Service-Postman-Collection.json)**

* **Usage:** Import the collection file into Postman.
* **Environment Configuration:** Adjust the collection-scoped `baseUrl` variable to point to `http://localhost:8001` (for direct testing) or `http://localhost:7000` (when testing via the gateway).

It includes test script hooks to automatically save the returned UUID of the registered doctor in a dynamic variable (`{{last_doctor_id}}`) for subsequent requests.
