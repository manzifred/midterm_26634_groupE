package com.example.landregistration.repository.location;

import com.example.landregistration.entity.location.Cell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CellRepository extends JpaRepository<Cell, Long> {
    Cell findByCode(String code);
    Cell findByName(String name);
}
