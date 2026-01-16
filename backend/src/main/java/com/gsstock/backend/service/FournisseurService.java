package com.gsstock.backend.service;

import com.gsstock.backend.domain.purchase.Fournisseur;
import com.gsstock.backend.repository.FournisseurRepository;
import com.gsstock.backend.web.dto.purchase.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FournisseurService {

    private final FournisseurRepository fournisseurRepository;

    public List<FournisseurDto> findAllActive() {
        return fournisseurRepository.findByActiveTrueOrderByNomAsc()
                .stream().map(this::toDto).toList();
    }

    public FournisseurDto getById(Long id) {
        Fournisseur f = fournisseurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fournisseur not found"));
        return toDto(f);
    }

    public List<FournisseurDto> search(String q) {
        return fournisseurRepository.searchActive(q)
                .stream().map(this::toDto).toList();
    }

    public FournisseurDto create(CreateFournisseurRequest req) {
        Fournisseur f = new Fournisseur();
        f.setNom(req.nom());
        f.setIce(req.ice());
        f.setTelephone(req.telephone());
        f.setEmail(req.email());
        f.setAdresse(req.adresse());
        f.setActive(true);

        return toDto(fournisseurRepository.save(f));
    }

    public FournisseurDto update(Long id, UpdateFournisseurRequest req) {
        Fournisseur f = fournisseurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fournisseur not found"));

        f.setNom(req.nom());
        f.setIce(req.ice());
        f.setTelephone(req.telephone());
        f.setEmail(req.email());
        f.setAdresse(req.adresse());
        if (req.active() != null) f.setActive(req.active());

        return toDto(fournisseurRepository.save(f));
    }

    // Soft delete
    public void delete(Long id) {
        Fournisseur f = fournisseurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fournisseur not found"));
        f.setActive(false);
        fournisseurRepository.save(f);
    }

    private FournisseurDto toDto(Fournisseur f) {
        return new FournisseurDto(
                f.getId(),
                f.getNom(),
                f.getIce(),
                f.getTelephone(),
                f.getEmail(),
                f.getAdresse(),
                f.isActive()
        );
    }
}
