package com.example.landregistration.repository.location;

import com.example.landregistration.entity.location.Village;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VillageRepository extends JpaRepository<Village, Long> {
    Village findByCode(String code);
    Village findByName(String name);
}
