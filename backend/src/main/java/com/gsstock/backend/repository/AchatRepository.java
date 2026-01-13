package com.gsstock.backend.repository;

import com.gsstock.backend.domain.purchase.Achat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AchatRepository extends JpaRepository<Achat, Long> {

    @Query("""
       select coalesce(sum(a.totalHT), 0),
              coalesce(sum(a.totalTVA), 0),
              coalesce(sum(a.totalTTC), 0)
       from Achat a
       where a.status = 'VALIDATED'
         and (:from is null or a.date >= :from)
         and (:to is null or a.date <= :to)
    """)
    List<Object[]> sumDepenses(
            @Param("from") LocalDate from,
            @Param("to") LocalDate to
    );

    @Query("""
       select a.fournisseur.id,
              a.fournisseur.nom,
              coalesce(sum(a.totalTTC), 0)
       from Achat a
       where a.status = 'VALIDATED'
         and (:from is null or a.date >= :from)
         and (:to is null or a.date <= :to)
       group by a.fournisseur.id, a.fournisseur.nom
    """)
    List<Object[]> sumByFournisseur(
            @Param("from") LocalDate from,
            @Param("to") LocalDate to
    );
    @Query("""
   select a from Achat a
   where a.status = 'VALIDATED'
     and (:from is null or a.date >= :from)
     and (:to is null or a.date <= :to)
   order by a.date desc
""")
    List<Achat> findValidatedBetween(
            @Param("from") LocalDate from,
            @Param("to") LocalDate to
    );

}
