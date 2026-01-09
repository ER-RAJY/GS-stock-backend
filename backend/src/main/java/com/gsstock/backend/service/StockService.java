package com.gsstock.backend.service;

import com.gsstock.backend.domain.stock.*;
import com.gsstock.backend.exception.ResourceNotFoundException;
import com.gsstock.backend.exception.StockInsufficientException;
import com.gsstock.backend.repository.ProduitRepository;
import com.gsstock.backend.repository.StockMovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class StockService {

    private final ProduitRepository produitRepository;
    private final StockMovementRepository stockMovementRepository;

    // ======================
    // ADD ENTREE
    // ======================
    public StockMovement addEntree(Long produitId, BigDecimal quantite, String commentaire) {
        Produit produit = produitRepository.findById(produitId)
                .orElseThrow(() -> new ResourceNotFoundException("Produit not found"));

        StockMovement mouvement = new StockMovement();
        mouvement.setProduit(produit);
        mouvement.setType(MovementType.ENTREE);
        mouvement.setQuantite(quantite);
        mouvement.setDate(LocalDate.now());
        mouvement.setCommentaire(commentaire);

        return stockMovementRepository.save(mouvement);
    }

    // ======================
    // ADD SORTIE
    // ======================
    public StockMovement addSortie(Long produitId, BigDecimal quantite, String commentaire) {
        Produit produit = produitRepository.findById(produitId)
                .orElseThrow(() -> new ResourceNotFoundException("Produit not found"));

        BigDecimal stockActuel = calculateStock(produitId);

        if (stockActuel.compareTo(quantite) < 0) {
            throw new StockInsufficientException();
        }

        StockMovement mouvement = new StockMovement();
        mouvement.setProduit(produit);
        mouvement.setType(MovementType.SORTIE);
        mouvement.setQuantite(quantite);
        mouvement.setDate(LocalDate.now());
        mouvement.setCommentaire(commentaire);

        return stockMovementRepository.save(mouvement);
    }

    // ======================
    // CALCULATE STOCK
    // ======================
    public BigDecimal calculateStock(Long produitId) {

        BigDecimal totalEntree = stockMovementRepository
                .sumQuantiteByProduitAndType(produitId, MovementType.ENTREE);

        BigDecimal totalSortie = stockMovementRepository
                .sumQuantiteByProduitAndType(produitId, MovementType.SORTIE);

        return totalEntree.subtract(totalSortie);
    }
}
