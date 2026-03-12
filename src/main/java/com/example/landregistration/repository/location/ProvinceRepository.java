package com.example.landregistration.repository.location;

import com.example.landregistration.entity.location.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long> {
    Province findByCode(String code);
    Province findByName(String name);
}
