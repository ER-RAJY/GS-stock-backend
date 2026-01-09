package com.gsstock.backend.service;

import com.gsstock.backend.domain.purchase.Fournisseur;
import com.gsstock.backend.repository.FournisseurRepository;
import com.gsstock.backend.web.dto.purchase.FournisseurDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FournisseurService {

    private final FournisseurRepository fournisseurRepository;

    public List<FournisseurDto> findAll() {
        return fournisseurRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    private FournisseurDto toDto(Fournisseur f) {
        return new FournisseurDto(
                f.getId(),
                f.getNom(),
                f.getIce(),
                f.getPhone(),
                f.getAddress(),
                f.isActive()
        );
    }
}
