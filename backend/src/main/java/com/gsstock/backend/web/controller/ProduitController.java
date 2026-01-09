package com.gsstock.backend.web.controller;

import com.gsstock.backend.service.ProduitService;
import com.gsstock.backend.web.dto.stock.CreateProduitRequest;
import com.gsstock.backend.web.dto.stock.ProduitDto;
import com.gsstock.backend.web.dto.stock.UpdateProduitRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produits")
@RequiredArgsConstructor
public class ProduitController {

    private final ProduitService produitService;

    // ======================
    // GET ALL PRODUITS
    // ======================
    @GetMapping
    public List<ProduitDto> getAll() {
        return produitService.findAll();
    }

    // ======================
    // GET ONE PRODUIT
    // ======================
    @GetMapping("/{id}")
    public ProduitDto getById(@PathVariable Long id) {
        return produitService.findById(id);
    }
    // ======================
    // CREATE PRODUIT
    // ======================
    @PostMapping
    public ProduitDto create(@RequestBody @Valid CreateProduitRequest request) {
        return produitService.create(request);
    }
    // ======================
// UPDATE PRODUIT
// ======================
    @PutMapping("/{id}")
    public ProduitDto update(
            @PathVariable Long id,
            @RequestBody @Valid UpdateProduitRequest request
    ) {
        return produitService.update(id, request);
    }

    // ======================
// DEACTIVATE PRODUIT
// ======================
    @DeleteMapping("/{id}")
    public void deactivate(@PathVariable Long id) {
        produitService.update(id, new UpdateProduitRequest(
                " ",
                null,
                false
        ));
    }

}
