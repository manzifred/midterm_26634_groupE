package com.example.landregistration.entity.location;

import jakarta.persistence.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "village")
public class Village {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String code;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cell_id", nullable = false)
    private Cell cell;

    @OneToMany(mappedBy = "village", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore // Prevents circular reference during serialization
    private List<com.example.landregistration.entity.User> users;

    public Village() {}

    public Village(String name, String code, Cell cell) {
        this.name = name;
        this.code = code;
        this.cell = cell;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public Cell getCell() { return cell; }
    public void setCell(Cell cell) { this.cell = cell; }

    public List<com.example.landregistration.entity.User> getUsers() { return users; }
    public void setUsers(List<com.example.landregistration.entity.User> users) { this.users = users; }
}
