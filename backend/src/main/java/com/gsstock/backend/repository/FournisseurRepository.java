package com.gsstock.backend.repository;

import com.gsstock.backend.domain.purchase.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FournisseurRepository extends JpaRepository<Fournisseur,Long> {
}
