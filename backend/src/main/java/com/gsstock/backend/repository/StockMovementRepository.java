package com.gsstock.backend.repository;

import com.gsstock.backend.domain.stock.MovementType;
import com.gsstock.backend.domain.stock.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


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


    @Query("""
   select m.produit.id,
          m.produit.designation,
          sum(case when m.type = 'ENTREE' then m.quantite else 0 end),
          sum(case when m.type = 'SORTIE' then m.quantite else 0 end)
   from StockMovement m
   where (:from is null or m.date >= :from)
     and (:to is null or m.date <= :to)
   group by m.produit.id, m.produit.designation
""")
    List<Object[]> consommationStock(
            @Param("from") LocalDate from,
            @Param("to") LocalDate to
    );
    @Query("""
    select m from StockMovement m
    where (:produitId is null or m.produit.id = :produitId)
      and (:type is null or m.type = :type)
      and (:from is null or m.date >= :from)
      and (:to is null or m.date <= :to)
""")
    Page<StockMovement> search(
            @Param("produitId") Long produitId,
            @Param("type") MovementType type,
            @Param("from") LocalDate from,
            @Param("to") LocalDate to,
            Pageable pageable
    );
    @Query("""
    select m.produit.id,
           m.produit.designation,
           coalesce(sum(m.quantite), 0)
    from StockMovement m
    where m.type = 'SORTIE'
      and (:from is null or m.date >= :from)
      and (:to is null or m.date <= :to)
    group by m.produit.id, m.produit.designation
    order by coalesce(sum(m.quantite), 0) desc
""")
    List<Object[]> topSorties(
            @Param("from") LocalDate from,
            @Param("to") LocalDate to,
            Pageable pageable
    );

}
