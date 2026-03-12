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

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("user") // Prevents circular reference
    private Profile profile;

    @ManyToMany(mappedBy = "owners", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("owners") // Prevents circular reference
    private java.util.List<Property> properties;

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

    public Profile getProfile() { return profile; }
    public void setProfile(Profile profile) { this.profile = profile; }

    public java.util.List<Property> getProperties() { return properties; }
    public void setProperties(java.util.List<Property> properties) { this.properties = properties; }
    
    // Convenience methods to access hierarchy through village
    public Cell getCell() { return village != null ? village.getCell() : null; }
    public Sector getSector() { return getCell() != null ? getCell().getSector() : null; }
    public District getDistrict() { return getSector() != null ? getSector().getDistrict() : null; }
    public Province getProvince() { return getDistrict() != null ? getDistrict().getProvince() : null; }
}