package com.example.landregistration.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.example.landregistration.entity.location.Village;
import com.example.landregistration.entity.location.District;
import com.example.landregistration.entity.location.Sector;
import com.example.landregistration.entity.location.Cell;
import com.example.landregistration.entity.location.Province;

@Entity
@Table(name = "user_table")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Prevents 500 Error
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    @ManyToOne(fetch = FetchType.EAGER) // EAGER ensures data shows in Postman
    @JoinColumn(name = "village_id")
    @JsonIgnoreProperties("users") // Stops loop back to users
    private Village village;

    public User() {}

    public User(String name, String email, Village village) {
        this.name = name;
        this.email = email;
        this.village = village;
    }

    // --- GETTERS AND SETTERS ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Village getVillage() { return village; }
    public void setVillage(Village village) { this.village = village; }
    
    // Convenience methods to access hierarchy through village
    public Cell getCell() { return village != null ? village.getCell() : null; }
    public Sector getSector() { return getCell() != null ? getCell().getSector() : null; }
    public District getDistrict() { return getSector() != null ? getSector().getDistrict() : null; }
    public Province getProvince() { return getDistrict() != null ? getDistrict().getProvince() : null; }
}