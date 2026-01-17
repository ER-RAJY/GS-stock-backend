package com.gsstock.backend.service.pdf;

import com.gsstock.backend.domain.purchase.Achat;
import com.gsstock.backend.domain.purchase.AchatStatus;
import com.gsstock.backend.repository.AchatRepository;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class AchatPdfService {

    private final AchatRepository achatRepository;

    public byte[] generatePdf(Long achatId) {

        Achat achat = achatRepository.findById(achatId)
                .orElseThrow(() -> new RuntimeException("Achat not found"));

        if (achat.getStatus() != AchatStatus.VALIDATED) {
            throw new RuntimeException("Achat not validated");
        }

        AchatPdfData data = mapToPdfData(achat);

        try {
            String html = loadTemplate();
            html = fillTemplate(html, data);

            ByteArrayOutputStream out = new ByteArrayOutputStream();

            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withHtmlContent(html, null);
            builder.toStream(out);
            builder.run();

            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("PDF generation failed", e);
        }
    }

    private String loadTemplate() throws Exception {
        return new String(
                new ClassPathResource("pdf/templates/achat.html")
                        .getInputStream().readAllBytes(),
                StandardCharsets.UTF_8
        );
    }

    private String fillTemplate(String html, AchatPdfData data) {

        String linesHtml = data.lines().stream()
                .map(l -> """
                    <tr>
                        <td>%s</td>
                        <td>%s</td>
                        <td>%s</td>
                        <td>%s</td>
                    </tr>
                """.formatted(
                        l.produit(),
                        l.quantite(),
                        l.prixHT(),
                        l.totalHT()
                ))
                .reduce("", String::concat);

        return html
                .replace("{{reference}}", data.reference())
                .replace("{{date}}", data.date().toString())
                .replace("{{fournisseur}}", data.fournisseur())
                .replace("{{lines}}", linesHtml)
                .replace("{{totalHT}}", data.totalHT().toString())
                .replace("{{totalTVA}}", data.totalTVA().toString())
                .replace("{{totalTTC}}", data.totalTTC().toString());
    }

    private AchatPdfData mapToPdfData(Achat achat) {
        return new AchatPdfData(
                achat.getReferenceFacture(),
                achat.getDate(),
                achat.getFournisseur().getNom(),
                achat.getLines().stream()
                        .map(l -> new AchatPdfData.Line(
                                l.getProduit().getDesignation(),
                                l.getQuantite(),
                                l.getPrixUnitaireHT(),
                                l.getTotalHT()
                        ))
                        .toList(),
                achat.getTotalHT(),
                achat.getTotalTVA(),
                achat.getTotalTTC()
        );
    }
}
