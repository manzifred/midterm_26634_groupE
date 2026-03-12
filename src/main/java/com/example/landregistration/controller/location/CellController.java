package com.example.landregistration.controller.location;

import com.example.landregistration.entity.location.Cell;
import com.example.landregistration.service.location.CellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/location/cells")
public class CellController {

    @Autowired
    private CellService cellService;

    @PostMapping
    public Cell createCell(@RequestParam String name, @RequestParam String code, @RequestParam Long sectorId) {
        return cellService.saveCell(name, code, sectorId);
    }

    @GetMapping("/{id}")
    public Cell getCellById(@PathVariable Long id) {
        return cellService.getCellById(id);
    }

    @GetMapping("/code/{code}")
    public Cell getCellByCode(@PathVariable String code) {
        return cellService.getCellByCode(code);
    }

    @GetMapping("/name/{name}")
    public Cell getCellByName(@PathVariable String name) {
        return cellService.getCellByName(name);
    }

    @GetMapping
    public Page<Cell> getAllCells(@PageableDefault(size = 10) Pageable pageable) {
        return cellService.getAllCells(pageable);
    }
}
