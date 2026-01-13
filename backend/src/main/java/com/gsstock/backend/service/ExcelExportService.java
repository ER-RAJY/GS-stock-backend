package com.gsstock.backend.service;

import com.gsstock.backend.domain.purchase.Achat;
import com.gsstock.backend.repository.AchatRepository;
import com.gsstock.backend.web.dto.stock.ProduitDto;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelExportService {

    private final ProduitService produitService;

    private final AchatRepository achatRepository;

    // ======================
    // EXPORT STOCK
    // ======================
    public byte[] exportStock() {

        List<ProduitDto> produits = produitService.findAll();

        try (Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("Stock");

            // Header
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Produit");
            header.createCell(1).setCellValue("Stock actuel");
            header.createCell(2).setCellValue("Stock minimum");
            header.createCell(3).setCellValue("Alert");

            int rowIdx = 1;

            for (ProduitDto p : produits) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(p.designation());
                row.createCell(1).setCellValue(toDouble(p.stockActuel()));
                row.createCell(2).setCellValue(toDouble(p.stockMin()));
                row.createCell(3).setCellValue(p.stockAlert() ? "OUI" : "NON");
            }

            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);

            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Excel export failed", e);
        }
    }

    // ======================
    // EXPORT ACHATS
    // ======================
    public byte[] exportAchats(LocalDate from, LocalDate to) {

        List<Achat> achats = achatRepository.findValidatedBetween(from, to);

        try (Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("Achats");

            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Référence");
            header.createCell(1).setCellValue("Fournisseur");
            header.createCell(2).setCellValue("Date");
            header.createCell(3).setCellValue("Total HT");
            header.createCell(4).setCellValue("Total TVA");
            header.createCell(5).setCellValue("Total TTC");

            int rowIdx = 1;
            for (Achat a : achats) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(a.getReferenceFacture());
                row.createCell(1).setCellValue(a.getFournisseur().getNom());
                row.createCell(2).setCellValue(a.getDate().toString());
                row.createCell(3).setCellValue(toDouble(a.getTotalHT()));
                row.createCell(4).setCellValue(toDouble(a.getTotalTVA()));
                row.createCell(5).setCellValue(toDouble(a.getTotalTTC()));
            }

            for (int i = 0; i <= 5; i++) sheet.autoSizeColumn(i);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Excel export achats failed", e);
        }
    }

    private double toDouble(BigDecimal v) {
        return v == null ? 0 : v.doubleValue();
    }

}
