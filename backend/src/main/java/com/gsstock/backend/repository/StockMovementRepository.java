package com.gsstock.backend.repository;

import com.gsstock.backend.domain.stock.MovementType;
import com.gsstock.backend.domain.stock.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
    @Query("""
       select coalesce(sum(m.quantite), 0)
       from StockMovement m
       where m.produit.id = :produitId
       and m.type = :type
    """)
    BigDecimal sumQuantiteByProduitAndType(
            @Param("produitId") Long produitId,
            @Param("type") MovementType type
    );
    @Query("""
        select m from StockMovement m
        where (:produitId is null or m.produit.id = :produitId)
          and (:type is null or m.type = :type)
          and (:from is null or m.date >= :from)
          and (:to is null or m.date <= :to)
        order by m.date desc
    """)
    List<StockMovement> search(
            @Param("produitId") Long produitId,
            @Param("type") MovementType type,
            @Param("from") LocalDate from,
            @Param("to") LocalDate to
    );
}
