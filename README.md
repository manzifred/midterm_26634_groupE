<div align="center">

# 🏛️ Land Registration & Management System

### A Professional Spring Boot Application for Rwanda's Administrative Hierarchy

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-12%2B-blue.svg)](https://www.postgresql.org/)
[![License](https://img.shields.io/badge/License-Academic-yellow.svg)](LICENSE)

## 🎯 Overview

This project is a comprehensive **Land Registration and Management System** built with Spring Boot, demonstrating advanced database design principles and RESTful API development. The system manages Rwanda's five-level administrative hierarchy (Province → District → Sector → Cell → Village) and implements user registration with proper location tracking.


## 📊 Entity Relationship Diagram
<img width="2800" height="2200" alt="erd-diagram" src="https://github.com/user-attachments/assets/5a0be4ee-f54c-40eb-a758-9d7c8fb205ef" />


<div align="center">

### Land Registration & Management System - Complete ERD


**Key Features of This ERD:**
- ✅ **8 Core Tables** + 1 Junction Table = **9 Total Tables**
- ✅ **5-Level Geographic Hierarchy** (Province → District → Sector → Cell → Village)
- ✅ **4 Relationship Types Implemented**:
  - One-to-Many: Location hierarchy chain
  - One-to-One: User ↔ Profile
  - Many-to-Many: User ↔ Property
  - Many-to-One: Each level to parent

*Complete Entity Relationship Diagram showing all entities and their relationships*

</div>

### Database Entities Overview

| Entity | Type | Purpose | Key Relationships |
|--------|------|---------|-------------------|
| **Province** | Geography | Top-level administrative division | → District (1:M) |
| **District** | Geography | Second-level division | ← Province (M:1), → Sector (1:M) |
| **Sector** | Geography | Third-level division | ← District (M:1), → Cell (1:M) |
| **Cell** | Geography | Fourth-level division | ← Sector (M:1), → Village (1:M) |
| **Village** | Geography | Fifth-level division | ← Cell (M:1), → User (1:M) |
| **User** | People | Registered system users | ← Village (M:1), ↔ Profile (1:1), ↔ Property (M:M) |
| **Profile** | People | User profile information | ↔ User (1:1) |
| **Property** | Assets | Land properties | ↔ User (M:M), ← District (M:1) |
| **Owner_Property** | Junction | Many-to-Many join table | Links User ↔ Property |

### Relationship Summary

```
Province (1) ──┬─→ District (M) ──┬─→ Sector (M) ──┬─→ Cell (M) ──┬─→ Village (M) ──┬─→ User (M)
               │                   │                 │              │                  │
               │                   │                 │              │                  ├─→ Profile (1:1)
               │                   │                 │              │                  │
               │                   │                 │              │                  └─→ Property (M:M)
               │                   │                 │              │
               │                   └─────────────────┴──────────────┴─→ Property (M)
```

---

## ✨ Key Features

### 1. 🗺️ Rwanda Administrative Hierarchy Management

- Complete 5-level location structure implementation
- Province → District → Sector → Cell → Village
- Cascading relationships with proper foreign key constraints
- Efficient data retrieval at any hierarchy level

### 2. 👥 User Management System

- User registration with village-level precision
- Automatic location hierarchy linking
- Email uniqueness validation using `existsByEmail()`
- Profile management with One-to-One relationship

### 3. 🏘️ Property Management

- Land property registration
- Multiple ownership support (Many-to-Many)
- District-level property tracking
- Owner-property relationship management

### 4. 🔍 Advanced Query Capabilities

- Retrieve users by province code or name
- JPQL queries with relationship traversal
- Efficient JOIN operations
- Custom repository methods

### 5. 📄 Pagination & Sorting

- Spring Data JPA Pageable implementation
- Customizable page size and number
- Multi-field sorting support
- Performance optimization for large datasets

### 6. 🛡️ Data Integrity

- Foreign key constraints
- Cascade operations
- Unique constraints on codes and emails
- Proper null handling

---

## 🛠️ Technology Stack

### Backend Framework
- **Spring Boot 3.2.0** - Modern Java framework
- **Spring Data JPA** - Database abstraction layer
- **Hibernate** - ORM implementation

### Database
- **PostgreSQL 12+** - Relational database
- **JDBC Driver** - Database connectivity

### Build & Dependencies
- **Maven 3.6+** - Dependency management
- **Java 17** - Programming language

### Additional Libraries
- **Lombok** - Boilerplate code reduction
- **Jackson** - JSON serialization
- **Spring Web** - REST API support

---

## 🏗️ Database Architecture

### Design Principles

1. **Normalization**: All tables follow Third Normal Form (3NF)
2. **Referential Integrity**: Foreign keys enforce relationships
3. **No Redundancy**: Users store only village_id, not entire hierarchy
4. **Scalability**: Indexed foreign keys for fast queries
5. **Maintainability**: Clear naming conventions and structure

### Key Design Decisions

#### ✅ Why Users Store Only Village ID

**Problem**: Should users store province_id, district_id, sector_id, cell_id, AND village_id?

**Solution**: Store ONLY village_id!

**Benefits**:
- ✅ Eliminates data redundancy
- ✅ Prevents data inconsistency
- ✅ Follows normalization principles
- ✅ Easier to maintain
- ✅ Full hierarchy accessible via relationships

**Example**:
```java
// User only stores village_id
User user = userRepository.findById(1);

// But can access entire hierarchy
Province province = user.getVillage().getCell().getSector().getDistrict().getProvince();
// Or using convenience methods
Province province = user.getProvince();
```

---

## 📁 Project Structure

```
land-registration-system/
├── src/
│   ├── main/
│   │   ├── java/com/example/landregistration/
│   │   │   ├── controller/
│   │   │   │   ├── location/
│   │   │   │   │   ├── ProvinceController.java
│   │   │   │   │   ├── DistrictController.java
│   │   │   │   │   ├── SectorController.java
│   │   │   │   │   ├── CellController.java
│   │   │   │   │   └── VillageController.java
│   │   │   │   └── UserController.java
│   │   │   ├── entity/
│   │   │   │   ├── location/
│   │   │   │   │   ├── Province.java
│   │   │   │   │   ├── District.java
│   │   │   │   │   ├── Sector.java
│   │   │   │   │   ├── Cell.java
│   │   │   │   │   └── Village.java
│   │   │   │   ├── User.java
│   │   │   │   ├── Profile.java
│   │   │   │   └── Property.java
│   │   │   ├── repository/
│   │   │   │   ├── location/
│   │   │   │   │   ├── ProvinceRepository.java
│   │   │   │   │   ├── DistrictRepository.java
│   │   │   │   │   ├── SectorRepository.java
│   │   │   │   │   ├── CellRepository.java
│   │   │   │   │   └── VillageRepository.java
│   │   │   │   ├── UserRepository.java
│   │   │   │   └── PropertyRepository.java
│   │   │   ├── service/
│   │   │   │   ├── location/
│   │   │   │   │   ├── ProvinceService.java
│   │   │   │   │   ├── DistrictService.java
│   │   │   │   │   ├── SectorService.java
│   │   │   │   │   ├── CellService.java
│   │   │   │   │   └── VillageService.java
│   │   │   │   └── UserService.java
│   │   │   └── LandRegistrationApplication.java
│   │   └── resources/
│   │       └── application.properties
├── docs/
│   └── erd-diagram.png
├── pom.xml
├── README.md
├── IMPLEMENTATION_GUIDE.md
├── SUBMISSION_CHECKLIST.md
├── VIVA_VOCE_GUIDE.md
└── PROJECT_VERIFICATION_REPORT.md
```

---

## 🚀 Installation & Setup

### Prerequisites

Ensure you have the following installed:

- ☑️ **Java 17** or higher ([Download](https://www.oracle.com/java/technologies/downloads/))
- ☑️ **Maven 3.6+** ([Download](https://maven.apache.org/download.cgi))
- ☑️ **PostgreSQL 12+** ([Download](https://www.postgresql.org/download/))
- ☑️ **Git** ([Download](https://git-scm.com/downloads))

### Step 1: Clone the Repository

```bash
git clone https://github.com/manzifred/midterm_26634_groupE.git
cd midterm_26634_groupE
```

### Step 2: Create Database

Open PostgreSQL and create the database:

```sql
CREATE DATABASE land_registration_db;
```

### Step 3: Configure Database Connection

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/land_registration_db
spring.datasource.username=postgres
spring.datasource.password=your_password_here

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

### Step 4: Build the Project

```bash
mvn clean install
```

### Step 5: Run the Application

```bash
mvn spring-boot:run
```

The application will start on **http://localhost:8080**

### Step 6: Verify Installation

Check if the application is running:

```bash
curl http://localhost:8080/api/users
```

---

## 📡 API Documentation

### Base URL
```
http://localhost:8080
```

### 🗺️ Location Management Endpoints

#### 1. Province Management

**Create Province**
```http
POST /api/location/provinces?name=Kigali&code=KGL
```

**Get All Provinces**
```http
GET /api/location/provinces?page=0&size=10
```

**Get Province by ID**
```http
GET /api/location/provinces/{id}
```

#### 2. District Management

**Create District**
```http
POST /api/location/districts?name=Nyarugenge&code=NYR&provinceId=1
```

**Get Districts by Province**
```http
GET /api/location/districts/province/{provinceId}
```

#### 3. Sector Management

**Create Sector**
```http
POST /api/location/sectors?name=Rugenge&code=RGN&districtId=1
```

**Get Sectors by District**
```http
GET /api/location/sectors/district/{districtId}
```

#### 4. Cell Management

**Create Cell**
```http
POST /api/location/cells?name=Muhima&code=MHM&sectorId=1
```

**Get Cells by Sector**
```http
GET /api/location/cells/sector/{sectorId}
```

#### 5. Village Management

**Create Village**
```http
POST /api/location/villages?name=Muhima Village&code=MHMV&cellId=1
```

**Get Villages by Cell**
```http
GET /api/location/villages/cell/{cellId}
```

### 👥 User Management Endpoints

#### 1. Create User

**Important**: Users are created with ONLY `villageId` (not province!)

```http
POST /api/users
Content-Type: application/json

{
  "name": "Fred Manzi",
  "email": "fred.manzi@example.com",
  "villageId": 1
}
```

**Response**:
```json
{
  "id": 1,
  "name": "Fred Manzi",
  "email": "fred.manzi@example.com",
  "village": {
    "id": 1,
    "name": "Muhima Village",
    "code": "MHMV",
    "cell": {
      "id": 1,
      "name": "Muhima",
      "code": "MHM",
      "sector": {
        "id": 1,
        "name": "Rugenge",
        "code": "RGN",
        "district": {
          "id": 1,
          "name": "Nyarugenge",
          "code": "NYR",
          "province": {
            "id": 1,
            "name": "Kigali",
            "code": "KGL"
          }
        }
      }
    }
  }
}
```

#### 2. Get All Users (with Pagination & Sorting)

```http
GET /api/users?page=0&size=10&sort=name,asc
```

**Query Parameters**:
- `page`: Page number (default: 0)
- `size`: Items per page (default: 10)
- `sort`: Sort field and direction (e.g., `name,asc` or `email,desc`)

**Response**:
```json
{
  "content": [
    {
      "id": 1,
      "name": "Fred Manzi",
      "email": "fred.manzi@example.com",
      "village": {...}
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalElements": 5,
  "totalPages": 1
}
```

#### 3. Get Users by Province Code

```http
GET /api/users/province/code/KGL?page=0&size=10
```

#### 4. Get Users by Province Name

```http
GET /api/users/province/name/Kigali?page=0&size=10
```

#### 5. Check Email Existence

```http
GET /api/users/exists/fred.manzi@example.com
```

**Response**: `true` or `false`

---

## 🧪 Testing Guide

### Manual Testing with Postman/cURL

#### Test Scenario 1: Create Complete Location Hierarchy

```bash
# 1. Create Province
curl -X POST "http://localhost:8080/api/location/provinces?name=Kigali&code=KGL"

# 2. Create District (use province ID from step 1)
curl -X POST "http://localhost:8080/api/location/districts?name=Nyarugenge&code=NYR&provinceId=1"

# 3. Create Sector (use district ID from step 2)
curl -X POST "http://localhost:8080/api/location/sectors?name=Rugenge&code=RGN&districtId=1"

# 4. Create Cell (use sector ID from step 3)
curl -X POST "http://localhost:8080/api/location/cells?name=Muhima&code=MHM&sectorId=1"

# 5. Create Village (use cell ID from step 4)
curl -X POST "http://localhost:8080/api/location/villages?name=Muhima%20Village&code=MHMV&cellId=1"
```

#### Test Scenario 2: Create User with Village

```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Fred Manzi",
    "email": "fred.manzi@example.com",
    "villageId": 1
  }'
```

#### Test Scenario 3: Test Pagination

```bash
# Get first page (10 users)
curl "http://localhost:8080/api/users?page=0&size=10"

# Get second page
curl "http://localhost:8080/api/users?page=1&size=10"

# Get with sorting
curl "http://localhost:8080/api/users?page=0&size=10&sort=name,asc"
```

#### Test Scenario 4: Test Province Queries

```bash
# By province code
curl "http://localhost:8080/api/users/province/code/KGL"

# By province name
curl "http://localhost:8080/api/users/province/name/Kigali"
```

#### Test Scenario 5: Test Email Existence

```bash
# Check if email exists
curl "http://localhost:8080/api/users/exists/fred.manzi@example.com"
# Returns: true

# Check non-existent email
curl "http://localhost:8080/api/users/exists/nonexistent@example.com"
# Returns: false
```

---

<div align="center">

**Built using Spring Boot**

**© 2026 Fred Manzi - All Rights Reserved**

</div>
