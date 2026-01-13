package com.gsstock.backend.web.controller;

import com.gsstock.backend.service.StatsService;
import com.gsstock.backend.web.dto.stats.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsService;

    // ======================
    // DEPENSES GLOBALES
    // ======================
    @GetMapping("/depenses")
    public DepenseStatsDto depenses(
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to
    ) {
        return statsService.depenses(from, to);
    }

    // ======================
    // DEPENSES PAR FOURNISSEUR
    // ======================
    @GetMapping("/depenses/fournisseurs")
    public List<DepenseByFournisseurDto> depensesParFournisseur(
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to
    ) {
        return statsService.depensesParFournisseur(from, to);
    }
}
