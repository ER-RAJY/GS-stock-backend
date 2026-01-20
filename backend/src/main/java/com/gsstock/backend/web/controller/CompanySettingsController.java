package com.gsstock.backend.web.controller;

import com.gsstock.backend.domain.common.CompanySettings;
import com.gsstock.backend.service.CompanySettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/settings/company")
@RequiredArgsConstructor
public class CompanySettingsController {

    private final CompanySettingsService service;

    // 🔹 Upload logo (ADMIN)
    @PostMapping("/logo")
    @PreAuthorize("hasRole('ADMIN')")
    public void uploadLogo(@RequestParam("file") MultipartFile file) {
        service.saveLogo(file);
    }

    // 🔹 Get logo (PUBLIC / PDF)
    @GetMapping("/logo")
    public ResponseEntity<byte[]> getLogo() {
        CompanySettings s = service.get();

        if (s.getLogo() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(s.getLogoContentType()))
                .body(s.getLogo());
    }
}
