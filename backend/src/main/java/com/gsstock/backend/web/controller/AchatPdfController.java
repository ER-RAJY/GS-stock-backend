package com.gsstock.backend.web.controller;

import com.gsstock.backend.service.pdf.AchatPdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/achats")
@RequiredArgsConstructor
public class AchatPdfController {

    private final AchatPdfService achatPdfService;

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable Long id) {

        byte[] pdf = achatPdfService.generatePdf(id);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=achat_" + id + ".pdf"
                )
                .body(pdf);
    }
}
