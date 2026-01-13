package com.gsstock.backend.service;

import com.gsstock.backend.repository.StockMovementRepository;
import com.gsstock.backend.web.dto.stats.StockConsommationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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
}
