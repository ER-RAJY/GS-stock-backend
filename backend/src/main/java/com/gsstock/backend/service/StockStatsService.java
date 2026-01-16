package com.gsstock.backend.service;

import com.gsstock.backend.repository.StockMovementRepository;
import com.gsstock.backend.web.dto.stats.StockConsommationDto;
import com.gsstock.backend.web.dto.stats.TopProduitDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.PageRequest;


@Service
@RequiredArgsConstructor
public class StockStatsService {

    private final StockMovementRepository stockMovementRepository;

    public List<StockConsommationDto> consommation(
            LocalDate from,
            LocalDate to
    ) {
        return stockMovementRepository.consommationStock(from, to)
                .stream()
                .map(r -> {
                    BigDecimal entree = (BigDecimal) r[2];
                    BigDecimal sortie = (BigDecimal) r[3];
                    BigDecimal stock = entree.subtract(sortie);

                    return new StockConsommationDto(
                            (Long) r[0],
                            (String) r[1],
                            entree,
                            sortie,
                            stock
                    );
                })
                .toList();
    }
    public List<TopProduitDto> topProduitsSortie(LocalDate from, LocalDate to, int limit) {

        int safeLimit = Math.max(1, Math.min(limit, 50));

        return stockMovementRepository
                .topSorties(from, to, PageRequest.of(0, safeLimit))
                .stream()
                .map(r -> new TopProduitDto(
                        (Long) r[0],
                        (String) r[1],
                        (BigDecimal) r[2]
                ))
                .toList();
    }
}
