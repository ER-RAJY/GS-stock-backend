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

import static com.gsstock.backend.service.pdf.XmlEscaper.esc;

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

            // Base URI for HTML (images, css if any)
            builder.withHtmlContent(html, "classpath:/pdf/");

            builder.toStream(out);
            builder.useFastMode();
            builder.useDefaultPageSize(210, 297, PdfRendererBuilder.PageSizeUnits.MM);

            builder.run();

            byte[] pdfBytes = out.toByteArray();
            System.out.println("PDF generated, size = " + pdfBytes.length);

            return pdfBytes;

        } catch (Exception e) {
            throw new RuntimeException("PDF generation failed", e);
        }
    }

    private String loadTemplate() throws Exception {
        return new String(
                new ClassPathResource("pdf/templates/achat.html")
                        .getInputStream()
                        .readAllBytes(),
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
                        esc(l.produit()),
                        esc(l.quantite().toString()),
                        esc(l.prixHT().toString()),
                        esc(l.totalHT().toString())
                ))
                .reduce("", String::concat);

        return html
                .replace("{{reference}}", esc(data.reference()))
                .replace("{{date}}", esc(data.date().toString()))
                .replace("{{fournisseur}}", esc(data.fournisseur()))
                .replace("{{lines}}", linesHtml)
                .replace("{{totalHT}}", esc(data.totalHT().toString()))
                .replace("{{totalTVA}}", esc(data.totalTVA().toString()))
                .replace("{{totalTTC}}", esc(data.totalTTC().toString()));
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
