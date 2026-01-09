package com.gsstock.backend.service;

import com.gsstock.backend.domain.stock.MovementType;
import com.gsstock.backend.domain.stock.StockMovement;
import com.gsstock.backend.exception.BusinessException;
import com.gsstock.backend.repository.StockMovementRepository;
import com.gsstock.backend.web.dto.stock.CreateMovementRequest;
import com.gsstock.backend.web.dto.stock.StockMovementDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StockMovementService {

    private final StockService stockService;
    private final StockMovementRepository stockMovementRepository;

    public StockMovementDto addEntree(CreateMovementRequest request) {
        StockMovement m = stockService.addEntree(
                request.produitId(),
                request.quantite(),
                request.commentaire()
        );
        return toDto(m);
    }

    public StockMovementDto addSortie(CreateMovementRequest request) {
        StockMovement m = stockService.addSortie(
                request.produitId(),
                request.quantite(),
                request.commentaire()
        );
        return toDto(m);
    }

    public List<StockMovementDto> findAll() {
        return stockMovementRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    private StockMovementDto toDto(StockMovement m) {
        return new StockMovementDto(
                m.getId(),
                m.getType().name(),
                m.getQuantite(),
                m.getDate(),
                m.getCommentaire(),
                m.getProduit().getId()
        );
    }
    public List<StockMovementDto> search(
            Long produitId,
            String type,
            LocalDate from,
            LocalDate to
    ) {
        MovementType movementType = null;

        if (type != null) {
            try {
                movementType = MovementType.valueOf(type.toUpperCase());
            } catch (IllegalArgumentException ex) {
                throw new BusinessException("Type de mouvement invalide: " + type);
            }
        }

        return stockMovementRepository
                .search(produitId, movementType, from, to)
                .stream()
                .map(this::toDto)
                .toList();
    }


}
