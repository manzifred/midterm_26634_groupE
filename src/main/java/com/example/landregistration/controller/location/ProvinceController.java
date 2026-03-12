package com.example.landregistration.controller.location;

import com.example.landregistration.entity.location.Province;
import com.example.landregistration.service.location.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/location/provinces")
public class ProvinceController {

    @Autowired
    private ProvinceService provinceService;

    @PostMapping
    public Province createProvince(@RequestParam String name, @RequestParam String code) {
        return provinceService.saveProvince(name, code);
    }

    @GetMapping("/{id}")
    public Province getProvinceById(@PathVariable Long id) {
        return provinceService.getProvinceById(id);
    }

    @GetMapping("/code/{code}")
    public Province getProvinceByCode(@PathVariable String code) {
        return provinceService.getProvinceByCode(code);
    }

    @GetMapping("/name/{name}")
    public Province getProvinceByName(@PathVariable String name) {
        return provinceService.getProvinceByName(name);
    }

    @GetMapping
    public Page<Province> getAllProvinces(@PageableDefault(size = 10) Pageable pageable) {
        return provinceService.getAllProvinces(pageable);
    }
}
