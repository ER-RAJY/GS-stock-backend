package com.gsstock.backend.repository;

import com.gsstock.backend.domain.purchase.Achat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AchatRepository extends JpaRepository<Achat, Long> {

}
