package com.gsstock.backend.web.controller;

import com.gsstock.backend.service.StockStatsService;
import com.gsstock.backend.web.dto.stats.StockConsommationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/stats/stock")
@RequiredArgsConstructor
public class StockStatsController {

    private final StockStatsService stockStatsService;

    @GetMapping("/consommation")
    public List<StockConsommationDto> consommation(
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to
    ) {
        return stockStatsService.consommation(from, to);
    }
}
