package com.example.landregistration.service.location;

import com.example.landregistration.entity.location.Sector;
import com.example.landregistration.entity.location.District;
import com.example.landregistration.repository.location.SectorRepository;
import com.example.landregistration.repository.location.DistrictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SectorService {

    @Autowired
    private SectorRepository sectorRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @SuppressWarnings("null")
    public Sector saveSector(String name, String code, Long districtId) {
        District district = districtRepository.findById(districtId)
                .orElseThrow(() -> new RuntimeException("District not found"));
        
        Sector sector = new Sector(name, code, district);
        return sectorRepository.save(sector);
    }

    public Sector getSectorByCode(String code) {
        return sectorRepository.findByCode(code);
    }

    public Sector getSectorByName(String name) {
        return sectorRepository.findByName(name);
    }

    @SuppressWarnings("null")
    public Sector getSectorById(Long id) {
        return sectorRepository.findById(id).orElse(null);
    }

    @SuppressWarnings("null")
    public Page<Sector> getAllSectors(Pageable pageable) {
        return sectorRepository.findAll(pageable);
    }
}
