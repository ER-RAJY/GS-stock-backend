package com.gsstock.backend.repository;

import com.gsstock.backend.domain.purchase.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FournisseurRepository extends JpaRepository<Fournisseur,Long> {
    List<Fournisseur> findByActiveTrueOrderByNomAsc();

    @Query("""
        select f from Fournisseur f
        where f.active = true
          and (:q is null or lower(f.nom) like lower(concat('%', :q, '%'))
               or lower(f.ice) like lower(concat('%', :q, '%'))
               or lower(f.telephone) like lower(concat('%', :q, '%'))
               or lower(f.email) like lower(concat('%', :q, '%')))
        order by f.nom asc
    """)
    List<Fournisseur> searchActive(@Param("q") String q);
}
