package com.example.landregistration.controller.location;

import com.example.landregistration.entity.location.District;
import com.example.landregistration.service.location.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/location/districts")
public class DistrictController {

    @Autowired
    private DistrictService districtService;

    @PostMapping
    public District createDistrict(@RequestParam String name, @RequestParam String code, @RequestParam Long provinceId) {
        return districtService.saveDistrict(name, code, provinceId);
    }

    @GetMapping("/{id}")
    public District getDistrictById(@PathVariable Long id) {
        return districtService.getDistrictById(id);
    }

    @GetMapping("/code/{code}")
    public District getDistrictByCode(@PathVariable String code) {
        return districtService.getDistrictByCode(code);
    }

    @GetMapping("/name/{name}")
    public District getDistrictByName(@PathVariable String name) {
        return districtService.getDistrictByName(name);
    }

    @GetMapping
    public Page<District> getAllDistricts(@PageableDefault(size = 10) Pageable pageable) {
        return districtService.getAllDistricts(pageable);
    }
}
