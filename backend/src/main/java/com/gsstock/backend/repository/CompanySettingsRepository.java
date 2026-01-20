package com.gsstock.backend.repository;

import com.gsstock.backend.domain.common.CompanySettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanySettingsRepository
        extends JpaRepository<CompanySettings, Long> {
}
