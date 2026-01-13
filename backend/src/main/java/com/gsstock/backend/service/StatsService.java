package com.gsstock.backend.service;

import com.gsstock.backend.repository.AchatRepository;
import com.gsstock.backend.web.dto.stats.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final AchatRepository achatRepository;

    public DepenseStatsDto depenses(LocalDate from, LocalDate to) {

        List<Object[]> result = achatRepository.sumDepenses(from, to);

        Object[] r = result.isEmpty()
                ? new Object[]{BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO}
                : result.get(0);

        return new DepenseStatsDto(
                (BigDecimal) r[0],
                (BigDecimal) r[1],
                (BigDecimal) r[2]
        );
    }


    public List<DepenseByFournisseurDto> depensesParFournisseur(
            LocalDate from,
            LocalDate to
    ) {
        return achatRepository.sumByFournisseur(from, to)
                .stream()
                .map(r -> new DepenseByFournisseurDto(
                        (Long) r[0],
                        (String) r[1],
                        (BigDecimal) r[2]
                ))
                .toList();
    }
}
