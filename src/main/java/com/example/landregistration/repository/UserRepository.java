package com.example.landregistration.repository;

import com.example.landregistration.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    // Retrieve users by province code - traverse through village hierarchy
    @Query("SELECT u FROM User u WHERE u.village.cell.sector.district.province.code = :provinceCode")
    Page<User> findUsersByProvinceCode(@Param("provinceCode") String provinceCode, Pageable pageable);

    // Retrieve users by province name - traverse through village hierarchy
    @Query("SELECT u FROM User u WHERE u.village.cell.sector.district.province.name = :provinceName")
    Page<User> findUsersByProvinceName(@Param("provinceName") String provinceName, Pageable pageable);
}