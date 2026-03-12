package com.example.landregistration.entity;

import jakarta.persistence.*;
import java.util.List;
import com.example.landregistration.entity.location.District;

@Entity
@Table(name = "property")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", nullable = false)
    private District district;

    @ManyToMany
    @JoinTable(
        name = "owner_property",
        joinColumns = @JoinColumn(name = "property_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> owners;

    // Constructors
    public Property() {}

    public Property(String address, District district) {
        this.address = address;
        this.district = district;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public District getDistrict() { return district; }
    public void setDistrict(District district) { this.district = district; }

    public List<User> getOwners() { return owners; }
    public void setOwners(List<User> owners) { this.owners = owners; }
}