package com.gsstock.backend.web.controller;

import com.gsstock.backend.service.ExcelExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/export")
@RequiredArgsConstructor
public class ExportController {

    private final ExcelExportService excelExportService;

    // ======================
    // EXPORT STOCK EXCEL
    // ======================
    @GetMapping("/stock/excel")
    public ResponseEntity<byte[]> exportStockExcel() {

        byte[] file = excelExportService.exportStock();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=stock.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(file);
    }

    // ======================
// EXPORT ACHATS EXCEL
// ======================
    @GetMapping("/achats/excel")
    public ResponseEntity<byte[]> exportAchatsExcel(
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to
    ) {
        byte[] file = excelExportService.exportAchats(from, to);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=achats.xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(file);
    }

}
