# Land Registration & Management System

A comprehensive Spring Boot application implementing Rwanda's administrative hierarchy for land registration and user management.

**Student ID**: 26634 | **Group**: E  
**Repository**: [github.com/manzifred/midterm_26634_groupE](https://github.com/manzifred/midterm_26634_groupE)

---

## Overview

This system manages Rwanda's five-level administrative structure (Province → District → Sector → Cell → Village) with user registration and property management capabilities. Built using Spring Boot 3.2.0, PostgreSQL, and JPA/Hibernate, it demonstrates proper database design, RESTful API development, and efficient data management.

---

## Entity Relationship Diagram

![ERD Diagram](https://raw.githubusercontent.com/manzifred/midterm_26634_groupE/main/docs/erd-diagram.png)

### Database Structure

The system consists of **9 entities** organized into three categories:

**Geographic Hierarchy (5 levels)**:
- Province → District → Sector → Cell → Village

**User Management**:
- User (linked to Village)
- Profile (One-to-One with User)

**Asset Management**:
- Property (Many-to-Many with User)
- owner_property (Junction table)

### Relationships Implemented

| Relationship Type | Implementation | Description |
|------------------|----------------|-------------|
| **One-to-Many** | Province → District → Sector → Cell → Village → User | Geographic hierarchy chain |
| **One-to-One** | User ↔ Profile | Each user has one profile |
| **Many-to-Many** | User ↔ Property | Users can own multiple properties; properties can have multiple owners |

---

## Technology Stack

- **Framework**: Spring Boot 3.2.0
- **Language**: Java 17
- **Database**: PostgreSQL 12+
- **ORM**: Hibernate (JPA)
- **Build Tool**: Maven 3.6+
- **Libraries**: Lombok, Jackson, Spring Data JPA

---

## Key Features

### 1. Location Hierarchy Management

The system implements Rwanda's complete administrative structure:

```
Province (e.g., Kigali City)
  └─ District (e.g., Gasabo)
      └─ Sector (e.g., Remera)
          └─ Cell (e.g., Rukiri I)
              └─ Village (e.g., Village A)
                  └─ Users
```

**Implementation Approach**:
- Users store only `village_id` (not the entire hierarchy)
- Full location path accessible through JPA relationships
- Prevents data redundancy and maintains normalization

### 2. User Management

- User registration with village-level location
- Email uniqueness validation using `existsByEmail()`
- Automatic hierarchy linking through relationships
- Profile management (One-to-One relationship)

### 3. Property Management

- Land property registration
- Multiple ownership support (Many-to-Many)
- District-level property tracking

### 4. Advanced Querying

- Retrieve users by province code or name
- JPQL queries with relationship traversal
- Efficient JOIN operations across hierarchy levels

### 5. Pagination & Sorting

- Spring Data JPA Pageable implementation
- Customizable page size and sorting
- Performance optimization for large datasets

---

## Project Structure

```
src/main/java/com/example/landregistration/
├── controller/
│   ├── location/          # Province, District, Sector, Cell, Village controllers
│   └── UserController.java
├── entity/
│   ├── location/          # Geographic entities
│   ├── User.java
│   ├── Profile.java
│   └── Property.java
├── repository/
│   ├── location/          # Location repositories
│   └── UserRepository.java
├── service/
│   ├── location/          # Location services
│   └── UserService.java
└── LandRegistrationApplication.java
```

---

## Installation & Setup

### Prerequisites

- Java 17+
- Maven 3.6+
- PostgreSQL 12+

### Steps

1. **Clone the repository**
```bash
git clone https://github.com/manzifred/midterm_26634_groupE.git
cd midterm_26634_groupE
```

2. **Create database**
```sql
CREATE DATABASE land_registration_db;
```

3. **Configure database** (edit `src/main/resources/application.properties`)
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/land_registration_db
spring.datasource.username=postgres
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

4. **Build and run**
```bash
mvn clean install
mvn spring-boot:run
```

Application runs on: **http://localhost:8080**

---

## API Endpoints

### Location Management

Create the hierarchy from top to bottom:

```http
# 1. Create Province
POST /api/location/provinces?name=Kigali&code=KGL

# 2. Create District
POST /api/location/districts?name=Gasabo&code=GS&provinceId=1

# 3. Create Sector
POST /api/location/sectors?name=Remera&code=RM&districtId=1

# 4. Create Cell
POST /api/location/cells?name=Rukiri I&code=RK&sectorId=1

# 5. Create Village
POST /api/location/villages?name=Village A&code=VA&cellId=1
```

### User Management

```http
# Create User (only villageId required)
POST /api/users
Content-Type: application/json

{
  "name": "Fred Manzi",
  "email": "fred@example.com",
  "villageId": 1
}

# Get All Users (with pagination)
GET /api/users?page=0&size=10&sort=name,asc

# Get Users by Province Code
GET /api/users/province/code/KGL

# Get Users by Province Name
GET /api/users/province/name/Kigali

# Check Email Existence
GET /api/users/exists/fred@example.com
```

---

## Implementation Details

### 1. Location Saving

Users are saved with **only village_id**. The complete hierarchy is accessible through relationships:

```java
// UserService.java
public User saveUser(String name, String email, Long villageId) {
    Village village = villageRepository.findById(villageId)
            .orElseThrow(() -> new RuntimeException("Village not found"));
    
    User user = new User(name, email, village);
    return userRepository.save(user);
}
```

**Benefits**:
- Eliminates data redundancy
- Maintains database normalization (3NF)
- Full hierarchy accessible via: `user.getVillage().getCell().getSector().getDistrict().getProvince()`

### 2. Pagination & Sorting

Implemented using Spring Data JPA's `Pageable`:

```java
// UserController.java
@GetMapping
public Page<User> getAllUsers(@PageableDefault(size = 10) Pageable pageable) {
    return userService.getAllUsers(pageable);
}
```

**How it works**:
- Generates SQL with LIMIT and OFFSET clauses
- Loads only requested subset of data
- Improves performance for large datasets

### 3. Many-to-Many Relationship

User ↔ Property relationship using join table:

```java
// Property.java
@ManyToMany
@JoinTable(
    name = "owner_property",
    joinColumns = @JoinColumn(name = "property_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id")
)
private List<User> owners;
```

**Join Table Structure**:
- `owner_property` contains `property_id` and `user_id`
- Allows multiple owners per property
- Allows multiple properties per user

### 4. One-to-Many Relationships

Location hierarchy chain:

```java
// District.java
@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name = "province_id", nullable = false)
private Province province;

// Province.java
@OneToMany(mappedBy = "province", cascade = CascadeType.ALL)
private List<District> districts;
```

**Foreign Keys**: Each level stores parent's ID (province_id, district_id, sector_id, cell_id)

### 5. One-to-One Relationship

User ↔ Profile:

```java
// Profile.java
@OneToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "user_id", nullable = false)
private User user;

// User.java
@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
private Profile profile;
```

### 6. existBy() Method

Email uniqueness check:

```java
// UserRepository.java
boolean existsByEmail(String email);

// Usage in UserController.java
if (userService.existsByEmail(email)) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body("Email already exists");
}
```

**How it works**: Spring Data JPA generates a COUNT query without loading the entity.

### 7. Province Retrieval

JPQL query traversing relationships:

```java
// UserRepository.java
@Query("SELECT u FROM User u WHERE u.village.cell.sector.district.province.code = :code")
Page<User> findUsersByProvinceCode(@Param("code") String code, Pageable pageable);

@Query("SELECT u FROM User u WHERE u.village.cell.sector.district.province.name = :name")
Page<User> findUsersByProvinceName(@Param("name") String name, Pageable pageable);
```

**Generated SQL**: Hibernate creates INNER JOINs across all five location tables.

---

## Database Design Principles

1. **Normalization**: All tables follow Third Normal Form (3NF)
2. **Referential Integrity**: Foreign keys enforce relationships
3. **No Redundancy**: Users store only village_id, not entire hierarchy
4. **Scalability**: Indexed foreign keys for fast queries
5. **Maintainability**: Clear naming conventions and structure

---

## Testing

### Example Test Flow

```bash
# 1. Create location hierarchy
curl -X POST "http://localhost:8080/api/location/provinces?name=Kigali&code=KGL"
curl -X POST "http://localhost:8080/api/location/districts?name=Gasabo&code=GS&provinceId=1"
curl -X POST "http://localhost:8080/api/location/sectors?name=Remera&code=RM&districtId=1"
curl -X POST "http://localhost:8080/api/location/cells?name=Rukiri I&code=RK&sectorId=1"
curl -X POST "http://localhost:8080/api/location/villages?name=Village A&code=VA&cellId=1"

# 2. Create user
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"Fred Manzi","email":"fred@example.com","villageId":1}'

# 3. Query users by province
curl "http://localhost:8080/api/users/province/code/KGL"

# 4. Test pagination
curl "http://localhost:8080/api/users?page=0&size=10&sort=name,asc"

# 5. Check email existence
curl "http://localhost:8080/api/users/exists/fred@example.com"
```

---

## Project Status

✅ **Compilation**: BUILD SUCCESS (zero errors)  
✅ **Database**: PostgreSQL with proper schema  
✅ **API**: All endpoints functional  
✅ **Relationships**: All 4 types implemented  
✅ **Pagination**: Fully working  
✅ **Queries**: Province retrieval by code and name  

---

## Author

**Fred Manzi**  
Student ID: 26634 | Group E

---

**Built with Spring Boot 3.2.0 | Java 17 | PostgreSQL**
