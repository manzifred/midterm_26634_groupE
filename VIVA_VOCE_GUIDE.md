# 🎓 Viva-Voce Study Guide

## Theory Questions You Might Be Asked

### 1. Database Relationships

**Q: What are the 4 main types of database relationships?**

A:
1. **One-to-One (1:1)**: Each record in Table A relates to exactly one record in Table B
   - Example: User ↔ Profile
   - Implementation: FK on child side with unique constraint
   - Your code: `@OneToOne @JoinColumn(name = "user_id")`

2. **One-to-Many (1:M)**: One record in Table A relates to many records in Table B
   - Example: Province → Districts (one province has many districts)
   - Implementation: FK on child table
   - Your code: `@OneToMany(mappedBy = "province")`

3. **Many-to-One (M:1)**: Reverse of One-to-Many
   - Example: Districts → Province (many districts belong to one province)
   - Implementation: FK on many side
   - Your code: `@ManyToOne @JoinColumn(name = "province_id")`

4. **Many-to-Many (M:M)**: Many records in Table A relate to many records in Table B
   - Example: Users ↔ Properties (one user can own many properties, one property can have many owners)
   - Implementation: Join table in between
   - Your code: `@ManyToMany @JoinTable(name = "owner_property")`

---

**Q: Explain the Location Hierarchy in Your Project**

A:
```
Province (1)
    ↓ One Province has many Districts
District (M)
    ↓ One District has many Sectors
Sector (M)
    ↓ One Sector has many Cells
Cell (M)
    ↓ One Cell has many Villages
Village (M)
    ↓ One Village has many Users
User (M)
```

This is implemented as a **chain of One-to-Many relationships**, where:
- Each parent level stores a collection of child entities
- Each child entity has a foreign key to its parent
- This ensures hierarchical data organization

---

### 2. Your Implementation Specifics

**Q: Why did you remove the redundant foreign keys (district_id, sector_id, cell_id) from the User table?**

A:
Original design was:
```java
User {
    @ManyToOne private Village village;
    @ManyToOne private District district;    // ❌ Redundant
    @ManyToOne private Sector sector;        // ❌ Redundant
    @ManyToOne private Cell cell;            // ❌ Redundant
}
```

Issues with redundant FKs:
1. **Data Duplication**: Same hierarchy level stored multiple times
2. **Inconsistency Risk**: User could have conflicting locations (village from one cell, but sector from different cell)
3. **Violates 3NF**: Not in Third Normal Form
4. **Storage Waste**: Database size increased unnecessarily
5. **Update Anomalies**: If village changes, must update district/sector/cell too

Improved design:
```java
User {
    @ManyToOne private Village village;  // ✅ Only necessary FK
}
```

Benefits:
- Data is accessed through relationships: User → Village → Cell → Sector → District → Province
- Single source of truth (village_id)
- Follows database normalization rules (3NF)
- Same outcome (can retrieve all hierarchy), better design

---

**Q: How does a User get access to Province information if there's no direct foreign key?**

A:
Through relationship traversal via convenience methods:

```java
// In User.java
public Province getProvince() {
    return getDistrict() != null ? getDistrict().getProvince() : null;
}

public District getDistrict() {
    return getSector() != null ? getSector().getDistrict() : null;
}

public Sector getSector() {
    return getCell() != null ? getCell().getSector() : null;
}

public Cell getCell() {
    return village != null ? village.getCell() : null;
}

// When we call: user.getProvince()
// Returns: user.village.cell.sector.district.province
```

This works because:
1. Hibernate automatically loads related entities (with EAGER fetch)
2. JPA manages the object graph
3. We navigate through the chain of relationships
4. No database query needed for traversal (already in memory)

---

### 3. Query Implementation

**Q: Explain the JPQL query for retrieving users by province code**

A:
```java
@Query("SELECT u FROM User u WHERE u.village.cell.sector.district.province.code = :provinceCode")
Page<User> findUsersByProvinceCode(@Param("provinceCode") String provinceCode, Pageable pageable);
```

How it works:
1. **JPQL Syntax**: Uses entity and relationship names (not SQL column names)
2. **Relationship Navigation**: `u.village.cell.sector.district.province` follows the object graph
3. **Hibernate Translation**: Converts to SQL with proper JOINs:
   ```sql
   SELECT u.* FROM user_table u
   INNER JOIN village v ON u.village_id = v.id
   INNER JOIN cell c ON v.cell_id = c.id
   INNER JOIN sector s ON c.sector_id = s.id
   INNER JOIN district d ON s.district_id = d.id
   INNER JOIN province p ON d.province_id = p.id
   WHERE p.code = ?
   ```
4. **Parameter Binding**: `:provinceCode` is a named parameter (SQL injection safe)
5. **Pagination**: Pageable parameter adds LIMIT and OFFSET

---

### 4. Spring Boot Concepts

**Q: What is Pagination and why is it important?**

A:
Pagination: Dividing large result sets into smaller pages

Why important:
1. **Performance**: Load only needed records (e.g., 10/1000 users)
2. **Memory Efficiency**: Don't load entire database into memory
3. **User Experience**: Faster response times
4. **Database Load**: Reduces query stress
5. **Scalability**: Supports millions of records

Implementation in Spring Data JPA:
```java
// In controller
@GetMapping
public Page<User> getAllUsers(@PageableDefault(size = 10) Pageable pageable) {
    return userService.getAllUsers(pageable);
}

// In service
@SuppressWarnings("null")
public Page<User> getAllUsers(Pageable pageable) {
    return userRepository.findAll(pageable);
}

// In repository - automatically supports Pageable
public interface UserRepository extends JpaRepository<User, Long> {
    // JpaRepository provides findAll(Pageable)
}
```

Response includes:
```json
{
    "content": [{...}, {...}],
    "pageable": {
        "pageNumber": 0,
        "pageSize": 10
    },
    "totalElements": 150,
    "totalPages": 15
}
```

---

**Q: What is the difference between FetchType.EAGER and FetchType.LAZY?**

A:
| Aspect | EAGER | LAZY |
|--------|-------|------|
| Definition | Immediately load related entities | Load related entities only when accessed |
| Performance on Single Query | Slower (loads extra data) | Faster (loads only main entity) |
| Performance on Multiple Accesses | Faster (data already loaded) | Slower (triggers new query each time) |
| Use Case | When you always need the related data | When you might not need related data |
| Memory | Higher (all related data loaded) | Lower (only loaded if needed) |

Your implementation:
```java
// EAGER - Used in User/Location classes where we always show hierarchy
@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name = "village_id")
private Village village;

// LAZY - Used for Profile where it might not be accessed
@OneToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "user_id")
private User user;
```

---

### 5. Validation & Testing

**Q: Explain the existsByEmail() method and why it's used**

A:
```java
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
```

What happens:
1. Spring Data JPA generates: `SELECT 1 FROM user_table WHERE email = ? LIMIT 1`
2. Returns `true` if email found, `false` otherwise
3. More efficient than loading full User object

Usage in controller:
```java
if (userService.existsByEmail(email)) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body("Email already exists");
}
```

Why use it:
- Prevents duplicate emails
- Data integrity enforcement
- Business rule validation
- Returns 409 Conflict HTTP status
- Efficient: Uses LIMIT 1 (stops after first match)

---

### 6. REST API Concepts

**Q: What are HTTP status codes used in your API?**

A:
- **201 Created**: User created successfully
  ```
  POST /api/users → 201 Created {user data}
  ```
- **200 OK**: Request successful
  ```
  GET /api/users → 200 OK {page of users}
  ```
- **400 Bad Request**: Missing required fields
  ```
  POST /api/users (without villageId) → 400 Bad Request
  ```
- **404 Not Found**: Resource not found
  ```
  POST /api/users (village not found) → 404 Not Found
  ```
- **409 Conflict**: Email already exists
  ```
  POST /api/users (duplicate email) → 409 Conflict
  ```

---

**Q: What is the difference between @RequestParam and @RequestBody?**

A:
| Feature | @RequestParam | @RequestBody |
|---------|---|---|
| Source | URL query parameters | HTTP request body |
| Format | Key-value pairs in URL | JSON/XML body |
| Example | `/provinces?name=X&code=Y` | `POST /users {json}` |
| Binding | Individual parameters | Object deserialization |
| Use Case | Simple CRUD operations | Complex objects |

Your implementation:
```java
// Location controllers use @RequestParam
@PostMapping
public Province createProvince(
    @RequestParam String name, 
    @RequestParam String code) {
    // Called as: POST /api/location/provinces?name=X&code=Y
}

// User controller uses @RequestBody
@PostMapping
public ResponseEntity<?> createUser(@RequestBody Map<String, Object> request) {
    // Called as: POST /api/users
    // Body: {name: "X", email: "Y", villageId: Z}
}
```

---

## 🎯 Key Takeaways for Viva-Voce

1. **Database Design**: Your project demonstrates proper normalization (3NF)
2. **Relationships**: All 4 types properly implemented
3. **Performance**: Pagination and proper fetch types
4. **Data Integrity**: Constraints and validations
5. **REST Best Practices**: Proper HTTP status codes
6. **Query Optimization**: JPQL with proper JOINs
7. **Spring Boot**: Proper use of annotations and dependency injection

---

## 💡 Possible Follow-up Questions

### Q: What would happen if you tried to delete a Province?
A: Because of `cascade = CascadeType.ALL`, deleting a Province would:
- Delete all its Districts
- Delete all Sectors of those Districts
- Delete all Cells of those Sectors
- Delete all Villages of those Cells
- Delete all Users (cascading down)
- Delete all their Profiles (if cascade configured)

This is why cascade must be used carefully - it can cause unintended bulk deletion.

### Q: How would you optimize the province query further?
A: 
1. Add indexes on foreign keys: CREATE INDEX idx_user_village ON user_table(village_id)
2. Add database index on province code: CREATE INDEX idx_province_code ON province(code)
3. Consider caching: @Cacheable for frequently accessed provinces
4. Denormalization: Store province_id directly in village (if frequent access)

### Q: What if a user tries to create a user with non-existent village ID?
A: 
- The `saveUser()` method catches this:
  ```java
  Village village = villageRepository.findById(villageId)
      .orElseThrow(() -> new RuntimeException("Village not found"));
  ```
- Returns 404 Not Found to the client
- User is not created

### Q: How does your implementation handle concurrent requests?
A:
- Spring Boot is thread-safe by default
- Database handles concurrent access with transaction isolation
- JPA transactions ensure ACID properties
- @Transactional on service methods (implicit in repository operations)

---

## 📚 Document Structure

You have three documents:
1. **IMPLEMENTATION_GUIDE.md** - Detailed explanation with code examples
2. **SUBMISSION_CHECKLIST.md** - Quick reference for all fixes
3. **This file (VIVA_VOCE_GUIDE.md)** - Theory questions and answers

Show these during viva-voce to demonstrate understanding! 🎓
