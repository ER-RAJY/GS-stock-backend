package com.gsstock.backend.service;

import com.gsstock.backend.domain.purchase.*;
import com.gsstock.backend.domain.stock.Produit;
import com.gsstock.backend.repository.*;
import com.gsstock.backend.web.dto.purchase.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AchatService {

    private final AchatRepository achatRepository;
    private final AchatLineRepository achatLineRepository;
    private final FournisseurRepository fournisseurRepository;
    private final ProduitRepository produitRepository;

    public AchatDto create(CreateAchatRequest request) {

        Fournisseur fournisseur = fournisseurRepository.findById(request.fournisseurId())
                .orElseThrow(() -> new RuntimeException("Fournisseur not found"));

        Achat achat = new Achat();
        achat.setReferenceFacture(request.referenceFacture());
        achat.setDate(request.date());
        achat.setTvaRate(request.tvaRate());
        achat.setStatus(AchatStatus.DRAFT);
        achat.setFournisseur(fournisseur);

        return toDto(achatRepository.save(achat));
    }

    public AchatDto addLine(Long achatId, CreateAchatLineRequest request) {

        Achat achat = achatRepository.findById(achatId)
                .orElseThrow(() -> new RuntimeException("Achat not found"));

        Produit produit = produitRepository.findById(request.produitId())
                .orElseThrow(() -> new RuntimeException("Produit not found"));

        AchatLine line = new AchatLine();
        line.setAchat(achat);
        line.setProduit(produit);
        line.setQuantite(request.quantite());
        line.setPrixUnitaireHT(request.prixUnitaireHT());

        BigDecimal totalHT = request.quantite().multiply(request.prixUnitaireHT());
        BigDecimal totalTVA = totalHT.multiply(achat.getTvaRate()).divide(BigDecimal.valueOf(100));
        BigDecimal totalTTC = totalHT.add(totalTVA);

        line.setTotalHT(totalHT);
        line.setTotalTVA(totalTVA);
        line.setTotalTTC(totalTTC);

        achatLineRepository.save(line);

        return toDto(achat);
    }

    public AchatDto validate(Long achatId) {

        Achat achat = achatRepository.findById(achatId)
                .orElseThrow(() -> new RuntimeException("Achat not found"));

        List<AchatLine> lines = achatLineRepository.findByAchatId(achatId);

        BigDecimal totalHT = lines.stream()
                .map(AchatLine::getTotalHT)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalTVA = lines.stream()
                .map(AchatLine::getTotalTVA)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        achat.setTotalHT(totalHT);
        achat.setTotalTVA(totalTVA);
        achat.setTotalTTC(totalHT.add(totalTVA));
        achat.setStatus(AchatStatus.VALIDATED);

        return toDto(achat);
    }

    private AchatDto toDto(Achat achat) {
        List<AchatLineDto> lines = achat.getLines() == null ? List.of() :
                achat.getLines().stream().map(this::toLineDto).toList();

        return new AchatDto(
                achat.getId(),
                achat.getReferenceFacture(),
                achat.getDate(),
                achat.getTvaRate(),
                achat.getStatus().name(),
                achat.getTotalHT(),
                achat.getTotalTVA(),
                achat.getTotalTTC(),
                achat.getFournisseur().getId(),
                lines
        );
    }

    private AchatLineDto toLineDto(AchatLine l) {
        return new AchatLineDto(
                l.getId(),
                l.getProduit().getId(),
                l.getQuantite(),
                l.getPrixUnitaireHT(),
                l.getTotalHT(),
                l.getTotalTVA(),
                l.getTotalTTC()
        );
    }
    public List<AchatDto> findAll() {
        return achatRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

}
