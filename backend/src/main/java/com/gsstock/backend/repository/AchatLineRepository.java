package com.gsstock.backend.repository;

import com.gsstock.backend.domain.purchase.AchatLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AchatLineRepository extends JpaRepository<AchatLine, Long> {
    List<AchatLine> findByAchatId(Long achatId);

}
