package com.gsstock.backend.repository;

import com.gsstock.backend.domain.stock.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitRepository extends JpaRepository<Produit, Long> {

}
