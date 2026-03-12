package com.example.landregistration.service.location;

import com.example.landregistration.entity.location.Village;
import com.example.landregistration.entity.location.Cell;
import com.example.landregistration.repository.location.VillageRepository;
import com.example.landregistration.repository.location.CellRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VillageService {

    @Autowired
    private VillageRepository villageRepository;

    @Autowired
    private CellRepository cellRepository;

    @SuppressWarnings("null")
    public Village saveVillage(String name, String code, Long cellId) {
        Cell cell = cellRepository.findById(cellId)
                .orElseThrow(() -> new RuntimeException("Cell not found"));
        
        Village village = new Village(name, code, cell);
        return villageRepository.save(village);
    }

    public Village getVillageByCode(String code) {
        return villageRepository.findByCode(code);
    }

    public Village getVillageByName(String name) {
        return villageRepository.findByName(name);
    }

    @SuppressWarnings("null")
    public Village getVillageById(Long id) {
        return villageRepository.findById(id).orElse(null);
    }

    @SuppressWarnings("null")
    public Page<Village> getAllVillages(Pageable pageable) {
        return villageRepository.findAll(pageable);
    }
}
