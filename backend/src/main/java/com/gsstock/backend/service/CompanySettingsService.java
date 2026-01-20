package com.gsstock.backend.service;

import com.gsstock.backend.domain.common.CompanySettings;
import com.gsstock.backend.repository.CompanySettingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CompanySettingsService {

    private final CompanySettingsRepository repository;

    public CompanySettings get() {
        return repository.findById(1L)
                .orElseGet(() -> {
                    CompanySettings s = new CompanySettings();
                    s.setCompanyName("My Company");
                    return repository.save(s);
                });
    }

    public void saveLogo(MultipartFile file) {
        try {
            CompanySettings settings = get();
            settings.setLogo(file.getBytes());
            settings.setLogoContentType(file.getContentType());
            repository.save(settings);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save logo", e);
        }
    }
}
