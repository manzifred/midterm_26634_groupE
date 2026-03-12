package com.example.landregistration.controller.location;

import com.example.landregistration.entity.location.Village;
import com.example.landregistration.service.location.VillageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/location/villages")
public class VillageController {

    @Autowired
    private VillageService villageService;

    @PostMapping
    public Village createVillage(@RequestParam String name, @RequestParam String code, @RequestParam Long cellId) {
        return villageService.saveVillage(name, code, cellId);
    }

    @GetMapping("/{id}")
    public Village getVillageById(@PathVariable Long id) {
        return villageService.getVillageById(id);
    }

    @GetMapping("/code/{code}")
    public Village getVillageByCode(@PathVariable String code) {
        return villageService.getVillageByCode(code);
    }

    @GetMapping("/name/{name}")
    public Village getVillageByName(@PathVariable String name) {
        return villageService.getVillageByName(name);
    }

    @GetMapping
    public Page<Village> getAllVillages(@PageableDefault(size = 10) Pageable pageable) {
        return villageService.getAllVillages(pageable);
    }
}
