package com.example.landregistration.repository.location;

import com.example.landregistration.entity.location.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {
    District findByCode(String code);
    District findByName(String name);
}
