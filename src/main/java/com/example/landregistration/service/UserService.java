package com.example.landregistration.service;

import com.example.landregistration.entity.User;
import com.example.landregistration.entity.location.Village;
import com.example.landregistration.repository.UserRepository;
import com.example.landregistration.repository.location.VillageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VillageRepository villageRepository;

    @SuppressWarnings("null")
    public User saveUser(String name, String email, Long villageId) {
        Village village = villageRepository.findById(villageId)
                .orElseThrow(() -> new RuntimeException("Village not found"));
        
        User user = new User(name, email, village);
        return userRepository.save(user);
    }

    public Page<User> getUsersByProvinceCode(String code, Pageable pageable) {
        return userRepository.findUsersByProvinceCode(code, pageable);
    }

    public Page<User> getUsersByProvinceName(String name, Pageable pageable) {
        return userRepository.findUsersByProvinceName(name, pageable);
    }

    @SuppressWarnings("null")
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}