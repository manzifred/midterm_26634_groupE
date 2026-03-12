package com.example.landregistration.service.location;

import com.example.landregistration.entity.location.Province;
import com.example.landregistration.repository.location.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProvinceService {

    @Autowired
    private ProvinceRepository provinceRepository;

    public Province saveProvince(String name, String code) {
        Province province = new Province(name, code);
        return provinceRepository.save(province);
    }

    public Province getProvinceByCode(String code) {
        return provinceRepository.findByCode(code);
    }

    public Province getProvinceByName(String name) {
        return provinceRepository.findByName(name);
    }

    @SuppressWarnings("null")
    public Province getProvinceById(Long id) {
        return provinceRepository.findById(id).orElse(null);
    }

    @SuppressWarnings("null")
    public Page<Province> getAllProvinces(Pageable pageable) {
        return provinceRepository.findAll(pageable);
    }
}
