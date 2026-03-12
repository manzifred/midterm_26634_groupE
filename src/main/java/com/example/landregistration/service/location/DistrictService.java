package com.example.landregistration.service.location;

import com.example.landregistration.entity.location.District;
import com.example.landregistration.entity.location.Province;
import com.example.landregistration.repository.location.DistrictRepository;
import com.example.landregistration.repository.location.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DistrictService {

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private ProvinceRepository provinceRepository;

    @SuppressWarnings("null")
    public District saveDistrict(String name, String code, Long provinceId) {
        Province province = provinceRepository.findById(provinceId)
                .orElseThrow(() -> new RuntimeException("Province not found"));
        
        District district = new District(name, code, province);
        return districtRepository.save(district);
    }

    public District getDistrictByCode(String code) {
        return districtRepository.findByCode(code);
    }

    public District getDistrictByName(String name) {
        return districtRepository.findByName(name);
    }

    @SuppressWarnings("null")
    public District getDistrictById(Long id) {
        return districtRepository.findById(id).orElse(null);
    }

    @SuppressWarnings("null")
    public Page<District> getAllDistricts(Pageable pageable) {
        return districtRepository.findAll(pageable);
    }
}
