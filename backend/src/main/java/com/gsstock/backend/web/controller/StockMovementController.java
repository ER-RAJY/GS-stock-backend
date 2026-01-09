package com.gsstock.backend.web.controller;

import com.gsstock.backend.service.StockMovementService;
import com.gsstock.backend.web.dto.stock.CreateMovementRequest;
import com.gsstock.backend.web.dto.stock.StockMovementDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/movements")
@RequiredArgsConstructor
public class StockMovementController {

    private final StockMovementService stockMovementService;

    // ======================
    // ADD ENTREE
    // ======================
    @PostMapping("/entree")
    public StockMovementDto addEntree(
            @RequestBody @Valid CreateMovementRequest request
    ) {
        return stockMovementService.addEntree(request);
    }

    // ======================
    // ADD SORTIE
    // ======================
    @PostMapping("/sortie")
    public StockMovementDto addSortie(
            @RequestBody @Valid CreateMovementRequest request
    ) {
        return stockMovementService.addSortie(request);
    }

    // ======================
    // LIST ALL MOVEMENTS
    // ======================
    @GetMapping
    public List<StockMovementDto> getAll() {
        return stockMovementService.findAll();

    }

    // ======================
// SEARCH MOVEMENTS
// ======================
    @GetMapping("/search")
    public List<StockMovementDto> search(
            @RequestParam(required = false) Long produitId,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to
    ) {
        return stockMovementService.search(produitId, type, from, to);
    }

}
