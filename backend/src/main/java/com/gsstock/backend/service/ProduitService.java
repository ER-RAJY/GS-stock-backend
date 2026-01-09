package com.gsstock.backend.service;

import com.gsstock.backend.domain.stock.Produit;
import com.gsstock.backend.domain.stock.Unite;
import com.gsstock.backend.exception.BusinessException;
import com.gsstock.backend.exception.ResourceNotFoundException;
import com.gsstock.backend.repository.ProduitRepository;
import com.gsstock.backend.web.dto.stock.CreateProduitRequest;
import com.gsstock.backend.web.dto.stock.ProduitDto;
import com.gsstock.backend.web.dto.stock.UpdateProduitRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProduitService {

    private final ProduitRepository produitRepository;
    private final StockService stockService;

    public List<ProduitDto> findAll() {
        return produitRepository.findAll().stream()
                .map(p -> toDto(p, stockService.calculateStock(p.getId())))
                .toList();
    }

    public ProduitDto findById(Long id) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit not found"));
        return toDto(produit, stockService.calculateStock(id));
    }

    private ProduitDto toDto(Produit p, BigDecimal stock) {
        return new ProduitDto(
                p.getId(),
                p.getDesignation(),
                p.getUnite().name(),
                p.getStockMin(),
                stock,
                p.isActive()
        );
    }
    // ======================
    // CREATE PRODUIT
    // ======================
    public ProduitDto create(CreateProduitRequest request) {

        Unite unite;
        try {
            unite = Unite.valueOf(request.unite().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new BusinessException("Unité invalide: " + request.unite());
        }

        Produit produit = new Produit();
        produit.setDesignation(request.designation());
        produit.setUnite(unite);
        produit.setStockMin(request.stockMin());
        produit.setActive(true);

        Produit saved = produitRepository.save(produit);

        return toDto(saved, BigDecimal.ZERO);
    }

    // ======================
    // UPDATE PRODUIT
    // ======================
    public ProduitDto update(Long id, UpdateProduitRequest request) {

        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit not found"));

        produit.setDesignation(request.designation());

        if (request.stockMin() != null) {
            produit.setStockMin(request.stockMin());
        }

        if (request.active() != null) {
            produit.setActive(request.active());
        }

        return toDto(produit, stockService.calculateStock(id));
    }


}
