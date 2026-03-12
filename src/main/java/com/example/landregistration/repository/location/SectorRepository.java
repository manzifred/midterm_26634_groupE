package com.example.landregistration.repository.location;

import com.example.landregistration.entity.location.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Long> {
    Sector findByCode(String code);
    Sector findByName(String name);
}
