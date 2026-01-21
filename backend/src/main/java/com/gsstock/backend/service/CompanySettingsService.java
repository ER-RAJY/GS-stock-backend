package com.gsstock.backend.service;

import com.gsstock.backend.domain.common.CompanySettings;
import com.gsstock.backend.repository.CompanySettingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CompanySettingsService {

    private final CompanySettingsRepository repository;

    // 🔹 GET (auto-create if not exists)
    @Transactional
    public CompanySettings get() {

        return repository.findById(1L)
                .orElseGet(() -> {
                    CompanySettings cs = new CompanySettings();
                    cs.setId(1L);
                    cs.setCompanyName("Company Name");
                    cs.setIce("");
                    cs.setAddress("");
                    cs.setPhone("");
                    cs.setEmail("");
                    cs.setLogo(null);
                    cs.setLogoContentType(null);
                    return repository.save(cs);
                });
    }

    // 🔹 Upload logo (ADMIN)
    @Transactional
    public void updateLogo(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Empty logo file");
        }

        try {
            // ⚠️ مهم: استعمل get() ماشي findById
            CompanySettings cs = get();

            cs.setLogo(file.getBytes());
            cs.setLogoContentType(file.getContentType());

            repository.save(cs);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to save logo", e);
        }
    }
}
