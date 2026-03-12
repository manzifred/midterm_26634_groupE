package com.example.landregistration.controller;

import com.example.landregistration.entity.User;
import com.example.landregistration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // 1. Create a new User (must provide village ID only)
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody Map<String, Object> request) {
        try {
            String name = (String) request.get("name");
            String email = (String) request.get("email");
            Long villageId = ((Number) request.get("villageId")).longValue();

            if (name == null || email == null || villageId == null) {
                return ResponseEntity.badRequest().body("name, email, and villageId are required");
            }

            // Check if email already exists
            if (userService.existsByEmail(email)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
            }

            User user = userService.saveUser(name, email, villageId);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // 2. Get All Users with Pagination (Criteria 3)
    @GetMapping
    public Page<User> getAllUsers(@PageableDefault(size = 10) Pageable pageable) {
        return userService.getAllUsers(pageable);
    }

    // 3. Search by Province Code (Criteria 8)
    @GetMapping("/province/code/{code}")
    public Page<User> getUsersByProvinceCode(@PathVariable String code, Pageable pageable) {
        return userService.getUsersByProvinceCode(code, pageable);
    }

    // 4. Search by Province Name (Criteria 8)
    @GetMapping("/province/name/{name}")
    public Page<User> getUsersByProvinceName(@PathVariable String name, Pageable pageable) {
        return userService.getUsersByProvinceName(name, pageable);
    }

    // 5. Check if Email Exists (Criteria 7)
    @GetMapping("/exists/{email}")
    public boolean checkEmailExists(@PathVariable String email) {
        return userService.existsByEmail(email);
    }
}