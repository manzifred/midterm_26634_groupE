package com.example.landregistration.controller.location;

import com.example.landregistration.entity.location.Sector;
import com.example.landregistration.service.location.SectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/location/sectors")
public class SectorController {

    @Autowired
    private SectorService sectorService;

    @PostMapping
    public Sector createSector(@RequestParam String name, @RequestParam String code, @RequestParam Long districtId) {
        return sectorService.saveSector(name, code, districtId);
    }

    @GetMapping("/{id}")
    public Sector getSectorById(@PathVariable Long id) {
        return sectorService.getSectorById(id);
    }

    @GetMapping("/code/{code}")
    public Sector getSectorByCode(@PathVariable String code) {
        return sectorService.getSectorByCode(code);
    }

    @GetMapping("/name/{name}")
    public Sector getSectorByName(@PathVariable String name) {
        return sectorService.getSectorByName(name);
    }

    @GetMapping
    public Page<Sector> getAllSectors(@PageableDefault(size = 10) Pageable pageable) {
        return sectorService.getAllSectors(pageable);
    }
}
