package com.example.landregistration.service.location;

import com.example.landregistration.entity.location.Cell;
import com.example.landregistration.entity.location.Sector;
import com.example.landregistration.repository.location.CellRepository;
import com.example.landregistration.repository.location.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CellService {

    @Autowired
    private CellRepository cellRepository;

    @Autowired
    private SectorRepository sectorRepository;

    @SuppressWarnings("null")
    public Cell saveCell(String name, String code, Long sectorId) {
        Sector sector = sectorRepository.findById(sectorId)
                .orElseThrow(() -> new RuntimeException("Sector not found"));
        
        Cell cell = new Cell(name, code, sector);
        return cellRepository.save(cell);
    }

    public Cell getCellByCode(String code) {
        return cellRepository.findByCode(code);
    }

    public Cell getCellByName(String name) {
        return cellRepository.findByName(name);
    }

    @SuppressWarnings("null")
    public Cell getCellById(Long id) {
        return cellRepository.findById(id).orElse(null);
    }

    @SuppressWarnings("null")
    public Page<Cell> getAllCells(Pageable pageable) {
        return cellRepository.findAll(pageable);
    }
}
